package com.ichmed.roguegalaxy.entity.pickup;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.entity.Entity;
import com.ichmed.bol2d.entity.damage.HealthSystemIndestructible;
import com.ichmed.bol2d.entity.pickup.EntityPickup;
import com.ichmed.roguegalaxy.entity.ai.shotpatterns.ShotpatternDouble;
import com.ichmed.roguegalaxy.entity.player.EntityRGPlayer;

public class EntityDoubleLaser extends EntityPickup
{
	public EntityDoubleLaser()
	{
		this.textureName = "item_laser_double1";
		this.healthSystem = new HealthSystemIndestructible();
		this.isInmoveable = true;
	}

	@Override
	public Vector2f getInitialSize()
	{
		return new Vector2f(50, 50);
	}

	@Override
	public boolean canPickUp(Entity e)
	{
		if (!(e instanceof EntityRGPlayer)) return false;
		return super.canPickUp(e);
	}

	@Override
	public void pickUp(Entity e)
	{
		EntityRGPlayer p = (EntityRGPlayer) e;
		p.shotPatterns.add(new ShotpatternDouble());
	}
}
