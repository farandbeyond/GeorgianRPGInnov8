/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.battle.helper;

import engine.Blackwind;

/**
 *
 * @author Connor
 */
public class EntityLoader {
    public static final int
    		PLAYER=0,
            SLIME=1,
            GOBLIN=2,
            SNAKE=3,
            BAT=4,
            WILSON=40,
            MATILDA=41,
            MERG=42,
            HUNTRESS=43;
    
    private static int[]    basicStats = {20,20,10,10,6,5,6,5,5,5},
                            slimeStats = {15,15,10,10,2,1,4,8,4},
                            goblinStats= {25,25,00,00,9,1,6,0,3},
                            snakeStats = {15,15,10,10,3,10,3,6,10},
                            batStats    ={10,10,00,00,5,15,1,0,10};
    
    
    public static BattleEntity loadEntity(int entityID){
        switch(entityID){
            //enemy entities:  stats, name, element, xpgained when killed
        	case 0:     return new BattleEntity(basicStats, Blackwind.getPlayer().getName());
            case 1:     return new BattleEntity(slimeStats,"Slime",Element.WATER,20);
            case 2:     return new BattleEntity(goblinStats,"Goblin",Element.NEUTRAL,40);
            case 3:	return new BattleEntity(snakeStats,"Snake",Element.EARTH,10);
            case 4:     return new BattleEntity(batStats,"Bat",Element.AIR,15);
            case 40:    return new BattleEntity(basicStats, "Wilson");
            case 41:    return new BattleEntity(basicStats, "Matilda");
            case 42:    return new BattleEntity(basicStats, "Merg");
            case 43:    return new BattleEntity(basicStats, "Huntress");
        }
        return null;
    }
}
