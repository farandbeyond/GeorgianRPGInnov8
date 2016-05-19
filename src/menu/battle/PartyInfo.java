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
public class PartyInfo extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel[] pmemNames, pmemHp, pmemMp;
    JLabel partySelector;
    JPanel[] pmemBackgrounds;
    Party party;
    
    PartyInfo(Party party){
        this.party=party;
        
        this.setLayout(null);
        this.setBounds(0, 300, 440, 480);
        this.setBackground(Color.GREEN);
        this.setVisible(true);
        
        pmemNames = new JLabel[4];
        pmemHp = new JLabel[4];
        pmemMp = new JLabel[4];
        pmemBackgrounds = new JPanel[4];
        
        partySelector = new JLabel();
        partySelector.setSize(25, 25);
        partySelector.setLocation(-25, -125);
        partySelector.setText("^");
        partySelector.setFont(new Font("Serif",Font.BOLD, 35));
        partySelector.setVisible(true);
        this.add(partySelector);
        
        for(int i=0;i<4;i++){
            pmemBackgrounds[i] = new JPanel();
            pmemBackgrounds[i].setSize(110, 180);
            pmemBackgrounds[i].setLocation(0+110*i,0);
            pmemBackgrounds[i].setVisible(true);
            pmemBackgrounds[i].setBorder(BorderFactory.createLineBorder(Color.black));
            this.add(pmemBackgrounds[i]);
            
            pmemNames[i]=new JLabel();
            pmemNames[i].setSize(100, 50);
            pmemNames[i].setLocation(40,25);
            pmemNames[i].setFont(new Font("Serif",Font.BOLD, 20));
            pmemNames[i].setVisible(true);
            pmemBackgrounds[i].add(pmemNames[i]);
            
            pmemHp[i]=new JLabel();
            pmemHp[i].setSize(100, 50);
            pmemHp[i].setLocation(40,80);
            pmemHp[i].setFont(new Font("Serif",Font.BOLD, 20));
            pmemHp[i].setVisible(true);
            pmemBackgrounds[i].add(pmemHp[i]);
            
            pmemMp[i]=new JLabel();
            pmemMp[i].setSize(100, 50);
            pmemMp[i].setLocation(40,130);
            pmemMp[i].setFont(new Font("Serif",Font.BOLD, 20));
            pmemMp[i].setVisible(true);
            pmemBackgrounds[i].add(pmemMp[i]);
            
            
        }
        
        
        updatePartyInformation();
    }
    public void updatePartySelectorPosition(int menuPos){
        //partySelector.setLocation(45+110*menuPos, 150);
        
            pmemBackgrounds[menuPos].add(partySelector);
            this.repaint();
        
    }
    public void updatePartyInformation(){
        for(int i=0;i<4;i++){
            try{
                pmemNames[i].setText(party.getMemberFromParty(i).getName());
                pmemHp[i].setText(String.format("%d / %d Hp", party.getMemberFromParty(i).getHp(),party.getMemberFromParty(i).getMaxHp()));
                pmemMp[i].setText(String.format("%d / %d Mp", party.getMemberFromParty(i).getMp(),party.getMemberFromParty(i).getMaxMp()));
                pmemBackgrounds[i].setBackground(party.getMemberFromParty(i).isDead()?Color.RED:Color.CYAN);
            }catch(NullPointerException e){
                pmemNames[i].setText("---");
                pmemHp[i].setText("-- / --");
                pmemMp[i].setText("-- / --");
                pmemBackgrounds[i].setBackground(Color.GRAY);
            }
        }
        
    }
    
    public void paint(Graphics g){
    	//g.setColor(Color.GREEN);
    	//g.fillRect(0,300,440,480);
        g.setFont(new Font("Serif",Font.BOLD, 35));
        g.setColor(Color.BLACK);
        g.drawString("^", partySelector.getX(), partySelector.getY());
        for(int i=0;i<4;i++){
        	g.setFont(new Font("Serif",Font.BOLD, 20));
        	g.setColor(pmemBackgrounds[i].getBackground());
        	g.fillRect(pmemBackgrounds[i].getX()+getX(),pmemBackgrounds[i].getY()+getY(),pmemBackgrounds[i].getWidth()+getX(),pmemBackgrounds[i].getHeight()+getY());
        	g.setColor(Color.BLACK);
            g.drawString(pmemNames[i].getText(), getX()+i*110, pmemNames[i].getY()+getY());
            g.drawString(pmemHp[i].getText(), getX()+i*110, pmemHp[i].getY()+getY());
            g.drawString(pmemMp[i].getText(), getX()+i*110, pmemMp[i].getY()+getY());
            g.drawRect(getX()+i*110, getY(), 110, 480);
        }
    }
    
    public void loadNewPartyList(Party p){
        this.party=p;
    }
    public void newBattle(Party p){
        loadNewPartyList(p);
        updatePartyInformation();
        this.setVisible(true);
    }
}

