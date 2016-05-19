package entities.entityHelper;

import java.io.Serializable;
import java.util.ArrayList;

//EntityInfo - All copies of his entity share these properties
public class EntityInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private static ArrayList<EntityInfo> allEntities;
	//This arraylist is used in the editor to organize the data
	public static final String[] LABELS  = {"Name", "Blockable?","null","null","null"};

	private String name;
	private boolean blocks;
	
	public EntityInfo(String name, boolean block){
		this.name = name;
		blocks = block;
	}
	
	public static void resetEntities(){
		allEntities = new ArrayList<EntityInfo>();
	}
	
	public static void setEntityArray(ArrayList<EntityInfo> newList){
		allEntities = newList;
	}
	
	public static void addEntity(EntityInfo e){
		allEntities.add(e);
	}
	
	public static EntityInfo findByID(int id){
		return allEntities.get(id);
	}
	
	public String isBlockableStr() {
		if(isBlockable())
			return "True";
		return "False";
	}
	
	//SETTERS
	public void setName(String newName){
		name = newName;
	}
	public void setBlockable(boolean newBlock){
		blocks = newBlock;
	}
	
	//GETTERS
	public static EntityInfo getEntity(int entityID){return allEntities.get(entityID);}
	public static ArrayList<EntityInfo> getAllEntities(){return allEntities;}
	public String getName(){return name;}
	public boolean isBlockable(){return blocks;}
	public static int getEntityCount(){return allEntities.size();}
}
