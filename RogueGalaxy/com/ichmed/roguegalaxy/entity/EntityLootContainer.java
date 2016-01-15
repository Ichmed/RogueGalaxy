package com.ichmed.roguegalaxy.entity;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.entity.*;
import com.ichmed.bol2d.entity.damage.*;
import com.ichmed.bol2d.gui.Console;
import com.ichmed.roguegalaxy.render.AnimationOpen;

public class EntityLootContainer extends Entity implements IInteractable
{

	public EntityLootContainer()
	{
		this.isInmoveable = true;
		this.textureName = "lootContainer";
		this.lifespan = -1;
	}

	@Override
	protected HealthSystem getHealthSystem(Float health)
	{
		return new HealthSystemIndestructible();
	}

	private int startOpeningTick = -1;

	@Override
	public boolean isActive()
	{
		return true;
	}

	@Override
	public float getRange()
	{
		return 100;
	}

	@Override
	public int[] getAcceptedKeys()
	{
		return new int[] { GLFW.GLFW_KEY_SPACE };
	}

	@Override
	public void interact(int key)
	{
		if (key == GLFW.GLFW_KEY_SPACE) this.open();
	}

	private void open()
	{
		if (this.startOpeningTick == -1)
		{
			this.animation = new AnimationOpen(this, 60);
			this.startOpeningTick = Game.getTicksTotal();
		}
	}

	@Override
	public int getLayer()
	{
		return 3;
	}

	@Override
	public void onDeath()
	{
		Console.log(this.healthSystem.shouldDie());
		super.onDeath();
	}

	@Override
	public Vector2f getInitialSize()
	{
		return new Vector2f(100f, 100f);
	}

	@Override
	public EntityType getType()
	{
		return EntityType.NPC;
	}
}
