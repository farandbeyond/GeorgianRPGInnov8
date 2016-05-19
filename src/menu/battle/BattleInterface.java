/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.battle;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import menu.battle.helper.BattleAction;
import menu.battle.helper.BattleActionLoader;
import menu.battle.helper.BattleEntity;
import menu.battle.helper.Inventory;
import menu.battle.helper.Item.ItemCannotDoThisException;
import menu.battle.helper.Party;
import menu.battle.helper.UseItem;
import engine.Blackwind;

/**
 *
 * @author Connor
 */
public class BattleInterface extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	
	public static Party enemyParty;
	JFrame window;
    JPanel bg;
    JLabel assistText;
    
    EnemyInfo enemyInfo;
    PartyInfo partyInfo;
    ActionsList actionsInfo;
    BattleOptions options;
    InventoryList invInfo;
    
    boolean confirmEvent, cancelEvent, battleOver;
    int menuPositiony, menuPositionx, memberAction;
    
    Random rand;
    ArrayList<BattleAction> actions;
    ArrayList<BattleEntity> targets;
    
    public BattleInterface(Party party, Party enemyList, Inventory inv){
        rand = new Random();
        
        window = Blackwind.getFrame();
        enemyInfo = new EnemyInfo(enemyList);
        partyInfo = new PartyInfo(party);
        invInfo = new InventoryList(inv);
        
        
        actionsInfo = new ActionsList(party.getMemberFromParty(0));
        options = new BattleOptions();
        actions = new ArrayList<>();
        targets = new ArrayList<>();
        battleOver=false;
        
        assistText = new JLabel();
        assistText.setSize(608, 50);
        assistText.setLocation(200, 0);
        assistText.setBackground(Color.LIGHT_GRAY);
        
        bg = new JPanel();
        bg.setSize(608,480);
        bg.setBackground(Color.BLUE);
        
        /*
        window.add(actionsInfo);
        window.add(invInfo);
        window.add(enemyInfo);
        window.add(partyInfo);
        window.add(options);
        window.add(bg);
        window.add(assistText);
        */
        
        updateEnemyNames();
        
    //    battle();
    }
    private void updateEnemyNames(){
        ArrayList<Integer> flaggedNames = new ArrayList<>();
        
        
        for(int q=0;q<3;q++){
            System.out.println(q);
            for(int w=0;w<3;w++){
                try{
                if(enemyInfo.enemies.getMemberFromParty(q).getName().equals(enemyInfo.enemies.getMemberFromParty(w).getName())&&q!=w){
                    flaggedNames.add(q);
                }
                }catch(NullPointerException r){

                }
            }
        }
        
        int i=0;
        for(int name:flaggedNames){
            i++;
            enemyInfo.enemies.adjustName(name, i);
        }
        enemyInfo.updateEnemyInformation();
    }
    public void prepareBattle(Party party, Party enemies, Inventory inv){
        partyInfo.loadNewPartyList(party);
        enemyInfo.loadNewEnemyList(enemies);
        enemyInfo.updateEnemyInformation();
        enemyInfo.repaint();
        invInfo.loadNewItemList(inv);
        this.repaint();
        battle();
    }
    
    public void battle(){
        battleOver=false;
        while(!battleOver){
            memberAction=0;
            for(memberAction=0;memberAction<4;memberAction++){
                //System.out.println(memberAction);
                try{
                    //System.out.println(memberAction);
                    if(!partyInfo.party.getMemberFromParty(memberAction).isDead()){
                        partyInfo.pmemBackgrounds[memberAction].setBackground(Color.GREEN);

                        selectActions(partyInfo.party.getMemberFromParty(memberAction));
                    }
                    partyInfo.updatePartyInformation();
                    //partyInfo.pmemBackgrounds[memberAction].setBackground(Color.CYAN);
                }catch(NullPointerException e){
                    
                }catch(IndexOutOfBoundsException r){
                    
                }
            }
            setEnemyActions();
            sortByDex();
            executeAllActions();
            clearActionList();
            partyInfo.updatePartyInformation();
            enemyInfo.updateEnemyInformation();
            checkForBattleOver();
        }
        System.out.println("Battle Over");
        for(int i=0;i<4;i++){
            try{
                int xpGained=0;
                for(int e=0;e<3;e++){
                    try{
                        xpGained+=enemyInfo.enemies.getMemberFromParty(e).getXpGranted();
                    }catch(NullPointerException g){
                        
                    }
                }
                if(!partyInfo.party.getMemberFromParty(i).isDead()){
                    partyInfo.party.getMemberFromParty(i).gainExp(xpGained);
                    partyInfo.party.getMemberFromParty(i).levelup();
                }
                System.out.println(partyInfo.party.getMemberFromParty(i).getName()+" has "+partyInfo.party.getMemberFromParty(i).getExpUntilLevel()+" exp until next level");
            }catch(NullPointerException t){
                
            }
        }
        Blackwind.setStatus(Blackwind.STATUS_OVERWORLD);
    }
    public void selectActions(BattleEntity memberActing){
        try{
            while(true){
                memberActing.getHp();
                resetEvents();
                menuPositiony=updateMenuPos(4,menuPositiony);
                options.updateSelectorPosition(menuPositiony);
                if(confirmEvent){
                    switch(menuPositiony){
                        case 0:
                            BattleAction attack = BattleActionLoader.loadAction(BattleActionLoader.ATTACK);
                            attack.setCaster(memberActing);
                            BattleEntity target1 = selectEnemyTarget();
                            if(target1!=null&&!target1.isDead()){
                                targets.add(target1);
                                actions.add(attack);
                                return;
                            }else{
                                setAssistText("Choose a different target");
                            }
                            resetEvents();
                            menuPositiony=0;
                            break;
                        case 1:
                            BattleAction action = selectActionFromList(memberActing);
                            if(action!=null){
                                if(action.getTarget()==BattleAction.ENEMY){
                                    BattleEntity target2 = selectEnemyTarget();
                                    if(target2!=null){
                                        targets.add(target2);
                                        actions.add(action);
                                        return;
                                    }
                                }else{
                                   BattleEntity target2 = selectPartyTarget();
                                    if(target2!=null){
                                        targets.add(target2);
                                        actions.add(action);
                                        return;
                                    } 
                                }
                            }
                            menuPositiony=1;
                            break;
                        case 2://System.out.print("Not Yet Implemented");break;
                            invInfo.setVisible(true);
                            while(!cancelEvent){
                                resetEvents();
                                invInfo.updateItemsContents();
                                menuPositiony=updateMenuPos(8,menuPositiony);
                                invInfo.updateSelectorPosition(menuPositiony);
                                //BattleAction item = new UseItem(e,invInfo.selectItemFromList());
                                if(confirmEvent){
                                    BattleAction item = new UseItem(memberActing,invInfo.inv.getItem(menuPositiony));
                                    if(item!=null){
                                        if(item.getTarget()==BattleAction.ENEMY){
                                            BattleEntity target2 = selectEnemyTarget();
                                            if(target2!=null){

                                                targets.add(target2);
                                                actions.add(item);
                                                invInfo.setVisible(false);
                                                menuPositiony=2;resetEvents();
                                                return;
                                            }
                                        }else{
                                           BattleEntity target2 = selectPartyTarget();
                                            if(target2!=null){
                                                targets.add(target2);
                                                actions.add(item);
                                                invInfo.setVisible(false);
                                                menuPositiony=2;resetEvents();
                                                return;
                                            } 
                                        }
                                    }
                                    invInfo.setVisible(false);
                                    resetEvents();
                                    menuPositiony=2;
                                    break;
                                }
                            }
                            invInfo.setVisible(false);
                            menuPositiony=2;
                            resetEvents();
                            break;
                        case 3:Blackwind.setStatus(Blackwind.STATUS_OVERWORLD);
                    }

                }if(cancelEvent){
                    resetEvents();
                    
                    //if you are on the first member and cancel, it goes to fist member. if you are on the second member and cancel, it goes to the first member
                    memberAction--;
                    memberAction--;
                    if(actions.size()>0){
                    actions.remove(actions.size()-1);
                    targets.remove(targets.size()-1);
                    }
                    if(memberAction<=-2)
                        memberAction=-1;
                    return;
                }

            }
        }catch(NullPointerException t){
            
        }
    }
    private BattleEntity selectEnemyTarget(){
        resetEvents();
        enemyInfo.enemySelector.setVisible(true);
        while(true){
            menuPositionx=updateMenuPos(3,menuPositionx);
            enemyInfo.updateEnemySelectorPosition(menuPositionx);
            if(confirmEvent){
                enemyInfo.enemySelector.setVisible(false);
                System.out.println("Selected target");
                return enemyInfo.enemies.getMemberFromParty(menuPositionx);
            }
            if(cancelEvent){
                enemyInfo.enemySelector.setVisible(false);
                return null;
            }
        }
        
    }
    private BattleEntity selectPartyTarget(){
        resetEvents();
        partyInfo.partySelector.setVisible(true);
        while(true){
            menuPositionx=updateMenuPos(4,menuPositionx);
            partyInfo.updatePartySelectorPosition(menuPositionx);
            if(confirmEvent){
                partyInfo.partySelector.setVisible(false);
                if(partyInfo.party.getMemberFromParty(menuPositionx)!=null){
                    System.out.println("Selected target");
                    return partyInfo.party.getMemberFromParty(menuPositionx);
                }else{
                    System.out.println("Not a party member");
                    return null;
                }
            }
            if(cancelEvent){
                enemyInfo.enemySelector.setVisible(false);
                return null;
            }
        }
    }
    
    private BattleAction selectActionFromList(BattleEntity e){
        actionsInfo.setVisible(true);
        actionsInfo.updateActionsContents(e);
        menuPositiony=0;
        resetEvents();
        while(true){
            menuPositiony=updateMenuPos(8,menuPositiony);
            actionsInfo.updateActionSelectorPosition(menuPositiony);
            if(confirmEvent){
                try{
                resetEvents();
                actionsInfo.setVisible(false);
                return e.getSkillList().get(menuPositiony);
                }catch(IndexOutOfBoundsException t){
                    return null;
                }
            }
            if(cancelEvent){
                resetEvents();
                actionsInfo.setVisible(false);
                menuPositiony=1;
                return null;
            }
        }
    }
    private void resetEvents(){
        confirmEvent=false;
        cancelEvent=false;
    }
    private int updateMenuPos(int maxPos, int menuPos){
        if(menuPos>=0&&menuPos<maxPos){
            return menuPos;
        }else if(menuPos<0)
            return maxPos-1;
        else
            return 0;
    }
    private void setEnemyActions(){
        for(int i=0;i<enemyInfo.enemies.getCurrentPartySize();i++){
            
            BattleEntity enemy = enemyInfo.enemies.getMemberFromParty(i);
            if(!enemy.isDead()){
                BattleAction attack = BattleActionLoader.loadAction(BattleActionLoader.ATTACK);
                attack.setCaster(enemy);
                int target = rand.nextInt(partyInfo.party.getCurrentPartySize());
                actions.add(attack);
                targets.add(partyInfo.party.getMemberFromParty(target));
            }
        }
    }
 
    private void setAssistText(String text){
        assistText.setText(text);
        assistText.setVisible(true);
    }
    
    private void executeAllActions(){
        for(int i=0;i<actions.size();i++){
            try{
                if(!actions.get(i).getCaster().isDead()&&!targets.get(i).isDead()||actions.get(i).getClass()==UseItem.class)
                    actions.get(i).execute(targets.get(i));
                else if(targets.get(i).isDead())
                    System.out.println(actions.get(i).getCaster().getName()+" did nothing");
                if(targets.get(i).isDead())
                    System.out.printf("%s is dead\n",targets.get(i).getName());
            }catch(ItemCannotDoThisException t){
                System.out.println("Item had no effect");
            }
        }
    }
    
    private void clearActionList(){
        int actionsToClear = actions.size();
        for(int i=0;i<actionsToClear;i++){
            actions.remove(0);
        }
        for(int i=0;i<actionsToClear;i++){
            targets.remove(0);
        }
        System.out.println(targets.size()+" "+actions.size());
    }
    
    private void checkForBattleOver(){
        boolean partyDead=true, enemiesDead=true;
        for(BattleEntity member:partyInfo.party.getPartyArray()){
            try{
            if(!member.isDead()){
                partyDead=false;
                break;
            }
            }catch(NullPointerException e){
                
            }
        }
        for(BattleEntity member:enemyInfo.enemies.getPartyArray()){
            try{
            if(!member.isDead()){
                enemiesDead=false;
                break;
            }
            }catch(NullPointerException e){
                
            }
        }
        if(partyDead||enemiesDead){
            battleOver=true;
        }
    }
    
    private void sortByDex(){
        for(int i=0;i<actions.size();i++){
            int highest = 0;
            for(int e=0;e<(actions.size()-i);e++){
                if(actions.get(i).getCaster().getDex()>highest){
                    highest=i;
                }
            }
            BattleAction tempB = actions.get(highest);
            BattleEntity tempT = targets.get(highest);
            actions.remove(highest);
            targets.remove(highest);
            actions.add(tempB);
            targets.add(tempT);
        }
    }
    

    public void confirmEvent(){
        confirmEvent=true;
    }
    public void cancelEvent(){
        cancelEvent=true;
    }
    public void upEvent(){
        menuPositiony--;
    }
    public void downEvent(){
        menuPositiony++;
    }
    public void leftEvent(){
        menuPositionx--;
        //System.out.println(menuPositionx);
    }
    public void rightEvent(){
        menuPositionx++;
        //System.out.println(menuPositionx);
    }
    
    public static void setEnemyPartyEncounter(Party newEnemyParty){
    	enemyParty = newEnemyParty;
    }
    
    public static Party getEnemyParty(){
    	return enemyParty;
    }

	public void run() {
		battle();
	}
	
    public void paint(Graphics g){
       if(options.isVisible())
            options.paint(g);
        if(enemyInfo.isVisible())
            enemyInfo.paint(g);
        if(partyInfo.isVisible())
            partyInfo.paint(g);
        if(invInfo.isVisible())
            invInfo.paint(g);
        if(actionsInfo.isVisible())
            actionsInfo.paint(g);
    }
    
	public void tryEndBattle() {
		if(battleOver){
			Blackwind.setStatus(Blackwind.STATUS_OVERWORLD);
		}
	}
}
