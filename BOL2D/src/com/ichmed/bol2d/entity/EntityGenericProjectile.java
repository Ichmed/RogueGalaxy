package com.ichmed.bol2d.entity;

import org.lwjgl.util.vector.*;

public class EntityGenericProjectile extends Entity
{
	protected float damage = 5;

	public EntityGenericProjectile()
	{
		this.speed = 20;
		this.isSolid = false;
		this.layer = 2;
	}
	
	@Override
	public Vector2f getInitialSize()
	{
		return new Vector2f(40, 40);
	}
}
