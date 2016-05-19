/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 *
 * @author Connor
 */
public class OptionsPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel[] menuOptions; JLabel selector;
    OptionsPanel(){
        //super();
        this.setBounds(400,0,608,480);
        //this.setBackground(Color.RED);
        this.setLayout(null);
        
        selector = new JLabel();
        selector.setSize(25, 50);
        selector.setLocation(0, 40);
        selector.setText(">");
        selector.setFont(new Font("Serif",Font.BOLD, 30));
        //selector.setVisible(true);
        this.add(selector);
        menuOptions=new JLabel[6];
        for(int i=0;i<6;i++){
            menuOptions[i]=new JLabel();
            menuOptions[i].setBounds(25, 45+30*i, 188, 45*i+70);
            //menuOptions[i].setBackground(Color.blue);
            menuOptions[i].setFont(new Font("Serif",Font.BOLD, 20));
            //menuOptions[i].setVisible(true);
            this.add(menuOptions[i]);
        }
        menuOptions[0].setText("Inventory");
        menuOptions[1].setText("Status");
        menuOptions[2].setText("Spells");
        menuOptions[3].setText("Equipment");
        menuOptions[4].setText("Swap Members");
        menuOptions[5].setText("Save");
    }
    
    public void loadMainMenuOptions(){
        menuOptions[0].setText("Inventory");
        menuOptions[1].setText("Status");
        menuOptions[2].setText("Spells");
        menuOptions[3].setText("Equipment");
        menuOptions[4].setText("Swap Members");
        menuOptions[5].setText("Save");
    }
    public void loadInventoryMenuOptions(){
        menuOptions[0].setText("Use");
        menuOptions[1].setText("Equip");
        menuOptions[2].setText("Examine");
        menuOptions[3].setText("Drop");
        menuOptions[4].setText("");
        menuOptions[5].setText("");
    }
    public void loadStatusMenuOptions(){
        menuOptions[0].setText("Exit");
        menuOptions[1].setText("");
        menuOptions[2].setText("");
        menuOptions[3].setText("");
        menuOptions[4].setText("");
        menuOptions[5].setText("");
    }
    public void loadSpellsMenuOptions(){
        menuOptions[0].setText("Cast");
        menuOptions[1].setText("Description");
        menuOptions[2].setText("Element");
        menuOptions[3].setText("");
        menuOptions[4].setText("");
        menuOptions[5].setText("");
    }
    public void loadEquipmentMenuOptions(){
        menuOptions[0].setText("Equip");
        menuOptions[1].setText("UnEquip");
        menuOptions[2].setText("Description");
        menuOptions[3].setText("");
        menuOptions[4].setText("");
        menuOptions[5].setText("");
    }        
            
    public void updateSelectorPosition(int menuPosition){
        selector.setLocation(0,menuPosition*45+40);
        //repaint();
    }
    
    public void paint(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif",Font.BOLD, 30));
        g.drawString(selector.getText(), selector.getX()+400, selector.getY());
        g.setFont(new Font("Serif",Font.BOLD, 20));
        for(int i=0;i<6;i++){
            g.drawString(menuOptions[i].getText(), menuOptions[i].getX()+400, menuOptions[i].getY()-10+(i*15));
        }
    }
}
