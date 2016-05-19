/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.battle.helper;

import java.util.Random;

/**
 *
 * @author Connor
 */
public class Weapon extends Item{
    int     baseDamage,
            rollDamage,
            id;
    String  name,
            description;
    Random  rand;
    Weapon(int id, int baseDamage, int rollDamage, String name, String description, int shopValue){
        super(id,1,99,name,description,shopValue);
        this.baseDamage=baseDamage;
        this.rollDamage=rollDamage;
        rand = new Random();
        /*
        this.id=id;
        this.baseDamage=baseDamage;
        this.name=name;
        this.description=description;
        this.rollDamage=rollDamage;
        rand = new Random();*/
    }
    @Override
    public void use(BattleEntity Target){
        //System.out.println("No Effect");
        throw new ItemCannotDoThisException("Item Cannot be used");
    }
    @Override
    public int attack(){
        return baseDamage+rand.nextInt(rollDamage);
    }
    @Override
    public String getDamageRange(){
        return String.format("%d-%d", baseDamage,baseDamage+rollDamage);
    }
    public void isArmor(){
        throw new ItemCannotDoThisException("Item cannot be equipped as armor");
    }
    /*public int attack(int Strength){
        int damage = baseDamage+rand.nextInt(rollDamage)+(Strength/2);
        System.out.println(damage+" Damage");
        return damage;
    }*/

    @Override
    public void equip(BattleEntity e) {
        //throw new ItemCannotDoThisException("Item Cannot Be equipped");
    }

    @Override
    public void unEquip(BattleEntity e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public static void main(String[] args){
        text("----------Test01----------");
        text("Ooh a sword! Sharp!");
        int[] basicStats = {15,15,10,10,5,5,5,5,5,5};
        BattleEntity wilson = new BattleEntity(basicStats, "Wilson");
        Weapon sword = new Weapon(101,5,4,"Bronze Sword","A simple sword for simple people",100);
        text(sword.getName());
        text("----------Test02----------");
        text("Wilson Equips the sword.");
        wilson.weapon=sword;
        text(wilson.weapon.getName());
        /*text("----------Test03----------");
        text("Wilson attacks... himself. ouch");
        getWilson(wilson);
        wilson.damage(wilson.weapon.attack(wilson.getStr()));
        getWilson(wilson);*/
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
