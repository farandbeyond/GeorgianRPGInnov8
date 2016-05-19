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

import menu.battle.helper.BattleEntity;
import menu.battle.helper.Party;
/**
 *
 * @author Connor
 */
public class StatusPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JLabel[] stats, equipment,hpmp;
    JLabel name, isDead, level, exp, expUntilLevel, equipmentSelector;
    
    Party p;
    StatusPanel(Party p){
        this.setBounds(0, 0, 400, 480);
        this.setLayout(null);
        this.setBackground(java.awt.Color.MAGENTA);
        stats = new JLabel[5];
        hpmp = new JLabel[2];
        equipment = new JLabel[4];
        for(int i=0;i<5;i++){
            stats[i]=new JLabel();
            stats[i].setSize(60,40);
            stats[i].setLocation(i*60+25, 220);
            stats[i].setFont(new Font("Serif",Font.BOLD, 18));
            stats[i].setVisible(true);
            this.add(stats[i]);
        }
        for(int i=0;i<2;i++){
            hpmp[i]=new JLabel();
            hpmp[i].setSize(100,40);
            hpmp[i].setLocation(i*100+25, 180);
            hpmp[i].setFont(new Font("Serif",Font.BOLD, 20));
            hpmp[i].setVisible(true);
            this.add(hpmp[i]);
        }
        for(int i=0;i<4;i++){
            equipment[i]=new JLabel();
            equipment[i].setSize(200,25);
            equipment[i].setLocation(25, 265+i*25);
            equipment[i].setFont(new Font("Serif",Font.BOLD, 20));
            equipment[i].setVisible(true);
            this.add(equipment[i]);
        }

        name=new JLabel();
        name.setSize(100,100);
        name.setLocation(25, 25);
        name.setFont(new Font("Serif",Font.BOLD, 30));
        name.setVisible(true);
        this.add(name);

        isDead=new JLabel();
        isDead.setSize(50,40);
        isDead.setLocation(25,150);
        isDead.setFont(new Font("Serif",Font.BOLD, 20));
        isDead.setVisible(true);
        this.add(isDead);
        
        level=new JLabel();
        level.setSize(100,80);
        level.setLocation(150,35);
        level.setFont(new Font("Serif",Font.BOLD, 30));
        level.setVisible(true);
        this.add(level);
        
        exp=new JLabel();
        exp.setSize(200,40);
        exp.setLocation(25,360);
        exp.setFont(new Font("Serif",Font.BOLD, 20));
        exp.setVisible(true);
        this.add(exp);
        
        expUntilLevel=new JLabel();
        expUntilLevel.setSize(300,40);
        expUntilLevel.setLocation(25,400);
        expUntilLevel.setFont(new Font("Serif",Font.BOLD, 20));
        expUntilLevel.setVisible(true);
        this.add(expUntilLevel);
        
        equipmentSelector=new JLabel();
        equipmentSelector.setSize(25, 25);
        equipmentSelector.setLocation(0,265);
        equipmentSelector.setText(">");
        equipmentSelector.setFont(new Font("Serif",Font.BOLD, 30));
        equipmentSelector.setVisible(false);
        this.add(equipmentSelector);
        
        updateText(p.getMemberFromParty(0));
    }
    public void updateText(BattleEntity e){
        //this.setVisible(true);
        try{
        stats[0].setText(String.format("Str: %d",e.getStr()));
        stats[1].setText(String.format("Dex: %d",e.getDex()));
        stats[2].setText(String.format("Vit: %d",e.getVit()));
        stats[3].setText(String.format("Int: %d",e.getInt()));
        stats[4].setText(String.format("Res: %d",e.getRes()));
        try{
            equipment[0].setText(String.format("Weapon:\t %s",e.getWeapon().getName()));
        }catch(NullPointerException q){
            equipment[0].setText("Weapon: ---");
            //System.out.println("Weapon is null");
        }
        for(int i=1;i<4;i++){
            try{
                equipment[i].setText(String.format("Armor:\t %s",e.getArmor(i-1).toString()));
            }catch(NullPointerException q){
                equipment[i].setText("Armor: ---");
            }
        }
        
        hpmp[0].setText(String.format("HP: %d/%d",e.getHp(),e.getMaxHp()));
        hpmp[1].setText(String.format("MP: %d/%d",e.getMp(),e.getMaxMp()));
        
        name.setText(e.getName());
        
        isDead.setText(!e.isDead()?"Alive":"Dead");
        
        level.setText("Level "+e.getLevel());
        exp.setText("Exp Gained: "+e.getExp());
        expUntilLevel.setText("Exp until next level: "+e.getExpUntilLevel());
        //System.out.println("Loaded text properly");
        }catch(NullPointerException h){
           System.out.println("Error Loading Entity");
       }
    }
    public void changeArmorEquipmentSelectorPosition(int menuPos){
        //System.out.println(menuPos);
        //equipmentSelector.setVisible(true);
        equipmentSelector.setLocation(0,265+(menuPos+1)*25);
    }
    public void changeEquipmentSelectorPosition(int menuPos){
        equipmentSelector.setLocation(0,265+menuPos*25);
    }
    public void paint(Graphics g){
        g.setColor(Color.MAGENTA);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif",Font.BOLD, 20));
        
        for(int i=0;i<5;i++){
            g.drawString(stats[i].getText(), stats[i].getX(), stats[i].getY());
        }
        for(int i=0;i<2;i++){
            g.drawString(hpmp[i].getText(), hpmp[i].getX(), hpmp[i].getY());
        }
        for(int i=0;i<4;i++){
            g.drawString(equipment[i].getText(), equipment[i].getX(), equipment[i].getY());
        }
        g.drawString(name.getText(), name.getX(), name.getY());
        g.drawString(isDead.getText(), isDead.getX(), isDead.getY());
        g.drawString(level.getText(), level.getX(), level.getY());
        g.drawString(exp.getText(), exp.getX(), exp.getY());
        g.drawString(expUntilLevel.getText(), expUntilLevel.getX(), expUntilLevel.getY());
        g.drawString(equipmentSelector.getText(), equipmentSelector.getX(), equipmentSelector.getY());
      
    }
}
