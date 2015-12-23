package com.ichmed.roguegalaxy.entity.ai.shotpatterns;

import java.util.*;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.entity.Entity;

public class ShotpatternCircular extends Shotpattern
{
	@Override
	public List<Entity> perform(List<Entity> list, int iteration, Object... args)
	{
		List<Entity> l = new ArrayList<Entity>();
		for (Entity e : list)
		{
			Vector2f v1 = (Vector2f) e.getVelocity().normalise();
			double d = Math.acos(v1.x);
			if (v1.y < 0) d = 2 * Math.PI - d;
			float distance = e.getSize().x * iteration;

			for (int i = 0; i < (int) args[0]; i++)
			{
				double f = d + 2d * i * Math.PI / ((double)(int)args[0]);
				Entity a = e.clone();
				Vector2f v = new Vector2f((float) Math.cos(f) * distance, (float) Math.sin(f) * distance);
				a.setPosition(new Vector2f(a.getPosition().x + v.x, a.getPosition().y + v.y));
				l.add(a);
			}
		}
		return l;
	}
}
