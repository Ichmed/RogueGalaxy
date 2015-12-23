package com.ichmed.roguegalaxy.entity.ai.behaviour.cleanup;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.entity.Entity;
import com.ichmed.bol2d.entity.ai.behaviour.BehaviourCleanup;

public class BehaviourRemoveOnCleanup extends BehaviourCleanup
{
	@Override
	public boolean perform(Entity entity, Entity target)
	{
		Game.getGameWorld().removeEntity(entity);
		return true;
	}

}
