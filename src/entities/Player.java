package entities;
import java.awt.Color;
import java.awt.Graphics;

public class Player extends Entity{

	public Player(String playerName, int x, int y){
		setName(playerName);
		setX(x);
		setY(y);
	}
	  
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.drawOval(getPosX(), getPosY(), getSize(), getSize());
		g.setColor(Color.RED);
		g.fillOval(getPosX(), getPosY(), getSize(), getSize());
		drawName(g);
		g.setColor(Color.YELLOW);
		int endX = getDirection() == Entity.LEFT ? -getSize()/2 : getDirection() == Entity.RIGHT ? getSize()/2 : 0;
		int endY = getDirection() == Entity.UP ? -getSize()/2 : getDirection() == Entity.DOWN ? getSize()/2 : 0;
		g.drawLine(getPosX()+getSize()/2, getPosY()+getSize()/2, getPosX()+getSize()/2+endX, getPosY()+getSize()/2+endY);
	}
	
	public boolean isPlayer(){
		return true;
	}
}
