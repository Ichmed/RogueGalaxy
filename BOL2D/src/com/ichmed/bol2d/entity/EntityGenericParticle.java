package com.ichmed.bol2d.entity;

import org.lwjgl.util.vector.Vector2f;

public class EntityGenericParticle extends Entity
{

	public EntityGenericParticle()
	{
		this.lifespan = 20 + (int)(Math.random() * 50);
		this.isSolid = false;
		this.spawnDebrisOnDeath = false;
		this.rotateToMovement = false;
		this.type = EntityType.PARTICLE;
	}
	
	@Override
	public Vector2f getInitialSize()
	{
		return new Vector2f(5, 5);
	}

	@Override
	public void performCleanup()
	{
		this.kill();
	}
}
