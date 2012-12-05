package com.ali4z.minecraft.logsim;


import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class WirelessRedstoneLogSim extends StateBasedGame {
	public static final int MAINSTATE = 0;
	
	public WirelessRedstoneLogSim() {
		super("Wireless Redstone LogSim");
		
		this.addState(new WirelessRedstoneLogSimView(MAINSTATE));
		
		this.enterState(MAINSTATE);
	}

	public static void main(String[] args) throws SlickException {
		System.setProperty(
				"org.lwjgl.librarypath", 
				new File(
						new File(
								System.getProperty("user.dir"),
								"native"
						),
						LWJGLUtil.getPlatformName()
				).getAbsolutePath()
		);
		System.setProperty(
				"net.java.games.input.librarypath", 
				System.getProperty("org.lwjgl.librarypath")
		);

		AppGameContainer app = new AppGameContainer(new WirelessRedstoneLogSim());

		app.setDisplayMode(1024, 800, false);
		app.start();
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		//this.getState(MAINSTATE).init(gameContainer, this);
		//this.getState(CREDSTATE).init(gameContainer, this);
	}
}
