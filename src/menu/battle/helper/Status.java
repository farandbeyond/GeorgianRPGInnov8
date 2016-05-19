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
public abstract class Status {
    public static final int
            BUFF=1,
            DEBUFF=0;
    
    public static final int
            i10=0,
            i25=1,
            i50=2;
    public static final int
            buffDuration=5;
    
    public abstract void assign(BattleEntity target);
    public abstract int getStatChanged();
    public abstract int getType();
    public abstract int getDuration();
    public abstract void setDuration(int newDuration);
    public abstract boolean reduceDuration();
    public abstract double statAjustment();
    
    public static Buff loadBuff(int Stat, int change){
        switch(Stat){
            case StatID.STRENGTH:
                switch(change){
                    case i10:return new Buff("Str up I",StatID.STRENGTH,buffDuration,1.1);
                    case i25:return new Buff("Str up II",StatID.STRENGTH,buffDuration,1.25);
                    case i50:return new Buff("Str up III",StatID.STRENGTH,buffDuration,1.5);
                }
            case StatID.DEXTERITY:
                switch(change){
                    case i10:return new Buff("Dex up I",StatID.DEXTERITY,buffDuration,1.1);
                    case i25:return new Buff("Dex up II",StatID.DEXTERITY,buffDuration,1.25);
                    case i50:return new Buff("Dex up III",StatID.DEXTERITY,buffDuration,1.5);
                }
            case StatID.VITALITY:
                switch(change){
                    case i10:return new Buff("Vit up I",StatID.VITALITY,buffDuration,1.1);
                    case i25:return new Buff("Vit up II",StatID.VITALITY,buffDuration,1.25);
                    case i50:return new Buff("Vit up III",StatID.VITALITY,buffDuration,1.5);
                }
            case StatID.INTELLIGENCE:
                switch(change){
                    case i10:return new Buff("Int up I",StatID.INTELLIGENCE,buffDuration,1.1);
                    case i25:return new Buff("Int up II",StatID.INTELLIGENCE,buffDuration,1.25);
                    case i50:return new Buff("Int up III",StatID.INTELLIGENCE,buffDuration,1.5);
                }
            case StatID.RESISTANCE:
                switch(change){
                    case i10:return new Buff("Res up I",StatID.RESISTANCE,buffDuration,1.1);
                    case i25:return new Buff("Res up II",StatID.RESISTANCE,buffDuration,1.25);
                    case i50:return new Buff("Res up III",StatID.RESISTANCE,buffDuration,1.5);
                }
            case StatID.MAXHP:
                switch(change){
                    case i10:return new Buff("HP up I",StatID.MAXHP,buffDuration,1.1);
                    case i25:return new Buff("HP up II",StatID.MAXHP,buffDuration,1.25);
                    case i50:return new Buff("HP up III",StatID.MAXHP,buffDuration,1.5);
                }
            case StatID.MAXMP:
                switch(change){
                    case i10:return new Buff("MP up I",StatID.MAXMP,buffDuration,1.1);
                    case i25:return new Buff("MP up II",StatID.MAXMP,buffDuration,1.25);
                    case i50:return new Buff("MP up III",StatID.MAXMP,buffDuration,1.5);
                }
        }
    return new Buff("null",StatID.HP,1,1.5);    
    }
    public static Debuff loadDebuff(int Stat, int change){
        switch(Stat){
            case StatID.STRENGTH:
                switch(change){
                    case i10:return new Debuff("Str down I",StatID.STRENGTH,buffDuration,1.1);
                    case i25:return new Debuff("Str down II",StatID.STRENGTH,buffDuration,1.25);
                    case i50:return new Debuff("Str down III",StatID.STRENGTH,buffDuration,1.5);
                }
            case StatID.DEXTERITY:
                switch(change){
                    case i10:return new Debuff("Dex down I",StatID.DEXTERITY,buffDuration,1.1);
                    case i25:return new Debuff("Dex down II",StatID.DEXTERITY,buffDuration,1.25);
                    case i50:return new Debuff("Dex down III",StatID.DEXTERITY,buffDuration,1.5);
                }
            case StatID.VITALITY:
                switch(change){
                    case i10:return new Debuff("Vit down I",StatID.VITALITY,buffDuration,1.1);
                    case i25:return new Debuff("Vit down II",StatID.VITALITY,buffDuration,1.25);
                    case i50:return new Debuff("Vit down III",StatID.VITALITY,buffDuration,1.5);
                }
            case StatID.INTELLIGENCE:
                switch(change){
                    case i10:return new Debuff("Int down I",StatID.INTELLIGENCE,buffDuration,1.1);
                    case i25:return new Debuff("Int down II",StatID.INTELLIGENCE,buffDuration,1.25);
                    case i50:return new Debuff("Int down III",StatID.INTELLIGENCE,buffDuration,1.5);
                }
            case StatID.RESISTANCE:
                switch(change){
                    case i10:return new Debuff("Res down I",StatID.RESISTANCE,buffDuration,1.1);
                    case i25:return new Debuff("Res down II",StatID.RESISTANCE,buffDuration,1.25);
                    case i50:return new Debuff("Res down III",StatID.RESISTANCE,buffDuration,1.5);
                }
            case StatID.MAXHP:
                switch(change){
                    case i10:return new Debuff("HP down I",StatID.MAXHP,buffDuration,1.1);
                    case i25:return new Debuff("HP down II",StatID.MAXHP,buffDuration,1.25);
                    case i50:return new Debuff("HP down III",StatID.MAXHP,buffDuration,1.5);
                }
            case StatID.MAXMP:
                switch(change){
                    case i10:return new Debuff("MP down I",StatID.MAXMP,buffDuration,1.1);
                    case i25:return new Debuff("MP down II",StatID.MAXMP,buffDuration,1.25);
                    case i50:return new Debuff("MP down III",StatID.MAXMP,buffDuration,1.5);
                }
        }
    return new Debuff("null",StatID.HP,1,1.5);    
    }
    
}
