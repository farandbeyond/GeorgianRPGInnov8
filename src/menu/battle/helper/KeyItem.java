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
public class KeyItem extends Item {
    
    KeyItem(int id, int quantity, int maxQuantity, String name,String description){
        super(id,quantity,maxQuantity,name,description,0);
    }
    public void use(BattleEntity target){
        System.out.printf("%s",getDescription());
    }

    @Override
    public int attack() {
        throw new ItemCannotDoThisException("Item Cannot attack");
    }

    @Override
    public String getDamageRange() {
        throw new ItemCannotDoThisException("Item has no damage range");
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
