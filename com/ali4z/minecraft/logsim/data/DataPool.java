package com.ali4z.minecraft.logsim.data;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class DataPool {
	private List<WRNode> nodes;
	
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	
	public DataPool() {
		flush();
	}
	
	public void flush() {
		nodes = new ArrayList<WRNode>();
	}
	
	
	public void addTX(int x, int y, int z, String freq, long time) {
		remTX(x, y, z, freq);
		
		WRNodeTX tx = new WRNodeTX();
		tx.x = x;
		tx.y = y;
		tx.z = z;
		tx.freq = freq;
		tx.state = false;
		tx.time = time;
		
		nodes.add(tx);
	}
	public void remTX(int x, int y, int z, String freq) {
		for ( int i = 0; i < nodes.size(); i++ )
			if ( nodes.get(i) instanceof WRNodeTX && nodes.get(i).equals(x, y, z, freq) )
				nodes.remove(i);
	}
	public void setTXState(int x, int y, int z, String freq, boolean state, long time) {
		for ( int i = 0; i < nodes.size(); i++ ) {
			if ( nodes.get(i) instanceof WRNodeTX && nodes.get(i).equals(x, y, z, freq) ) {
				((WRNodeTX)nodes.get(i)).state = state;
				nodes.get(i).time = time;
			}
		}
	}
	
	public void addRX(int x, int y, int z, String freq, long time) {
		remRX(x, y, z, freq);
		
		WRNodeRX rx = new WRNodeRX();
		rx.x = x;
		rx.y = y;
		rx.z = z;
		rx.freq = freq;
		rx.time = time;
		
		nodes.add(rx);
	}
	public void remRX(int x, int y, int z, String freq) {
		for ( int i = 0; i < nodes.size(); i++ )
			if ( nodes.get(i) instanceof WRNodeRX && nodes.get(i).equals(x, y, z, freq) )
				nodes.remove(i);
	}
	
	public List<WRNode> getNodes() {
		return nodes;
	}
	
	public void updateMinMax(int orientation) {
		int minX = 0;
		int maxX = 0;
		int minY = 0;
		int maxY = 0;
		
		switch (orientation) {
			case 0:

				for ( WRNode node: nodes ) {
					if ( minX == 0 || node.x < minX ) {
						minX = node.x;
					}
					if ( maxX == 0 || node.x > maxX ) {
						maxX = node.x;
					}
					if ( minY == 0 || node.z < minY ) {
						minY = node.z;
					}
					if ( maxY == 0 || node.z > maxY ) {
						maxY = node.z;
					}
				}
				break;
			case 1:
				for ( WRNode node: nodes ) {
					if ( minX == 0 || node.x < minX ) {
						minX = node.x;
					}
					if ( maxX == 0 || node.x > maxX ) {
						maxX = node.x;
					}
					if ( minY == 0 || node.y < minY ) {
						minY = node.y;
					}
					if ( maxY == 0 || node.y > maxY ) {
						maxY = node.y;
					}
				}
				break;
			case 2:
				for ( WRNode node: nodes ) {
					if ( minX == 0 || node.y < minX ) {
						minX = node.y;
					}
					if ( maxX == 0 || node.y > maxX ) {
						maxX = node.y;
					}
					if ( minY == 0 || node.z < minY ) {
						minY = node.z;
					}
					if ( maxY == 0 || node.z > maxY ) {
						maxY = node.z;
					}
				}
				break;
		}
		
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}
	

	public void render(Graphics g, int width, int height, int orientation, long currdate)  {
		float xMul = 1;
		if (maxX-minX+16 > width/16 && maxX-minX+16 != 0 )
			xMul = ((float)width/16f) / (float)(maxX-minX+16);
		
		float yMul = 1;
		if (maxY-minY+16 > height/16 && maxY-minY+16 != 0 )
			yMul = ((float)height/16) / (float)(maxY-minY+16);
		
	
		for ( WRNode node: nodes ) {
			int x = node.x;
			int y = node.z;
			switch (orientation) {
				case 1:
					x = node.x;
					y = node.y;
					break;
				case 2:
					x = node.y;
					y = node.z;
					break;
			}
			
			if ( node instanceof WRNodeRX ) {
				g.setColor(Color.yellow);
				if ( currdate == node.time )
					g.fillOval((x-minX)*16*xMul, (y-minY)*16*yMul, 16*xMul, 16*yMul);
				
				boolean state = false;
				for ( WRNode node2: nodes ) {
					if ( node2 instanceof WRNodeTX && node2.freq.equals(node.freq) && ((WRNodeTX)node2).state) {
						state = true;
						break;
					}
				}
				if ( state )
					g.setColor(Color.green);
				else
					g.setColor(Color.red);
				
				g.drawOval((x-minX)*16*xMul, (y-minY)*16*yMul, 16*xMul, 16*yMul);
				g.drawString(node.freq, (x-minX)*16*xMul, (y-minY)*16*yMul);
			} else if ( node instanceof WRNodeTX ) {
				g.setColor(Color.yellow);
				if ( currdate == node.time )
					g.fillRect((x-minX)*16*xMul, (y-minY)*16*yMul, 16*xMul, 16*yMul);
				
				if ( ((WRNodeTX)node).state )
					g.setColor(Color.green);
				else
					g.setColor(Color.red);

				g.drawRect((x-minX)*16*xMul, (y-minY)*16*yMul, 16*xMul, 16*yMul);
				g.drawString(node.freq, (x-minX)*16*xMul, (y-minY)*16*yMul);
			}
		}

		g.setColor(Color.white);
		for ( WRNode node: nodes ) {
			
			int x = node.x;
			int y = node.z;
			switch (orientation) {
				case 1:
					x = node.x;
					y = node.y;
					break;
				case 2:
					x = node.y;
					y = node.z;
					break;
			}
			
			g.pushTransform();
			g.rotate((x-minX)*16*xMul+16, (y-minY)*16*yMul, 30);
			g.drawString(node.x+","+node.y+","+node.z, (x-minX)*16*xMul+16, (y-minY)*16*yMul);
			g.popTransform();
		}
	}
	
	private abstract class WRNode {
		public int x;
		public int y;
		public int z;
		public String freq;
		public long time;
		
		@Override
		public boolean equals(Object node) {
			return (
					node instanceof WRNode &&
					x == ((WRNode)node).x &&
					y == ((WRNode)node).y &&
					z == ((WRNode)node).z &&
					freq.equals(((WRNode)node).freq)
			);
		}
		
		public boolean equals(int x, int y, int z, String freq) {
			return (
					x == this.x &&
					y == this.y &&
					z == this.z &&
					freq.equals(this.freq)
			);
		}

		public String toString() {
			return "("+x+","+y+","+z+")";
		}
	}
	
	private class WRNodeTX extends WRNode {
		public boolean state;
	}
	
	private class WRNodeRX extends WRNode {
	}
}
