package com.ichmed.roguegalaxy;

import java.util.*;

import com.ichmed.bol2d.entity.pickup.item.*;

public class ItemData
{
	private String texture = "item_default";

	private List<StatContainer> stats = new ArrayList<StatContainer>();
	private String name;
	private String readableName;
	private String descritpion;

	public Item create()
	{
		Item i = new Item()
		{

			@Override
			public void init()
			{
			}

			@Override
			public String getTexture()
			{
				return texture;
			}
		};

		for (StatContainer s : stats)
			i.addStat(s.getName(), s.getValue());
		
		i.name = readableName;
		i.description = descritpion;

		return i;
	}

	public void addStat(String name, float value)
	{
		this.stats.add(new StatContainer(name, value));
	}

	public String getReadableName()
	{
		return readableName;
	}

	public void setReadableName(String readableName)
	{
		this.readableName = readableName;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTexture()
	{
		return texture;
	}

	public void setTexture(String texture)
	{
		this.texture = texture;
	}

}
