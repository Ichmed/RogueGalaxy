package com.ichmed.roguegalaxy;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.entity.Entity;
import com.ichmed.bol2d.render.*;
import com.ichmed.bol2d.world.World;
import com.ichmed.roguegalaxy.entity.HealthSystemPlayer;
import com.ichmed.roguegalaxy.entity.npc.EntityDummy;
import com.ichmed.roguegalaxy.entity.player.EntityRGPlayer;

public class RGWorld extends World
{
	public RGWorld()
	{
		player = new EntityRGPlayer();
		player.setCenter(new Vector2f());
		this.spawn(player);
	}

	Entity e = new EntityDummy();

	@Override
	public void update()
	{
		// e.kill();
		// e = new EntityDummy();
		// e.setCenter(new Vector2f(0.2f, 0f));
		// spawn(e);^
		if (Game.getTicksTotal() % 20 == 0 && EntityDummy.dummyAmount < 50)
		{
			double deg = Math.random() * 2 * Math.PI;
			EntityDummy e = new EntityDummy();
			e.setPosition(new Vector2f(1100 * (float) Math.cos(deg) + player.getCenter().x, 1100 * (float) Math.sin(deg) + player.getCenter().y));
			e.speed = 2 + (float) Math.random() * 5;
			this.spawn(e);
		}
		super.update();
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
		RenderUtil.drawTexturedRect(-.95f, .86f, .4, .1f, "gui_hull_" + s);
		RenderUtil.setColor(RenderUtil.CYAN, 0.75f);
		// RenderUtil.drawRect(-.97, .85f, .45 * p.shield / 20, .13f);
		RenderUtil.setColor(RenderUtil.CYAN, 1f);
		TextUtil.drawText("Shields: " + (int) p.shield, "default", -.9f, .9f, .05f);
		RenderUtil.setColor(RenderUtil.WHITE, 1f);
		TextUtil.drawText("Hull: " + (int) p.getHealth(), "default", -.9f, .85f, .05f);
		TextUtil.drawText("Scrap: " + (int) player.getStat("SCRAP", 0), "default", -1f, -1f, .05f);
		TextUtil.drawText("Entities: " + getCurrentEntities().size(), "default", -1f, 0f, .05f);
		TextUtil.drawText("Particles: " + currentParticles, "default", -1f, .1f, .05f);
		TextUtil.drawText("Ticks this second: " + Game.getTicksThisSecond(), "default", -1f, -.1f, .05f);
		TextUtil.drawText("FPS: " + Game.getFps(), "default", -1f, -.15f, .05f);
		TextUtil.drawText("Scrap: " + (int) player.getStat("SCRAP", 0), "default", -1f, -1f, .05f);
	}
}
