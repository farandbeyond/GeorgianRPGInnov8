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
public class Gold {
    int gold;
    Gold(int startingGold){
        this.gold=startingGold;
    }
    Gold(){
        gold=500;
    }
    
    public void printGold(){
        System.out.println(gold);
    }
    public int getGold(){
        return gold;
    }
    public boolean canAfford(int cost){
        return cost<gold;
    }
    public void increase(int increase){
        gold+=increase;
    }
    public void decrease(int decrease){
        gold-=decrease;
    }
}
