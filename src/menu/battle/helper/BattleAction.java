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
public abstract class BattleAction {
    public static final int
            ENEMY=0,
            ALLY=1;
    
    BattleEntity caster;
    BattleAction(BattleEntity caster){
        this.caster=caster;
    }
    public BattleEntity getCaster(){
        return caster;
    }
    public void setCaster(BattleEntity caster){
        this.caster=caster;
    }
    public abstract boolean execute(BattleEntity target);
    public abstract String getName();
    public abstract int getMpCost();
    public abstract int getElement();
    public abstract String getDescription();
    public abstract int getTarget();
    
    public class OutOfManaError extends RuntimeException{
		private static final long serialVersionUID = 1L;

		public OutOfManaError(String error){
            super(error);
        }
    }
}
