package com.ichmed.roguegalaxy.entity.pickup.item;

import com.ichmed.bol2d.entity.pickup.item.Item;


public class ItemPirate extends Item
{

	@Override
	public void init()
	{
		this.addStat("ARRR", 1);
	}

	@Override
	public String getTexture()
	{
		return "item_human_pirate";
	}

}
