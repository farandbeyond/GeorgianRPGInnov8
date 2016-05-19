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
public class PhysicalSkill extends BattleAction{
    int     baseDamage,
            rollDamage,
            castingStat,
            defenseStat,
            element;
    String  name,
            description;
    Random  rand;
    PhysicalSkill(String name, int baseDamage, int rollDamage, int castingStat, int defenseStat){
        super(null);
        this.baseDamage=baseDamage;
        this.rollDamage=rollDamage;
        rand=new Random();
        this.castingStat=castingStat;
        this.defenseStat=defenseStat;
        this.name=name;
        this.element=Element.NEUTRAL;
        this.description="Descriptions are not yet passed in to this type of ability";
    }
    public boolean execute(BattleEntity target){
        //this.caster=caster;
        int statBonus=StatID.retrieveStat(getCaster(), castingStat);
        int damage=baseDamage+rand.nextInt(rollDamage);
        damage+=statBonus;
        damage-=StatID.retrieveStat(target, defenseStat);
        if(damage<0)
            damage=0;
        target.damage(damage);
        System.out.printf("%s has taken %d damage from %s's %s\n", target.toString(),damage,caster.getName(),toString());
        return true;
    }
    
    public String toString(){
        return name;
    }
        @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMpCost() {
        return 0;
    }

    @Override
    public int getElement() {
        return element;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getTarget() {
        return BattleAction.ENEMY;
    }
}
