package com.ichmed.bol2d.util.render;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

public class Texture
{
	public static Texture makeTexture(String path, String format) throws Exception
	{
		return makeTexture(ImageIO.read(new File(path)));
	}

	public static Texture makeTexture(BufferedImage bfrdImg)
	{
		byte[] imgData = new byte[bfrdImg.getWidth() * bfrdImg.getHeight() * 4];
		for (int i = 0; i < imgData.length; i += 4)
		{
			Color color = new Color(bfrdImg.getRGB(bfrdImg.getWidth() - 1 - (i / 4) % bfrdImg.getWidth(), bfrdImg.getHeight() - 1 - (i / 4) / bfrdImg.getWidth()), true);
			imgData[i] = (byte) (color.getRed());
			imgData[i + 1] = (byte) (color.getGreen());
			imgData[i + 2] = (byte) (color.getBlue());
			imgData[i + 3] = (byte) (color.getAlpha());
		}
		return new Texture(imgData, bfrdImg.getWidth(), bfrdImg.getHeight());
	}

	public Texture(byte[] data, int width, int height)
	{
		this.ID = glGenTextures();
		try
		{
			ByteBuffer byteBuf = BufferUtils.createByteBuffer(data.length);
			byteBuf.put(data);
			byteBuf.flip();

			glBindTexture(GL_TEXTURE_2D, ID); // Bind texture ID

			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
			//
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuf);
			// // Setup wrap mode
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

			// Setup texture scaling filtering
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

			// Send texel data to OpenGL

			// Return the texture ID so we can bind it later again

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private final int ID;

	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, ID);
	}
}
