package com.ali4z.minecraft.logsim;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.ali4z.minecraft.logsim.business.LogFileDataParser;
import com.ali4z.minecraft.logsim.data.DataPool;
import com.ali4z.minecraft.logsim.data.LogFile;
import com.ali4z.minecraft.logsim.data.LogFileData;

public class WirelessRedstoneLogSimView extends BasicGameState {
	private int stateID;
	
	private LogFile logFile;
	
	private DataPool clientPool;
	private DataPool serverPool;
	
	private int currentId = 0;
	private int nextId = 0;
	private Long lastTime;
	private Long lastUpdateTime;
	
	private List<LogFileData> currentClientData;
	private List<LogFileData> currentServerData;
	
	private boolean auto = false;
	private int speed = 1;
	
	private int orientation = 0;
	
	private long startTime = 0;
	private long endTime = 0;
	
	private String clientBGString = "";
	private String serverBGString = "";
	private Color bgFade = new Color(0,0,0,200);
	
	public WirelessRedstoneLogSimView(int stateID) {
		this.stateID = stateID;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		logFile = new LogFile("wirelessRedstone.log");
		clientPool = new DataPool();
		serverPool = new DataPool();
		
		lastTime = System.currentTimeMillis();
		lastUpdateTime = System.currentTimeMillis();
		
		currentClientData = logFile.getData(logFile.getTimes().get(currentId),0);
		currentServerData = logFile.getData(logFile.getTimes().get(currentId),1);
			
		
		startTime = logFile.getTimes().get(0);
		endTime = logFile.getTimes().get(logFile.getTimes().size()-1);
		
		gc.setShowFPS(false);
		gc.setAlwaysRender(true);
		gc.setTargetFrameRate(100);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.drawString("CLIENT", 0, 0);
		g.drawString("SERVER", gc.getWidth()/2, 0);
		
		g.pushTransform();
			g.translate(0, 16);
			renderClientPool(gc,g);
				g.pushTransform();
					g.translate(gc.getWidth()/2, 0);
					renderServerPool(gc,g);
				g.popTransform();
		
		g.popTransform();
		
		renderTimeLine(gc, g);
		renderOrientation(gc, g);
	}
	private void renderClientPool(GameContainer gc, Graphics g) {
		Long time = startTime;
		if ( currentClientData.size() > 0 )
			time = currentClientData.get(0).getTime();	

		g.setColor(Color.black);
		g.fillRect(0, 0, gc.getWidth()/2, gc.getHeight()-40 );
		g.setColor(Color.white);
		g.drawRect(0, 0, gc.getWidth()/2, gc.getHeight()-40 );
		g.drawString(clientBGString, 0, 50);
		g.setColor(bgFade);
		g.fillRect(0, 0, gc.getWidth()/2, gc.getHeight()-40 );
		
		clientPool.render(
				g, 
				gc.getWidth()/2, gc.getHeight()-40, 
				orientation, 
				time
		);
	}
	private void renderServerPool(GameContainer gc, Graphics g) {
		Long time = startTime;
		if ( currentServerData.size() > 0 )
			time = currentServerData.get(0).getTime();	

		g.setColor(Color.black);
		g.fillRect(0, 0, gc.getWidth()/2, gc.getHeight()-40 );
		g.setColor(Color.white);
		g.drawRect(0, 0, gc.getWidth()/2, gc.getHeight()-40 );
		g.drawString(serverBGString, 0, 50);
		g.setColor(bgFade);
		g.fillRect(0, 0, gc.getWidth()/2, gc.getHeight()-40 );
		
		serverPool.render(
				g, 
				gc.getWidth()/2, gc.getHeight()-40, 
				orientation, 
				time
		);
	}
	private void renderTimeLine(GameContainer gc, Graphics g) {
		float mul = 0;
		double time = 0;
		if ( currentClientData.size() > 0 && currentClientData.get(0).getTime()-startTime > 0 ) {
			mul = (float)(currentClientData.get(0).getTime()-startTime) / (float)(endTime-startTime);
			time = currentClientData.get(0).getTime();
		} else if ( currentServerData.size() > 0 && currentServerData.get(0).getTime()-startTime > 0 ) {
			mul = (float)(currentServerData.get(0).getTime()-startTime) / (float)(endTime-startTime);
			time = currentServerData.get(0).getTime();
		}

		// Draw progress bar.
		g.setColor(Color.red);
		g.fillRect(
				10, 
				gc.getHeight()-20,
				((gc.getWidth()-20))*mul, 
				20
		);
		
		// Draw horisontal line.
		g.setColor(Color.white);
		g.drawLine(
				10, 
				gc.getHeight()-10, 
				gc.getWidth()-10, 
				gc.getHeight()-10
		);
		
		// Draw time points.
		float mul2;
		for ( Long t: logFile.getTimes() ) {
			mul2 = 0;
			if ( t-startTime > 0 )
				mul2 = (float)(t-startTime) / (float)(endTime-startTime);

			float x = 10+((gc.getWidth()-20))*mul2;
			
			g.drawLine(
					x, 
					gc.getHeight(), 
					x, 
					gc.getHeight()-20
			);
			
			if ( t == time ) {
				g.pushTransform();
				g.rotate(
						x-10, 
						gc.getHeight()-20,
						-90
				);
				g.drawString(
						Long.toString(t),
						x-10, 
						gc.getHeight()-20
				);
				g.popTransform();
				
			}
		}
	}
	private void renderOrientation(GameContainer gc, Graphics g) {
		String x = "";
		Color cx = Color.white;
		String y = "";
		Color cy = Color.white;
		switch (orientation) {
			case 0:
				x = "X";
				cx = Color.red;
				y = "Z";
				cy = Color.blue;
				break;
			case 1:
				x = "X";
				cx = Color.red;
				y = "Y";
				cy = Color.green;
				break;
			case 2:
				x = "Y";
				cx = Color.green;
				y = "Z";
				cy = Color.blue;
				break;
		}
		g.setColor(cx);
		g.drawLine(
			gc.getWidth()/2-10, 
			gc.getHeight()/2-10, 
			gc.getWidth()/2+10,
			gc.getHeight()/2-10
		);
		g.drawString(
				x, 
				gc.getWidth()/2-4,
				gc.getHeight()/2-28
		);
		
		g.setColor(cy);
		g.drawLine(
			gc.getWidth()/2-10, 
			gc.getHeight()/2-10, 
			gc.getWidth()/2-10,
			gc.getHeight()/2+10
		);
		g.drawString(
				y, 
				gc.getWidth()/2-24,
				gc.getHeight()/2-10
		);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		updateInput(gc);
		
		if ( auto && System.currentTimeMillis()-(100*speed) > lastUpdateTime ) {
			lastUpdateTime = System.currentTimeMillis();
			nextStep();
		}
		if ( currentId != nextId ) {
			currentId = nextId;
			updateData();
		}
	}
	private void updateInput(GameContainer gc) {
		if ( System.currentTimeMillis()-200 > lastTime ) {
			if ( gc.getInput().isKeyDown(Input.KEY_ENTER) ) {
				nextStep();
				lastTime = System.currentTimeMillis();
			}
			if ( gc.getInput().isKeyDown(Input.KEY_A) ) {
				toggleAuto();
				lastTime = System.currentTimeMillis();
			}
			if ( gc.getInput().isKeyDown(Input.KEY_O) ) {
				nextOrientation();
				lastTime = System.currentTimeMillis();
			}
			if ( gc.getInput().isKeyDown(Input.KEY_1)  ) {
				setSpeed(1);
				lastTime = System.currentTimeMillis();
			} 
			if ( gc.getInput().isKeyDown(Input.KEY_2)  ) {
				setSpeed(2);
				lastTime = System.currentTimeMillis();
			} 
			if ( gc.getInput().isKeyDown(Input.KEY_3)  ) {
				setSpeed(3);
				lastTime = System.currentTimeMillis();
			} 
			if ( gc.getInput().isKeyDown(Input.KEY_4)  ) {
				setSpeed(4);
				lastTime = System.currentTimeMillis();
			} 
			if ( gc.getInput().isKeyDown(Input.KEY_5)  ) {
				setSpeed(5);
				lastTime = System.currentTimeMillis();
			} 
			if ( gc.getInput().isKeyDown(Input.KEY_6)  ) {
				setSpeed(6);
				lastTime = System.currentTimeMillis();
			} 
			if ( gc.getInput().isKeyDown(Input.KEY_7)  ) {
				setSpeed(7);
				lastTime = System.currentTimeMillis();
			} 
			if ( gc.getInput().isKeyDown(Input.KEY_8)  ) {
				setSpeed(8);
				lastTime = System.currentTimeMillis();
			} 
			if ( gc.getInput().isKeyDown(Input.KEY_9)  ) {
				setSpeed(9);
				lastTime = System.currentTimeMillis();
			}
			if ( gc.getInput().isKeyDown(Input.KEY_0)  ) {
				setSpeed(10);
				lastTime = System.currentTimeMillis();
			}
		}
	}
	private void toggleAuto() {
		auto = !auto;
	}
	private void nextStep() {
		nextId++;
		
		if ( nextId > logFile.getTimes().size()-1 ) {
			nextId = 0;
			clientPool.flush();
			serverPool.flush();
		}
	}
	private void nextOrientation() {
		orientation++;
		if ( orientation > 2 ) orientation = 0;
		
		serverPool.updateMinMax(orientation);
		clientPool.updateMinMax(orientation);
	}
	private void setSpeed(int i) {
		speed = i;
	}
	private void updateData() {
		currentClientData = logFile.getData(logFile.getTimes().get(currentId), 0);
		currentServerData = logFile.getData(logFile.getTimes().get(currentId), 1);
		
		clientBGString = "";
		for ( LogFileData data: currentClientData ) {
			clientBGString += data.getTheClass()+"->"+data.getTheMethod()+"\n";
			LogFileDataParser.parseData(clientPool, data);
		}

		serverBGString = "";
		for ( LogFileData data: currentServerData ) {
			serverBGString += data.getTheClass()+"->"+data.getTheMethod()+"\n";
			LogFileDataParser.parseData(serverPool, data);
		}
		
		serverPool.updateMinMax(orientation);
		clientPool.updateMinMax(orientation);
	}
	
	@Override
	public int getID() {
		return stateID;
	}

}
