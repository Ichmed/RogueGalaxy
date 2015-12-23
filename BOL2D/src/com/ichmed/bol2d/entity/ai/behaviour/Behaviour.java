package com.ichmed.bol2d.entity.ai.behaviour;

import com.ichmed.bol2d.entity.Entity;

public abstract class Behaviour
{
	public final BehaviourType type;
	
	public Behaviour(BehaviourType type)
	{
		this.type = type;
	}
	public abstract boolean perform(Entity e, Entity f);
}
