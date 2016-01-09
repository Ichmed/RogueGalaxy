package com.ichmed.roguegalaxy.gui.inventory;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.entity.pickup.item.ItemStack;
import com.ichmed.bol2d.gui.IGuiElement;
import com.ichmed.bol2d.render.*;
import com.ichmed.bol2d.render.TextUtil.TextOrientation;

public class GuiItemSlot implements IGuiElement
{
	
	private Vector2f pos;
	private ItemStack itemStack;

	public GuiItemSlot(Vector2f pos, ItemStack itemStack)
	{
		super();
		this.pos = pos;
		this.itemStack = itemStack;
	}

	@Override
	public void render()
	{
		Vector2f pos = this.getPosition();
		Vector2f size = this.getSize();
		RenderUtil.setColor(RenderUtil.WHITE);
		RenderUtil.drawLibraryTextureRect(pos.x, pos.y, size.x, size.y, "gui$itemslot_background");
		RenderUtil.drawLibraryTextureRect(pos.x, pos.y, size.x, size.y, itemStack.item.getTexture());
		TextUtil.drawText(itemStack.amount + "", "default", pos.x + 0.195f, pos.y + 0.005f, 0.08f, TextOrientation.RIGHT_BOUND);
		RenderUtil.setColor(RenderUtil.BLACK);
		TextUtil.drawText(itemStack.amount + "", "default", pos.x + 0.2f, pos.y, 0.08f, TextOrientation.RIGHT_BOUND);
	}

	@Override
	public boolean keyboardCallback(long window, int key, int scancode, int action, int mods)
	{
		return false;
	}

	@Override
	public boolean mouseCallback(long window, int button, int action, int mods)
	{
		return false;
	}

	@Override
	public void receivePriority()
	{
	}

	@Override
	public Vector2f getPosition()
	{
		return new Vector2f(pos);
	}

	@Override
	public Vector2f getSize()
	{
		return new Vector2f(0.2f, 0.2f);
	}

	@Override
	public boolean isVisible()
	{
		return true;
	}

	@Override
	public void update()
	{
	}

}
