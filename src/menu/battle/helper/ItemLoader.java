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
public class ItemLoader {
    public static final int
            EMPTY=0,
            POTION=1,
            ELIXER=2,
            REJUVI=3,
            PHEONIXDOWN=4,
            FIREBOMB=5,
            ICEBOMB=6,
            BRONZESWORD=10,
            IRONSWORD=11,
            STEELSWORD=12,
            LEATHERARMOR=13,
            LEATHERGLOVES=14,
            DOORKEY=40;
    /**
     * 
     * @param itemID
     * @param q quantity
     * @return 
     */
    public static Item loadItem(int itemID, int q){
        switch(itemID){
            //healing items  ID,Quantity,maxQuantity,HealAmount,Name,Description,HealType
            case 1: return new HealingItem(itemID,q,10,10,"Potion","Heals for 10hp",HealingItem.HEALTH,50);
            case 2: return new HealingItem(itemID,q,10,10,"Elixer","Heals for 10mp",HealingItem.MANA,100);
            case 3: return new HealingItem(itemID,q,10,10,"Rejuvi","Heals for 10hp/10mp",HealingItem.HPANDMP,250);
                
            case 4: return new RevivalItem(itemID,q,10,5,"Pheonix Down","Revives a dead character",500);
            //damaging items ID,Quantity,maxQuantity,damageAmount,Name,Description,element
            case 5: return new DamageItem(itemID,q,5,10,"Fire Bomb","Deals 10 fire damage",Element.FIRE,75);
            case 6: return new DamageItem(itemID,q,5,10,"Ice Grenade","Deals 10 water damage",Element.WATER,75);
            //Equipment     ID,baseDamage,RollDamage,Name, Description (Quantities are always 1)    
            case 10: return new Weapon(itemID,5,4,"Bronze Sword","A simple sword for simple people",200);
            case 11: return new Weapon(itemID,7,5,"Iron Sword","A less simple sword for simple people",500);
            case 12: return new Weapon(itemID,9,6,"Steel Sword","A sword for more advanced people",1000);
                //armor                 id,damageReduction,statToIncrease,increaseValue,name,description,shopValue
            case 13: return new Armor(itemID,2,StatID.VITALITY,1,"Leather Armor","Weak Armor, meant for novices",100);
            case 14: return new Armor(itemID,1,StatID.DEXTERITY,1,"Leather Gloves","Thieves Gloves",80);    
            //key item                  ID,quantity,maxQuantity,name,description
            case 40: return new KeyItem(itemID,1,1,"Door Key","It fits a door. you think");
            
        }
        return null;
    }
}
