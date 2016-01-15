package com.ichmed.roguegalaxy.entity.ai.behaviour.update.movement;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.entity.Entity;
import com.ichmed.bol2d.entity.ai.behaviour.BehaviourUpdate;
import com.ichmed.bol2d.entity.ai.behaviour.target.TargetType;

public class BehaviourMoveTowardsTarget extends BehaviourUpdate
{
	private TargetType targetType;
		
	public BehaviourMoveTowardsTarget(TargetType targetType)
	{
		super();
		this.targetType = targetType;
	}

	@Override
	public boolean perform(Entity entity, Entity target)
	{
		Entity e = entity.targets.get(targetType);
		if(e == null) return false;
		entity.accelerate(Vector2f.sub(e.getCenter(), entity.getCenter(), null), entity.getStat("SPEED"));
		return true;
	}

}
