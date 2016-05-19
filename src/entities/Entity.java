package entities;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Random;

import menu.battle.BattleInterface;
import menu.battle.helper.EnemyPartyLoader;
import engine.Blackwind;
import entities.entityHelper.TextEvent;

public abstract class Entity{
	
	public static Random r = new Random();
	//Enums
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	//Entity information
	private String name;
	private boolean blockable = true;
	//Locational
	private int x, y, posX, posY;
	//Visual
	private int size = Blackwind.SQUARESIZE;
	private int faceDirection = DOWN;
	//Movement
	private boolean movingUp, movingDown, movingLeft, movingRight, moving = false;
	private int moveAmount = 0;
	private int speed = 2;
        //test
        TextEvent textEvent;
	
	//Abstract Methods
	public abstract void paint(Graphics g);
	
	public void drawName(Graphics g){
		FontMetrics fm = g.getFontMetrics();
		int x = (20 - fm.stringWidth(getName())) / 2;
		int y = (fm.getAscent() + (10 - (fm.getAscent() + fm.getDescent())) / 2);
		g.drawString(getName(), x+getPosX()+7, y+getPosY()-12);
	}
	
	//Movement
	public void move(int speed){
		if(isMoving()){
			//get the direction
			int direction = isMovingUp() ? Entity.UP :
				(isMovingDown() ? Entity.DOWN :
					(isMovingLeft() ? Entity.LEFT :
						(isMovingRight() ? Entity.RIGHT : -1)));
                        setDirection(direction);
			if(direction == -1)
				return;
			//check if movement is invalid (blocked / offscreen)
			int newLocationX = getX() + (direction < 2 ? 0 : direction == 2 ? -1 : 1);
			int newLocationY = getY() + (direction > 1 ? 0 : direction == 0 ? -1 : 1);
			if(!Blackwind.getMap().canMove(newLocationY, newLocationX)){
				stopMove();
				syncPosition();
				return;
			}
			//move
			switch(direction){
				case Entity.UP: setPosY(getPosY()-speed);break;
				case Entity.DOWN: setPosY(getPosY()+speed);break;
				case Entity.LEFT: setPosX(getPosX()-speed);break;
				case Entity.RIGHT: setPosX(getPosX()+speed);break;
			}
			//check if we are done moving
			decrementMoveAmount(speed);
			if(getMoveAmount() == 0){
				if(isBlockable()){
					Blackwind.getMap().block(newLocationY, newLocationX);
					Blackwind.getMap().unblock(getY(), getX());
				}
				setX(newLocationX);
				setY(newLocationY);
				stopMove();
				//after we move, chance for an encounter
				if(isPlayer()){
					int encounter = r.nextInt(100)+1;
					if(encounter <= Blackwind.ENCOUNTER_PERCENTAGE){
						BattleInterface.setEnemyPartyEncounter(EnemyPartyLoader.getRandomEnemyParty());
						Blackwind.setStatus(Blackwind.STATUS_BATTLE);
					}
				}
			}
		}
	}
	
	public void startMove(int direction){
		if(!moving){
			switch(direction){
				case Entity.UP:		movingUp = true;break;
				case Entity.DOWN:	movingDown = true;break;
				case Entity.LEFT:	movingLeft = true;break;
				case Entity.RIGHT:	movingRight = true;break;
			}
			faceDirection = direction;
			moving = true;
			moveAmount = Blackwind.SQUARESIZE;
		}
	}
	public void stopMove(){
		movingUp = false;
		movingDown = false;
		movingLeft = false;
		movingRight = false;
		moving = false;
	}
	
	protected void decrementMoveAmount(int amount){
		moveAmount -= amount;
	}
	
	private void syncPosition(){
		setPosX(getX()*Blackwind.SQUARESIZE);
		setPosY(getY()*Blackwind.SQUARESIZE);
	}
	
	
	//Setters
	//information
	protected void setSpeed(int newSpeed){
		//MUST BE A FACTOR OF THE CURRENT MAIN.SQUARESIZE
		if(Blackwind.SQUARESIZE % newSpeed != 0)
			speed = newSpeed;
	}
	protected void setName(String newName){name = newName;}
	protected void setSize(int newSize){size = newSize;}
	//location
	protected void setX(int newX){
		x = newX;
		setPosX(newX*Blackwind.SQUARESIZE);
	}
	protected void setY(int newY){
		y = newY;
		setPosY(newY*Blackwind.SQUARESIZE);
	}
	protected void setPosX(int newX){posX = newX;} //absolute positioning, should only be used when absolutely necessary
	protected void setPosY(int newY){posY = newY;} //absolute positioning, should only be used when absolutely necessary
	
	public boolean isPlayer(){
		return false;
	}
        
        public void setDirection(int newDirection){
            faceDirection = newDirection;
        }
	
	//GETTERS
	//location
	public int getX(){return x;}
	public int getY(){return y;}
	public int getPosX(){return posX;} //absolute positioning, should only be used when absolutely necessary
	public int getPosY(){return posY;} //absolute positioning, should only be used when absolutely necessary
	public int getSize(){return size;}
	//movement
	public boolean isMovingUp(){return movingUp;}
	public boolean isMovingDown(){return movingDown;}
	public boolean isMovingLeft(){return movingLeft;}
	public boolean isMovingRight(){return movingRight;}
	public boolean isMoving(){return moving;}
	public int getMoveAmount(){return moveAmount;}
	public boolean isBlockable(){return blockable;}
	//information
	public String getName(){return name;}
	public int getSpeed(){return speed;}
	public int getDirection(){return faceDirection;}
	//Blockable
	public void setBlockable(boolean blocks){blockable = blocks;}
        //Text
        public TextEvent getTextEvent(){return textEvent;}
}
