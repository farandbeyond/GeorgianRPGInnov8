package entities.entityHelper;

import java.io.Serializable;

public class NpcAreaPoint implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	
	public int getX(){return x;}
	public int getY(){return y;}
	public int[] getPoint(){return new int[] {x,y};}
	
	public NpcAreaPoint(int xloc, int yloc){
		x = xloc;
		y = yloc;
	}
}
