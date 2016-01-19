package com.ichmed.roguegalaxy.render;

import com.ichmed.bol2d.entity.Entity;
import com.ichmed.bol2d.render.animation.Animation;

public class AnimationOpen extends Animation
{
	public AnimationOpen(Entity target, int length)
	{
		super(target, "default", target.textureName + "_opening", length);
		this.lastTexture = target.textureName + "_open";
		this.doesLoop = false;
	}

}
