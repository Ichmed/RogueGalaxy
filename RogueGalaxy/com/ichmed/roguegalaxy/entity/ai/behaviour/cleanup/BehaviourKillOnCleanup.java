package com.ichmed.roguegalaxy.entity.ai.behaviour.cleanup;

import com.ichmed.bol2d.entity.Entity;
import com.ichmed.bol2d.entity.ai.behaviour.BehaviourCleanup;


public class BehaviourKillOnCleanup extends BehaviourCleanup
{

	@Override
	public boolean perform(Entity entity, Entity target)
	{
		entity.kill();
		return true;
	}

}
