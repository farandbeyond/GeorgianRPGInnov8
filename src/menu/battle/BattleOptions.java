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
/**
 *
 * @author Connor
 */
public class BattleOptions extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel[] options;
    JLabel selector;
    private final int numberOfOptions = 4;
    BattleOptions(){
        this.setLayout(null);
        this.setBounds(440,230,608,480);
        this.setVisible(true);
        this.setBackground(Color.yellow);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        options = new JLabel[numberOfOptions];
        for(int i=0;i<numberOfOptions;i++){
            options[i]=new JLabel();
            options[i].setSize(100,30);
            options[i].setLocation(45, i*40+25);
            options[i].setVisible(true);
            options[i].setFont(new Font("Serif",Font.BOLD, 20));
            this.add(options[i]);
        }
        selector=new JLabel();
        selector.setSize(25,25);
        selector.setLocation(10, 30);
        selector.setVisible(true);
        selector.setText(">");
        selector.setFont(new Font("Serif",Font.BOLD, 30));
        this.add(selector);
        setMenuOptions();
    }
    public void setMenuOptions(){
        options[0].setText("Attack");
        options[1].setText("Skill");
        options[2].setText("Item");
        options[3].setText("Flee");
    }
    public void updateSelectorPosition(int menuPosition){
        selector.setLocation(10, menuPosition*40+30);
    }
    
    public void paint(Graphics g){
    	g.setColor(Color.YELLOW);
        g.fillRect(440,230,608,480);
        g.setColor(Color.BLACK);
        g.drawRect(440,230,608,480);
        g.setFont(new Font("Serif",Font.BOLD, 20));
        for(int i=0;i<numberOfOptions;i++){
            g.drawString(options[i].getText(),45+getX(), i*40+45+getY());
        }
        g.drawString(">", getX()+selector.getX(), getY()+selector.getY()+20);
    }
}
