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
public class Element{
    public static final int
            NEUTRAL=0,
            AIR=1,
            WATER=2,
            EARTH=3,
            FIRE=4,
            LIGHT=5,
            DARK=6;
    /**
     * calculates the damage multiplier based on the attacking and defending elements.
     * @param targetElement element of the target, as the enumerator
     * @param spellElement element of the spell, as the enumerator
     * @return double of the damage multiplier
     */
    public static double handler(int targetElement, int spellElement){
        
        if(targetElement==spellElement||targetElement==Element.NEUTRAL||spellElement==Element.NEUTRAL){//case 1: if either side is neutral or both elements are the same
                return 1.0;
        }else if(targetElement<=4&&spellElement<=4){//if both elements are not light or dark
            if(targetElement-spellElement==1||targetElement-spellElement==-3){//case 2: if element is super effective
                return 2.0;
            }else if(spellElement-targetElement==1||spellElement-targetElement==-3){//case 3: if element is not very effective
                return 0.5;
            }
            return 0.75; //case 4: if element is opposed.
        }else{
            if((spellElement-targetElement)*(spellElement-targetElement)==1){//case 5: if the elements are light and dark
                return 2.0;
            }else if(spellElement-targetElement==2 || spellElement+targetElement==7&&spellElement>4){//case 6: light attack a child of dark or dark attack child of light
                return 1.5;
            }else if(spellElement-targetElement==-2||spellElement+targetElement==7&&spellElement<5){//case 7: child of one attacking the other
                return 1.0;
            }else{
                return-1; //case 8: heal ratio.
            }
        }
    }
    public static String getElementName(int element){
        switch(element){
            case NEUTRAL:return"No Element";
            case AIR:return"Air";
            case WATER:return"Water";
            case EARTH:return"Earth";
            case FIRE:return"Fire";
            case LIGHT:return"Light";
            case DARK:return"Dark";
    }
        return "err";
    }
    public static void main(String[] args){
        String[] elements = {"Neutral","Air","Water","Earth","Fire","Light","Dark"};
        for(int i=0;i<7;i++){
            for(int w=0;w<7;w++){
                System.out.println(elements[i]+" Is attacking "+elements[w]);
                System.out.println(handler(w,i)+" Is the damage multiplier \n");
                
            }
        }
    }
    
}
