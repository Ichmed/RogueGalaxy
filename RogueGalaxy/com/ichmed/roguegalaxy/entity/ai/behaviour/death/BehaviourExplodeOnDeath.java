package com.ichmed.roguegalaxy.entity.ai.behaviour.death;

import com.ichmed.bol2d.entity.Entity;
import com.ichmed.bol2d.entity.ai.behaviour.BehaviourDeath;
import com.ichmed.bol2d.world.World;
import com.ichmed.roguegalaxy.RogueGalaxy;
import com.ichmed.roguegalaxy.entity.misc.Explosion;

public class BehaviourExplodeOnDeath extends BehaviourDeath
{
	float damage, range, speed, width;

	public BehaviourExplodeOnDeath(float damage, float range, float speed, float width)
	{
		super();
		this.damage = damage;
		this.range = range;
		this.speed = speed;
		this.width = width;
	}

	@Override
	public boolean perform(Entity entity, Entity target)
	{
		World w = RogueGalaxy.getGameWorld();
		Entity e = new Explosion(damage, range, speed, width);
		e.setCenter(entity.getCenter());
		w.spawn(e);
		return true;
	}
}
