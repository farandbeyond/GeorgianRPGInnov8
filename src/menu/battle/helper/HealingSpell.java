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
public class HealingSpell extends Spell{
    int baseHeal, rollHeal, mpCost;
    String name, description;
    Random rand;
    int castingStat;
    HealingSpell(String name, String description, int baseHeal,int rollHeal, int mpCost, int castingStat){
        super(null);
        this.mpCost=mpCost;
        this.baseHeal=baseHeal;
        this.rollHeal=rollHeal;
        this.name=name;
        this.description=description;
        this.castingStat=castingStat;
        rand = new Random();
    }

    @Override
    public boolean cast(BattleEntity target) {
        //this.caster=caster;
        try{
            if(!getCaster().canCastSpell(mpCost))
                throw new OutOfManaError("Not Enough MP");
            else{
                getCaster().spellCast(mpCost);
                int heal =0;
                heal+=baseHeal+rand.nextInt(rollHeal);
                target.heal(heal);
                System.out.printf("Healed %s for %d\n",target.toString(),heal);
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
        return 0;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getTarget() {
        return BattleAction.ALLY;
    }
}
