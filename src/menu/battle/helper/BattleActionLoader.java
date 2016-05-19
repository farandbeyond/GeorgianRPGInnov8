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
public class BattleActionLoader {
    public static final int
            ATTACK=0,
            FIREBALL=1,
            CURE=10;
    
    public static BattleAction loadAction(int actionID){
        switch(actionID){
            case 0:return new PhysicalSkill("Attack",5,5,StatID.STRENGTH,StatID.VITALITY);
            case 1:return new DamageSpell("Fireball","A large orb of flame",10,5,8,Element.FIRE,StatID.INTELLIGENCE);
            case 2:return new DamageSpell("Lightning","A bolt of Lightning",10,5,8,Element.AIR,StatID.INTELLIGENCE);
            case 3:return new DamageSpell("Quake","Shakes the earth",10,5,8,Element.EARTH,StatID.INTELLIGENCE);
            case 10:return new HealingSpell("Cure","A soothing light cures wounds",7,4,5,StatID.INTELLIGENCE);
        }
        return null;
    }
}
