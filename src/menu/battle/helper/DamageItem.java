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
public class DamageItem extends Item{
    int     element,
            damage;
    DamageItem(int id, int quantity, int maxQuantity,int damageValue, String name,String description,int element, int shopValue){
        super(id,quantity,maxQuantity,name,description,shopValue);
        this.element=element;
        this.damage=damageValue;
    }
    public void use(BattleEntity target){
        int dmg = damage;
        lowerQuantity(1);
        dmg*=Element.handler(target.getElement(), element);
        target.damage(dmg);
        System.out.printf("%s dealt %d damage to %s\n",getName(), dmg, target.getName());
    }

    @Override
    public int attack() {
        throw new ItemCannotDoThisException("Item Cannot attack");
    }

    @Override
    public String getDamageRange() {
        throw new ItemCannotDoThisException("Item does not have a damage range");
    }

    @Override
    public void equip(BattleEntity e) {
        throw new ItemCannotDoThisException("Item Cannot Be equipped");
    }

    @Override
    public void unEquip(BattleEntity e) {
        throw new ItemCannotDoThisException("Item Cannot Be equipped");
    }
    public void isArmor(){
        throw new ItemCannotDoThisException("Item cannot be equipped as armor");
    }
}
