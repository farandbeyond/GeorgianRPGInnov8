package engine.editor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import engine.*;
import entities.entityHelper.EntityInfo;

public class Editor extends JPanel implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;
	
	private static Editor editor;
	private static JFrame window;
	//MENU BAR
	private static JMenuBar menuBar;
	private static JMenu file;
	private static JMenuItem file_newmap;
	private static JMenuItem file_savemap;
	private static JMenuItem file_loadmap;
	private static JMenuItem file_closemap;
	private static JMenu edit;
	private static JMenuItem edit_entity;
	private static JMenuItem edit_tile;
	//Entity Editor
	private static boolean entityEditorOpen = false;
	private static EntityEditor entityEditor;
	private static JFrame entityEditorWindow;
	//Map and Map Editor
	private static boolean mapLoaded = false;
	private static boolean mapChanged = false;
	private static Map loadedMap;
	private static JComboBox<String> editType;
	//Edit types for the Editor type drop down menu
	public static final int EDIT_TILE = 0;
	public static final int EDIT_ENTITIES = 1;
	//Edit Selectors
	private static JComboBox<ImageIcon> tileSelecterLeft;
	private static JComboBox<ImageIcon> tileSelecterRight;
	private static JComboBox<String> npcSelecter;
	//Selected Entity Info
	private static EntitySpawner selectedSpawner = null;
	
	public Editor(){
		setPreferredSize(new Dimension(Blackwind.SQUARESX*32+200,Blackwind.SQUARESY*32));
		setLayout(null);
		//Setup the MapEditor Combo Box's
		editType = new JComboBox<String>();
		editType.addItem("Tile Edit Mode");
		editType.addItem("Entity Edit Mode");
		editType.setBounds(620,10,160,30);
		add(editType);
		
		tileSelecterLeft = new JComboBox<ImageIcon>();
		tileSelecterRight = new JComboBox<ImageIcon>();
		tileSelecterLeft.setBounds(620,50,60,30);
		tileSelecterRight.setBounds(720,50,60,30);
		for(BufferedImage b : Tile.getAllImages()){
			tileSelecterLeft.addItem(new ImageIcon(b));
			tileSelecterRight.addItem(new ImageIcon(b));
		}
		add(tileSelecterLeft);
		add(tileSelecterRight);
		
		npcSelecter = new JComboBox<String>();
		npcSelecter.setBounds(670,90,110,30);
		for(EntityInfo e : EntityInfo.getAllEntities())
			npcSelecter.addItem(e.getName());
		add(npcSelecter);
	}
	
	public static void main(String[]args){
		System.out.println("Starting Up - Editor");
		System.out.println("======================");
		System.out.println("Loading Tiles...");
		Tile.loadImages();
		System.out.println("Loading Entities...");
		EntityEditor.loadEntities();
		System.out.println("Starting Editor...");
		editor = new Editor();
		setupMenu();
		window = new JFrame("Editor");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLayout(new BorderLayout());
		window.add(menuBar, BorderLayout.NORTH);
		window.add(editor, BorderLayout.CENTER);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		editor.addMouseListener(editor);
		window.addWindowListener(new WindowListener(){
			public void windowClosing(WindowEvent e){
				if(mapChanged){
					switch(JOptionPane.showConfirmDialog(entityEditor, "Would you like to save the map before closing?")){
						case JOptionPane.OK_OPTION:{
							loadedMap.saveMap();
							System.out.println("Saving Map");
						}
						case JOptionPane.NO_OPTION:{
							System.exit(0);
						}break;
					}
				}
				else{
					window.dispose();
				}	
			}
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
		});
	}
	
	public static void setupMenu(){
		menuBar = new JMenuBar();
		file = new JMenu("File [TODO]");
		file_newmap = new JMenuItem("Create New Map [TODO]");
		file_savemap = new JMenuItem("Save Map");
		file_loadmap = new JMenuItem("Load Map");
		file_closemap = new JMenuItem("Close Map");
		file.add(file_newmap);
		file.addSeparator();
		file.add(file_savemap);
		file.add(file_loadmap);
		file.addSeparator();
		file.add(file_closemap);
		menuBar.add(file);
		edit = new JMenu("Edit");
		edit_entity = new JMenuItem("Open Entity Editor");
		edit_tile = new JMenuItem("Open Tile Editor [TODO]");
		edit.add(edit_tile);
		edit.add(edit_entity);
		file.add(edit);
		menuBar.add(edit);
		file.addActionListener(editor);
		file_newmap.addActionListener(editor);
		file_savemap.addActionListener(editor);
		file_loadmap.addActionListener(editor);
		file_closemap.addActionListener(editor);
		edit.addActionListener(editor);
		edit_entity.addActionListener(editor);
		edit_tile.addActionListener(editor);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 19*32, 15*32);
		if(!mapLoaded){
			return;
		}
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, loadedMap.getX()*32, loadedMap.getY()*32);
		//Draw the tiles
		for(int col = 0; col < loadedMap.getX(); col++){
			for(int row = 0; row < loadedMap.getY(); row++){
				Tile thisTile = loadedMap.getTiles()[row][col];
				g.drawImage(thisTile.getImage(), col*32, row*32, 32, 32, null);
			}
		}
		//Draw the lines
		for(int row = 0; row < loadedMap.getY()+1; row++)
			g.drawLine(0, row*32, loadedMap.getX()*32, row*32);
		for(int col = 0; col < loadedMap.getX()+1; col++)
			g.drawLine(col*32, 0, col*32, loadedMap.getY()*32);
		//Draw all entity spawns
		for(EntitySpawner entity : loadedMap.getSpawners()){
			g.setColor(new Color(100,100,0,100));
			g.fillRect(entity.getX() * Blackwind.SQUARESIZE, entity.getY() * Blackwind.SQUARESIZE, Blackwind.SQUARESIZE, Blackwind.SQUARESIZE);
		}
		//Draw our selected Entity if we have one
		g.setColor(Color.BLACK);
		try{
			g.drawString(selectedSpawner.toString(), 700, 400);
		}catch(NullPointerException noSpawnerSelected){
			g.drawString("No Entity Selected", 700, 400);
		}
	}

	public void actionPerformed(ActionEvent ae){
		if(ae.getSource() == file_loadmap){
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(System.getProperty("user.dir")+"/maps/"));
			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				String fileName = file.getName();
				loadedMap = Map.load(fileName, true);
				loadedMap.despawnNPCs();
				mapLoaded = true;
			}
		}
		else if(ae.getSource() == file_savemap){
			if(mapLoaded){
				loadedMap.saveMap();
				mapChanged = false;
			}
		}
		else if(ae.getSource() == file_closemap){
			if(mapLoaded){
				closeMap();
			}
		}
		else if(ae.getSource() == edit_entity){
			openEntityEditor();
		}
		repaint();
	}
	
	public static void closeMap(){
		if(mapChanged){
			switch(JOptionPane.showConfirmDialog(entityEditor, "There are unsaved changes to the map. Would you like to save them?")){
				case JOptionPane.OK_OPTION:{
					loadedMap.saveMap();
					mapChanged = false;
				}break;
				case JOptionPane.CANCEL_OPTION:{
					return;
				}
			}
		}
		mapLoaded = false;
		loadedMap = null;
	}
	
	public static void generateError(String message, Exception error){
		if(error == null){
			JOptionPane.showMessageDialog(window, message);
			return;
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		error.printStackTrace(pw);
		String errorMsg = sw.toString().substring(0, sw.toString().indexOf("at javax"));
		JOptionPane.showMessageDialog(window, String.format("%s%nError Below:%n%n%s", message, errorMsg));
	}
	
	private void openEntityEditor(){
		if(entityEditorOpen){
			entityEditorWindow.setVisible(true);
			entityEditorWindow.setLocationRelativeTo(null);
			return;
		}
		entityEditor = new EntityEditor();
		entityEditorOpen = true;
		entityEditorWindow = new JFrame("Entity Editor");
		entityEditorWindow.setResizable(false);
		entityEditorWindow.add(entityEditor);
		entityEditorWindow.pack();
		entityEditorWindow.setLocationRelativeTo(null);
		entityEditorWindow.setVisible(true);
		entityEditorWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		entityEditorWindow.addWindowListener(new WindowListener(){
			public void windowClosing(WindowEvent e){
				if(entityEditor.madeChanges()){
					switch(JOptionPane.showConfirmDialog(entityEditor, "You have unsaved Entity changes. Would you like to save them?")){
						case JOptionPane.OK_OPTION:{
							EntityEditor.saveEntities();
							System.out.println("Saving Entities");
						}
						case JOptionPane.NO_OPTION:{
							entityEditorOpen = false;
							entityEditorWindow.dispose();
						}break;
					}
				}
				else{
					entityEditorOpen = false;
					entityEditorWindow.dispose();
				}	
			}
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
		});
	}

	//MouseListener
	public void mouseClicked(MouseEvent me) {}
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
	public void mousePressed(MouseEvent me) {
		//Get the x and y of the square we clicked on
		int squareX = me.getX()/32;
		int squareY = me.getY()/32;
		//Only do this if we are inside our map
		if(squareX >= Blackwind.SQUARESX || squareY >= Blackwind.SQUARESY || !mapLoaded)
			return;
		//Switch depending on what mode we are set on
		switch(editType.getSelectedIndex()){
			//If we are on edit tile mode
			case EDIT_TILE:{
				if(me.getButton() == MouseEvent.BUTTON1)
					loadedMap.changeTile(squareY, squareX, tileSelecterLeft.getSelectedIndex());
				else if(me.getButton() == MouseEvent.BUTTON3)
					loadedMap.changeTile(squareY, squareX, tileSelecterRight.getSelectedIndex());
				mapChanged = true;
			}break;
			//If we are on edit entities mode
			case EDIT_ENTITIES:{
				for(EntitySpawner thisSpawner : loadedMap.getSpawners()){
					//find the spawner we clicked on and set it as our selected spawner
					if(thisSpawner.getX() == squareX && thisSpawner.getY() == squareY)
						selectedSpawner = thisSpawner;
				}
			}break;
		}
		//Repaint every click
		repaint();
	}
	public void mouseReleased(MouseEvent me) {}
}
