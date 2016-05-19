/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.battle;

import menu.battle.helper.Inventory;
import menu.battle.helper.Item;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class InventoryList extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel[] itemInfo;
    JLabel invSelector;
    int offset;
    Inventory inv;
    
    InventoryList(Inventory i){
        inv=i;
        this.setLayout(null);
        this.setBounds(440, 120, 608, 480);
        this.setVisible(false);
        this.setBackground(Color.PINK);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        
        itemInfo = new JLabel[8];
        for(int e=0;e<8;e++){
            itemInfo[e]= new JLabel();
            itemInfo[e].setSize(130, 40);
            itemInfo[e].setLocation(25,e*40+5);
            itemInfo[e].setVisible(true);
            itemInfo[e].setFont(new Font("Serif",Font.BOLD, 20));
            this.add(itemInfo[e]);
        }
        invSelector=new JLabel();
        invSelector.setSize(25,25);
        invSelector.setLocation(10, 30);
        invSelector.setVisible(true);
        invSelector.setText(">");
        invSelector.setFont(new Font("Serif",Font.BOLD, 30));
        this.add(invSelector);
        
        updateItemsContents();
    
   
}
    public Item selectItemFromList(int itemToGet){
        return(inv.getItem(itemToGet));
        
    }
    public void updateSelectorPosition(int menuPos){
        invSelector.setLocation(0, menuPos*40+15);
    }
    public void updateItemsContents(){
        for(int i=0;i<8;i++){
            try{
                itemInfo[i].setText(String.format("x%d %s",inv.getItem(i+offset).getQuantity(), inv.getItem(i+offset).toString()));
                //itemInfo[i].setText(inv.getItem(i+offset).toString());
                //itemQuantities[i].setText("x"+inv.getItem(i+offset).getQuantity());
            }catch(IndexOutOfBoundsException t){
                itemInfo[i].setText("x- ---");
            }
        }
    }
    public void loadNewItemList(Inventory inv){
        this.inv=inv;
    }
    
    public void paint(Graphics g){
    	g.setColor(Color.PINK);
        g.fillRect(440, 120, 608, 480);
        g.setColor(Color.BLACK);
        g.drawRect(440, 120, 608, 480);
        for(int i=0;i<8;i++){
        	g.drawString(itemInfo[i].getText(),itemInfo[i].getX()+getX(), itemInfo[i].getY()+getY()+15);
        }
    	g.drawString(invSelector.getText(),invSelector.getX()+getX(), invSelector.getY()+getY()+15);
    }
    
    public void updateItemsContents2(int displaySize, int menuPos){
        for(int i=0;i<displaySize;i++){

            if(displaySize/menuPos*100>60&&displaySize<inv.getSize()){
                offset++;
                menuPos--;
            }
            else if(displaySize/menuPos*100<40&&displaySize<inv.getSize()){
                offset--;
                menuPos++;
            }
            updateItemsContents();
        }
        
    }
}
