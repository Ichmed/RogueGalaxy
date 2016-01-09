package com.ichmed.roguegalaxy.gui.inventory;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.entity.pickup.item.*;
import com.ichmed.bol2d.gui.Menu;
import com.ichmed.bol2d.render.RenderUtil;

public class MenuInventory extends Menu
{
	public MenuInventory(IInventory inventory)
	{
		List<ItemStack> l = inventory.getContent();
		for(int i = 0; i < l.size(); i++)
		{
			this.add(new GuiItemSlot(new Vector2f((i % 6) * 0.2f, 1.5f - (i / 6) * 0.2f), l.get(i)));
		}
	}
	
	@Override
	protected void renderBackground()
	{
		RenderUtil.setColor(RenderUtil.WHITE, 1);
		RenderUtil.drawLibraryTextureRect(0, 0, 2, 2, "gui$inventory_background");
	}

}
