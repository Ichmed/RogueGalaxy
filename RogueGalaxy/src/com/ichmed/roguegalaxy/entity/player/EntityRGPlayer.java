package com.ichmed.roguegalaxy.entity.player;

import static org.lwjgl.glfw.GLFW.*;

import java.util.*;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.entity.*;
import com.ichmed.bol2d.entity.ai.behaviour.*;
import com.ichmed.bol2d.entity.damage.DamageType;
import com.ichmed.bol2d.entity.player.EntityPlayer;
import com.ichmed.roguegalaxy.RogueGalaxy;
import com.ichmed.roguegalaxy.entity.HealthSystemPlayer;
import com.ichmed.roguegalaxy.entity.ai.behaviour.impact.*;
import com.ichmed.roguegalaxy.entity.ai.shotpatterns.Shotpattern;
import com.ichmed.roguegalaxy.util.Global;

public class EntityRGPlayer extends EntityPlayer
{
	public float accelaration = 0.5f;
	public float breakSpeed = 1f;
	public float attackSpeed = 10f;
	private float attackCooldown;
	public List<Shotpattern> shotPatterns = new ArrayList<Shotpattern>();

	public EntityRGPlayer()
	{
		this.speed = 10;
		this.damage = 5;
		ArrayList<Behaviour> b1 = new ArrayList<Behaviour>();
		this.projectlieBehaviourSets.add(b1);
		this.enemy = EntityType.NPC;
		this.setStat("MAX_PROJECTILES", Global.PLAYER_PROJECTILE_MAX);
		// b1.add(new BehaviourHomingProjectile(400, 40, 5));
		b1.add(new BehaviourDieOnImpact());
		b1.add(new BehaviourRemoveOnCleanup());
		this.healthSystem = new HealthSystemPlayer(20, 1.0f);
		b1.add(new BehaviourDealDamageOnImpact(true, 5, DamageType.LASER));
		// b1.add(new BehaviourExplodeOnDeath(1, 100, 40, -1));
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		attackCooldown -= attackSpeed;
		if (RogueGalaxy.isKeyDown(GLFW_KEY_D)) this.accelerate(new Vector2f(accelaration, 0));
		else if (this.velocity.x > 0) this.velocity.x = Math.max(this.velocity.x - breakSpeed, 0);

		if (RogueGalaxy.isKeyDown(GLFW_KEY_A)) this.accelerate(new Vector2f(-accelaration, 0));
		else if (this.velocity.x < 0) this.velocity.x = Math.min(this.velocity.x + breakSpeed, 0);

		if (RogueGalaxy.isKeyDown(GLFW_KEY_S)) this.accelerate(new Vector2f(0, -accelaration));
		else if (this.velocity.y < 0) this.velocity.y = Math.min(this.velocity.y + breakSpeed, 0);

		if (RogueGalaxy.isKeyDown(GLFW_KEY_W)) this.accelerate(new Vector2f(0, accelaration));
		else if (this.velocity.y > 0) this.velocity.y = Math.max(this.velocity.y - breakSpeed, 0);

		if (RogueGalaxy.isButtonDown(GLFW_MOUSE_BUTTON_1)) fireProjectile(new Vector2f(0, 0));
	}

	public void fireProjectile(Vector2f v)
	{
		if (attackCooldown <= 0)
		{
			EntityGenericProjectile e = new EntityGenericProjectile();
			e.accelerate((Vector2f) RogueGalaxy.getCursorPosition().normalise().scale(e.speed));
			e.addBehaviour(this.getProjectileBehaviours(0));
			e.setCenter(this.getCenter());
			e.enemy = this.enemy;
			e.owner = this;
			e.enemy = this.enemy;
			e.textureName = "laser1";
			e.isInmoveable = true;
			List<Entity> l = new ArrayList<Entity>();
			// int projNum = (int) this.getStat("CURRENT_PROJECTILES");
			l.add(e);
			for (Shotpattern p : shotPatterns)
			{
				// if (projNum + l.size() > this.getStat("MAX_PROJECTILES"))
				// break;
				l = p.perform(l, 4);
			}
			this.modStat("CURRENT_PROJECTILES", l.size(), 0);
			for (Entity f : l)
				Game.getGameWorld().spawn(f);
			attackCooldown = 60;
		}
	}

	@Override
	public boolean accelerate(Vector2f v)
	{
		if (v.x > 0) this.velocity.x += Math.min(this.speed - this.velocity.x, v.x);
		if (v.x < 0) this.velocity.x += Math.max(-this.speed - this.velocity.x, v.x);
		if (v.y > 0) this.velocity.y += Math.min(this.speed - this.velocity.y, v.y);
		if (v.y < 0) this.velocity.y += Math.max(-this.speed - this.velocity.y, v.y);
		return true;
	}

	@Override
	public Vector2f getInitialSize()
	{
		return new Vector2f(80, 80);
	}

}
