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
public class BattleBase {
    private static final int
            ATTACK=0;
    Party party,
            enemies;
    Inventory inventory;
    
    BattleBase(Party party, Party enemies, Inventory inventory){
        this.party=party;
        this.enemies=enemies;
        this.inventory=inventory;
    }
    public void dealDamage(BattleEntity target, int damage){
        target.damage(damage);
    }
    public void heal(BattleEntity target, int heal){
        target.heal(heal);
    }
    
    
    
    public void getEntityInfo(BattleEntity e){
        e.printHealthAndMana();
    }
    
    public BattleEntity getPartyEntity(int e){
        return party.getPartyArray()[e];
    }
    public BattleEntity getEnemyEntity(int e){
        return enemies.getPartyArray()[e];
    }
    public Inventory getInventory(){
        return inventory;
    }
    /*public void useItem(int itemSlot, Entity target){
        inventory.use(itemSlot, target);
    }*/
    public void useItem(Item item, BattleEntity target){
        item.use(target);
    }
    public void listItems(){
        inventory.listItems();
    }
    public void listParty(){
        party.printAllMembersHealthAndMana();
    }
    public void listEnemies(){
        enemies.printAllMembersHealthAndMana();
    }
    public void listAll(){
        listParty();
        System.out.println(" ");
        listEnemies();
    }
    
    
    
    
    public static void main(String[] args){
        text("----------Test01----------");
        text("Creation of variables");
        /*int[] basicStats = {15,15,10,10,5,5,5,5,5,5};
        Entity wilson = new Entity(basicStats, "Wilson");
        Entity matilda = new Entity(basicStats, "Matilda");
        Entity merg = new Entity(basicStats, "Merg");
        Entity huntress = new Entity(basicStats, "Huntress");
        Party party = new Party(4);
        party.add(wilson);
        party.add(matilda);
        party.add(merg);
        party.add(huntress);*/
        
        Party party = new Party(4);
        party.add(EntityLoader.loadEntity(EntityLoader.WILSON));
        party.add(EntityLoader.loadEntity(EntityLoader.MATILDA));
        party.add(EntityLoader.loadEntity(EntityLoader.HUNTRESS));
        party.add(EntityLoader.loadEntity(EntityLoader.MERG));
        /*Party enemies = new Party(3);
        Entity slime = new Entity(basicStats,"Slime");
        enemies.add(slime);
        enemies.add(slime);
        enemies.add(slime);*/
        
        Party enemies = new Party(3);
        enemies.add(EntityLoader.loadEntity(EntityLoader.SLIME));
        enemies.add(EntityLoader.loadEntity(EntityLoader.SLIME));
        enemies.add(EntityLoader.loadEntity(EntityLoader.SLIME));
        
        Inventory inv = new Inventory(4);
        inv.add(ItemLoader.loadItem(ItemLoader.POTION, 3));
        inv.add(ItemLoader.loadItem(ItemLoader.ELIXER, 3));
        inv.add(ItemLoader.loadItem(ItemLoader.REJUVI, 3));
        party.getMemberFromParty(0).gainExp(200);
        party.getMemberFromParty(0).gainExp(200);
        party.getMemberFromParty(1).gainExp(200);
        
        BattleBase battle = new BattleBase(party, enemies, inv);
        text("----------Test02----------");
        text("Show all Entities in battle");
        battle.listAll();
        text("----------Test03----------");
        text("Give everyone the 'Attack' ability");
        BattleAction attack = new PhysicalSkill("Attack",5,3,StatID.STRENGTH,StatID.VITALITY);
        for(BattleEntity member:party.getPartyArray()){
            member.addBattleAction(attack);
        }
        for(BattleEntity enemy:enemies.getPartyArray()){
            enemy.addBattleAction(attack);
        }
        text("----------Test04----------");
        text("Time to test attacking");
        battle.getPartyEntity(0).useBattleAction(ATTACK, battle.getEnemyEntity(0));
        battle.listEnemies();
        battle.getPartyEntity(1).useBattleAction(ATTACK, battle.getEnemyEntity(0));
        battle.listEnemies();
        battle.getPartyEntity(2).useBattleAction(ATTACK, battle.getEnemyEntity(0));
        battle.listEnemies();
        text("----------Test04----------");
        text("Using an Item in battle");
        battle.listItems();
        battle.getPartyEntity(1).damage(10);
        battle.getPartyEntity(1).printHealthAndMana();
        battle.useItem(battle.getInventory().getItem(0), battle.getPartyEntity(1));
        battle.getPartyEntity(1).printHealthAndMana();
        text("----------Test05----------");
        text("Lots of Spells!");
        Spell fireball = new DamageSpell("Fireball","A large orb of flame",10,5,8,Element.FIRE,StatID.INTELLIGENCE);
        Spell cure = new HealingSpell("Cure","A soothing light cures wounds",7,4,5,StatID.INTELLIGENCE);
        battle.getPartyEntity(0).addBattleAction(fireball);
        battle.getPartyEntity(1).addBattleAction(cure);
        text("Success creating and assigning spells");
        battle.getPartyEntity(0).useBattleAction(1, battle.getEnemyEntity(1));
        battle.getPartyEntity(1).useBattleAction(1, battle.getPartyEntity(0));
        battle.listParty();
        battle.listEnemies();
        text("----------Test06----------");
        text("Wilson exhausts his mana pool");
        battle.getPartyEntity(0).useBattleAction(1, battle.getEnemyEntity(1));
        battle.getPartyEntity(0).useBattleAction(1, battle.getEnemyEntity(1));
        battle.getPartyEntity(0).useBattleAction(1, battle.getEnemyEntity(1));
        battle.getPartyEntity(1).useBattleAction(1, battle.getPartyEntity(0));
        battle.getPartyEntity(1).useBattleAction(1, battle.getPartyEntity(0));
        battle.getPartyEntity(1).useBattleAction(1, battle.getPartyEntity(0));
        battle.getPartyEntity(1).useBattleAction(1, battle.getPartyEntity(0));
    }
    public static void text(String text){
        System.out.println(text);
    }
}
