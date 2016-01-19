package com.ichmed.roguegalaxy;

import java.io.IOException;
import java.util.*;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.entity.pickup.item.ItemData;
import com.ichmed.bol2d.gui.Menu;
import com.ichmed.bol2d.render.texturelibrary.TextureLibrarySheet;
import com.ichmed.bol2d.util.IGameWithItems;
import com.ichmed.bol2d.util.input.*;
import com.ichmed.bol2d.util.io.DataLoader;
import com.ichmed.bol2d.util.procedural.RandomPool;
import com.ichmed.roguegalaxy.gui.*;
import com.ichmed.roguegalaxy.util.Global;

public class RogueGalaxy extends Game implements IGameWithItems
{
	public static CursorPopup cursorPopup = new CursorPopup();
	
	public Map<String, ItemData> items = new HashMap<String, ItemData>();
	public Map<String, RandomPool<String>> itemPools = new HashMap<String, RandomPool<String>>();

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
			TextureLibrarySheet.createLibrary("gui", "resc/texture/gui/", true);
			DataLoader.loadAll(this);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		this.gui[9].add(cursorPopup);
		this.gui[2].add(new WorldMap());
	}

	@Override
	public Map<String, RandomPool<String>> getItemPools()
	{
		return this.itemPools;
	}

	@Override
	public Map<String, ItemData> getAllItems()
	{
		return this.items;
	}
}
