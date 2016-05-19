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
public class Debuff extends Status{
    int statDebuffed, duration; double debuffValue;
    String name;
    
    Debuff(String name,int StatID, int duration, double buffValue){
        this.name=name;
        this.statDebuffed=StatID;
        this.debuffValue=buffValue;
        this.duration=duration;
    }
    
    @Override
    public void assign(BattleEntity target){
        target.addNewStatus(this);
    }
    @Override
    public int getStatChanged(){
        return statDebuffed;
    }
    @Override
    public int getType(){
        return DEBUFF;
    }
    @Override
    public int getDuration(){
        return duration;
    }
    @Override
    public void setDuration(int dur){
        duration=dur;
    }
    @Override
    public boolean reduceDuration(){
        duration--;
        return duration==0;
    }
    @Override
    public double statAjustment(){
        return 1/debuffValue;
    }
    
    public String toString(){
        return name+": Reduces "+StatID.getStatName(statDebuffed)+" by "+debuffValue+"x";
    }
}
