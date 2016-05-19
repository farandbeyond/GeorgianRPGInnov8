package entities.entityHelper;

import java.io.Serializable;

public class NpcMovementHelper implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//Enums
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	private static int[] defaultPath = {DOWN,DOWN,DOWN,RIGHT,RIGHT,RIGHT,UP,UP,UP,LEFT,LEFT,LEFT};
	private static int[] defaultAreaX = {1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7};
	private static int[] defaultAreaY = {1,1,1,1,1,1,1,2,2,2,2,2,2,2,3,3,3,3,3,3,3};
	private NpcAreaPoint[] area;
	
	private static boolean mustLoop = true; //the path must start and stop in the same area
	
	private int[] path;
	
	public NpcMovementHelper(){
		setPath(defaultPath);
		setArea(defaultAreaX, defaultAreaY);
	}

	public NpcMovementHelper(int[] directions, boolean loops){ //start PATROL
		mustLoop = loops;
		setPath(directions);
		setArea(defaultAreaX, defaultAreaY);
	}
	
	public NpcMovementHelper(int[] xPoints, int[] yPoints){ //start AREA
		setPath(defaultPath);
		setArea(xPoints, yPoints);
	}
	
	
	private void setPath(int[] directions){ //PATROL
		if(mustLoop){
			int sum = 0;
			for(int move : defaultPath){
				switch(move){
					case UP:sum++;break;
					case DOWN:sum--;break;
					case LEFT:sum-=10000;break;
					case RIGHT:sum+=10000;break;
				}
			}
			if(sum != 0){
				System.err.println("The path does not loop and will be set to null.");
				path = null;
				return;
			}
		}
		path = directions;
	}

	private void setArea(int[] xlocs, int[] ylocs){
		if(xlocs.length != ylocs.length){
			System.err.println("Length of x array and Length of y array are not the same. Cannot create NPCArea");
			return;
		}
		area = new NpcAreaPoint[xlocs.length];
		for(int i = 0; i < xlocs.length; i++)
			area[i] = new NpcAreaPoint(xlocs[i],ylocs[i]);
	}
	
	//only used for MOVEMENT_AREA
	public boolean isValidArea(int checkX, int checkY){
		for(NpcAreaPoint p : area)
			if(p.getX() == checkX && p.getY() == checkY)
				return true;
		return false;
	}
	
	public void setArea(NpcAreaPoint[] newarea){area = newarea;}
	public void setMovementPath(int[] newpath){path = newpath;}
	public NpcAreaPoint[] getArea(){return area;}
	public int[] getPath(){return path;}
}
