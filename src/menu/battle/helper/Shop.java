/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.battle.helper;

import static menu.battle.helper.Inventory.text;

import java.util.ArrayList;

/**
 *
 * @author Connor
 */
public class Shop {
    
    ArrayList<Item> forSale;
    
    Shop(){
        forSale=new ArrayList<>();
    }
    Shop(ArrayList<Item> forSale){
        this.forSale=forSale;
    }
    public void add(Item item){
        forSale.add(item);
    }
    public void listAllItems(){
        for(Item item:forSale){
            System.out.println(item.toString());
        }
    }
    public void buy(Gold partyMoney, Inventory inv, int itemID, int quantity){
        Item itemToBuy = ItemLoader.loadItem(itemID, quantity);
        int totalCost = itemToBuy.getShopValue()*quantity;
        try{
            if(confirmEvent()){
                if(partyMoney.canAfford(totalCost)){
                    if(inv.add(itemToBuy)){
                        partyMoney.decrease(totalCost);
                    }
                }else{
                    throw new UnsupportedOperationException();
                }

            }
        }catch (UnsupportedOperationException e){
            System.out.printf("Not enough money. Item costs %d gold per unit, totalling %d\n",itemToBuy.getShopValue(),itemToBuy.getShopValue()*quantity);
        }
    }
    public void sell(Gold partyMoney, Item itemToSell, int quantityToSell){
                try{
                    if(quantityToSell<itemToSell.getQuantity()){
                        if(confirmEvent()){
                            itemToSell.lowerQuantity(quantityToSell);
                            partyMoney.increase(itemToSell.getShopValue()/2*quantityToSell);
                        }
                    }else{
                        throw new UnsupportedOperationException("Not Enough Quantity to sell");
                    }
                }catch (UnsupportedOperationException e){
            System.out.printf("Cannot sell more of %s than you own. you have x%d\n",itemToSell.toString(),itemToSell.getQuantity());
        }
    }
    public boolean confirmEvent(){
        return true;
    }
    
    
    public static void main(String[] args){
        text("----------Test01----------");
        text("Creation of items");
        Shop itemShop = new Shop();
        Inventory inv = new Inventory(5);
        Party party = new Party(4);
        party.add(EntityLoader.loadEntity(EntityLoader.WILSON));
        party.add(EntityLoader.loadEntity(EntityLoader.MATILDA));
        party.add(EntityLoader.loadEntity(EntityLoader.MERG));
        party.add(EntityLoader.loadEntity(EntityLoader.HUNTRESS));
        inv.add(ItemLoader.loadItem(ItemLoader.POTION, 5));
        inv.add(ItemLoader.loadItem(ItemLoader.FIREBOMB, 5));
        itemShop.add(ItemLoader.loadItem(ItemLoader.POTION, 99));
        text("----------Test02----------");
        text("Buying items that i have to many of");
        System.out.printf("%d Gold\n",party.getMoney());
        itemShop.listAllItems();
        itemShop.buy(party.getGold(), inv, ItemLoader.POTION, 5);
        System.out.printf("%d Gold\n",party.getMoney());
        text("----------Test03----------");
        text("Buying items that i cant afford");
        itemShop.add(ItemLoader.loadItem(ItemLoader.STEELSWORD,99));
        itemShop.listAllItems();
        System.out.printf("%d Gold\n",party.getMoney());
        itemShop.buy(party.getGold(), inv, ItemLoader.STEELSWORD, 5);
        System.out.printf("%d Gold\n",party.getMoney());
        inv.listItems();
        text("----------Test04----------");
        text("I can actually buy this");
        itemShop.add(ItemLoader.loadItem(ItemLoader.ELIXER,99));
        System.out.printf("%d Gold\n",party.getMoney());
        itemShop.buy(party.getGold(), inv, ItemLoader.ELIXER, 1);
        System.out.printf("%d Gold\n",party.getMoney());
        inv.listItems();
        text("----------Test05----------");
        text("Selling some items. The potions can go.");
        System.out.printf("%d Gold\n",party.getMoney());
        inv.listItems();
        itemShop.sell(party.getGold(), inv.getItem(0), 2);
        System.out.printf("%d Gold\n",party.getMoney());
        inv.listItems();
        text("----------Test06----------");
        text("Selling more potions than i have");
        System.out.printf("%d Gold\n",party.getMoney());
        inv.listItems();
        itemShop.sell(party.getGold(), inv.getItem(1), 6);
        System.out.printf("%d Gold\n",party.getMoney());
        inv.listItems();
    }
}
