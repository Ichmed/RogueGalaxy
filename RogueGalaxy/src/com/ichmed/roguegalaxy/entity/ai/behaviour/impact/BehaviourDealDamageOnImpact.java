package com.ichmed.roguegalaxy.entity.ai.behaviour.impact;

import com.ichmed.bol2d.entity.Entity;
import com.ichmed.bol2d.entity.ai.behaviour.BehaviourImpact;
import com.ichmed.bol2d.entity.damage.DamageType;

public class BehaviourDealDamageOnImpact extends BehaviourImpact
{
	boolean multiplyByOwnerDamage;
	float damage;
	DamageType type;

	public BehaviourDealDamageOnImpact(boolean multiplyByOwnerDamage, float damage, DamageType type)
	{
		this.multiplyByOwnerDamage = multiplyByOwnerDamage;
		this.damage = damage;
		this.type = type;
	}

	@Override
	public boolean actuallyPerform(Entity entity, Entity target)
	{
		if(target == null) return false;
		return target.hurt(damage, type) > 0;
	}

}
