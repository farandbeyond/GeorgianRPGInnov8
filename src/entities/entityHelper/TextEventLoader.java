/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.entityHelper;

/**
 *
 * @author Connor
 */
public class TextEventLoader {
    public static final int 
            HELLO=1,
            LONG=2;
    
    public static TextEvent loadTextEvent(int textEventID){
        switch(textEventID){
            case 1: return new TextEvent("Squelch!");
            case 2: return new TextEvent("I say good chap! 'tis a wonderful day to be in existance!   have you seen doth bats and goblins? such easy pickings!    shout a 'hey ho' to the devs of this wonderful game. I am   vary happy to exist within their wonderful creation.        Tally ho and enjoy the rest of this fine showcase chaps!");
            case 3: return new TextEvent("Problem? :^)");
            case 4: return new TextEvent("Get out of my house.",false);
        }
        return null;
    }
}
