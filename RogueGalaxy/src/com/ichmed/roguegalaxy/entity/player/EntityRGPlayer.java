package com.ichmed.roguegalaxy.entity.player;

import static org.lwjgl.glfw.GLFW.*;

import java.util.*;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.entity.*;
import com.ichmed.bol2d.entity.ai.behaviour.*;
import com.ichmed.bol2d.entity.damage.*;
import com.ichmed.bol2d.entity.pickup.item.*;
import com.ichmed.bol2d.entity.player.EntityPlayer;
import com.ichmed.bol2d.gui.Console;
import com.ichmed.bol2d.util.input.InputManager;
import com.ichmed.roguegalaxy.RogueGalaxy;
import com.ichmed.roguegalaxy.entity.HealthSystemPlayer;
import com.ichmed.roguegalaxy.entity.ai.behaviour.impact.*;
import com.ichmed.roguegalaxy.entity.ai.shotpatterns.Shotpattern;
import com.ichmed.roguegalaxy.gui.inventory.MenuInventory;
import com.ichmed.roguegalaxy.util.Global;

public class EntityRGPlayer extends EntityPlayer implements IInventory
{
	public float accelaration = 0.5f;
	public float breakSpeed = 1f;
	public float attackSpeed = 10f;
	private float attackCooldown;
	public List<Shotpattern> shotPatterns = new ArrayList<Shotpattern>();
	public List<ItemStack> inventory = new ArrayList<ItemStack>();

	public EntityRGPlayer()
	{
		this.speed = 10;
		this.damage = 5;
		this.enemy = EntityType.NPC;
		this.setStat("MAX_PROJECTILES", Global.PLAYER_PROJECTILE_MAX);
		// b1.add(new BehaviourHomingProjectile(400, 40, 5));
		// b1.add(new BehaviourExplodeOnDeath(1, 100, 40, -1));
		this.velocity = new Vector2f(0, 10);
	}

	@Override
	protected HealthSystem getHealthSystem(Float health)
	{
		return new HealthSystemPlayer(20, 1.0f);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		attackCooldown -= this.getStat("ATTACK_SPEED", 1.0f);

		if (InputManager.isPrimaryReceiver(this))
		{
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
	}

	public void fireProjectile(Vector2f v)
	{
		if (attackCooldown <= 0)
		{
			EntityGenericProjectile e = new EntityGenericProjectile();
			v = RogueGalaxy.getCursorPosition();
			if (v.x != 0 || v.y != 0) e.accelerate((Vector2f) v.normalise().scale(e.speed));
			else e.accelerate(new Vector2f(0, 1));
			e.setCenter(this.getCenter());
			e.enemy = this.enemy;
			e.owner = this;
			e.enemy = this.enemy;
			e.textureName = "laser1";
			e.addBehaviour(new BehaviourDieOnImpact());
			e.addBehaviour(new BehaviourRemoveOnCleanup());
			float damage = this.getStat("DAMAGE", 1);
			e.addBehaviour(new BehaviourDealDamageOnImpact(true, damage, DamageType.LASER));
			e.isInmoveable = true;
			List<Entity> l = new ArrayList<Entity>();
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
		if (v.x > 0) this.velocity.x += Math.min(this.getStat("MAX_SPEED", 10) - this.velocity.x, v.x);
		if (v.x < 0) this.velocity.x += Math.max(-this.getStat("MAX_SPEED", 10) - this.velocity.x, v.x);
		if (v.y > 0) this.velocity.y += Math.min(this.getStat("MAX_SPEED", 10) - this.velocity.y, v.y);
		if (v.y < 0) this.velocity.y += Math.max(-this.getStat("MAX_SPEED", 10) - this.velocity.y, v.y);
		return true;
	}

	@Override
	public Vector2f getInitialSize()
	{
		return new Vector2f(80, 80);
	}

	@Override
	public boolean keyboardCallback(long window, int key, int scancode, int action, int mods)
	{
		if (action == GLFW_PRESS && key == GLFW_KEY_ENTER) Console.enable();
		if (action == GLFW_RELEASE)
		{
			if (key == GLFW_KEY_ESCAPE) Game.getCurrentGame().pauseScreen.enable();
			if (key == GLFW_KEY_E) (new MenuInventory(this)).enable();
		}
		return true;
	}

	@Override
	public boolean mouseCallback(long window, int button, int action, int mods)
	{
		return true;
	}

	@Override
	public boolean pickUpItem(ItemStack stack)
	{
		for (ItemStack s : this.inventory)
			if (s.item.equals(stack.item))
			{
				if (stack.item.isUnique()) return false;
				s.add(stack);
				stack.pickUp(this);
				return true;
			}
		this.inventory.add(stack);
		stack.pickUp(this);
		return true;
	}

	@Override
	public void receivePriority()
	{
	}

	@Override
	public List<ItemStack> getContent()
	{
		return this.inventory;
	}

}
