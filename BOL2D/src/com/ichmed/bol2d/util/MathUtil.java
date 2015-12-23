package com.ichmed.bol2d.util;

import org.lwjgl.util.vector.Vector2f;


public class MathUtil
{
	public static boolean isPointInArea(Vector2f point, Vector2f areaPos, Vector2f areaSize)
	{
		return point.x > areaPos.x && point.x < areaPos.x + areaSize.x && point.y > areaPos.y && point.y < areaPos.y + areaSize.y;
	}

	public static float compareVectorToVector(Vector2f a, Vector2f b)
	{
		return ((a.x * a.x) + (a.y * a.y)) - ((b.x * b.x) + (b.y * b.y));
	}

	public static float compareVectorToLength(Vector2f a, float length)
	{
		return ((a.x * a.x) + (a.y * a.y)) - length * length;
	}
	
	public static boolean positionsInRange(Vector2f posA, Vector2f posB, float range)
	{
		Vector2f v = new Vector2f();
		Vector2f.sub(posA, posB, v);
		return compareVectorToLength(v, range) <= 0;
	}

	public static double getRotationForDirection(Vector2f direction)
	{
		if (direction.x == 0 && direction.y == 0) return -1;
		Vector2f dir = new Vector2f(direction);
		Vector2f v1 = (Vector2f) dir.normalise();
		double d = Math.acos(v1.x);
		if (v1.y < 0) d = 2 * Math.PI - d;
		d = Math.toDegrees(d);
		d += 270;
		return d;
	}

	public static boolean areVectorsEqual(Vector2f a, Vector2f b)
	{
		if (a == null || b == null) return false;
		return a.x == b.x && a.y == b.y;
	}

	public static boolean areVectorsEqual(Vector2f a, Vector2f b, float tolerance)
	{
		if (a == null || b == null) return false;
		return Math.abs(a.x / b.x - b.x / a.x) <= tolerance && Math.abs(a.y / b.y - b.y / a.y) <= tolerance;
	}
}
