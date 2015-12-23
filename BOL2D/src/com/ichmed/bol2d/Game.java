package com.ichmed.bol2d;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.DoubleBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.entity.Entity;
import com.ichmed.bol2d.util.render.TextureLibrary;
import com.ichmed.bol2d.world.World;

public abstract class Game
{
	private static Game currentGame;

	public static void startNewGame(Game game, World world)
	{
		Game.currentGame = game;
		Game.currentGame.gameWorld = world;
		game.run();
	}

	private static HashMap<Integer, Boolean> isKeyDown = new HashMap<Integer, Boolean>();
	private static HashMap<Integer, Boolean> wasKeyDown = new HashMap<Integer, Boolean>();
	private static HashMap<Integer, Boolean> isButtonDown = new HashMap<Integer, Boolean>();
	private static HashMap<Integer, Boolean> wasButtonDown = new HashMap<Integer, Boolean>();

	
	private static int fps;
	
	
	public static int getFps()
	{
		return fps;
	}

	private static Vector2f cursorPos;

	static final int TICKS_PER_SECOND = 60;
	private static int ticksThisSecond;
	public static int getTicksThisSecond()
	{
		return ticksThisSecond;
	}

	private int ticksTotal;
	static long lastSecond = System.currentTimeMillis();

	int WIDTH = 800;
	int HEIGHT = 800;

	// We need to strongly reference callback instances.
	private GLFWErrorCallback errorCallback;

	private GLFWKeyCallback keyCallback;
	private GLFWMouseButtonCallback mouseCallback;

	// The window handle
	private long window;

	public static Game getCurrentGame()
	{
		return currentGame;
	}

	public static World getGameWorld()
	{
		return currentGame.getCurrentGameWorld();
	}

	private World gameWorld;

	protected World getCurrentGameWorld()
	{
		return gameWorld;
	}

	public static int getTicksTotal()
	{
		return currentGame.ticksTotal;
	}

	public static boolean isKeyDown(int key)
	{
		Boolean b = isKeyDown.get(key);
		return b == null ? false : b;
	}

	public static boolean isButtonDown(int key)
	{
		Boolean b = isButtonDown.get(key);
		return b == null ? false : b;
	}

	public static Vector2f getCursorPosition()
	{
		return new Vector2f(cursorPos);
	}

	public static boolean wasKeyPressed(int key)
	{
		Boolean was = wasKeyDown.get(key);
		Boolean is = isKeyDown(key);

		if (was == null || is == null) return false;
		return was == false && is == true;
	}

	public static boolean wasKeyReleased(int key)
	{
		Boolean was = wasKeyDown.get(key);
		Boolean is = isKeyDown(key);

		if (was == null || is == null) return false;
		return was == true && is == false;
	}

	public static void updateKeyMaps()
	{
		wasKeyDown = new HashMap<Integer, Boolean>(isKeyDown);
	}

	private void init()
	{
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (glfwInit() != GLFW_TRUE) throw new IllegalStateException("Unable to initialize GLFW");

		// Configure our window
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		// Create the window
		window = glfwCreateWindow(WIDTH, HEIGHT, "Rogue Galaxy", NULL, NULL);
		if (window == NULL) throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed,
		// repeated or released.
		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback()
		{
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods)
			{
				if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(window, GLFW_TRUE);
				if (action == GLFW_PRESS || action == GLFW_REPEAT) isKeyDown.put(key, true);
				if (action == GLFW_RELEASE) isKeyDown.put(key, false);

			}
		});

		glfwSetMouseButtonCallback(window, mouseCallback = new GLFWMouseButtonCallback()
		{

			@Override
			public void invoke(long window, int button, int action, int mods)
			{
				if (action == GLFW_PRESS || action == GLFW_REPEAT) isButtonDown.put(button, true);
				if (action == GLFW_RELEASE) isButtonDown.put(button, false);
			}
		});

		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Center our window
		glfwSetWindowPos(window, (vidmode.width() - WIDTH) / 2, (vidmode.height() - HEIGHT) / 2);

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}

	public void run()
	{
		try
		{
			init();
			loop();

			// Release window and window callbacks
			glfwDestroyWindow(window);
			keyCallback.release();
		} finally
		{
			// Terminate GLFW and release the GLFWErrorCallback
			glfwTerminate();
			errorCallback.release();
		}
	}

	private void loop()
	{
		GL.createCapabilities();
		glClearColor(.004f, 0.0f, 0.1f, 0.0f);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		try
		{
			TextureLibrary.init("resc/texture/", shouldShowTextureStiching());
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		long lastMillis = System.currentTimeMillis();
		while (glfwWindowShouldClose(window) == GLFW_FALSE)
		{
			DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
			DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
			glfwGetCursorPos(window, x, y);
			cursorPos = new Vector2f((float) (x.get() - WIDTH / 2) / (WIDTH / 2), -(float) (y.get() - HEIGHT / 2) / (HEIGHT / 2));

			if (ticksThisSecond < TICKS_PER_SECOND)
			{
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				gameWorld.update();
				glfwSwapBuffers(window);
				glfwPollEvents();
				ticksThisSecond++;
				ticksTotal++;
				long m = System.currentTimeMillis();
				long n = lastMillis + (1000 / 60);
				long l = n - m;
				if (l > 0) try
				{
					Thread.sleep(l);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				lastMillis = System.currentTimeMillis();
			} else
			{
				if (lastSecond + 1000L < System.currentTimeMillis())
				{
					for (Entity e : gameWorld.getCurrentEntities())
						e.performCleanup();

				} else
				{
					try
					{
						Thread.sleep(lastSecond + 1000L - System.currentTimeMillis());
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
			if (System.currentTimeMillis() >= lastSecond + 1000)
			{
				lastSecond = System.currentTimeMillis();
				fps = ticksThisSecond;
				ticksThisSecond = 0;
			}
		}

		TextureLibrary.cleanUp();
	}

	public abstract boolean shouldShowTextureStiching();

	public abstract float getRotationUpdateInterval();

	public abstract int getParticleMax();

	public abstract float getParticleRate();

	public abstract int getMaxTexLibSize();
}
