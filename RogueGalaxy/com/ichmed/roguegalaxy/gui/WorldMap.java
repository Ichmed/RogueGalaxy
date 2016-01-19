package com.ichmed.roguegalaxy.gui;

import java.awt.*;

import org.lwjgl.util.vector.*;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.gui.*;
import com.ichmed.bol2d.render.RenderUtil;
import com.ichmed.bol2d.render.texture.TextureDynamicAWT;

public class WorldMap extends DefaultGuiElement
{
	TextureDynamicAWT texture = new TextureDynamicAWT(200, 200);

	@Override
	public boolean isVisible()
	{
		return true;
	}

	@Override
	public void render()
	{
		texture.bind();
		RenderUtil.setColor(new Vector3f(0.5f, 1, 1), 0.7f);
		RenderUtil.drawTexturedRect(new Vector4f(0.5f, -1f, 1f, -0.5f), new Vector4f(0, 0, 1, 1));
		RenderUtil.setColor(RenderUtil.WHITE, 1);
		RenderUtil.drawLibraryTextureRect(0.5f, -1f, 0.5f, 0.5f, "gui_map_frame");
	}

	@Override
	public void update()
	{
		Graphics2D g2d = texture.getGraphics();
		g2d.setBackground(Color.DARK_GRAY);
		g2d.clearRect(0, 0, 200, 200);
		Color c = (Game.getTicksTotal() % 60) > 29 ? Color.WHITE : Color.LIGHT_GRAY;
		g2d.setColor(c);
		int x = -(int)(Game.getGameWorld().player.getCenter().x / 1000f);
		int y = (int)(Game.getGameWorld().player.getCenter().y / 1000f);
		x += 100;
		y += 100;
		
		int size = 4;
		
		g2d.fillRect(x - size/2, y - size/2, size, size);
		super.update();
	}
	
	
	
	
}
