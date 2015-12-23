package com.ichmed.bol2d.world;

import static org.lwjgl.opengl.GL11.*;

import java.util.*;

import org.lwjgl.util.vector.Vector2f;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.entity.*;
import com.ichmed.bol2d.entity.player.EntityPlayer;
import com.ichmed.bol2d.util.MathUtil;
import com.ichmed.bol2d.util.render.RenderUtil;

public abstract class World
{
	private List<Entity> currentEntities = new ArrayList<Entity>();
	private List<Entity> nextEntities = new ArrayList<Entity>();
	public EntityPlayer player;
	private float RENDER_RANGE = 1200;
	private int renderRangeLastTick = 0;
	private List<Entity> entitiesInRenderRange = null;
	private float UPDATE_RANGE = 2000;
	private int updateRangeLastTick = 0;
	public int currentParticles = 0;
	private List<Entity> entitiesToCleanup = new ArrayList<Entity>();
	private List<Entity> entitiesInUpdateRange = null;

	public void updateAllEntities()
	{
		this.nextEntities = new ArrayList<Entity>(this.currentEntities);
		for (Entity e : getEntitesInUpdateRange())
			e.onUpdate();

		for (Entity e : entitiesToCleanup)
			e.performCleanup();
		this.currentEntities = nextEntities;
	}

	public void drawAllEntities()
	{
		glPushMatrix();
		RenderUtil.centerViewOnEntity(player);
		for (int i = 0; i < 5; i++)
			for (Entity e : getEntitesInRenderRange())
			{
				e.draw(i);
			}
		glPopMatrix();
		RenderUtil.resetLastEntityCentered();
	}

	protected List<Entity> getEntitesInRenderRange()
	{
		if (this.entitiesInRenderRange == null || renderRangeLastTick < Game.getTicksTotal())
		{
			renderRangeLastTick = Game.getTicksTotal();
			entitiesInRenderRange = new ArrayList<Entity>();
			for (Entity e : currentEntities)
				if (MathUtil.compareVectorToLength(Vector2f.sub(player.getCenter(), e.getCenter(), null), RENDER_RANGE) < 0) entitiesInRenderRange.add(e);
		}
		return entitiesInRenderRange;
	}

	protected List<Entity> getEntitesInUpdateRange()
	{
		if (this.entitiesInUpdateRange == null || updateRangeLastTick < Game.getTicksTotal())
		{
			updateRangeLastTick = Game.getTicksTotal();
			entitiesInUpdateRange = new ArrayList<Entity>();
			for (Entity e : currentEntities)
			{
				if (MathUtil.compareVectorToLength(Vector2f.sub(player.getCenter(), e.getCenter(), null), UPDATE_RANGE) < 0) entitiesInUpdateRange.add(e);
				else entitiesToCleanup.add(e);
			}
		}
		return entitiesInUpdateRange;
	}

	public void update()
	{
		// if(Game.getTicksTotal() < 1) t =
		// Texture.makeTexture("resc/texture/player1.png", "png");
		glColor3f(1, 1, 1);
		updateAllEntities();
		drawAllEntities();
		drawHud();
		Game.updateKeyMaps();
	}

	public abstract void drawHud();

	public void spawn(Entity e)
	{
		if (e.getType() == EntityType.PARTICLE)
		{
			if (currentParticles >= Game.getCurrentGame().getParticleMax()) return;
			currentParticles++;
		}
		this.nextEntities.add(e);
		e.onSpawn();
	}

	public void removeEntity(Entity e)
	{
		if (this.nextEntities.remove(e)) if (e.getType() == EntityType.PARTICLE) currentParticles--;
	}

	public List<Entity> getOverlappingEntities(Entity e)
	{
		return getOverlappingEntities(e, true);
	}

	public List<Entity> getOverlappingEntities(Entity e, boolean solidsOnly)
	{
		return getOverlappingEntities(e, currentEntities, solidsOnly);
	}

	public List<Entity> getOverlappingEntities(Entity e, List<Entity> l, boolean solidsOnly)
	{
		List<Entity> ret = new ArrayList<Entity>();
		l = sortListByDistance(e, l, e.getSize().x + e.getSize().y);
		for (Entity d : l)
		{
			if (e == d) continue;
			if (solidsOnly && !d.isSolid) continue;
			Vector2f v = e.getPosition();
			if (MathUtil.isPointInArea(new Vector2f(v.x, v.y), d.getPosition(), d.getSize()) || MathUtil.isPointInArea(new Vector2f(v.x + e.getSize().x, v.y), d.getPosition(), d.getSize())
					|| MathUtil.isPointInArea(new Vector2f(v.x + e.getSize().x, v.y + e.getSize().y), d.getPosition(), d.getSize())
					|| MathUtil.isPointInArea(new Vector2f(v.x, v.y + e.getSize().y), d.getPosition(), d.getSize())) ret.add(d);
		}

		return ret;
	}

	public List<Entity> sortListByDistance(Entity center, List<Entity> list)
	{
		return sortListByDistance(center, list, -1);
	}

	public List<Entity> sortListByDistance(Entity center, List<Entity> list, float maxDist)
	{
		if (maxDist >= 0)
		{
			ArrayList<Entity> l = new ArrayList<Entity>();
			for (Entity e : list)
				if (MathUtil.compareVectorToLength(Vector2f.sub(center.getCenter(), e.getCenter(), null), maxDist) <= 0) l.add(e);
			list = l;
		}

		list.sort(new Comparator<Entity>()
		{
			@Override
			public int compare(Entity o1, Entity o2)
			{
				Vector2f v1 = Vector2f.sub(center.getCenter(), o1.getCenter(), null);
				Vector2f v2 = Vector2f.sub(center.getCenter(), o2.getCenter(), null);
				float f = MathUtil.compareVectorToVector(v1, v2);
				return f > 0 ? 1 : (f < 0 ? -1 : 0);
			}
		});

		return list;
	}

	public List<Entity> selectFromListByType(List<Entity> list, EntityType type)
	{
		ArrayList<Entity> l = new ArrayList<Entity>();
		for (Entity e : list)
			if (e.getType() == type) l.add(e);
		return l;
	}

	public List<Entity> getCurrentEntities()
	{
		return currentEntities;
	}
}
