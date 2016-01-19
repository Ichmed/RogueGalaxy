package com.ichmed.roguegalaxy.gui.inventory;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.entity.pickup.item.ItemStack;
import com.ichmed.bol2d.gui.*;
import com.ichmed.bol2d.render.*;
import com.ichmed.bol2d.render.TextUtil.TextOrientation;
import com.ichmed.bol2d.util.MathUtil;
import com.ichmed.roguegalaxy.RogueGalaxy;


public class GuiItemSlot extends DefaultGuiElement implements IClickable
{
	private ItemStack itemStack;

	public GuiItemSlot(Vector2f pos, ItemStack itemStack)
	{
		super();
		this.setPosition(pos);
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
		TextUtil.drawText("$[color=BLACK]" + itemStack.amount, "default", pos.x + 0.23f, pos.y, 0.08f, TextOrientation.RIGHT_BOUND);
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
		if(MathUtil.isPointInArea(Game.getCursorPosition(), this.getPosition(), this.getSize()))
			RogueGalaxy.cursorPopup.setMessage(this.itemStack.amount + "x " + this.itemStack.item.name);			
	}

	@Override
	public boolean rightClick(int action)
	{
		return false;
	}

	@Override
	public boolean leftClick(int action)
	{
		return false;
	}

	@Override
	public void reset()
	{
	}
}
