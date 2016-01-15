package com.ichmed.roguegalaxy;

import org.lwjgl.util.vector.*;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.entity.Entity;
import com.ichmed.bol2d.render.*;
import com.ichmed.bol2d.render.TextUtil.TextOrientation;
import com.ichmed.bol2d.world.World;
import com.ichmed.roguegalaxy.entity.HealthSystemPlayer;
import com.ichmed.roguegalaxy.entity.npc.EntityTest;
import com.ichmed.roguegalaxy.entity.player.EntityRGPlayer;


public class RGWorld extends World
{
	Texture background;

	@Override
	public void init()
	{
		player = new EntityRGPlayer();
		player.setCenter(new Vector2f());
		this.spawn(player);
		try
		{
			background = Texture.makeTexture("resc/texture/backgroundA.png", ".png");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		super.init();
	}

	@Override
	public void update()
	{

		// e.kill();
		// e = new EntityDummy();
		// e.setCenter(new Vector2f(0.2f, 0f));
		// spawn(e);^
		if (Game.getFlag("SPAWNENEMIES", 1) == 1 && Game.getTicksTotal() % 20 == 0 && !Game.isPaused() && EntityTest.dummyAmount < Game.getFlag("MAXDUMMIES", 10))
		{
			for (int i = 0; i < Game.getFlag("DUMMYRATE", 1); i++)
			{
				double deg = Math.random() * 2 * Math.PI;
				Entity e = new EntityTest();
				e.setPosition(new Vector2f(1100 * (float) Math.cos(deg) + player.getCenter().x, 1100 * (float) Math.sin(deg) + player.getCenter().y));
				e.speed = 2 + (float) Math.random() * 5;
				this.spawn(e);
			}
		}
		super.update();
	}

	@Override
	protected void drawBackground()
	{
		background.bind();
		float x = player.getPosition().x / 100000f;
		float y = player.getPosition().y / 100000f;
		Vector4f v = new Vector4f(x, y, x + 0.2f, y + 0.2f);
		RenderUtil.drawTexturedRect(new Vector4f(-1, -1, 1, 1), v);
		super.drawBackground();
	}

	@Override
	public void drawHud()
	{
		String s;
		HealthSystemPlayer p = (HealthSystemPlayer) player.healthSystem;
		float f = player.healthSystem.getHealth();
		if (f > 75) s = "full";
		else if (f > 50) s = "75";
		else if (f > 25) s = "50";
		else s = "25";
		RenderUtil.drawLibraryTextureRect(-.95f, .86f, .4, .1f, "gui_hull_" + s);
		RenderUtil.setColor(RenderUtil.CYAN, 0.75f);
		// RenderUtil.drawRect(-.97, .85f, .45 * p.shield / 20, .13f);
		RenderUtil.setColor(RenderUtil.CYAN, 1f);
		TextUtil.drawText("Shields: " + (int) p.shield, "default", -.9f, .9f, .05f);
		RenderUtil.setColor(RenderUtil.WHITE, 1f);
		TextUtil.drawText("Hull: " + (int) p.getHealth(), "default", -.9f, .85f, .05f);

		if (Game.getFlag("DEBUG") == 1)
		{
			String text = "";
			text += "Current Entities: " + getCurrentEntities().size() + "\n";
			text += "FPS: " + Game.getFps() + "\n";
//			TextUtil.drawText("Next Entities: " + getNextEntities().size(), "default", -1f, .05f, .05f);
//			TextUtil.drawText("Dif: " + (getCurrentEntities().size() - getNextEntities().size()), "default", -1f, .1f, .05f);
//			TextUtil.drawText("Ticks this second: " + Game.getTicksThisSecond(), "default", -1f, -.1f, .05f);
			TextUtil.drawText(text, "default", -1f, 0f, .05f);
		}
		
		float width = 0.15f;
		RenderUtil.drawLibraryTextureRect(-width * 4 , 0.6, width * 8, width, "gui_zone_frame");
		TextUtil.drawText("$[color=BLACK]WELCOME TO:", "default", 0, 0.75f, 0.09f, TextOrientation.CENTERED);
		float alpha = (float) (0.8f + Math.cos(Game.getTicksTotal() / 10f) * 0.1f);
		TextUtil.drawText("$[color=(0.2, 1, 0.3, " + alpha + ")]Bummfuck Illinois $[texture=player1]", "default", 0, 0.635f, 0.1f, TextOrientation.CENTERED);
	}
}
