package engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import entities.Entity;
import entities.NPC;
import entities.Player;
import entities.entityHelper.EntityInfo;
import entities.entityHelper.TextEventLoader;

public class Map implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String mapName;
	private int mapSizeX;
	private int mapSizeY;
	private Tile[][] tileList;
	private transient static boolean loadedFromEditor = false;
	private transient boolean[][] walkable;
	private ArrayList<EntitySpawner> spawner = new ArrayList<EntitySpawner>();
	
	//Will be regenerated every map load
	private transient BufferedImage background;
	private transient static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public Map(String name, int sizeX, int sizeY, Tile[][] tiles, ArrayList<EntitySpawner> entitySpawner){
		mapName = name;
		mapSizeX = sizeX;
		mapSizeY = sizeY;
		tileList = tiles;
		spawner = entitySpawner;
		entities = new ArrayList<Entity>();
		generateWalkableSpace(); //set default walkable space
		spawnNPCs(); //spawn all entities (This modifies walkable space)
		generateBackgroundImage(tiles); //Create the background image
	}
	
	private void generateWalkableSpace(){
		//sets the walkable array to all true
		walkable = new boolean[getY()][getX()];
		for(int row = 0; row < getY(); row++){
			for(int col = 0; col < getX(); col++){
				walkable[row][col] = tileList[row][col].getWalkable();
			}
		}
	}
	
	private void spawnNPCs(){
		//Check the maps EntitySpawner list, find the corrisponding EntityInfo, and create entities with them
		for(EntitySpawner e : spawner){
			EntityInfo info = EntityInfo.findByID(e.getID());
			NPC entity = new NPC(info.getName(), e.getX(), e.getY(), e.getMovementStyle(), e.getTextEvent());
			entity.setBlockable(info.isBlockable());
			addObject(entity);
		}
	}
	
	public void despawnNPCs(){
		entities = null;
	}
	
	public static Map createDefaultMap(){
		int x = Blackwind.SQUARESX;
		int y = Blackwind.SQUARESY;
		Tile[][] alltiles = new Tile[y][x];
		for(int i = 0; i < y; i++){
			for(int j = 0; j < x; j++){
				if(i == y-1 || j == x-1 || i == 0 || j == 0){
					alltiles[i][j] = new Tile(Tile.WALL);
				}
				else{
					alltiles[i][j] = new Tile(Tile.GRASS);
				}
			}
		}
		ArrayList<EntitySpawner> defaultMapSpawns = new ArrayList<EntitySpawner>();
		//I want to spawn entity 'A' AT 'X','Y', AND IT MOVES LIKE 'Z'
		defaultMapSpawns.add(new EntitySpawner(0, 4, 6, NPC.MOVEMENT_STILL,TextEventLoader.loadTextEvent(3)));
		defaultMapSpawns.add(new EntitySpawner(1, 5, 11, NPC.MOVEMENT_FREE,TextEventLoader.loadTextEvent(1)));
		defaultMapSpawns.add(new EntitySpawner(2, 10, 5, NPC.MOVEMENT_PATROL,TextEventLoader.loadTextEvent(2)));
		defaultMapSpawns.add(new EntitySpawner(3, 1, 1, NPC.MOVEMENT_AREA,TextEventLoader.loadTextEvent(4)));
		return new Map("TestMap", Blackwind.SQUARESX, Blackwind.SQUARESY, alltiles, defaultMapSpawns);
	}
	
	private void generateBackgroundImage(Tile[][] tiles){
		background = new BufferedImage(mapSizeX*Blackwind.SQUARESIZE, mapSizeY*Blackwind.SQUARESIZE, BufferedImage.TYPE_INT_RGB);
		Graphics g = background.createGraphics();
		for(int row = 0; row < mapSizeY; row++){
			for(int col = 0; col < mapSizeX; col++){
				g.drawImage(tiles[row][col].getImage(), col*Blackwind.SQUARESIZE, row*Blackwind.SQUARESIZE, null);
			}
		}
	}
	
	//Add entities to the map
	protected void addObject(Entity e){
		if(e.isBlockable())
			block(e.getY(), e.getX());
		entities.add(e);
	}
	
	//MOVEMENT
	public void block(int row, int col){
		walkable[row][col] = false;
	}
	
	public void unblock(int row, int col){
		walkable[row][col] = true;
	}
	
	public boolean canMove(int row, int col){
		try{
			return walkable[row][col];
		}catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
	}
	
	//returns all players on the map
	public ArrayList<Player> getPlayers(){
		ArrayList<Player> ret = new ArrayList<Player>();
		for(Entity e : entities)
			if(e instanceof Player)
				ret.add((Player) e);
		return ret;
	}
	
	//changes a tile at row, col, to a set tile.
	public void changeTile(int row, int col, int tileID){
		tileList[row][col] = new Tile(tileID);
	}
	
	//Save and load a map
	public void saveMap(){
		try{
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir")+"/maps/"+getName()+".m"));
			output.writeObject(this);
			output.flush();
			output.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static Map load(String fileName, boolean fromEditor){
		loadedFromEditor = fromEditor;
		if(!fileName.endsWith(".m"))
			fileName += ".m";
		try{
			System.out.println("Loading Map " + fileName);
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(System.getProperty("user.dir")+"/maps/"+fileName));
			Map m = (Map) input.readObject();
			input.close();
			Map ret = new Map(m.getName(), m.getX(), m.getY(), m.getTiles(), m.getSpawners());
			return ret;
		}catch(IOException e){
			System.out.println("Something went wrong.");
			e.printStackTrace();
		}catch(ClassNotFoundException e)
		{
			System.out.println("Map version out of date");
			e.printStackTrace();
		}
		return null;
	}

	//Getters
	public String getName(){return mapName;}
	public int getX(){return mapSizeX;}
	public int getY(){return mapSizeY;}
	public BufferedImage getMapImage(){return background;}
	public boolean[][] getWalkable(){return walkable;}
	public ArrayList<Entity> getEntities(){return entities;}
	public Tile[][] getTiles(){return tileList;}
	public ArrayList<EntitySpawner> getSpawners(){return spawner;}
	public boolean loadedFromEditor(){return loadedFromEditor;}
}
