/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import menu.battle.helper.BattleAction;
import menu.battle.helper.BattleEntity;
/**
 *
 * @author Connor
 */
public class SpellsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	ArrayList<BattleAction> abilities;
    int scroll;
    JLabel spellSelector;
    JLabel[] spellNames, spellCosts;
    
    SpellsPanel(ArrayList<BattleAction> spells){
        abilities = new ArrayList<>();
        this.abilities=spells;
        
        this.setBounds(0,0,400,480);
        this.setBackground(Color.green);
        this.setLayout(null);
        
        spellNames = new JLabel[10];
        spellCosts = new JLabel[10];
        
        for(int i=0;i<10;i++){
            spellNames[i]=new JLabel();
            spellNames[i].setSize(300,40);
            spellNames[i].setLocation(25,40+40*i);
            spellNames[i].setVisible(true);
            spellNames[i].setFont(new Font("Serif",Font.BOLD, 18));
            this.add(spellNames[i]);
           
            spellCosts[i]=new JLabel();
            spellCosts[i].setSize(50,40);
            spellCosts[i].setLocation(330,40+40*i);
            spellCosts[i].setVisible(true);
            spellCosts[i].setFont(new Font("Serif",Font.BOLD, 18));
            this.add(spellCosts[i]);
        }
                
        spellSelector=new JLabel();
        spellSelector.setSize(25, 50);
        spellSelector.setLocation(0,35);
        spellSelector.setText(">");
        spellSelector.setFont(new Font("Serif",Font.BOLD, 30));
        spellSelector.setVisible(false);
        this.add(spellSelector);
    }
    public void changeSpellsSelectorPosition(int menuPos){
        spellSelector.setLocation(0,35+40*menuPos);
    }
    public void loadSpells(BattleEntity e){
        this.abilities=e.getSkillList();
        for(int i=scroll;i<scroll+10;i++){
            try{
            spellNames[i].setText(String.format("%s",abilities.get(i).getName()));
            spellCosts[i].setText(String.format("%d mp",abilities.get(i).getMpCost()));
            }catch(IndexOutOfBoundsException f){
                spellNames[i].setText("---");
                spellCosts[i].setText("-");
            }
        }
    }
    
    public ArrayList<BattleAction> getSpellsList(){
        return abilities;
    }
    
    public void paint(Graphics g){
    	g.setColor(Color.green);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif",Font.BOLD, 18));
        for(int i=0;i<10;i++){
            g.drawString(spellNames[i].getText(), spellNames[i].getX(), spellNames[i].getY());
            g.drawString(spellCosts[i].getText(), spellCosts[i].getX(), spellCosts[i].getY());
        }
        g.drawString(spellSelector.getText(), spellSelector.getX(), spellSelector.getY());
        
    }
}
