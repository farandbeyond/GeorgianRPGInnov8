/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.entityHelper;

import engine.Blackwind;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Connor
 */
public class TextEvent extends JPanel implements Runnable{
    public static final int charsPerLine = 60;
    private String text;
    private boolean repeatable;
    private transient boolean previouslyTriggered = false;
    
    private static String[] textLines = new String[4];
    public static boolean advancing = false;
    
    TextEvent(String text, boolean repeatable){
        this.repeatable=repeatable;
        previouslyTriggered=false;
        this.text=text;
    }
    TextEvent(String text){
        repeatable=true;
        previouslyTriggered=false;
        this.text=text;
    }
    public static void displayText(String text){
        
        if(text.isEmpty())
            return;
        for(int i = 0; i < 4; i++){
            textLines[i] = "";
        }
        //Blackwind.setStatus(Blackwind.TEXTEVENT);
        advancing=false;
        try{
        for(int i = 0; i < 4; i++){
            String displayText = text.subSequence(i*charsPerLine,text.length()>=charsPerLine+i*charsPerLine?charsPerLine*(i+1):text.length()).toString();
            System.out.println(displayText);
            textLines[i]=displayText;
        }
        while(!advancing){
            try{
                Thread.sleep(100);
            }catch(InterruptedException r){
                //do nothing
            }
        }
        displayText(text.subSequence(charsPerLine*4,text.length()).toString());
            
        }catch(StringIndexOutOfBoundsException e){
        advancing=false;
            while(!advancing){
            try{
                Thread.sleep(100);
            }catch(InterruptedException r){
                //do nothing
            }
        }
            Blackwind.setStatus(Blackwind.STATUS_OVERWORLD);
            //Thread.currentThread().join(0);
        }
    }
    
    @Override
    public void paint(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Courier New", Font.ITALIC, 15));
        g.fillRect(0, 380, Blackwind.WIDTH, Blackwind.HEIGHT-380);
        g.setColor(Color.BLACK);
        g.drawRect(0, 380, Blackwind.WIDTH-1, Blackwind.HEIGHT-380-1);
        for(int i = 0; i < 4; i++)
            g.drawString(textLines[i], 20, 400+i*20);
    }
    
    public static void advance(){
        advancing=true;
    }
    
    public static void main(String[] args){
        displayText("Hello my name is issac and i am a genius at java blah blah  blah bard is the bast champion ever and i wont hear anything different Kappa. ertyuio;klmnkjlbn dtrhkyujbvkhgv,ljzkdbsafkjeh lkjfqhlakejgfvrcajewhrk gwelfjcalj haljwkhfkjl gheldfghjkhgfdfghjkjhgfdfgh7ftr7gyjtygtkgtfkhmanvydljoavuku akhu9 dfgakozgf7udosnh js8ugdwkh8gckq8uvdk knh fjk qbwkajnm abkjgh bfq,jwnb dkiuwahb,");
    }
    
    public void run(){
        System.out.println(previouslyTriggered);
        if((!repeatable)&&previouslyTriggered){
            displayText("Don't talk to me again");
        }else{
            
            previouslyTriggered=true;
            displayText(text);
        }
            
    }
    
    public String getText(){return text;}
}
