package com.ichmed.roguegalaxy.entity.misc;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.entity.*;
import com.ichmed.bol2d.entity.damage.HealthSystemIndestructible;
import com.ichmed.bol2d.util.MathUtil;
import com.ichmed.bol2d.world.World;
import com.ichmed.roguegalaxy.RogueGalaxy;

public class Explosion extends Entity
{
	float damage, range, width;

	public Explosion(float damage, float range, float speed, float width)
	{
		super();
		this.damage = damage;
		this.range = range;
		this.healthSystem = new HealthSystemIndestructible();
		this.speed = speed;
		this.width = width;
		this.isSolid = false;
		this.isInmoveable = true;
		this.spawnDebrisOnDeath = false;
		this.lifespan = (int) ((float) range / (float) speed);
		this.textureName = "explosion1";
		this.debrisTexture = "explosion1";
		this.debrisSize = 0;
		this.debrisFactor = .01f;
	}

	@Override
	protected Entity modDebris(Entity debris)
	{
		Entity e = new Entity()
		{
			@Override
			public Vector2f getInitialSize()
			{
				return new Vector2f();
			}

			@Override
			public void onUpdate()
			{
				Vector2f oldCenter = this.getCenter();
				this.size.x = this.size.y = this.getTicksExisted() * 2;
				this.setCenter(oldCenter);
				super.onUpdate();
			}

			@Override
			public EntityType getType()
			{
				return EntityType.PARTICLE;
			}
		};
		e.lifespan = 50 + (int) (20 * Math.random());
		e.spawnDebrisOnDeath = false;
		e.healthSystem = new HealthSystemIndestructible();
		e.isSolid = false;
		e.isInmoveable = true;
		return e;
	}

	@Override
	public void onSpawn()
	{
		this.size = new Vector2f(this.range / 2, this.range / 2);
		this.move(new Vector2f(-this.range / 4, -this.range / 2));
		spawnDebris();
		this.move(new Vector2f(this.range / 4, this.range / 4));
		this.size = new Vector2f();
		super.onSpawn();
	}

	@Override
	public void onUpdate()
	{
		if (this.getTicksExisted() == 10)
		{
			spawnDebris();
		}

		World w = RogueGalaxy.getGameWorld();
		List<Entity> l = w.getOverlappingEntities(this, EntityType.ALL);
		Vector2f oldCenter = this.getCenter();
		this.size.x = this.size.y = this.getTicksExisted() * this.speed;
		this.setCenter(oldCenter);
		for (Entity e : l)
		{
			Vector2f v = Vector2f.sub(e.getCenter(), this.getCenter(), null);
			if (this.width == -1 || MathUtil.compareVectorToLength(v, this.range - this.width) < 0)
			{
				if (v.length() == 0) continue;
				Vector2f speedVector = (Vector2f) v.normalise();
				e.accelerate(new Vector2f(speedVector.x * this.speed, speedVector.y * this.speed));
				if (e.isSolid) e.hurt(this.damage);
			}
		}
		super.onUpdate();
	}

	@Override
	public Vector2f getInitialSize()
	{
		return new Vector2f();
	}

	@Override
	public EntityType getType()
	{
		return EntityType.MISC;
	}

}
