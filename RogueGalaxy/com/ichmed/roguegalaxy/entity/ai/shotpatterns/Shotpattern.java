package com.ichmed.roguegalaxy.entity.ai.shotpatterns;

import java.util.List;

import com.ichmed.bol2d.entity.Entity;

public abstract class Shotpattern
{
	public abstract List<Entity> perform(List<Entity> list, int iteration, Object... args);
}
