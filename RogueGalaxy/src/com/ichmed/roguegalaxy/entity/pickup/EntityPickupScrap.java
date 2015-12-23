package com.ichmed.roguegalaxy.entity.pickup;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.entity.*;
import com.ichmed.bol2d.entity.damage.HealthSystemIndestructible;
import com.ichmed.bol2d.entity.pickup.EntityPickup;
import com.ichmed.roguegalaxy.entity.ai.behaviour.cleanup.BehaviourRemoveOnCleanup;
import com.ichmed.roguegalaxy.entity.ai.behaviour.update.movement.BehaviourMoveTowardsTarget;

public class EntityPickupScrap extends EntityPickup
{
	public EntityPickupScrap()
	{
		this.textureName = "scrap1";
		this.addBehaviour(new BehaviourMoveTowardsTarget(100));
		this.addBehaviour(new BehaviourRemoveOnCleanup());
		this.speed = 45;
		this.isInmoveable = true;
		this.enemy = EntityType.PLAYER;
		this.lifespan = 3600;
		this.rotateToMovement = false;
		this.healthSystem = new HealthSystemIndestructible();
	}

	@Override
	public boolean canPickUp(Entity e)
	{
		return e.getType() == EntityType.PLAYER;
	}

	@Override
	public Vector2f getInitialSize()
	{
		return new Vector2f(40, 40);
	}

	@Override
	public void pickUp(Entity e)
	{
		e.modStat("SCRAP", 1, 0);
	}

}
