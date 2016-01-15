package com.ichmed.roguegalaxy.entity.ai.behaviour.update.attack;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.entity.Entity;
import com.ichmed.bol2d.entity.ai.behaviour.BehaviourUpdate;
import com.ichmed.bol2d.util.MathUtil;

public class BehaviourShootProjectileAtTarget extends BehaviourUpdate
{
	private Entity projectileTemplate;
	private float range;
	private int cooldown;

	public BehaviourShootProjectileAtTarget(Entity projectileTemplate, float range, int cooldown)
	{
		super();
		this.projectileTemplate = projectileTemplate;
		this.range = range;
		this.cooldown = cooldown;
	}

	@Override
	public boolean perform(Entity entity, Entity target)
	{
		if (target == null || entity.actionCooldown > 0) return false;
		if (MathUtil.positionsInRange(entity.getCenter(), target.getCenter(), range))
		{
			Entity projectile = projectileTemplate.clone();
			projectile.setCenter(entity.getCenter());
			Vector2f v = Vector2f.sub(target.getCenter(), entity.getCenter(), null);
			v.normalise();
			v.scale(projectile.speed);
			projectile.velocity = v;
			Game.getGameWorld().spawn(projectile);
			entity.actionCooldown = cooldown;
			return true;
		}
		return false;
	}

}
