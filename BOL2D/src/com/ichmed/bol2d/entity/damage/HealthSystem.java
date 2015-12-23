package com.ichmed.bol2d.entity.damage;

public abstract class HealthSystem
{
	public abstract float hurt(float damage, DamageType dmgType);
	public abstract float getHealth();
	public abstract boolean shouldDie();
	public void update()
	{
	}
}
