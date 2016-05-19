package engine;

import handlers.PlayerHandle;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import menu.battle.BattleInterface;
import menu.battle.helper.*;
import menu.inventory.MenuHandlerFrame;
import engine.editor.Editor;
import engine.editor.EntityEditor;
import entities.Entity;
import entities.Player;
import entities.entityHelper.TextEvent;


public class Blackwind extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;

	//Map Size
	public static final int SQUARESIZE = 32;
	public static final int SQUARESX = 19;
	public static final int SQUARESY = 15;
	public static final int WIDTH = SQUARESIZE * SQUARESX;
	public static final int HEIGHT = SQUARESIZE * SQUARESY;
	
	//Swing
	private static JFrame window;
	private static Blackwind game;
	
	//Map
	private static Map map;
	public static final int ENCOUNTER_PERCENTAGE = 9;
	
	//Thread
	private static boolean gameRunning = false;
	
	//Game Vars
	public static final int STATUS_OVERWORLD = 0,
                                STATUS_PAUSED = 1,
                                STATUS_BATTLE = 2,
                                STATUS_TEXTEVENT = 3;
	private static int status = STATUS_OVERWORLD;
	
	public static final String PLAYERNAME = "Player 1";
	private static Player player;
	
	//Game Screens
	private static Party party = new Party(4);
	private static Inventory inventory = new Inventory(10);
	private static MenuHandlerFrame menu_inventory_handler;
	private static BattleInterface battle_interface;
	private static Thread menuThread, battleThread, textThread;
        private static TextEvent currentTextEvent;
	
	public Blackwind(){
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
	}
	
	public static void main(String[] args){
		System.out.println("Starting Up - Game");
		System.out.println("======================");
		window = new JFrame("Untitled");
		System.out.println("Loading Tiles...");
		Tile.loadImages();
		System.out.println("Loading Entities...");
		EntityEditor.loadEntities();
		System.out.println("Loading Map...");
		map = Map.load("TestMap", false);
	//	map = Map.createDefaultMap();
		System.out.println("Starting Game...");
		game = new Blackwind();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(game);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		player = new Player(PLAYERNAME,16,5);
		map.addObject(player);
		System.out.println("Creating Party...");
		party.add(EntityLoader.loadEntity(EntityLoader.PLAYER));
		party.add(EntityLoader.loadEntity(EntityLoader.WILSON));
		party.add(EntityLoader.loadEntity(EntityLoader.MATILDA));
		party.getMemberFromParty(0).addBattleAction(BattleActionLoader.loadAction(BattleActionLoader.FIREBALL));
		party.getMemberFromParty(0).addBattleAction(BattleActionLoader.loadAction(BattleActionLoader.CURE));
                party.getMemberFromParty(1).addBattleAction(BattleActionLoader.loadAction(BattleActionLoader.FIREBALL));
		party.getMemberFromParty(2).addBattleAction(BattleActionLoader.loadAction(BattleActionLoader.CURE));
		PlayerHandle handler = new PlayerHandle(map.getPlayers().get(0));
		menu_inventory_handler = new MenuHandlerFrame(inventory, party);
		window.add(menu_inventory_handler);
		window.addKeyListener(handler);
		inventory.add(ItemLoader.loadItem(ItemLoader.POTION, 10));
                inventory.add(ItemLoader.loadItem(ItemLoader.ELIXER, 10));
                inventory.add(ItemLoader.loadItem(ItemLoader.REJUVI, 10));
                inventory.add(ItemLoader.loadItem(ItemLoader.PHEONIXDOWN, 3));
                inventory.add(ItemLoader.loadItem(ItemLoader.IRONSWORD, 2));
                inventory.add(ItemLoader.loadItem(ItemLoader.IRONSWORD, 2));
                inventory.add(ItemLoader.loadItem(ItemLoader.LEATHERARMOR, 2));
                inventory.add(ItemLoader.loadItem(ItemLoader.LEATHERARMOR, 2));
                inventory.add(ItemLoader.loadItem(ItemLoader.LEATHERGLOVES, 2));
                inventory.add(ItemLoader.loadItem(ItemLoader.ICEBOMB, 5));
                inventory.add(ItemLoader.loadItem(ItemLoader.FIREBOMB, 5));
                
		Thread gameThread = new Thread(game);
		gameThread.start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		        gameRunning = false;
		        map.saveMap();
		    }
		});
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//draw the map
                switch(status){
                case(STATUS_TEXTEVENT):
			g.drawImage(map.getMapImage(), 0, 0, null);
			//draw the entities(over the map)
			for(Entity e : map.getEntities())
				e.paint(g);
			currentTextEvent.paint(g);
                    break;
		case(STATUS_OVERWORLD):
			g.drawImage(map.getMapImage(), 0, 0, null);
			//draw the entities(over the map)
			for(Entity e : map.getEntities())
				e.paint(g);
                    break;
		case(STATUS_PAUSED):
			menu_inventory_handler.paint(g);
                    break;
		case(STATUS_BATTLE):
			battle_interface.paint(g);
                    break;
                }
	}

	//RUNNABLE
	public void run() {
		gameRunning = true;
		while(gameRunning){
			repaint();
			switch(status){
			case STATUS_OVERWORLD:
				for(Entity e : map.getEntities())
					e.move(e.getSpeed());
				break;
			}
			try{
				Thread.sleep(1000/60);//60 FPS, roughly
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}	
	
	public static void setStatus(int newStatus) {
            System.out.println(newStatus);
		status = newStatus;
		switch(status){
			//When we switch to overworld
			case STATUS_OVERWORLD:{
				try{
					if(menuThread != null && menuThread.isAlive()){
                                            menuThread = null;
                                        }
                                        else if(battleThread != null && battleThread.isAlive()){
                                            battleThread = null;
                                        }
                                        else if(textThread != null && textThread.isAlive() && currentTextEvent != null){
						System.out.println("test1");textThread.join();
                                        }
				}catch(InterruptedException e){
					Editor.generateError("A Threading error has occured", e);
				}break;
			}
			case STATUS_PAUSED:{
				menuThread = new Thread(menu_inventory_handler);
				menuThread.start();
                                break;
			}
			case STATUS_BATTLE:{
				battle_interface = new BattleInterface(party,BattleInterface.getEnemyParty(),inventory);
				battleThread = new Thread(battle_interface);
				battleThread.start();
                                break;
			}
			case STATUS_TEXTEVENT:{
                                currentTextEvent = null;
                                int offsetX = player.getDirection() == Entity.LEFT ? 1 : player.getDirection() == Entity.RIGHT ? -1 : 0;
                                int offsetY = player.getDirection() == Entity.UP ? 1 : player.getDirection() == Entity.DOWN ? -1 : 0;
				for(Entity e : map.getEntities()){
                                    if(e.getX()+offsetX == player.getX() && e.getY()+offsetY == player.getY()){
                                        e.setDirection(player.getDirection()%2==0? player.getDirection()+1:player.getDirection()-1);
                                        System.out.println(e.getName());
                                        currentTextEvent = e.getTextEvent();
                                        break;
                                    }
                                }
				if(currentTextEvent == null)
                                    setStatus(STATUS_OVERWORLD);
                                else{
                                    textThread = new Thread(currentTextEvent);
                                    textThread.start();
                                }
                                break;
			}
		}
	}
	
	//SETTERS AND GETTERS
	public static JFrame getFrame(){return window;}
	public static Map getMap(){return map;}
	public static int getStatus(){return status;}
	public static MenuHandlerFrame getInventoryMenu(){return menu_inventory_handler;}
	public static Inventory getInventory(){return inventory;}
	public static Party getParty(){return party;}
	public static Player getPlayer(){return player;}
	public static Thread getBattleThread(){return battleThread;}
	public static BattleInterface getBattleInterface(){return battle_interface;}
}
