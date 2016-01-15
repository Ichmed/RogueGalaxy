package com.ichmed.roguegalaxy.gui;

import com.ichmed.bol2d.Game;
import com.ichmed.bol2d.gui.*;
import com.ichmed.bol2d.gui.GuiButton.ActionHandler;


public class MenuPause extends Menu
{
	public MenuPause()
	{
		GuiButton continueButton = new GuiButton(0.6f, 1.4f, 0.8f, 0.2f);
		continueButton.label = "CONTINUE";
		continueButton.setActionHandler(new ActionHandler()
		{			
			@Override
			public void leftClick()
			{
				disable();
			}
		});
		this.add(continueButton);
		GuiButton exitButton =  new GuiButton(0.6f, .2f, 0.8f, 0.2f);
		exitButton.label = "EXIT";
		exitButton.setActionHandler(new ActionHandler()
		{			
			@Override
			public void leftClick()
			{
				Game.close();
			}
		});
		this.add(exitButton);
	}
}
