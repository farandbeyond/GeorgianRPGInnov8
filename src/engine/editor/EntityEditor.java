package engine.editor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

import entities.entityHelper.EntityInfo;

public class EntityEditor extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	//to temporarly store all modified changes
	private static ArrayList<EntityInfo> tempInfoList;

	private static boolean changed = false;
	//Show 5/5 if 5 or less, show 5/X if more than 5 (always show 5 out of something amount of entities)
	private JScrollBar barV;
	private int offsetY;
	
	//The labels and editable fields
	private JLabel[] entityLabels = new JLabel[5]; //names of the fields along the top
	private JTextField[] entityData = new JTextField[25]; //fillable data, 5 rows of 5
	private JLabel[] IDs = new JLabel[5]; //ID labels along the left side
	
	public EntityEditor(){
		setPreferredSize(new Dimension(700,300));
		tempInfoList = EntityInfo.getAllEntities();
		barV = new JScrollBar(JScrollBar.VERTICAL,0,5,0,Math.max(5,EntityInfo.getEntityCount()));
		offsetY = barV.getValue();
		setLayout(null);
		barV.setBounds(680,0,20,300);
		add(barV);
		barV.addAdjustmentListener(new AdjustmentListener(){
			public void adjustmentValueChanged(AdjustmentEvent ae) {
				offsetY = barV.getValue();
				repaint();
			}
		});
		addMouseWheelListener(new MouseWheelListener(){
			public void mouseWheelMoved(MouseWheelEvent mw) {
				int moves = mw.getWheelRotation();
				barV.setValue(barV.getValue()+moves);
			}
		});
		setupLabels();
	}
	
	//Initialize all the JLabels and JTextFields
	private void setupLabels(){
		for(int i = 0; i < 25; i++){
			if(i < 5){
				entityLabels[i] = new JLabel("null");
				entityLabels[i].setBounds(i*700/5+30, 0, 700/5, 30);
				add(entityLabels[i]);
				IDs[i] = new JLabel("-1");
				IDs[i].setBounds(3, 40+50*i, 30, 30);
				add(IDs[i]);
			}
			entityData[i] = new JTextField("null");
			entityData[i].setBounds((i%5)*650/5+20, 40+(i/5)*(250/5), 650/5, 250/5);
			entityData[i].addActionListener(this);
			add(entityData[i]);
		}
	}

	public static void loadEntities(){
		try{
			EntityInfo.resetEntities(); //reset the static array in EntityInfo
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("entities"));
			// Keep loading Entityinfos until you find a null (end of file) and add them to the static EntityInfo array
			EntityInfo thisEntity = (EntityInfo) input.readObject();
			while(thisEntity != null){
				//This will loop until it generates an IOException (end of file in this case). The exception handles closing the input.
				EntityInfo.addEntity(thisEntity);
				thisEntity = (EntityInfo) input.readObject();
			}
			input.close();
		}catch(FileNotFoundException fnf){
			Editor.generateError("Entities file not found. Generating defaults.", null);
			EntityInfo.resetEntities();
			//DEFAULTS - CREATE 4 DEFAULT ENTITYINFOS FOR THE GAME
			EntityInfo still = new EntityInfo("Still Guy", true);
			EntityInfo free = new EntityInfo("Free Guy", true);
			EntityInfo patrol = new EntityInfo("Patrol Guy", true);
			EntityInfo area = new EntityInfo("Area Guy", true);
			EntityInfo area2 = new EntityInfo("Area Guy2", true);
			EntityInfo area3 = new EntityInfo("Area Guy3", true);
			EntityInfo.addEntity(still);
			EntityInfo.addEntity(free);
			EntityInfo.addEntity(patrol);
			EntityInfo.addEntity(area);
			EntityInfo.addEntity(area2);
			EntityInfo.addEntity(area3);
			//END DEFAULTS
			saveEntities();
		}catch(IOException e){
			System.out.println("Finished loading entities");
		}catch(ClassNotFoundException e)
		{
			Editor.generateError("Something unexpected went wrong loading entities.", e);
		}
	}
	
	static void saveEntities(){
		try{
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("entities"));
			//Loop through the static array and save them in the file one at a time
			for(EntityInfo e : EntityInfo.getAllEntities()){
				output.writeObject(e);
			}
			output.flush();
			output.close();
			System.out.println("Success Saving entities.");
			changed = false;
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Error while saving entities.");
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i = 0; i < 5; i++){
			entityLabels[i].setText(EntityInfo.LABELS[i]);
			EntityInfo info = tempInfoList.get(i+offsetY);
			entityData[i*5+0].setText(info.getName());
			entityData[i*5+1].setText(info.isBlockableStr());
			entityData[i*5+2].setText("Null");
			entityData[i*5+3].setText("Null");
			entityData[i*5+4].setText("Null");
			IDs[i].setText(""+(offsetY + i));
		}
	}
	
	public boolean madeChanges(){
		return changed;
	}

	public void actionPerformed(ActionEvent ae){
		changed = true;
		for(int i = 0; i < 25; i++){
			if(ae.getSource() == entityData[i]){
				switch(i){
					case 0: //CHANGING NAME FIELDS
					case 5:
					case 10:
					case 15:
					case 20: tempInfoList.get((i/5)+offsetY).setName(entityData[i].getText());
					break;
					case 1: // CHANGING BLOCKABLE FIELDS
					case 6:
					case 11:
					case 16:
					case 21: tempInfoList.get((i/5)+offsetY).setBlockable(entityData[i].getText().equals("True") ? true : false);
					break;
					//TODO other fields that arnt coded yet
				}
			}
		}
	}
}
