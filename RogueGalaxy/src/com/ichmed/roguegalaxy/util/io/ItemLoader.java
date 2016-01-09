package com.ichmed.roguegalaxy.util.io;

import java.io.*;
import java.util.*;

import com.ichmed.bol2d.gui.Console;
import com.ichmed.bol2d.util.procedural.*;
import com.ichmed.roguegalaxy.ItemData;
import com.ichmed.roguegalaxy.util.Global;

public class ItemLoader
{
	public static void loadItems(Map<String, ItemData> items, Map<String, RandomPool<ItemData>> itemPools)
	{
		try
		{
			Scanner scan = new Scanner(new File("resc/data/items/default.items"));
			ItemData data = null;
			RandomPool<ItemData> pool = null;
			String name = "";
			while (scan.hasNextLine())
			{
				String orig = scan.nextLine();
				orig = orig.trim();
				String s = orig.toUpperCase();
				s = s.trim();

				if (!(s.startsWith("//") || s.equals("")))
				{
					Console.log(s);
					if (s.startsWith("CREATE ITEM "))
					{
						name = s.split(" ")[2];
						items.put(name, data = new ItemData());
						data.setName(name);
					} else if (s.startsWith("SELCET ITEM"))
					{
						name = s.split(" ")[2];
						data = items.get(name);
						if (data == null) Console.log("Could not find item " + name);
					} else if (s.startsWith("NAME ")) data.setReadableName(orig.substring(5));
					else if (s.startsWith("STAT "))
					{
						String statName = s.split(" ")[1];
						String type = s.split(" ")[2];
						String value = s.split(" ")[3];

						if (type.equals("MOD")) statName += "_MUL";
						else if (type.equals("FLAT")) statName += "_ADD";
						else if (!type.equals("BASE")) Console.log("Plese use \"Base\"");
						data.addStat(statName, Float.valueOf(value));
					} else if (s.startsWith("TEXTURE ")) data.setTexture(orig.split(" ")[1]);
					else if (s.startsWith("CREATE POOL"))
					{
						pool = new RandomArrayPool<ItemData>(Global.RANDOM_SEED);
						String poolName = s.split(" ")[2];
						itemPools.put(poolName, pool);
						Console.log("Creating item pool " + poolName);
					} else if (s.startsWith("SELCET POOL"))
					{
						String poolName = s.split(" ")[2];
						pool = itemPools.get(poolName);
						if (pool == null) Console.log("Could not find pool " + poolName);
					} else if (s.startsWith("ADD ITEM "))
					{
						pool.add(items.get(s.split(" ")[2]), Integer.valueOf(s.split(" ")[3]));
					}
					else Console.log("Could not interpret \"" + s + "\"");
				}

			}
			scan.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
