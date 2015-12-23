package com.ichmed.bol2d.util.render;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.*;

import com.ichmed.bol2d.entity.Entity;
import com.ichmed.bol2d.util.MathUtil;

public class RenderUtil
{
	public static final Vector3f WHITE = new Vector3f(1, 1, 1);
	public static final Vector3f RED = new Vector3f(1, 0, 0);
	public static final Vector3f CYAN = new Vector3f(0, 1, 1);

	public static void drawTexturedRect(double x, double y, double width, double height, String name)
	{
		glEnable(GL_TEXTURE_2D);
		Vector4f v = TextureLibrary.getCoordinates(name);
		float x1 = 1 - v.x;
		float y1 = 1 - v.y;
		float x2 = 1 - (v.x + v.z);
		float y2 = 1 - (v.y + v.w);
		glBegin(GL_QUADS);
		{
			glTexCoord2f(x1, y2);
			glVertex2d(x, y);
			glTexCoord2f(x2, y2);
			glVertex2d(x + width, y);
			glTexCoord2f(x2, y1);
			glVertex2d(x + width, y + height);
			glTexCoord2f(x1, y1);
			glVertex2d(x, y + height);
		}
		glEnd();
	}

	public static void drawRect(double x, double y, double width, double height)
	{
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 0);
			glVertex2d(x, y);
			glTexCoord2f(1, 0);
			glVertex2d(x + width, y);
			glTexCoord2f(1, 1);
			glVertex2d(x + width, y + height);
			glTexCoord2f(0, 1);
			glVertex2d(x, y + height);
		}
		glEnd();
	}

	private static Entity lastEntityCentered;

	public static void centerViewOnEntity(Entity entity)
	{
		Vector2f v = lastEntityCentered == null ? new Vector2f() : lastEntityCentered.getCenter();
		glTranslatef((v.x - entity.getCenter().x) / 1000, (v.y - entity.getCenter().y) / 1000, 0);
		lastEntityCentered = entity;
	}

	public static void resetLastEntityCentered()
	{
		lastEntityCentered = null;
	}

	public static void setColor(Vector3f color)
	{
		setColor(color, 1);
	}

	public static void setColor(Vector3f color, float alpha)
	{
		glColor4f(color.x, color.y, color.z, alpha);
	}

	public static void pushMatrix()
	{
		glPushMatrix();
	}

	public static void popMatrix()
	{
		glPopMatrix();
	}

	public static void translate(Vector2f v)
	{
		glTranslatef(v.x, v.y, 0);
	}

	public static void rotateToDirection(Vector2f direction)
	{
		double d = MathUtil.getRotationForDirection(direction);
		glRotatef((float) d, 0, 0, 1);
	}

	public static void rotateByDegrees(float degrees)
	{
		glRotatef(degrees, 0, 0, 1);
	}
}
