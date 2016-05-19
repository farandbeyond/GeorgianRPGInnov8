package engine;

import java.io.Serializable;

import entities.entityHelper.EntityInfo;
import entities.entityHelper.NpcMovementHelper;
import entities.entityHelper.TextEvent;

//EntitySpawner - Things that can be changed between multiple copies of this entity
public class EntitySpawner implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int entityID;
	private int spawnX, spawnY;
	private int movementStyle;
	private NpcMovementHelper movementHelper;
        private TextEvent textEvent;
	
	public EntitySpawner(int ID, int x, int y, int movement, TextEvent t){
            textEvent = t;
		entityID = ID;
		spawnX = x;
		spawnY = y;
		movementStyle = movement;
		movementHelper = new NpcMovementHelper(); //If left out, use default
	}
	
	public EntitySpawner(int ID, int x, int y, int movement, NpcMovementHelper helper, TextEvent t){
                textEvent = t;
		entityID = ID;
		spawnX = x;
		spawnY = y;
		movementStyle = movement;
		movementHelper = helper;
	}
	
	public String toString(){
		return String.format("%s at %s:%s", EntityInfo.getAllEntities().get(getID()).getName(), getX(), getY());
		// example: "Still Guy at 4:4"
	}
	
	//GETTERS
	public int getX(){return spawnX;}
	public int getY(){return spawnY;}
	public int getID(){return entityID;}
	public int getMovementStyle(){return movementStyle;}
	public NpcMovementHelper getMovementHelper(){return movementHelper;}
        public TextEvent getTextEvent(){return textEvent;}
}
