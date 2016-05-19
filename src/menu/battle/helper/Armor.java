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
public class Armor extends Item{
    int damageReduction, statToIncrease, statIncreaseValue;
    
    Armor(int id, int dmgReduc, int statToIncrease, int statIncreaseValue, String name, String description, int shopValue){
        super(id,1,99,name,description,shopValue);
        this.damageReduction=dmgReduc;
        this.statIncreaseValue=statIncreaseValue;
        this.statToIncrease=statToIncrease;
    }
    public void equip(BattleEntity e){
        switch(statToIncrease){
            case StatID.HP:e.increaseHp(statToIncrease);break;
            case StatID.MP:e.increaseMp(statToIncrease);break;
            case StatID.STRENGTH:e.increaseStr(statToIncrease);break;
            case StatID.DEXTERITY:e.increaseDex(statToIncrease);break;
            case StatID.VITALITY:e.increaseVit(statToIncrease);break;
            case StatID.INTELLIGENCE:e.increaseInt(statToIncrease);break;
            case StatID.RESISTANCE:e.increaseRes(statToIncrease);break;
            //case StatID.STRENGTH:e.increaseStr(statToIncrease);
        }
        
    }
    public void unEquip(BattleEntity e){
        switch(statToIncrease){
            case StatID.HP:e.reduceHp(statToIncrease);break;
            case StatID.MP:e.reduceMp(statToIncrease);break;
            case StatID.STRENGTH:e.reduceStr(statToIncrease);break;
            case StatID.DEXTERITY:e.reduceDex(statToIncrease);break;
            case StatID.VITALITY:e.reduceVit(statToIncrease);break;
            case StatID.INTELLIGENCE:e.reduceInt(statToIncrease);break;
            case StatID.RESISTANCE:e.reduceRes(statToIncrease);break;
            //case StatID.STRENGTH:e.increaseStr(statToIncrease);
        }
    }
    @Override
    public void use(BattleEntity target) {
        
    }

    @Override
    public int attack() {
        throw new ItemCannotDoThisException("This Item Cannot Attack");
    }

    @Override
    public String getDamageRange() {
        throw new ItemCannotDoThisException("This Item Doesn't have a damage range");
    }
    public void isArmor(){
        //throw new ItemCannotDoThisException("Item cannot be equipped as armor");
    }
}
