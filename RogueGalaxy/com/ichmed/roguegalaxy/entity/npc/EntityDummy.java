package com.ichmed.roguegalaxy.entity.npc;

import org.lwjgl.util.vector.*;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.entity.*;
import com.ichmed.bol2d.entity.damage.DamageType;
import com.ichmed.roguegalaxy.entity.ai.behaviour.impact.*;
import com.ichmed.roguegalaxy.entity.ai.behaviour.update.attack.BehaviourShootProjectileAtTarget;

public class EntityDummy extends Entity
{

	public EntityDummy()
	{
		this.textureName = "letter_default_Q";
		this.lifespan = Game.getFlag("DUMMYLIFETIME", 30);
		this.enemy = EntityType.PLAYER;
		Entity e = new MyProjectile();
		this.addBehaviour(new BehaviourShootProjectileAtTarget(e, 2000, 1200));
//		this.addBehaviour(new BehaviourExplodeOnDeath(0, 100, 1, -1));
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
	public Vector2f getInitialSize()
	{
		return new Vector2f(48, 48);
	}

	@Override
	public EntityType getType()
	{
		return EntityType.NPC;
	}
}
