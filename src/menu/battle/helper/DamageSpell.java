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
public class DamageSpell extends Spell{
    int baseDamage, rollDamage, mpCost;
    String name, description;
    Random rand;
    
    int element, castingStat;
    DamageSpell(String name, String description, int baseDamage,int rollDamage, int mpCost,  int element, int castingStat){
        super(null);
        this.mpCost=mpCost;
        this.baseDamage=baseDamage;
        this.rollDamage=rollDamage;
        this.name=name;
        this.description=description;
        this.element=element;
        this.castingStat=castingStat;
        rand = new Random();
    }
    @Override
    public boolean cast(BattleEntity target) {
        try{
            if(!getCaster().canCastSpell(mpCost))
                throw new OutOfManaError("Not Enough MP");
            else{
                getCaster().spellCast(mpCost);
                int damage =0;
                damage+=baseDamage+rand.nextInt(rollDamage);
                damage*=Element.handler(target.getElement(), element);
                target.damage(damage);
                System.out.printf("dealt %d damage to %s\n",damage,target.toString());
            }
        }catch (OutOfManaError e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean execute(BattleEntity target) {
        return cast(target);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMpCost() {
        return mpCost;
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
