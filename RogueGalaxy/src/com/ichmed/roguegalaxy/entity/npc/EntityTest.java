package com.ichmed.roguegalaxy.entity.npc;

import org.lwjgl.util.vector.*;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.entity.*;
import com.ichmed.bol2d.entity.damage.DamageType;
import com.ichmed.roguegalaxy.entity.ai.behaviour.death.BehaviourExplodeOnDeath;
import com.ichmed.roguegalaxy.entity.ai.behaviour.impact.*;
import com.ichmed.roguegalaxy.entity.ai.behaviour.update.attack.BehaviourShootProjectileAtTarget;
import com.ichmed.roguegalaxy.entity.ai.behaviour.update.movement.BehaviourMoveTowardsTarget;
import com.ichmed.roguegalaxy.entity.pickup.*;

public class EntityTest extends Entity
{
	public static int dummyAmount;

	public EntityTest()
	{
		this.enemy = EntityType.PLAYER;
		// this.addBehaviour(new BehaviourHomingProjectile(1000, 0, 5));
		this.addBehaviour(new BehaviourDieOnImpact());
		this.addBehaviour(new BehaviourExplodeOnDeath(1, 200, 4, -1));
		this.addBehaviour(new BehaviourMoveTowardsTarget(2000));
		Entity e = new MyProjectile();
		this.addBehaviour(new BehaviourShootProjectileAtTarget(e, 2000, 1200));
		this.textureName = "enemy2";

		this.lifespan = 600;
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
	public void onDeath()
	{
		dummyAmount--;
		int i = (int) (Math.random() * 20);
		Entity e = new EntityPickupScrap();
		if (i == 0) e = new EntityDoubleLaser();
		e.setCenter(this.getCenter());
		Game.getGameWorld().spawn(e);
		super.onDeath();
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
		return new Vector2f(75, 75);
	}

	@Override
	public EntityType getType()
	{
		return EntityType.NPC;
	}
}
