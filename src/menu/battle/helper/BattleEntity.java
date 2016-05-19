/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.battle.helper;

import java.util.ArrayList;

import menu.battle.helper.Item.ItemCannotDoThisException;

/**
 * Entity represents a unit that can be found within a battle. This can be an enemy, non-player character, or a player character.
 * It will contain stats representing its strength, equipment equipped by the player, a name, and functions controlling these.
 * @author Connor
 */

public class BattleEntity {
    private Stat
            Strength,
            Dexterity,
            Vitality,
            Intelligence,
            Resistance,
            HP,
            HPMax,
            MP,
            MPMax;
    /*
    to replace:
    Stats:
    Str; weapon dmg
    Dex; some weapon dmg, turn speed
    Int; magic dmg
    Vit; defense, hp
    Res; magic defense
    */
    public String
            name;
    
    private boolean 
            isDead;
    private int     
            element,
            exp,
            level,
            guildValue;
    Item  weapon;
    Item[] armor;
    //only for enemy entities
    int xpGranted;
    /*
    Equipment   weapon,
                armor1,
                armor2,
                armor3;
    */
    
    ArrayList<BattleAction> abilities;
    ArrayList<Status> statuses;
    
    
    
    /**
     * Constructor.
     * @param allStats 0=hp, 1=maxhp, 2=mp, 3=maxmp, 4=str, 5=dex, 6=vit, 7=int, 8=res
     * @param name 
     */
    BattleEntity(int[] allStats, String name){
        setAllStats(allStats);
        this.name=name;
        isDead=false;
        element=Element.NEUTRAL;
        exp=0;
        level=1;
        abilities = new ArrayList<>();
        statuses=new ArrayList<>();
        armor = new Item[3];
    }
    BattleEntity(int[] allStats, String name, int element, int xpGranted){
        setAllStats(allStats);
        this.name=name;
        isDead=false;
        this.element=element;
        this.xpGranted=xpGranted;
        abilities = new ArrayList<>();
        statuses=new ArrayList<>();
        armor = new Item[3];
    }
    BattleEntity(int[] allStats, String name, boolean isDead, int element, int exp, Weapon weapon, int level){
        setAllStats(allStats);
        this.name=name;
        this.isDead=isDead;
        this.element=element;
        this.exp=exp;
        this.weapon=weapon;
        this.level=level;
        abilities = new ArrayList<>();
        statuses=new ArrayList<>();
        armor = new Item[3];
    }
    //all functions that edit a stat or variable
    private void setAllStats(int[] allStats){
       Strength     =new Stat(allStats[4]);
       Dexterity    =new Stat(allStats[5]);
       Vitality     =new Stat(allStats[6]);
       Intelligence =new Stat(allStats[7]);
       Resistance   =new Stat(allStats[8]);
       HP           =new Stat(allStats[0]);
       HPMax        =new Stat(allStats[1]);
       MP           =new Stat(allStats[2]);
       MPMax        =new Stat(allStats[3]);
    }
    public void damage(int damageTaken){
        this.HP.reduce(damageTaken);
        if(HP.get()<=0){
            isDead=true;
            HP.increase(0-HP.get());
        }
    }
    public void heal(int healValue){
        if(!isDead){
            this.HP.increase(healValue);
            if(HP.get()>HPMax.get()){
                HP.reduce(HP.get()-HPMax.get());
            }
        }else{
            //System.out.println("!!target is dead, and cannot be healed!!");
        }
    }
    public void mpHeal(int healValue){
        if(!isDead){
            this.MP.increase(healValue);
            if(MP.get()>MPMax.get()){
                MP.reduce(MP.get()-MPMax.get());
            }
        }else{
            //System.out.println("!!target is dead, and cannot be healed!!");
        }
    }
    public void raise(int healValue){
        if(isDead){
            this.HP.increase(healValue);
            isDead=false;
        }
        else{
            //System.out.println("Target is not dead");
        }
    }
    public boolean canCastSpell(int manaUsed){
        return manaUsed<=MP.get();
    }
    public void spellCast(int manaUsed){
        if(canCastSpell(manaUsed)){
            MP.reduce(manaUsed);
            //System.out.println("Fwoosh! Fireball!!");
        }else{
            //System.out.println("Not Enough Mana");
        } 
    }
    public void longRest(){
        isDead=false;
        heal(HPMax.get());
        mpHeal(MPMax.get());
    } 
    public void gainExp(int xpGained){
        this.exp+=xpGained;
        levelup();
    }
    public void levelup(){
        while(exp>=level*100){
            level+=1;
            System.out.printf("Level Up! %s Reached Level %d\n",name, level);
            increaseAllStats();
            
        }
    }
    public void increaseAllStats(){
        Strength.increase(1);
        Dexterity.increase(1);
        Vitality.increase(1);
        Intelligence.increase(1);
        Resistance.increase(1);
        
        HP.increase(5);
        HPMax.increase(5);
        MP.increase(5);
        MPMax.increase(5);
    }
    public void increaseAllStats(int hp, int mp, int str, int dex, int vit, int ine, int res){
        Strength.increase(str);
        Dexterity.increase(dex);
        Vitality.increase(vit);
        Intelligence.increase(ine);
        Resistance.increase(res);
        HP.increase(hp);
        HPMax.increase(hp);
        MP.increase(mp);
        MPMax.increase(mp);
    }
    public void assignGuildValue(int guildSlot){
        this.guildValue=guildSlot;
    }
    public void addBattleAction(BattleAction action){
        action.setCaster(this);
        abilities.add(action);
    }
    public void useBattleAction(int action, BattleEntity target){
        abilities.get(action).execute(target);
    }
    public void addNewStatus(Status s){
        int loc = hasBuff(s);
        if(loc!=-1){
            if(statuses.get(loc).getType()==s.getType())
                statuses.get(loc).setDuration(s.getDuration());
            else
                statuses.remove(loc);
        }else
            statuses.add(s);
    }
    public int hasBuff(Status s){
        for(int i=0;i<statuses.size();i++){
            if(statuses.get(i).getStatChanged()==s.getStatChanged()){
                return i;
            }
        }
        return -1;
    }
    public void listAllStatuses(){
        for(Status stat:statuses){
            System.out.println(stat.toString());
        }
    }
    public void removeAllStatuses(){
        for(int i=statuses.size()-1;i>=0;i--){
            statuses.remove(i);
        }
    }
    public Item equipWeapon(Item w){
        //if(w.getClass()!=ItemLoader.loadItem(ItemLoader.STEELSWORD, 1).getClass())
        if(w==null){
            Item unequip=weapon;
            weapon=null;
            if(unequip!=null)
                unequip.unEquip(this);
            return unequip;
        }
        w.attack();
        
        if(weapon!=null){
            Item unequipped = weapon;
            w.equip(this);
            weapon = w;
            unequipped.unEquip(this);
            return unequipped;
        }else{
            w.equip(this);
            weapon=w;
            return null;
        }
    }
    public boolean equipArmor(Item a){
        a.isArmor();
        for(int i=0;i<armor.length;i++){
            if(armor[i]==null){
                a.equip(this);
                armor[i]=a;
                return true;
            }
        }
        return false;
    }
    public Item swapArmor(Item a, int swap){
        
        try{
            if(a==null){
            Item unequipped = armor[swap];
            armor[swap]=a;
            if(unequipped!=null)
                unequipped.unEquip(this);
            return unequipped;
        }
        a.isArmor();
        Item unequipped = armor[swap];
        armor[swap] = a;
        a.equip(this);
        if(unequipped!=null)
            unequipped.unEquip(this);
        return unequipped;
        }catch(IndexOutOfBoundsException t){
            return null;
        }
    }
    //increase functions
    public void increaseStr(int i){
        Strength.increase(i);
    }
    public void increaseDex(int i){
        Dexterity.increase(i);
    }
    public void increaseVit(int i){
        Vitality.increase(i);
    }
    public void increaseInt(int i){
        Intelligence.increase(i);
    }
    public void increaseRes(int i){
        Resistance.increase(i);
    }
    public void increaseHp(int i){
        HPMax.increase(i);
    }
    public void increaseMp(int i){
        MPMax.increase(i);
    }
    
    public void reduceStr(int i){
        Strength.reduce(i);
    }
    public void reduceDex(int i){
        Dexterity.reduce(i);
    }
    public void reduceVit(int i){
        Vitality.reduce(i);
    }
    public void reduceInt(int i){
        Intelligence.reduce(i);
    }
    public void reduceRes(int i){
        Resistance.reduce(i);
    }
    public void reduceHp(int i){
        HPMax.reduce(i);
    }
    public void reduceMp(int i){
        MPMax.reduce(i);
    }
    //get functions
    public boolean isDead(){
        return isDead;
    }
    public int getStr(){
        for(Status effect:statuses){
            if(effect.getStatChanged()==StatID.STRENGTH){
                int newStat = Strength.get();
                newStat*=effect.statAjustment();
                return newStat;
            }
        }
        return Strength.get();
    }
    public int getDex(){
        for(Status effect:statuses){
            if(effect.getStatChanged()==StatID.DEXTERITY){
                int newStat = Dexterity.get();
                newStat*=effect.statAjustment();
                return newStat;
            }
        }
        return Dexterity.get();
    }
    public int getVit(){
        for(Status effect:statuses){
            if(effect.getStatChanged()==StatID.VITALITY){
                int newStat = Vitality.get();
                newStat*=effect.statAjustment();
                return newStat;
            }
        }
        return Vitality.get();
    }
    public int getInt(){
        for(Status effect:statuses){
            if(effect.getStatChanged()==StatID.INTELLIGENCE){
                int newStat = Intelligence.get();
                newStat*=effect.statAjustment();
                return newStat;
            }
        }
        return Intelligence.get();
    }
    public int getRes(){
        for(Status effect:statuses){
            if(effect.getStatChanged()==StatID.RESISTANCE){
                int newStat = Resistance.get();
                newStat*=effect.statAjustment();
                return newStat;
            }
        }
        return Resistance.get();
    }
    public int getHp(){
        return HP.get();
    }
    public int getMaxHp(){
        for(Status effect:statuses){
            if(effect.getStatChanged()==StatID.MAXHP){
                int newStat = HPMax.get();
                newStat*=effect.statAjustment();
                return newStat;
            }
        }
        return HPMax.get();
    }
    public int getMp(){
        return MP.get();
    }
    public int getMaxMp(){
        for(Status effect:statuses){
            if(effect.getStatChanged()==StatID.MAXMP){
                int newStat = MPMax.get();
                newStat*=effect.statAjustment();
                return newStat;
            }
        }
        return MPMax.get();
    }  
    public String getName(){
        return name;
    }
    public int getGuildValue(){
        return guildValue;
    }
    public int getElement(){
        return element;
    }
    public ArrayList<BattleAction> getSkillList(){
        return abilities;
    }
    public int getLevel(){
        return level;
    }
    public int getExp(){
        return exp;
    }
    public int getExpUntilLevel(){
        return (100*level)-exp;
        
    }
    public Item getWeapon(){
        return weapon;
    }
    public Item[] getArmorList(){
        return armor;
    }
    public Item getArmor(int itemInList){
        return armor[itemInList];
    }
    public int getXpGranted(){
        return xpGranted;
    }
    
    public void printAllArmor(){
        for(int i=0;i<armor.length;i++){
            try{
                System.out.println(armor[i].toString());
            }catch(NullPointerException e){
                System.out.println("---");
            }
        }
    }
    public void printHealthAndMana(){
        System.out.println(this);
        System.out.printf("%s/%s - %s/%s\n",getHp(),getMaxHp(),getMp(),getMaxMp());
        System.out.printf("Character %s dead\n",!isDead()?"is not":"is");
    }
    public void printAllStats(){
        System.out.printf("Str: %d Dex: %d Vit: %d Int: %d Res: %d\n",getStr(),getDex(),getVit(),getInt(),getRes());
    }
    //toString
    public String toString(){
        return name;
    }
    
    //nested class stat
    private class Stat {
        private int     stat;
        Stat(int stat){
            this.stat=stat;
        }
        public int get(){
            return stat;
        }
        public void reduce(int reduction){
            this.stat-=reduction;
        }
        public void increase(int increase){
            this.stat+=increase;
        }
    }

    
    //End of Class. Class tester after
    public static void main(String[] args){
        text("----------Test01----------");
        text("Create and print an instance of Entity, with stats");
        //int[] basicStats = {15,15,10,10,5,5,5,5,5,5};
        //Entity wilson = new Entity(basicStats, "Wilson");
        BattleEntity wilson = EntityLoader.loadEntity(EntityLoader.WILSON);
        text(wilson.toString());
        wilson.printAllStats();
        getWilson(wilson);
        
        text("----------Test02----------");
        text("Cause the entity to take damage, and then heal them after. the heal must not bring them above maxHP");
        text("10 Damage");
        wilson.damage(10);
        getWilson(wilson);
        text("Heal for 50");
        wilson.heal(50);
        getWilson(wilson);
        
        text("----------Test03----------");
        text("Cause the entity to take fatal damage. if the entity has taken fatal damage, they cannot be healed by the heal() function");
        text("20 damage. fatal damage");
        wilson.damage(20);
        getWilson(wilson);
        text("\n30 point heal. should fail due to death.");
        wilson.heal(30);
        getWilson(wilson);
        text("\nRaise wilson with a 5 point heal. should allow full heal after (10 point heal)");
        wilson.raise(5);
        wilson.heal(10);
        getWilson(wilson);
        
        text("----------Test04----------");
        text("Wilson casts a spell. it will consume 10 mana. After one use, wilson cant cast another spell.");
        getWilson(wilson);
        text("Spell #1");
        wilson.spellCast(10);
        getWilson(wilson);
        text("Spell #2");
        wilson.spellCast(10);
        getWilson(wilson);
        
        text("----------Test05----------");
        text("Heal to full. rest at an inn or somesuch.");
        wilson.longRest();
        getWilson(wilson);
        
        text("----------Test06----------");
        text("Gain Experience and level up");
        wilson.gainExp(30);
        wilson.gainExp(30);
        wilson.gainExp(30);
        getWilson(wilson);
        wilson.printAllStats();
        
        text("----------Test07----------");
        text("Raising skill by other means");
        wilson.increaseAllStats(10, 7, 1, 2, 3, 1, 1);
        getWilson(wilson);
        wilson.printAllStats();
        
        text("----------Test08----------");
        text("Testing buffs and debuffs");
        Status atkup = new Buff("Bravery",StatID.STRENGTH,5,1.5);
        Status defup = new Buff("Turtle",StatID.VITALITY,5,1.5);
        Status defdown = new Debuff("Weakening",StatID.VITALITY,5,1.5);
        wilson.printAllStats();
        text("Giving wilson attack up");
        wilson.addNewStatus(atkup);
        wilson.printAllStats();
        text("Giving wilson defense up");
        wilson.addNewStatus(defup);
        wilson.printAllStats();
        text("Giving wilson defense down (cancells the buff)");
        wilson.addNewStatus(defdown);
        wilson.printAllStats();
        text("Giving wilson defense down (reduces defence)");
        wilson.addNewStatus(defdown);
        wilson.printAllStats();
        text("\nList all status changes wilson has on him");
        wilson.listAllStatuses();
        text("----------Test08----------");
        text("Using the buff/debuff loader");
        wilson.removeAllStatuses();
        wilson.printHealthAndMana();
        wilson.printAllStats();
        wilson.addNewStatus(Status.loadBuff(StatID.STRENGTH, Status.i25));
        wilson.addNewStatus(Status.loadDebuff(StatID.DEXTERITY, Status.i10));
        wilson.addNewStatus(Status.loadBuff(StatID.MAXHP, Status.i50));
        wilson.addNewStatus(Status.loadBuff(StatID.MAXMP, Status.i25));
        wilson.addNewStatus(Status.loadDebuff(StatID.RESISTANCE, Status.i50));
        wilson.addNewStatus(Status.loadBuff(StatID.INTELLIGENCE, Status.i25));
        wilson.listAllStatuses();
        wilson.printHealthAndMana();
        wilson.printAllStats();
        text("----------Test09----------");
        text("Testing Equipment");
        wilson.removeAllStatuses();
        Item w = ItemLoader.loadItem(ItemLoader.STEELSWORD, 1);
        try{
            
            wilson.printAllStats();
            text("Equip");
            wilson.equipWeapon(w);
            wilson.printAllStats();
            wilson.equipArmor(ItemLoader.loadItem(ItemLoader.LEATHERARMOR, 1));
            wilson.printAllStats();
            text("Trying to equip a non equippable item");
            wilson.equipArmor(ItemLoader.loadItem(ItemLoader.POTION, 1));
        }catch(ItemCannotDoThisException e){
            System.out.println(e);
        }
        text("----------Test10----------");
        text("Overstacking on armor");
        wilson.printAllArmor();
        wilson.equipArmor(ItemLoader.loadItem(ItemLoader.LEATHERARMOR, 1));
        wilson.equipArmor(ItemLoader.loadItem(ItemLoader.LEATHERARMOR, 1));
        System.out.println(wilson.equipArmor(ItemLoader.loadItem(ItemLoader.LEATHERARMOR, 1)));
        wilson.printAllArmor();
        wilson.printAllStats();
        text("----------Test11----------");
        text("Swapping out a piece of armor");
        Item armor2 = ItemLoader.loadItem(ItemLoader.LEATHERGLOVES, 1);
        wilson.equipArmor(armor2);
        wilson.printAllArmor();
        wilson.printAllStats();
        wilson.swapArmor(armor2, 0);
        wilson.printAllArmor();
        wilson.printAllStats();
        try{
            wilson.equipWeapon(armor2);
        }catch(ItemCannotDoThisException e){
            System.out.println(e);
        }
        try{
            wilson.equipArmor(w);
        }catch(ItemCannotDoThisException e){
            System.out.println(e);
        }
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
