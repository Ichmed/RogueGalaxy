package com.ichmed.roguegalaxy.entity;

import com.ichmed.bol2d.entity.damage.*;

public class HealthSystemPlayer extends HealthSystem
{
	public float shield;
	float hull;
	int damageCooldown;

	public HealthSystemPlayer(float shield, float hull)
	{
		super();
		this.shield = shield;
		this.hull = hull;
	}

	@Override
	public float hurt(float damage, DamageType dmgType)
	{
		damageCooldown = 25;
		if (this.shield > 0)
		{
			shield -= damage;
			return damage;
		} else
		{
			this.hull -= damage * 0.01f;
			return damage;
		}
	}

	@Override
	public void update()
	{
		this.damageCooldown--;
		if (damageCooldown <= 0)
		{
			this.shield = Math.min(this.shield + 0.2f, 20);
		}
		super.update();
	}

	@Override
	public float getHealth()
	{
		return hull * 100;
	}

	@Override
	public boolean shouldDie()
	{
		return hull <= 0;
	}

	public String gealthString()
	{
		return (int) (hull * 100) + "%";
	}
}
