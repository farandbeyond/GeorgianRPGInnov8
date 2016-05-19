/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.battle;

import menu.battle.helper.Party;
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
public class EnemyInfo extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel[] enemyNames, enemyHp;
    JPanel[] enemyBackgrounds;
    JLabel enemySelector;
    Party enemies;
    EnemyInfo(Party enemies){
        this.enemies=enemies;
        
        this.setLayout(null);
        this.setBounds(0, 0, 608, 150);
        this.setBackground(Color.GRAY);
        
        enemySelector = new JLabel();
        enemySelector.setSize(25, 25);
        enemySelector.setLocation(25, 125);
        enemySelector.setText("^");
        enemySelector.setFont(new Font("Serif",Font.BOLD, 30));
        //enemySelector.setVisible(true);
        
        this.add(enemySelector);
        
        enemyNames = new JLabel[3];
        enemyHp = new JLabel[3];
        enemyBackgrounds = new JPanel[3];
        
        for(int i=0;i<3;i++){
            enemyNames[i]=new JLabel();
            enemyNames[i].setSize(100, 50);
            enemyNames[i].setLocation(25+40+180*i,25);
            enemyNames[i].setFont(new Font("Serif",Font.BOLD, 20));
            enemyNames[i].setVisible(true);
            this.add(enemyNames[i]);
            
            enemyHp[i]=new JLabel();
            enemyHp[i].setSize(100, 50);
            enemyHp[i].setLocation(25+40+180*i,80);
            enemyHp[i].setFont(new Font("Serif",Font.BOLD, 20));
            enemyHp[i].setVisible(true);
            this.add(enemyHp[i]);
            
            enemyBackgrounds[i] = new JPanel();
            enemyBackgrounds[i].setSize(180, 180);
            enemyBackgrounds[i].setLocation(25+180*i,0);
            enemyBackgrounds[i].setVisible(true);
            enemyBackgrounds[i].setBorder(BorderFactory.createLineBorder(Color.black));
            this.add(enemyBackgrounds[i]);
        }
        
        
        updateEnemyInformation();
    }
    public void updateEnemySelectorPosition(int menuPos){
        enemySelector.setLocation(25+90+180*menuPos, 125);
    }
    public void updateEnemyInformation(){
        
        for(int i=0;i<3;i++){
            try{
                enemyNames[i].setText(enemies.getMemberFromParty(i).getName());
                enemyHp[i].setText(String.format("%d / %d Hp", enemies.getMemberFromParty(i).getHp(),enemies.getMemberFromParty(i).getMaxHp()));
                enemyBackgrounds[i].setBackground(enemies.getMemberFromParty(i).isDead()?Color.RED:Color.CYAN);
            }catch(NullPointerException e){
                enemyNames[i].setText("---");
                enemyHp[i].setText("-- / --");
                enemyBackgrounds[i].setBackground(Color.GRAY);
            }
        }
        
    }
    public void loadNewEnemyList(Party p){
        this.enemies=p;
    }
    public void newBattle(Party p){
        loadNewEnemyList(p);
        updateEnemyInformation();
        this.setVisible(true);
    }
    public void paint(Graphics g){
        updateEnemyInformation();
        g.setColor(Color.GRAY);
      //  g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        for(int i=0;i<3;i++){
            g.setColor(enemyBackgrounds[i].getBackground());
            g.fillRect(enemyBackgrounds[i].getX(), enemyBackgrounds[i].getY(), enemyBackgrounds[i].getWidth(), enemyBackgrounds[i].getHeight());
            g.setColor(Color.BLACK);
            g.drawRect(enemyBackgrounds[i].getX(), enemyBackgrounds[i].getY(), enemyBackgrounds[i].getWidth(), enemyBackgrounds[i].getHeight());
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif",Font.BOLD, 20));
        for(int i=0;i<3;i++){
            g.drawString(
                    enemyHp[i].getText(), 
                    enemyHp[i].getX(),
                    enemyHp[i].getY());
            g.drawString(enemyNames[i].getText(), enemyNames[i].getX(), enemyNames[i].getY());
        }
        g.drawString(enemySelector.getText(), enemySelector.getX(), enemySelector.getY());
    }
}
