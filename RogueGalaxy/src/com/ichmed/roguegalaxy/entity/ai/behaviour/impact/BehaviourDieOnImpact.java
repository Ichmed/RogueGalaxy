package com.ichmed.roguegalaxy.entity.ai.behaviour.impact;

import com.ichmed.bol2d.entity.Entity;
import com.ichmed.bol2d.entity.ai.behaviour.BehaviourImpact;

public class BehaviourDieOnImpact extends BehaviourImpact
{
	@Override
	public boolean actuallyPerform(Entity entity, Entity target)
	{
		if(target == null)return false;
		if(target.getType() != entity.enemy) return false;
		entity.kill();
		return true;
	}
}
