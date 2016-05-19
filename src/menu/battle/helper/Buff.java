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
public class Buff extends Status{
    int statBuffed, duration; double buffValue;
    String name;
    
    public Buff(String name, int StatID, int duration, double buffValue){
        this.name=name;
        this.statBuffed=StatID;
        this.buffValue=buffValue;
        this.duration=duration;
    }
    @Override
    public void assign(BattleEntity target){
        target.addNewStatus(this);
    }
    @Override
    public int getStatChanged(){
        return statBuffed;
    }
    @Override
    public int getType(){
        return BUFF;
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
        return buffValue;
    }
    public String toString(){
        return name+": Increases "+StatID.getStatName(statBuffed)+" by "+statAjustment()+"x";
    }
}
