package com.ichmed.roguegalaxy.gui.inventory;

import java.util.List;

import org.lwjgl.glfw.GLFW;
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
			this.add(new GuiItemSlot(new Vector2f((i % 6) * 0.2f - 0.9f, 0.5f - (i / 6) * 0.2f), l.get(i)));
		}
	}
	
	@Override
	protected void renderBackground()
	{
		RenderUtil.setColor(RenderUtil.WHITE, 1);
		RenderUtil.drawLibraryTextureRect(-1, -1, 2, 2, "gui$inventory_background");
	}
	
	@Override
	public boolean keyboardCallback(long window, int key, int scancode, int action, int mods)
	{
		if(key == GLFW.GLFW_KEY_E && action == GLFW.GLFW_RELEASE) this.disable();
		return super.keyboardCallback(window, key, scancode, action, mods);
	}

}
