package com.ichmed.bol2d.entity.damage;

public class HealthSystemDefault extends HealthSystem
{
	float health;

	public HealthSystemDefault(float health)
	{
		super();
		this.health = health;
	}

	@Override
	public float hurt(float damage, DamageType dmgType)
	{
		health -= damage;
		return damage;
	}

	@Override
	public float getHealth()
	{
		return health;
	}

	@Override
	public boolean shouldDie()
	{
		return health <= 0;
	}

}
