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
public class RevivalItem extends Item implements Usable{
        int     healValue;
    public RevivalItem(int id, int quantity, int maxQuantity,int healValue, String name,String description, int shopValue){
        super(id,quantity,maxQuantity,name,description,shopValue);
        this.healValue=healValue;
    }
    @Override
    public void use(BattleEntity target){
        if(getQuantity()==0){
            System.out.println("You cannot use this item: You have none left");
        }else{
            lowerQuantity(1);
            target.raise(healValue);
        }
    }
    @Override
    public int attack() {
        throw new Item.ItemCannotDoThisException("Item Cannot Attack");
    }

    @Override
    public String getDamageRange() {
        throw new Item.ItemCannotDoThisException("Item does not have a damage range");
    }

    @Override
    public void equip(BattleEntity e) {
        throw new Item.ItemCannotDoThisException("Item Cannot Be equipped");
    }

    @Override
    public void unEquip(BattleEntity e) {
        throw new Item.ItemCannotDoThisException("Item Cannot Be equipped");
    }
    public void isArmor(){
        throw new Item.ItemCannotDoThisException("Item cannot be equipped as armor");
    }
}
