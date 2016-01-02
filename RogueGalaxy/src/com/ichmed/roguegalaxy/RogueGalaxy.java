package com.ichmed.roguegalaxy;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.gui.*;
import com.ichmed.bol2d.gui.GuiButton.ActionHandler;
import com.ichmed.bol2d.util.input.*;
import com.ichmed.roguegalaxy.util.Global;

public class RogueGalaxy extends Game
{
	public static void main(String[] args)
	{
		Game.startNewGame(new RogueGalaxy(), new RGWorld());
	}

	@Override
	public float getRotationUpdateInterval()
	{
		return Global.ROTATION_UPDATE_INTERVAL;
	}

	@Override
	public int getParticleMax()
	{
		return Global.PARTICLE_MAX;
	}

	@Override
	public float getParticleRate()
	{
		return Global.PARTICLE_RATE;
	}

	@Override
	public int getMaxTexLibSize()
	{
		return Global.TEXTURE_LIBRARY_SIZE;
	}

	@Override
	public boolean shouldShowTextureStiching()
	{
		return Global.SHOW_TEXTURE_STICHING;
	}

	@Override
	public IInputReceiver getDefaultInputReceiver()
	{
		return new DefaultInputReceiver();
	}

	@Override
	public Menu getPauseScreen()
	{
		Menu m = new Menu();
		GuiButton b = new GuiButton();
		b.label = "huhu";
		b.size = new Vector2f(0.8f, 0.2f);
		b.position = new Vector2f(0.2f, 0.2f);
		b.setActionHandler(new ActionHandler()
		{			
			@Override
			public void leftClick()
			{
				m.disable();
			}
		});
		m.add(b);
		return m;
	}
}
