package com.ichmed.roguegalaxy.entity.ai.shotpatterns;

import java.util.*;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.entity.Entity;

public class ShotpatternDouble extends Shotpattern
{

	@Override
	public List<Entity> perform(List<Entity> list, int iteration, Object... args)
	{
		List<Entity> l = new ArrayList<Entity>();
		for(Entity e : list)
		{
			Entity a = e.clone();
			Entity b = e.clone();
			Vector2f v1 = (Vector2f) e.getVelocity().normalise();
			double d = Math.acos(v1.x);
			if(v1.y < 0) d = 2 * Math.PI - d;
			d += Math.PI / 2d;
			float distance = 10 * iteration;
			Vector2f v = new Vector2f((float)Math.cos(d) * distance, (float)Math.sin(d) * distance);
			a.setPosition(new Vector2f(a.getPosition().x + v.x, a.getPosition().y + v.y));
			b.setPosition(new Vector2f(b.getPosition().x - v.x, b.getPosition().y - v.y));
			l.add(a);
			l.add(b);
		}
		return l;
	}
}
