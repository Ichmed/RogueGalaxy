package com.ichmed.bol2d.util.render;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector4f;

import com.ichmed.bol2d.Game;

public class TextureLibrary
{
	private static HashMap<String, Vector4f> textureCoordinates;
	private static Texture textureGL;
	private static BufferedImage textureAWT;
	private static int size;
	static boolean log;
	private static String libPath;

	public static void init(String path ,boolean showStiching) throws IOException
	{
		textureCoordinates = new HashMap<String, Vector4f>();
		log = showStiching;
		libPath = path;
		File lib = new File(path + "library.png");
		File raw = new File(path + "raw");
		if (needsRefreshing(lib, raw))
		{
			size = Game.getCurrentGame().getMaxTexLibSize();
			textureAWT = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = textureAWT.createGraphics();
			g2d.setColor(new Color(255, 0, 255));
			g2d.fillRect(0, 0, size, size);
			List<TextureData> l = new ArrayList<TextureData>();
			for (File f : raw.listFiles())
			{
				l.addAll(getTextureDataFromFile(f));
			}

			if (placeTexturesRecursiv(g2d, l) || placeTexturesRecursivSkipFirst(g2d, l))
			;
		} else
		{
			loadLibrary(path);
		}
		textureGL = Texture.makeTexture(textureAWT);
		textureGL.bind();
	}

	private static List<TextureData> getTextureDataFromFile(File f) throws IOException
	{
		List<TextureData> l = new ArrayList<TextureData>();
		if (f.getName().endsWith(".fontdata"))
		{
			Scanner sc = new Scanner(f);
			String s;
			while (sc.hasNextLine())
			{
				s = sc.nextLine();
				if(log)System.out.println("Generating " + s);
				int size;
				String nameAWT;
				String nameTexture;
				if (s.startsWith("font"))
				{
					nameAWT = s.split(" ")[1];
					nameTexture = s.split(" ")[2];
					size = Integer.valueOf(s.split(" ")[3]);
					l.addAll(generateFont(nameAWT, nameTexture, size));
				}
			}
			sc.close();
		} else
		{
			l.add(new TextureData(ImageIO.read(f), f.getName().split("\\.")[0]));
		}

		return l;
	}

	private static List<TextureData> generateFont(String nameAWT, String nameTexture, int fontSize)
	{
		ArrayList<TextureData> l = new ArrayList<TextureLibrary.TextureData>();
		for (char c = ' '; c < ' ' + 96; c++)
		{
			BufferedImage bfdImg = new BufferedImage(fontSize, fontSize, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = bfdImg.createGraphics();
			g2d.setFont(new Font(nameAWT.replace("_", " "), Font.BOLD, fontSize * 2 / 3));
			g2d.drawChars(new char[] { c }, 0, 1, 0, fontSize / 2);
			l.add(new TextureData(bfdImg, "letter_" + nameTexture + "_" + TextUtil.getNameForChar(c)));
		}
		return l;
	}

	public static void cleanUp()
	{
		try
		{
			ImageIO.write(textureAWT, "png", new File(libPath + "library1.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void loadLibrary(String path)
	{
	}

	private static boolean placeTexturesRecursivSkipFirst(Graphics2D g2d, List<TextureData> raw) throws IOException
	{
		if (raw.size() == 0) return true;
		if (raw.size() < 2) return false;
		if (tryToPlaceImage(g2d, raw.get(1)))
		{
			ArrayList<TextureData> l = new ArrayList<TextureData>(raw);
			l.remove(1);
			if (placeTexturesRecursiv(g2d, l) || placeTexturesRecursivSkipFirst(g2d, l)) return true;
			Vector4f v = getCoordinates(raw.get(1).name);
			removeImage(v, g2d);
		}
		return false;
	}

	private static boolean placeTexturesRecursiv(Graphics2D g2d, List<TextureData> raw) throws IOException
	{
		if (raw.size() == 0) return true;
		if (tryToPlaceImage(g2d, raw.get(0)))
		{
			ArrayList<TextureData> l = new ArrayList<TextureData>(raw);
			l.remove(0);
			if (placeTexturesRecursiv(g2d, l) || placeTexturesRecursivSkipFirst(g2d, l)) return true;
			Vector4f v = getCoordinates(raw.get(0).name);
			removeImage(v, g2d);
		}
		return false;
	}

	public static String getNameFromRawFile(File f)
	{
		return f.getName().split("\\.")[0];
	}

	private static void removeImage(Vector4f v, Graphics2D g2d)
	{
		g2d.drawRect((int) v.x, (int) v.y, (int) v.z, (int) v.w);
	}

	public static boolean tryToPlaceImage(Graphics2D g2d, TextureData data)
	{
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (spotIsEmpty(j, i, data.awtImg.getWidth(), data.awtImg.getHeight()))
				{
					int x = j;
					int y = i;
					if(log)System.out.println("Placed " + data.name);
					for (int k = 0; k < data.awtImg.getWidth(); k++)
						for (int l = 0; l < data.awtImg.getHeight(); l++)
						{
							textureAWT.setRGB(k + x, l + y, 0);
						}
					g2d.drawImage(data.awtImg, x, y, null);
					textureCoordinates.put(data.name, new Vector4f(x, y, data.awtImg.getWidth(), data.awtImg.getHeight()));
					return true;
				}
		return false;
	}

	private static boolean spotIsEmpty(int x, int y, int width, int height)
	{
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
			{
				if (!isPixelTransparent(x + i, y + j)) return false;
			}
		return true;
	}

	public static boolean isPixelTransparent(int x, int y)
	{
		if (x >= size || y >= size) return false;
		Color c = new Color(textureAWT.getRGB(x, y));
		return c.getRed() == 255 && c.getBlue() == 255;
	}

	private static boolean needsRefreshing(File lib, File raw)
	{
		for (File f : raw.listFiles())
			if (f.lastModified() > lib.lastModified()) return true;
		return false;
	}

	public static Vector4f getCoordinates(String textureName)
	{
		Vector4f v = new Vector4f(textureCoordinates.get(textureName));
		v.scale(1f / size);
		return v;
	}

	private static class TextureData
	{
		BufferedImage awtImg;
		String name;

		public TextureData(BufferedImage awtImg, String name)
		{
			super();
			this.awtImg = awtImg;
			this.name = name;
		}

	}
}
