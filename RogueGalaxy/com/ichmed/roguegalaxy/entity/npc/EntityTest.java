package com.ichmed.roguegalaxy.entity.npc;

import org.lwjgl.util.vector.*;

import com.ichmed.bol2d.entity.*;
import com.ichmed.bol2d.entity.ai.behaviour.target.*;
import com.ichmed.bol2d.entity.damage.*;
import com.ichmed.roguegalaxy.entity.ai.behaviour.impact.*;
import com.ichmed.roguegalaxy.entity.ai.behaviour.update.attack.BehaviourShootProjectileAtTarget;
import com.ichmed.roguegalaxy.entity.ai.behaviour.update.movement.BehaviourMoveTowardsTarget;

public class EntityTest extends Entity
{
	public static int dummyAmount;

	public EntityTest()
	{
		this.enemy = EntityType.PLAYER;
		// this.addBehaviour(new BehaviourHomingProjectile(1000, 0, 5));
		// this.addBehaviour(new BehaviourDieOnImpact());
		// this.addBehaviour(new BehaviourExplodeOnDeath(1, 200, 4, -1));
		this.addBehaviour(new BehaviourAquireTarget(new FilterPlayer(), TargetType.ATTACK));
		this.addBehaviour(new BehaviourMoveTowardsTarget(TargetType.ATTACK));
		Entity e = new MyProjectile();
		this.addBehaviour(new BehaviourShootProjectileAtTarget(e, 2000, 1200));
		this.textureName = "ship_skull";

		this.lifespan = -1;
		this.speed = 1;
	}

	public static class MyProjectile extends EntityGenericProjectile
	{
		public MyProjectile()
		{
			this.speed = 10;
			this.textureName = "laser1";
			this.enemy = EntityType.PLAYER;
			this.isInmoveable = true;
			this.color = new Vector3f(186 / 255f, 222 / 255f, 43 / 255f);
			this.addBehaviour(new BehaviourDieOnImpact());
			this.addBehaviour(new BehaviourDealDamageOnImpact(false, 1, DamageType.LASER));
		}

		@Override
		protected Entity modDebris(Entity debris)
		{
			debris.textureName = "laserParticle1";
			return super.modDebris(debris);
		}
	}
	
	

	@Override
	public void onUpdate()
	{
		super.onUpdate();
	}

	@Override
	public void onDeath()
	{
		dummyAmount--;
		// int i = (int) (Math.random() * 20);
		// Entity e = new
		// EntityPickupItem(RogueGalaxy.itemPools.get("DEFAULT").getRandom().create());
		// Entity e = new EntityLootContainer();
		// e.setCenter(this.getCenter());
		// Game.getGameWorld().spawn(e);
		super.onDeath();
	}

	@Override
	protected HealthSystem getHealthSystem(Float health)
	{
		return new HealthSystemIndestructible();
	}

	@Override
	public void onSpawn()
	{
		dummyAmount++;
		super.onSpawn();
	}

	@Override
	public Vector2f getInitialSize()
	{
		return new Vector2f(150, 150);
	}

	@Override
	public EntityType getType()
	{
		return EntityType.NPC;
	}
}
