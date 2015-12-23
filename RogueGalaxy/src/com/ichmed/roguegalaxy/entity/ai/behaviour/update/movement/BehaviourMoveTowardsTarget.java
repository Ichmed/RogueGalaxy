package com.ichmed.roguegalaxy.entity.ai.behaviour.update.movement;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.entity.*;
import com.ichmed.bol2d.entity.ai.behaviour.BehaviourUpdate;
import com.ichmed.bol2d.util.MathUtil;
import com.ichmed.bol2d.world.World;
import com.ichmed.roguegalaxy.RogueGalaxy;

public class BehaviourMoveTowardsTarget extends BehaviourUpdate
{

	public BehaviourMoveTowardsTarget(float targetDistance)
	{
		super();
		this.targetDistance = targetDistance;
	}

	private float targetDistance;

	@Override
	public boolean perform(Entity entity, Entity target)
	{
		World w = RogueGalaxy.getGameWorld();
		if ((entity.target == null || entity.target.isDead()))
		{
			List<Entity> l = w.sortListByDistance(entity, w.selectFromListByType(w.getCurrentEntities(), entity.enemy), targetDistance);
			entity.target = l.size() > 0 ? l.get(0) : null;
			entity.target = entity.target == entity ? (l.size() > 1 ? l.get(1) : null) : entity.target;
		}
		if (entity.target == null) return false;

		if (entity.target != null && !entity.target.isDead() && MathUtil.positionsInRange(entity.getCenter(), entity.target.getCenter(), targetDistance))
		{
			Vector2f v = new Vector2f();
			Vector2f.sub(entity.target.getCenter(), entity.getCenter(), v);
			if (v.x > 0 || v.y > 0)
			{
				v.normalise();
				v.scale(entity.speed);
				entity.velocity = v;
			}
		}
		return true;
	}

}
