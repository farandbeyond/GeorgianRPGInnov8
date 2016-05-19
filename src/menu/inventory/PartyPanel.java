/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.inventory;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import menu.battle.helper.BattleEntity;
import menu.battle.helper.Party;
import java.awt.Graphics;
/**
 *
 * @author Connor
 */


public class PartyPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel[] pNames, pMana, pHealth;
    private final int distFromTop = 40;
    JLabel partySelector;
    
    Party p;
    PartyPanel(Party p){
        this.p=p;
        this.setBounds(0,0,400,480);
        this.setBackground(Color.YELLOW);
        this.setLayout(null);
        //this.setVisible(true);
        
        pNames=new JLabel[p.getPartyArray().length];
        pHealth=new JLabel[p.getPartyArray().length];
        pMana=new JLabel[p.getPartyArray().length];
        
        for(int i=0;i<p.getPartyArray().length;i++){
            pNames[i]=new JLabel();
            pHealth[i]=new JLabel();
            pMana[i]=new JLabel();
            
            //pNames[i].setBounds(25, i*95+distFromTop, 435, i*95+distFromTop+40);
            pNames[i].setSize(410,40);
            pNames[i].setLocation(25,i*95+distFromTop);
            pNames[i].setFont(new Font("Serif",Font.BOLD, 18));
            //pNames[i].setVisible(true);
            //pHealth[i].setBounds(25, i*95+distFromTop+40, 85, i*95+distFromTop+80);
            pHealth[i].setSize(80,45);
            pHealth[i].setLocation(25,i*95+distFromTop+40);
            pHealth[i].setFont(new Font("Serif",Font.BOLD, 18));
            //pHealth[i].setVisible(true);
            //pMana[i].setBounds(95,i*95+distFromTop+40,155,i*95+distFromTop+80);
            pMana[i].setSize(80,45);
            pMana[i].setLocation(25+90,i*95+distFromTop+40);
            pMana[i].setFont(new Font("Serif",Font.BOLD, 18));
            //pMana[i].setVisible(true);
            //this.add(pNames[i]);
            //this.add(pHealth[i]);
            //this.add(pMana[i]);
        }
        updatePartyLabels();
        partySelector=new JLabel();
        partySelector.setSize(25, 50);
        partySelector.setLocation(0,35);
        partySelector.setText(">");
        partySelector.setFont(new Font("Serif",Font.BOLD, 30));
        //partySelector.setVisible(false);
        //this.add(partySelector);
    }
    public void updatePartyLabels(){
        for(int i=0;i<p.getPartyArray().length;i++){
            try{
            pNames[i]   .setText(p.getMemberFromParty(i).getName());
            pHealth[i]  .setText(String.format("HP: %d/%d",p.getMemberFromParty(i).getHp(),p.getMemberFromParty(i).getMaxHp()));
            pMana[i]    .setText(String.format("MP: %d/%d",p.getMemberFromParty(i).getMp(),p.getMemberFromParty(i).getMaxMp()));
            }catch(NullPointerException e){
            pNames[i]   .setText("---");
            pHealth[i]  .setText("HP: -/-");
            pMana[i]    .setText("MP: -/-");
            }
        }
    }
    public void movePartySelector(int menuPos){
        partySelector.setLocation(0, menuPos*95+45);
    }
    public void load(Party p){
        this.p=p;
    }
    public BattleEntity getMember(int member){
        return p.getMemberFromParty(member);
    }
    
    public void paint(Graphics g){
        super.paintComponent(g);
        //g.drawRect(40, 30, 100, 100);
        g.setColor(Color.YELLOW);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif",Font.BOLD, 18));
        for(int i=0;i<p.getPartyArray().length;i++){
            g.drawString(pNames[i].getText(), pNames[i].getX(), pNames[i].getY());
            g.drawString(pMana[i].getText(), pMana[i].getX(), pMana[i].getY());
            g.drawString(pHealth[i].getText(), pHealth[i].getX(), pHealth[i].getY());
        }
        g.setFont(new Font("Serif",Font.BOLD, 30));
        g.drawString(partySelector.getText(), partySelector.getX(), partySelector.getY());
    }
}
