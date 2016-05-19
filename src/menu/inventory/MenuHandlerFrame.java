/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu.inventory;

import handlers.PlayerHandle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import menu.battle.helper.Element;
import menu.battle.helper.BattleEntity;
import menu.battle.helper.EntityLoader;
import menu.battle.helper.Inventory;
import menu.battle.helper.Item;
import menu.battle.helper.ItemLoader;
import menu.battle.helper.Party;
import menu.battle.helper.SpellLoader;
import menu.battle.helper.Item.ItemCannotDoThisException;
import engine.Blackwind;

/**
 *
 * @author Connor
 */
public class MenuHandlerFrame extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	
	//menuOptions
    //private final int            width=608,height=480;
    private static final int
            INVENTORY=0,
            STATUS=1,
            SPELLS=2,
            EQUIPMENT=3,
            SWAPMEMBERS=4,
            SAVE=5;
    
    
    Joystick joystick;
    private int menuPosition;
    private boolean confirmEvent, cancelEvent;
    
 //   private JFrame frame;
    private JPanel assistPanel;
    private JLabel assistText;
    private InventoryPanel invPanel;
    private PartyPanel partyPanel;
    private StatusPanel statusPanel;
    private SpellsPanel spellsPanel;
    private OptionsPanel options;   
    
    /* 
    public MenuHandlerFrame(Inventory inv, Party p){
        joystick = new Joystick();
        FrameOptions(width, height);
        panelOptions(inv,p);
        addFramesAndPanels();
    }
    */
    
    public MenuHandlerFrame(Inventory inv, Party p){
        //FrameOptions(width, height);
        panelOptions(inv,p);
        System.out.println("Loaded panel options");
   //     frame = Blackwind.getFrame();
    }
    /*
    private void FrameOptions(int width, int height){
        frame=new JFrame();
        frame.setSize(width, height);
        frame.setBackground(Color.yellow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.addKeyListener(joystick);
        frame.setLayout(null);
    }
    */
    private void panelOptions(Inventory inv, Party p){
        invPanel= new InventoryPanel(inv);
        partyPanel = new PartyPanel(p);
        statusPanel = new StatusPanel(p);
        spellsPanel = new SpellsPanel(p.getMemberFromParty(0).getSkillList());
        
        invPanel.setVisible(false);
        partyPanel.setVisible(false);
        statusPanel.setVisible(false);
        spellsPanel.setVisible(false);
        
        options = new OptionsPanel();
        
        assistPanel = new JPanel();
        assistPanel.setVisible(true);
        assistPanel.setBounds(0, 0, 400, 35);
        assistPanel.setLayout(null);
        
        assistText = new JLabel();
        assistText.setVisible(true);
        assistText.setBounds(0,0,400,40);
        assistText.setFont(new Font("Serif",Font.BOLD, 18));
    }/*
    public void addFramesAndPanels(){
        frame.add(partyPanel);
        frame.add(options);
        frame.add(invPanel);
        frame.add(statusPanel);
        frame.add(spellsPanel);
        assistPanel.add(assistText);
        frame.add(assistPanel);
    }*/
    
     //main menu
    public void openMenu(Inventory inv, Party p){
        partyPanel.load(p);
        invPanel.setInventory(inv);
        updateMainMenu();
    }
    
    private void updateMainMenu(){
        resetEvents();
        partyPanel.setVisible(true);
        while(!cancelEvent){
            partyPanel.updatePartyLabels();
            updateMenuPos(6);
            options.updateSelectorPosition(menuPosition);
            if(confirmEvent){
                resetEvents();
                int save = menuPosition;
                switch(menuPosition){
                    case INVENTORY:     
                        partyPanel.setVisible(false);
                        invPanel.setVisible(true);
                        selectItemFromList();
                        partyPanel.setVisible(true);
                        invPanel.setVisible(false);
                        options.loadMainMenuOptions();
                        break;
                    case STATUS:        updateStatus();break;
                    case SPELLS:        updateSpells();break;
                    case EQUIPMENT:     equipItems();break;
                    case SWAPMEMBERS:   swapMembers();break;
                    case SAVE:          setAssistText("Not Yet Implemented");break;
                        
                }
                menuPosition=save;
            }
            Blackwind.getFrame().repaint();
        }
        Blackwind.setStatus(Blackwind.STATUS_OVERWORLD);
    }
    //global-ish commands
    private void resetEvents(){
        confirmEvent=false;
        cancelEvent=false;
    }
    public void setAssistText(String text){
        assistPanel.setVisible(true);
        assistPanel.updateUI();
        assistText.setText(text);
        System.out.println(text);
    }
    private void updateMenuPos(int maxPos){
        if(menuPosition>=0&&menuPosition<maxPos){
            return;
        }else if(menuPosition<0)
            menuPosition=maxPos-1;
        else
            menuPosition=0;
    }
    
    //inventory menu 
    private void selectItemFromList(){
        options.loadInventoryMenuOptions();
        menuPosition=0;
        invPanel.itemSelector.setVisible(true);
        while(!cancelEvent){
            invPanel.loadItems();
            updateMenuPos(10);
            invPanel.changeItemSelectorPosition(menuPosition);
            if(confirmEvent){
                try{
                int save=menuPosition;
                invPanel.inv.getItem(menuPosition).getDescription();
                itemOptions(save);
                menuPosition=save;
                }catch(IndexOutOfBoundsException e){
                    setAssistText("That is not an item");
                    resetEvents();
                }
            }
        }
        resetEvents();
        invPanel.itemSelector.setVisible(false);
        
    }
    private void itemOptions(int selection){
        //options.loadInventoryMenuOptions();
        resetEvents();
        menuPosition=0;
        while(!cancelEvent){
            invPanel.loadItems();
            updateMenuPos(4);
            options.updateSelectorPosition(menuPosition);
            if(confirmEvent){
                resetEvents();
                try{
                    invPanel.inv.getItem(selection).getDescription();
                    try{
                    switch(menuPosition){
                        case 0:
                            BattleEntity t1 = getItemTargetFromParty();
                            if(t1!=null){
                                invPanel.inv.getItem(selection).use(t1);break;
                            }else{
                                setAssistText("Cannot use an item on a non existent person");break;
                            }
                        case 1:
                            BattleEntity t2 = getItemTargetFromParty();
                            if(t2!=null){
                            //if it is a weapon
                            if(invPanel.inv.getItem(selection).getClass()==ItemLoader.loadItem(ItemLoader.BRONZESWORD, 1).getClass()){
                                invPanel.inv.add(t2.equipWeapon(ItemLoader.loadItem(invPanel.inv.getItem(selection).getId(), 1)));
                                setAssistText(String.format("%s Equipped %s", t2.getName(),invPanel.inv.getItem(selection).toString()));
                                invPanel.inv.reduceQuantity(selection);
                            }
                            //if it is an armor piece
                            else if(invPanel.inv.getItem(selection).getClass()==ItemLoader.loadItem(ItemLoader.LEATHERARMOR, 1).getClass()){
                                if(!t2.equipArmor(ItemLoader.loadItem(invPanel.inv.getItem(selection).getId(), 1))){
                                    inventorySwapEquipment(t2,ItemLoader.loadItem(invPanel.inv.getItem(selection).getId(), 1));
                                    setAssistText(String.format("%s Equipped %s", t2.getName(),invPanel.inv.getItem(selection).toString()));
                                    invPanel.inv.reduceQuantity(selection);
                                }else{
                                    setAssistText(String.format("%s Equipped %s", t2.getName(),invPanel.inv.getItem(selection).toString()));
                                    invPanel.inv.reduceQuantity(selection);
                                }
                            }
                            else{
                                setAssistText("This item cannot be equipped");
                            }
                            }
                            menuPosition=1;
                            break;
                        case 2:
                            setAssistText(invPanel.inv.getItem(selection).getDescription());break;
                        case 3:
                            setAssistText("Sure you wish to drop "+invPanel.inv.getItem(selection).toString()+"?");
                            while(!confirmEvent||!cancelEvent){    
                                if(confirmEvent){
                                   setAssistText("Dropped "+invPanel.inv.getItem(selection).toString());
                                   invPanel.inv.drop(selection);
                                   break;
                                }else if(cancelEvent){
                                    //System.out.println("Not Dropped");
                                    break;
                                }else{
                                    invPanel.loadItems();
                                }   
                            }
                            //System.out.println("Left the drop loop");
                            resetEvents();
                            break;
                            
                    }//end of switch
                    resetEvents();
                    }catch(ItemCannotDoThisException f){
                    setAssistText("Item cannot do that");
                    //System.out.println(f);
                    }
                }catch(IndexOutOfBoundsException e){
                    setAssistText("That is not an item");
                }
            }//end of if confirmevent
       }//end of while !cancelevent
       menuPosition=selection;
       resetEvents();
       //options.loadInventoryMenuOptions();
   }
    private BattleEntity getItemTargetFromParty(){
        BattleEntity selection=null;
        invPanel.setVisible(false);
        partyPanel.setVisible(true);
        int save = menuPosition;
        menuPosition=0;
        partyPanel.partySelector.setVisible(true);
        while(!confirmEvent||!cancelEvent){
            partyPanel.updatePartyLabels();
            updateMenuPos(4);
            partyPanel.movePartySelector(menuPosition);
            if(cancelEvent){
                menuPosition=save;
                invPanel.setVisible(true);
                partyPanel.setVisible(false);
                return null;
            }
            if(confirmEvent){
                try{
                    int select=menuPosition;
                    menuPosition=save;
                    invPanel.setVisible(true);
                    partyPanel.setVisible(false);
                    partyPanel.p.getMemberFromParty(select).getHp();
                    return partyPanel.p.getMemberFromParty(select);
                }catch(NullPointerException e){
                    setAssistText("Thats not a party member");
                    return null;
                }
            }
        }
        menuPosition=save;
        invPanel.setVisible(true);
        partyPanel.setVisible(false);
        return selection;
    }
    private void inventorySwapEquipment(BattleEntity equipper,Item Armor){
        invPanel.setVisible(false);
        statusPanel.setVisible(true);
        statusPanel.equipmentSelector.setVisible(true);
        statusPanel.updateText(equipper);
        menuPosition=0;
        resetEvents();
        
        while(true){
            setAssistText("Would you like to swap equipment?");
            updateMenuPos(3);
            statusPanel.changeArmorEquipmentSelectorPosition(menuPosition);
            if(confirmEvent){
                //System.out.printf("%s x%d Equipped",Armor.toString(),Armor.getQuantity());
                invPanel.inv.add(equipper.swapArmor(Armor, menuPosition));
                cancelEvent=true;
            }
            if(cancelEvent){
                invPanel.setVisible(true);
                statusPanel.setVisible(false);
                statusPanel.equipmentSelector.setVisible(false);
                resetEvents();
                //setAssistText("Success");
                return;
            }
        }
        
        
    }
   //status menu
    private void updateStatus(){
        int save = menuPosition;
        menuPosition=0;
        partyPanel.partySelector.setVisible(true);
        while(true){
            partyPanel.updatePartyLabels();
            updateMenuPos(4);
            partyPanel.movePartySelector(menuPosition);
            if(cancelEvent){
                partyPanel.partySelector.setVisible(false);
                resetEvents();
                menuPosition=save;
                return;
            }
            if(confirmEvent){
                try{
                    BattleEntity e = partyPanel.p.getMemberFromParty(menuPosition);
                    e.getHp();
                    displayTargetInformation(e);
                }catch(NullPointerException e){
                    setAssistText("Thats not a party member");
                    resetEvents();
                }
            }
        }
    }
    private void displayTargetInformation(BattleEntity e){
        resetEvents();
        menuPosition=0;
        partyPanel.setVisible(false);
        statusPanel.setVisible(true);
        statusPanel.updateText(e);
        options.loadStatusMenuOptions();
        while(!cancelEvent&&!confirmEvent){
            statusPanel.updateText(e);
            updateMenuPos(1);
            options.updateSelectorPosition(menuPosition);
        }
        resetEvents();
        options.loadMainMenuOptions();
        statusPanel.setVisible(false);
        partyPanel.setVisible(true);
    }
    //spells menu
    private void updateSpells(){
        
        resetEvents();
        spellsPanel.spellSelector.setVisible(true);
        menuPosition=0;
        int member = selectMember();
        if(member!=-1){
            options.loadSpellsMenuOptions();
            spellsPanel.setVisible(true);
            partyPanel.setVisible(false);
            resetEvents();
            while(true){
                spellsPanel.loadSpells(partyPanel.getMember(member));
                updateMenuPos(10);
                //options.updateSelectorPosition(menuPosition);
                spellsPanel.changeSpellsSelectorPosition(menuPosition);
                if(cancelEvent){
                    spellsPanel.setVisible(false);
                    partyPanel.setVisible(true);
                    options.loadMainMenuOptions();
                    resetEvents();
                    return;
                }
                if(confirmEvent){
                    //setAssistText("Confirmed");
                    resetEvents();
                    try{
                        int save=menuPosition;
                        spellsPanel.getSpellsList().get(menuPosition).toString();
                        spellOptions(menuPosition,partyPanel.getMember(member));
                        menuPosition=save;
                    }catch(IndexOutOfBoundsException e){
                        setAssistText("That is not a spell");
                    }
                }
            }
        }//end of if member!=-1
        options.loadMainMenuOptions();
        resetEvents();
    }
    private void spellOptions(int spell, BattleEntity caster){
        int save = menuPosition;
        menuPosition=0;
        while(true){
            updateMenuPos(3);
            options.updateSelectorPosition(menuPosition);
            if(cancelEvent){
                
                resetEvents();
                return;
            }
            if(confirmEvent){
                switch(menuPosition){
                    case 0:
                        if(!caster.isDead()){
                            if(spellsPanel.abilities.get(spell).getClass().equals(SpellLoader.loadSpell(SpellLoader.CURE).getClass())){
                                spellsPanel.setVisible(false);
                                while(true){
                                    int target = selectMember();
                                    if(target==-1){
                                        break;
                                    }
                                    try{
                                        if(!spellsPanel.abilities.get(spell).execute(partyPanel.getMember(target)))
                                            setAssistText("Not enough mana");
                                    }catch(IndexOutOfBoundsException q){
                                        setAssistText("Cancelled");
                                        cancelEvent=true;
                                    }
                                    
                                }
                                spellsPanel.setVisible(true);
                                menuPosition=save;
                            }else{
                                setAssistText("You shouldnt cast a harmful spell right now!");
                            }
                        }else{
                            setAssistText("You cannot cast spells while dead");
                        }
                        partyPanel.setVisible(false);
                        resetEvents();
                        
                        //partyPanel.setVisible(false);
                        break;
                    case 1:
                        setAssistText(spellsPanel.abilities.get(spell).getDescription());
                        resetEvents();
                        break;
                    case 2:
                        setAssistText(String.format("Element: %s",Element.getElementName(spellsPanel.abilities.get(spell).getElement())));
                        resetEvents();
                        break;
                        
                }
            }
            
        }
        
    }
    
    //equipment menu
    private void equipItems(){
        menuPosition=0;
        BattleEntity target = partyPanel.p.getMemberFromParty(selectMember());
        menuPosition=0;
        //if the target is valid
        if(target!=null){
            options.updateSelectorPosition(0);
            options.loadEquipmentMenuOptions();
            partyPanel.setVisible(false);
            statusPanel.setVisible(true);
            statusPanel.equipmentSelector.setVisible(true);
            resetEvents();
            statusPanel.updateText(target);
            //selecting an equipment from our target
            while(true){
                statusPanel.updateText(target);
                updateMenuPos(4);
                statusPanel.changeEquipmentSelectorPosition(menuPosition);
                setAssistText("Select a piece of equipment");
                //when a piece of equipment is selected
                if(confirmEvent){
                    int save=menuPosition;
                    resetEvents();
                    int itemSelectedFromList = menuPosition;
                    menuPosition=0;
                    //selecting an option from the equipment menu
                    while(true){
                        resetEvents();
                        statusPanel.updateText(target);
                        updateMenuPos(3);
                        options.updateSelectorPosition(menuPosition);
                        //when an option is selected
                        if(confirmEvent){
                            resetEvents();
                            int save2=menuPosition;
                            //based on the option selected
                            switch(menuPosition){
                                case 0: 
                                    statusPanel.setVisible(false);
                                    invPanel.setVisible(true);
                                    resetEvents();
                                    int save3 = menuPosition;
                                    selectItemToEquip(target,itemSelectedFromList);
                                    menuPosition=save3;
                                    
                                    options.loadEquipmentMenuOptions();
                                    invPanel.setVisible(false);
                                    statusPanel.setVisible(true);
                                    menuPosition=save2;
                                    break;
                                case 1:
                                    switch(itemSelectedFromList){
                                        case 0:
                                            invPanel.inv.add(target.equipWeapon(null));
                                            
                                            break;
                                        case 1:
                                        case 2:
                                        case 3:
                                            invPanel.inv.add(target.swapArmor(null, itemSelectedFromList-1));
                                            break;
                                    }
                                case 2:
                                    switch(itemSelectedFromList){
                                        case 0:
                                            try{
                                                setAssistText(target.getWeapon().getDescription());
                                                statusPanel.updateText(target);
                                                break;
                                            }catch(NullPointerException e){
                                                setAssistText("Nothing equipped");break;
                                            }
                                        case 1:
                                        case 2:
                                        case 3:
                                            try{
                                            setAssistText(target.getArmor(itemSelectedFromList-1).getDescription());
                                            break;
                                            }catch(NullPointerException s){
                                                setAssistText("Nothing Equipped");
                                                break;
                                            }
                                    }
                            }
                            
                        }
                        if(cancelEvent){
                            menuPosition=save;
                            break;
                        }
                    }
                    resetEvents();
                    /*
                    */
                }
                if(cancelEvent){
                    resetEvents();
                    options.loadMainMenuOptions();
                    invPanel.setVisible(false);
                    partyPanel.setVisible(true);
                    statusPanel.setVisible(false);
                    statusPanel.equipmentSelector.setVisible(false);
                    menuPosition=3;
                    return;
                }
            }
        }
    }
    private void selectItemToEquip(BattleEntity e, int equipSlot){
        options.loadInventoryMenuOptions();
        menuPosition=0;
        invPanel.itemSelector.setVisible(true);
        while(true){
            invPanel.loadItems();
            updateMenuPos(10);
            invPanel.changeItemSelectorPosition(menuPosition);
            if(confirmEvent){
                try{
                    switch(equipSlot){
                        case 0:
                            if(invPanel.inv.getItem(menuPosition).getClass()==ItemLoader.loadItem(ItemLoader.BRONZESWORD, 1).getClass()){
                                invPanel.inv.add(e.equipWeapon(ItemLoader.loadItem(invPanel.inv.getItem(menuPosition).getId(),1)));
                                invPanel.inv.getItem(menuPosition).lowerQuantity(1);
                                setAssistText("Equipped");
                                resetEvents();
                                invPanel.setVisible(false);
                                invPanel.itemSelector.setVisible(false);
                                break;
                            }else{
                                setAssistText("This item cannot be equipped");
                            }
                        case 1:
                        case 2:
                        case 3:
                            if(invPanel.inv.getItem(menuPosition).getClass()==ItemLoader.loadItem(ItemLoader.LEATHERARMOR, 1).getClass()){
                                invPanel.inv.add(e.swapArmor(ItemLoader.loadItem(invPanel.inv.getItem(menuPosition).getId(),1), equipSlot-1));
                                invPanel.inv.getItem(menuPosition).lowerQuantity(1);
                                setAssistText("Equipped");
                                resetEvents();
                                invPanel.setVisible(false);
                                invPanel.itemSelector.setVisible(false);
                                break;
                            }else{
                                setAssistText("This item cannot be equipped");
                            }
                    }
                    return;
                }//catch(IndexOutOfBoundsException s){
                catch(NullPointerException p){
                    setAssistText("That is not an item");
                    System.out.println("Out of bounds");
                    resetEvents();
                }
            }
            if(cancelEvent){
                resetEvents();
                invPanel.itemSelector.setVisible(false);
                return;
            }
        }
        
    }
    
    //swap members menu
    private int selectMember(){
        resetEvents();
        int save = menuPosition;
        //menuPosition=0;
        partyPanel.setVisible(true);
        partyPanel.partySelector.setVisible(true);
        while(true){
            partyPanel.updatePartyLabels();
            updateMenuPos(4);
            partyPanel.movePartySelector(menuPosition);
            if(cancelEvent){
                partyPanel.partySelector.setVisible(false);
                resetEvents();
                menuPosition=save;
                return -1;
            }
            if(confirmEvent){
                try{
                    BattleEntity e = partyPanel.p.getMemberFromParty(menuPosition);
                    e.getHp();
                    return menuPosition;
                }catch(NullPointerException e){
                    setAssistText("Thats not a party member");
                    resetEvents();
                }
            }
        }
    }
    private void swapMembers(){
        setAssistText("Who will swap?");
        int swap = selectMember();
        if(swap==-1){
            setAssistText("Cancelled");
            return;
        }
        setAssistText("Who will they swap with?");
        int swapTo = selectMember();
        if(swap!=-1&&swapTo!=-1){
            setAssistText("Swapped");
            partyPanel.p.swap(swap, swapTo);
        }else
            setAssistText("Cancelled");
        resetEvents();
    }
    
    //keyListener
    public class Joystick implements KeyListener{

        @Override
        public void keyTyped(KeyEvent ke) {
            
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            switch(ke.getExtendedKeyCode()){
                case KeyEvent.VK_O:confirmEvent();break;
                case KeyEvent.VK_P:cancelEvent();break;
                case KeyEvent.VK_W:upEvent();break;
                case KeyEvent.VK_A:leftEvent();break;
                case KeyEvent.VK_S:downEvent();break;
                case KeyEvent.VK_D:rightEvent();break;
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {
            
        }
        
    }
    public void confirmEvent(){
        confirmEvent=true;
        System.out.println("confirmed");
    }
    public void cancelEvent(){
        cancelEvent=true;
    }
    public void upEvent(){
        menuPosition--;
    }
    public void downEvent(){
        menuPosition++;
    }
    public void leftEvent(){
        
    }
    public void rightEvent(){
        
    }
    
    public static void main(String[] args) throws InterruptedException{
        Party party = new Party(4);
        party.add(EntityLoader.loadEntity(EntityLoader.WILSON));
        party.add(EntityLoader.loadEntity(EntityLoader.MERG));
        //party.add(EntityLoader.loadEntity(EntityLoader.MATILDA));
        party.add(EntityLoader.loadEntity(EntityLoader.SLIME));
        Inventory inv = new Inventory(15);
        inv.add(ItemLoader.loadItem(ItemLoader.POTION,5));
        inv.add(ItemLoader.loadItem(ItemLoader.ELIXER,5));
        inv.add(ItemLoader.loadItem(ItemLoader.REJUVI,5));
        inv.add(ItemLoader.loadItem(ItemLoader.STEELSWORD,1));
        inv.add(ItemLoader.loadItem(ItemLoader.FIREBOMB,5));
        inv.add(ItemLoader.loadItem(ItemLoader.ICEBOMB,5));
        inv.add(ItemLoader.loadItem(ItemLoader.LEATHERARMOR, 1));
        inv.add(ItemLoader.loadItem(ItemLoader.LEATHERGLOVES, 1));
        inv.add(ItemLoader.loadItem(ItemLoader.LEATHERGLOVES, 1));
        inv.add(ItemLoader.loadItem(ItemLoader.LEATHERGLOVES, 1));
        inv.add(ItemLoader.loadItem(ItemLoader.LEATHERGLOVES, 1));
        inv.add(ItemLoader.loadItem(ItemLoader.LEATHERGLOVES, 1));
        inv.add(ItemLoader.loadItem(ItemLoader.IRONSWORD,1));
        for(int i=0;i<3;i++){
            party.getMemberFromParty(i).addBattleAction(SpellLoader.loadSpell(SpellLoader.CURE));
        }
        party.getMemberFromParty(0).addBattleAction(SpellLoader.loadSpell(SpellLoader.FIREBALL));
        
        party.getMemberFromParty(0).equipWeapon(ItemLoader.loadItem(ItemLoader.STEELSWORD, 1));
        party.getMemberFromParty(0).equipArmor(ItemLoader.loadItem(ItemLoader.LEATHERGLOVES, 1));
        
        
        party.getMemberFromParty(0).gainExp(300);
        party.getMemberFromParty(0).gainExp(0);
        
        JFrame window = new JFrame();
        window.setSize(608, 480);
        window.setBackground(Color.yellow);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        //window.addKeyListener(joystick);
        window.setLayout(null);
        
        MenuHandlerFrame handler = new MenuHandlerFrame(inv,party);
        party.getMemberFromParty(1).gainExp(175);
        party.getMemberFromParty(2).gainExp(110);
        Blackwind.setStatus(Blackwind.STATUS_PAUSED);
        handler.addKeyListener(new PlayerHandle(null));
        handler.openMenu(inv,party);   
        //party.getMemberFromParty(2).gainExp(150);
        //party.getMemberFromParty(2).gainExp(100);
        //handler.openMenu(inv,party); 
    }
    
    public void paint(Graphics g){
    	if(partyPanel.isVisible()){
            partyPanel.paint(g);
        }
        if(invPanel.isVisible()){
            invPanel.paint(g);
        }
        if(options.isVisible()){
            options.paint(g);
        }
        if(spellsPanel.isVisible()){
            spellsPanel.paint(g);
        }
        if(statusPanel.isVisible()){
            statusPanel.paint(g);
        }
        options.paint(g);
        
    }

	public void run() {
		openMenu(Blackwind.getInventory(), Blackwind.getParty());
	}
}