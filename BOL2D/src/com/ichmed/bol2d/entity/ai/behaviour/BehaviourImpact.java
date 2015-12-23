package com.ichmed.bol2d.entity.ai.behaviour;

import com.ichmed.bol2d.entity.Entity;

public abstract class BehaviourImpact extends Behaviour
{
	public BehaviourImpact()
	{
		super(BehaviourType.ON_IMPACT);
	}

	@Override
	public boolean perform(Entity e, Entity f)
	{
		if(f == null)return false;
		if(!this.collideWithOwner() && f == e.owner) return false;
		if (!f.isSolid && this.onlySolids()) return false;
		return actuallyPerform(e, f);
	}

	protected abstract boolean actuallyPerform(Entity e, Entity f);

	protected boolean onlySolids()
	{
		return true;
	}
	
	protected boolean collideWithOwner()
	{
		return false;
	}

}
