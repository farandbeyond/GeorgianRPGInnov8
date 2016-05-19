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

import menu.battle.helper.Inventory;
/**
 *
 * @author Connor
 */
public class InventoryPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	Inventory inv;
    int scroll;
    JLabel itemSelector;
    JLabel[] itemNames, itemQuantities;
    InventoryPanel(Inventory inv){
        this.inv=inv;
        
        this.setBounds(0,0,400,480);
        //this.setBackground(Color.CYAN);
        this.setLayout(null);
        
        itemNames = new JLabel[10];
        itemQuantities = new JLabel[10];
        
        for(int i=0;i<10;i++){
            itemNames[i]=new JLabel();
            itemNames[i].setSize(300,40);
            itemNames[i].setLocation(25,40+40*i);
            itemNames[i].setVisible(true);
            itemNames[i].setFont(new Font("Serif",Font.BOLD, 18));
            this.add(itemNames[i]);
           
            itemQuantities[i]=new JLabel();
            itemQuantities[i].setSize(50,40);
            itemQuantities[i].setLocation(330,40+40*i);
            itemQuantities[i].setVisible(true);
            itemQuantities[i].setFont(new Font("Serif",Font.BOLD, 18));
            this.add(itemQuantities[i]);
        }
                
        itemSelector=new JLabel();
        itemSelector.setSize(25, 50);
        itemSelector.setLocation(0,35);
        itemSelector.setText(">");
        itemSelector.setFont(new Font("Serif",Font.BOLD, 30));
        itemSelector.setVisible(false);
        this.add(itemSelector);
    }
    public void changeItemSelectorPosition(int menuPos){
        itemSelector.setLocation(0,35+40*menuPos);
    }
    public void loadItems(){
        for(int i=scroll;i<scroll+10;i++){
            try{
            if(inv.getItem(i).getQuantity()==0){
                inv.drop(i);
            }
            itemNames[i].setText(inv.getItem(i).toString());
            itemQuantities[i].setText("x"+inv.getItem(i).getQuantity());
            }catch(IndexOutOfBoundsException e){
                itemNames[i].setText("---");
                itemQuantities[i].setText("-");
            }
        }
    }
    
    public void setInventory(Inventory inv){
        this.inv = inv;
    }
    public void paint(Graphics g){
        g.setColor(Color.CYAN);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g.setFont(new Font("Serif",Font.BOLD, 30));
        g.setColor(Color.BLACK);
        g.drawString(itemSelector.getText(), itemSelector.getX(), itemSelector.getY());
        for(int i=0;i<10;i++){
            g.drawString(itemNames[i].getText(),itemNames[i].getX(),itemNames[i].getY());
            g.drawString(itemQuantities[i].getText(),itemQuantities[i].getX(),itemQuantities[i].getY());
        }
        
    }
}
