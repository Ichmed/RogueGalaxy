package com.ichmed.roguegalaxy;

import java.io.IOException;
import java.util.*;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.gui.Menu;
import com.ichmed.bol2d.render.TextureLibrary;
import com.ichmed.bol2d.util.input.*;
import com.ichmed.bol2d.util.procedural.RandomPool;
import com.ichmed.roguegalaxy.gui.*;
import com.ichmed.roguegalaxy.util.Global;
import com.ichmed.roguegalaxy.util.io.ItemLoader;

public class RogueGalaxy extends Game
{
	public static CursorPopup cursorPopup = new CursorPopup();
	
	public Map<String, ItemData> items = new HashMap<String, ItemData>();
	public static Map<String, RandomPool<ItemData>> itemPools = new HashMap<String, RandomPool<ItemData>>();

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
		return new MenuPause();
	}

	@Override
	protected void loop()
	{
		cursorPopup.clear();
		super.loop();
	}

	@Override
	public void initGameData()
	{
		try
		{
			TextureLibrary.createLibrary("gui", "resc/texture/gui/", true);
			initItems();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		this.gui[9].add(cursorPopup);
	}

	private void initItems()
	{
		ItemLoader.loadItems(items, itemPools);
	}
}
