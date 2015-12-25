package com.ichmed.roguegalaxy;

import com.ichmed.bol2d.Game;
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

	public void interpretInput(String input)
	{
		super.interpretInput(input);
	}

}
