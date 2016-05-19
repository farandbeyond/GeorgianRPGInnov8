package handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import engine.Blackwind;
import entities.entityHelper.TextEvent;
import entities.Entity;
import entities.Player;

public class PlayerHandle implements KeyListener{

	private Player player;
	
	public PlayerHandle(Player p){
		player = p;
	}
	//KEYLISTENER
	public void keyTyped(KeyEvent key) {}

	public void keyReleased(KeyEvent key) {}

	public void keyPressed(KeyEvent key) {
		switch(Blackwind.getStatus()){
                    case Blackwind.STATUS_OVERWORLD :{
                            switch(key.getExtendedKeyCode()){
                            case KeyEvent.VK_W: player.startMove(Entity.UP);break;
                            case KeyEvent.VK_A: player.startMove(Entity.LEFT);break;
                            case KeyEvent.VK_S: player.startMove(Entity.DOWN);break;
                            case KeyEvent.VK_D: player.startMove(Entity.RIGHT);break;
                            case KeyEvent.VK_O: Blackwind.setStatus(Blackwind.STATUS_TEXTEVENT);break;
                            case KeyEvent.VK_ENTER: Blackwind.setStatus(Blackwind.STATUS_PAUSED);break;
                            }
                    }break;

                    case Blackwind.STATUS_PAUSED :{
                            switch(key.getExtendedKeyCode()){
                            case KeyEvent.VK_O:Blackwind.getInventoryMenu().confirmEvent();break;
                            case KeyEvent.VK_P:Blackwind.getInventoryMenu().cancelEvent();break;
                            case KeyEvent.VK_W:Blackwind.getInventoryMenu().upEvent();break;
                            case KeyEvent.VK_A:Blackwind.getInventoryMenu().leftEvent();break;
                            case KeyEvent.VK_S:Blackwind.getInventoryMenu().downEvent();break;
                            case KeyEvent.VK_D:Blackwind.getInventoryMenu().rightEvent();break;
                            }
                    }break;

                    case Blackwind.STATUS_BATTLE :{
                            switch(key.getExtendedKeyCode()){
                            case KeyEvent.VK_O:Blackwind.getBattleInterface().confirmEvent();break;
                            case KeyEvent.VK_P:Blackwind.getBattleInterface().cancelEvent();break;
                            case KeyEvent.VK_W:Blackwind.getBattleInterface().upEvent();break;
                            case KeyEvent.VK_A:Blackwind.getBattleInterface().leftEvent();break;
                            case KeyEvent.VK_S:Blackwind.getBattleInterface().downEvent();break;
                            case KeyEvent.VK_D:Blackwind.getBattleInterface().rightEvent();break;
                            case KeyEvent.VK_ENTER:Blackwind.getBattleInterface().tryEndBattle();break;
                    }
                }
                    case Blackwind.STATUS_TEXTEVENT :{
                        switch(key.getExtendedKeyCode()){
                            case KeyEvent.VK_O:TextEvent.advance();break;
                            case KeyEvent.VK_P:TextEvent.advance();break;
                }
            }//switch getStatus
	}//keyPressed
    }
}
