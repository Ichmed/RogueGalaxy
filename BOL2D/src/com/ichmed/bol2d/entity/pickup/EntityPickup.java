package com.ichmed.bol2d.entity.pickup;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.entity.*;

public abstract class EntityPickup extends Entity
{

	@Override
	public Vector2f getInitialSize()
	{
		return new Vector2f(20, 20);
	}

	@Override
	public void onUpdate()
	{
		for (Entity e : Game.getGameWorld().getOverlappingEntities(this))
			if (canPickUp(e))
			{
				pickUp(e);
				this.kill();
			}
		super.onUpdate();
	}

	public abstract void pickUp(Entity e);

	public boolean canPickUp(Entity e)
	{
		if (e.getType() == EntityType.PARTICLE || e.getType() == EntityType.PICKUP) return false;
		return true;

	}
}
