package com.ichmed.roguegalaxy.gui;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.gui.IGuiElement;
import com.ichmed.bol2d.render.*;

public class CursorPopup implements IGuiElement
{
	private String message = "Test";
	private float size = 0.05f;

	@Override
	public void render()
	{
		RenderUtil.setColor(RenderUtil.WHITE);
		RenderUtil.drawLibraryTextureRect(this.getPosition().x, this.getPosition().y, size / 2, size, "gui$buttonLeft");
		for(int i = 1; i < message.length() - 1; i++)
			RenderUtil.drawLibraryTextureRect(this.getPosition().x + i * size / 2, this.getPosition().y, size / 2, size, "gui$buttonMiddle");
		RenderUtil.drawLibraryTextureRect(this.getPosition().x + (message.length() - 1) * size / 2, this.getPosition().y, size / 2, size, "gui$buttonRight");
		TextUtil.drawText(message, "default", this.getPosition().x + 0.01f, this.getPosition().y, size);
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
	
	public void setMessage(String msg)
	{
		this.message = msg;
	}
	
	public void clear()
	{
		this.message = null;
	}

	@Override
	public void receivePriority()
	{
	}

	@Override
	public Vector2f getPosition()
	{
		return Game.getCursorPosition();
	}

	@Override
	public Vector2f getSize()
	{
		return new Vector2f();
	}

	@Override
	public boolean isVisible()
	{
		return message != null;
	}

	@Override
	public void update()
	{
	}

	@Override
	public IGuiElement getParent()
	{
		return null;
	}

	@Override
	public void setParent(IGuiElement parent)
	{
	}
}
