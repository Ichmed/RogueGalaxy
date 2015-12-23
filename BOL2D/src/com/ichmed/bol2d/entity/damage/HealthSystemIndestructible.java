package com.ichmed.bol2d.entity.damage;

public class HealthSystemIndestructible extends HealthSystem
{

	@Override
	public float hurt(float damage, DamageType dmgType)
	{
		return 0;
	}

	@Override
	public float getHealth()
	{
		return 0;
	}

	@Override
	public boolean shouldDie()
	{
		return false;
	}

}
