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
public abstract class Spell extends BattleAction{
    Spell(BattleEntity caster){
        super(caster);
    }
    public abstract boolean cast(BattleEntity target);
    //public abstract boolean execute(Entity target);

}
