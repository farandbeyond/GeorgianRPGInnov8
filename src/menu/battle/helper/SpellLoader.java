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
public  class SpellLoader {
    public static final int
            FIREBALL=1,
            CURE=2;
    
    public static Spell loadSpell(int spellID){
        switch(spellID){
            case 1:return new DamageSpell("Fireball","A large orb of flame",10,5,8,Element.FIRE,StatID.INTELLIGENCE);
            case 2:return new HealingSpell("Cure","A soothing light cures wounds",7,4,5,StatID.INTELLIGENCE);
        }
        return null;
    }
        
        
}
