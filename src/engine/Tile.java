package engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Tile implements Serializable{
	private static final long serialVersionUID = 1L;

	private static final int NUMBER_OF_TILES = 2;
	private static BufferedImage[] tiles_images = new BufferedImage[NUMBER_OF_TILES];
	private static String[] tiles_names = new String[NUMBER_OF_TILES];
	private static boolean[] tiles_walkable = new boolean[NUMBER_OF_TILES];
	
	public static final int GRASS = 0;
	public static final int WALL = 1;
	
	private int tileID;
	
	public Tile(int id){
		tileID = id;
	}
	
	public BufferedImage getImage(){return tiles_images[tileID];}
	public String getTileName(){return tiles_names[tileID];}
	public boolean getWalkable(){return tiles_walkable[tileID];}
	
	public static void loadImages(){
		try{
			setTile(0, ImageIO.read(new File("images/grass.png")), "Grass", true);
			setTile(1, ImageIO.read(new File("images/wall.png")), "Wall", false);
			System.out.println("Finished loading tiles");
		}catch (IOException e){
			System.out.println("Failed loading tiles");
			e.printStackTrace();
		}
	}
	
	private static void setTile(int index, BufferedImage image, String name, boolean walkable){
		tiles_images[index] = image;
		tiles_names[index] = name;
		tiles_walkable[index] = walkable;
	}
	
	public static BufferedImage[] getAllImages(){return tiles_images;}
}
