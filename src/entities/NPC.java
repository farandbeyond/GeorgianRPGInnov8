package entities;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import entities.entityHelper.NpcMovementHelper;
import entities.entityHelper.TextEvent;


public class NPC extends Entity{
	
	public static final int MOVEMENT_STILL = 0; //Do not move at al
	public static final int MOVEMENT_FREE = 1; //Move with no restrictions
	public static final int MOVEMENT_PATROL = 2; //Move along a set path
	public static final int MOVEMENT_AREA = 3; //Move around inside a set area
	
	private static Random r = new Random();
	
	private int movementStyle;
	//How often this npc moves in frames
	private int manMovementUpdateRate = 180;
	private int minMovementUpdateRate = 120;
	private int movementTicker, nextMovement;
	//For patrol and path movement
	private NpcMovementHelper helper; //The path or area to follow if set to MOVEMENT_PATROL or MOVEMENT_AREA
	int pathCounter = 0; //Count which step we are on in the path
	int oldX, oldY; //used to determine if we have moved yet for patrol.
	//For area movement
	
	public NPC(String name, int startX, int startY, int movement, TextEvent text){
		setName(name);
		setX(startX);
		setY(startY);
		movementStyle = movement;
		if(movementStyle == MOVEMENT_PATROL || movementStyle == MOVEMENT_AREA){
			helper = new NpcMovementHelper();
			oldX = getX();
			oldY = getY();
		}
		resetTicker();
                textEvent = text;
	}
	
	//Movement
	public void move(int speed){
		movementTicker++;
		if(movementTicker >= nextMovement){
		switch(movementStyle){
			//Do not move at all
			case MOVEMENT_STILL:return;
			
			//Move with no restrictions
			case MOVEMENT_FREE:{
				switch(r.nextInt(4)){
					case 0: startMove(Entity.UP);break;
					case 1: startMove(Entity.LEFT);break;
					case 2: startMove(Entity.DOWN);break;
					case 3: startMove(Entity.RIGHT);break;
				}break;
			}
			
			//Move along a set path
			case MOVEMENT_PATROL:{
				if(oldX != getX() || oldY != getY()){
					pathCounter++;
					oldX = getX();
					oldY = getY();
				}
				startMove(getNextPathMovement());
				break;
			}
			
			//Move around inside a set area
			case MOVEMENT_AREA:{
				switch(r.nextInt(4)){
					case 0: if(helper.isValidArea(getX(), getY()-1))startMove(Entity.UP);break;
					case 1: if(helper.isValidArea(getX()-1, getY()))startMove(Entity.LEFT);break;
					case 2: if(helper.isValidArea(getX(), getY()+1))startMove(Entity.DOWN);break;
					case 3: if(helper.isValidArea(getX()+1, getY()))startMove(Entity.RIGHT);break;
				}break;
			}
		}//end switch
		resetTicker();
		}//end if
		super.move(speed);
	}
	
	//Patrol Movement
	private int getNextPathMovement(){
		if(pathCounter >= helper.getPath().length)
			pathCounter = 0;
		int next = helper.getPath()[pathCounter];
		return next;
	}
	
	//movement delay
	private void resetTicker(){
		movementTicker = 0;
		nextMovement = r.nextInt(manMovementUpdateRate - minMovementUpdateRate) + minMovementUpdateRate + 1;
	}
	
	//visuals
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.drawOval(getPosX(), getPosY(), getSize(), getSize());
		g.setColor(Color.MAGENTA);
		g.fillOval(getPosX(), getPosY(), getSize(), getSize());
		drawName(g);
		g.setColor(Color.YELLOW);
		int endX = getDirection() == Entity.LEFT ? -getSize()/2 : getDirection() == Entity.RIGHT ? getSize()/2 : 0;
		int endY = getDirection() == Entity.UP ? -getSize()/2 : getDirection() == Entity.DOWN ? getSize()/2 : 0;
		g.drawLine(getPosX()+getSize()/2, getPosY()+getSize()/2, getPosX()+getSize()/2+endX, getPosY()+getSize()/2+endY);
	}
	
	//SETTERS
	public void setHelper(NpcMovementHelper newHelper){helper = newHelper;}
}
