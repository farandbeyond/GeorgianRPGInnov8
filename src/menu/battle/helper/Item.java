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
public abstract class Item {
    private
    int     quantity;
    private
    final
    int     maxQuantity,
            id,
            shopValue;
    private
    String  name,
            description;
    
    Item(int id, int quantity, int maxQuantity, String name,String description, int shopValue){
        this.id=id;
        this.name=name;
        this.quantity=quantity;
        this.maxQuantity=maxQuantity;
        this.description=description;
        this.shopValue=shopValue;
    }

    public void lowerQuantity(int itemsUsed){
        quantity-=itemsUsed;
    }
    public void restock(int increase){
        /*this.quantity+=increase;
        if(quantity>maxQuantity){
            System.out.printf("Too many %s, dropping %d\n",toString(),quantity-maxQuantity);
            lowerQuantity(quantity-maxQuantity);
            throw new UnsupportedOperationException("Cannot Fit "+toString()+" In Inventory");
        }*/
        if(quantity+increase>maxQuantity){
            throw new UnsupportedOperationException("Cannot Fit "+toString()+" In Inventory");
        }
        this.quantity+=increase;
    }
    
    public abstract void use(BattleEntity target);
    public abstract int attack();
    public abstract String getDamageRange();
    public abstract void equip(BattleEntity e);
    public abstract void unEquip(BattleEntity e);
    public abstract void isArmor();
    
    public int getQuantity(){
        return quantity;
    }
    public String getDescription(){
        return description;
    }
    public int getMaxQuantity(){
        return maxQuantity;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getShopValue(){
        return shopValue;
    }
    
    public String toString(){
        return name;
    }
    public void setQuantity(int number){
        quantity=number;
    }
            
    
    public class ItemCannotDoThisException extends RuntimeException{
		private static final long serialVersionUID = 1L;

		public ItemCannotDoThisException(String error){
            super(error);
        }
    }
}
