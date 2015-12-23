package com.ichmed.roguegalaxy.entity.ai.behaviour.update.movement;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.entity.*;
import com.ichmed.bol2d.entity.ai.behaviour.BehaviourUpdate;
import com.ichmed.bol2d.util.render.RenderUtil;
import com.ichmed.bol2d.world.World;
import com.ichmed.roguegalaxy.RogueGalaxy;

public class BehaviourHomingProjectile extends BehaviourUpdate
{
	private final int homingTime;
	private float targetDistance;
	private float homingSpeed;

	public BehaviourHomingProjectile(float targetDistance, int homingTime, float homingSpeed)
	{
		this.homingTime = homingTime;
		this.targetDistance = targetDistance;
		this.homingSpeed = homingSpeed;
	}

	@Override
	public boolean perform(Entity entity, Entity target)
	{
		World w = RogueGalaxy.getGameWorld();
		if ((entity.target == null || entity.target.isDead()) && this.homingTime <= entity.getTicksExisted())
		{
			List<Entity> l = w.sortListByDistance(entity, w.selectFromListByType(w.getCurrentEntities(), entity.enemy), targetDistance);
			entity.target = l.size() > 0 ? l.get(0) : null;
			entity.target = entity.target == entity ? (l.size() > 1 ? l.get(1) : null) : entity.target;
		}
		if(entity.target == null)return false; 

		if (entity.target != null && !entity.target.isDead() && this.homingTime <= entity.getTicksExisted())
		{
			entity.color = RenderUtil.RED;
			Vector2f v = new Vector2f();
			Vector2f.sub(entity.target.getCenter(), entity.getCenter(), v);
			if (v.x > 0 || v.y > 0)
			{
				v.normalise();
				entity.accelerate(new Vector2f(-entity.getVelocity().x * .2f, -entity.getVelocity().y * .2f));
				entity.accelerate((Vector2f) v.scale(this.homingSpeed));
			}
			Entity e = new EntityGenericParticle();
			e.setCenter(entity.getCenter());
			w.spawn(e);
		}
		else entity.color = RenderUtil.WHITE;
		return true;
	}

}
