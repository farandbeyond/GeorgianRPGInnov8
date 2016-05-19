/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.battle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import menu.battle.helper.BattleEntity;

/**
 *
 * @author Connor
 */
public class ActionsList extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel[] actions;
    JLabel actionSelector;
    int offset;
    ActionsList(BattleEntity e){
        this.setLayout(null);
        this.setBounds(440, 120, 608, 480);
        this.setVisible(false);
        this.setBackground(Color.PINK);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        actions = new JLabel[8];
        for(int i=0;i<8;i++){
            actions[i]= new JLabel();
            actions[i].setSize(130, 40);
            actions[i].setLocation(25,i*40+5);
            actions[i].setVisible(true);
            actions[i].setFont(new Font("Serif",Font.BOLD, 20));
            this.add(actions[i]);
        }
        actionSelector=new JLabel();
        actionSelector.setSize(25,25);
        actionSelector.setLocation(10, 30);
        actionSelector.setVisible(true);
        actionSelector.setText(">");
        actionSelector.setFont(new Font("Serif",Font.BOLD, 30));
        this.add(actionSelector);
        updateActionsContents(e);
    }
    public void updateActionSelectorPosition(int menuPos){
        actionSelector.setLocation(0,menuPos*40+15);
    }
    
    public void updateActionsContents(BattleEntity e){
        for(int i=0;i<8;i++){
            try{
                actions[i].setText(String.format("%dmp %s", e.getSkillList().get(i+offset).getMpCost(),e.getSkillList().get(i+offset).getName()));
            }catch(IndexOutOfBoundsException t){
                actions[i].setText("-mp  -");
                
            }
        }
    }
    public void updateActionsContents2(BattleEntity e, int displaySize, int menuPos){
        for(int i=0;i<displaySize;i++){
            try{
                if(displaySize/menuPos*100>60&&displaySize<e.getSkillList().size()){
                    offset++;
                    menuPos--;
                }
                else if(displaySize/menuPos*100<40&&displaySize<e.getSkillList().size()){
                    offset--;
                    menuPos++;
                }
                updateActionsContents(e);
            }catch(IndexOutOfBoundsException t){
                actions[i].setText("-mp  -");
            }
        }
    }
    
    public void paint(Graphics g){
        g.setColor(Color.PINK);
        g.fillRect(this.getX(), this.getY(), this.getWidth(),this.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(this.getX(), this.getY(), this.getWidth(),this.getHeight());
        g.setFont(new Font("Serif",Font.BOLD, 20));
        for(int i=0;i<8;i++){
            g.drawString(actions[i].getText(),actions[i].getX()+getX(),actions[i].getY()+getY()+15);
        }
        g.drawString(actionSelector.getText(),actionSelector.getX()+getX(),actionSelector.getY()+getY()+15);
    }
}
