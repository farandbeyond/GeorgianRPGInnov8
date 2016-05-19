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
public class HealingItem extends Item implements Usable {
    public static final int
            HEALTH=0,
            MANA=1,
            HPANDMP=2;
        int     healValue,
                type;
    public HealingItem(int id, int quantity, int maxQuantity,int healValue, String name,String description,int type, int shopValue){
        super(id,quantity,maxQuantity,name,description,shopValue);
        this.healValue=healValue;
        this.type=type;
    }
    @Override
    public void use(BattleEntity target){
        if(getQuantity()==0){
            System.out.println("You cannot use this item: You have none left");
        }else{
            lowerQuantity(1);
            switch(type){
                case 0:target.heal(healValue);break;
                case 1:target.mpHeal(healValue);break;
                case 2:target.heal(healValue);target.mpHeal(healValue);break;
            }
            
        }
    }
    @Override
    public int attack() {
        throw new ItemCannotDoThisException("Item Cannot Attack");
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
    
    //end of class. class tester below
    public static void main(String[] args){
        text("----------Test01----------");
        text("Wilson Finds a potion. He drinks it.");
        int[] basicStats = {15,15,10,10,5,5,5,5,5,5};
        BattleEntity wilson = new BattleEntity(basicStats, "Wilson");
        HealingItem potion = new HealingItem(0,2,5,10,"potion","Heals for 10hp",HealingItem.HEALTH,30);
        wilson.damage(10);
        getWilson(wilson);
        potion.use(wilson);
        getWilson(wilson);
        text("----------Test02----------");
        text("There were two. after drinking the second one, there are none left. wilson tries to drink the empty potion");
        wilson.damage(14);
        getWilson(wilson);
        potion.use(wilson);
        getWilson(wilson);
        potion.use(wilson);
        getWilson(wilson);
        text("----------Test03----------");
        text("More Potions! you found 3! then 3 more! you can carry them all!");
        potion.restock(3);
        text(""+potion.getQuantity());
        potion.restock(3);
        text(""+potion.getQuantity());
    }
    public static void text(String text){
        System.out.println(text);
    }
    public static void getWilson(BattleEntity wilson){
        System.out.println(wilson);
        System.out.println(wilson.getHp()+"/"+wilson.getMaxHp()+" hp "+wilson.getMp()+"/"+wilson.getMaxMp()+" mp");
        System.out.println("is dead?: "+wilson.isDead());
    }

    
}
