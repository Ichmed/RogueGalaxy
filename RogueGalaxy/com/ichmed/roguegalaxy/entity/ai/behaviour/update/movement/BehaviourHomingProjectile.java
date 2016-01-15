package com.ichmed.roguegalaxy.entity.ai.behaviour.update.movement;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.entity.Entity;
import com.ichmed.bol2d.entity.ai.behaviour.BehaviourUpdate;
import com.ichmed.bol2d.entity.ai.behaviour.target.TargetType;
import com.ichmed.bol2d.render.animation.AnimationSimple;

public class BehaviourHomingProjectile extends BehaviourUpdate
{
	private final int homingTime;
	private float homingSpeed;
	
	boolean locked = false;

	public BehaviourHomingProjectile(int homingTime, float homingSpeed)
	{
		this.homingTime = homingTime;
		this.homingSpeed = homingSpeed;
	}

	@Override
	public boolean perform(Entity entity, Entity target)
	{
		if(entity.getTicksExisted() > this.homingTime)
		{
			Entity e = entity.targets.get(TargetType.ATTACK);
			if(e != null)
			{
				if(!locked)
				{
					locked = true;
					entity.setAnimation(new AnimationSimple(entity, "homing"));
				}
				entity.setVelocity(Vector2f.sub(e.getCenter(), entity.getCenter(), null), homingSpeed);
			}
			else 
			{
				locked = false;
				entity.setAnimation(new AnimationSimple(entity, "armed"));
			}
		}
		return true;
	}

}
