/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.battle.helper;

/**
 *
 * @author Connor
 */
public class StatID {
    public static final int
            STRENGTH=0,
            DEXTERITY=1,
            VITALITY=2,
            INTELLIGENCE=3,
            RESISTANCE=4,
            HP=6,
            MAXHP=7,
            MP=8,
            MAXMP=9;
    
    public static int retrieveStat(BattleEntity e, int stat){
        switch(stat){
            case 0:return e.getStr();
            case 1:return e.getDex();
            case 2:return e.getVit();
            case 3:return e.getInt();
            case 4:return e.getRes();
            case 6:return e.getHp();
            case 7:return e.getMaxHp();
            case 8:return e.getMp();
            case 9:return e.getMaxMp();
        }
        return 0;
    }
    public static String getStatName(int stat){
        switch(stat){
            case STRENGTH:return "Strength";
            case DEXTERITY:return "Dexterity";
            case VITALITY:return "Vitality";
            case INTELLIGENCE:return "Intelligence";
            case RESISTANCE:return "Resistance";
            case HP:return "HP";
            case MAXHP:return "Max HP";
            case MP:return "MP";
            case MAXMP:return "Max MP";        
        }
        return "null";
    }
}
