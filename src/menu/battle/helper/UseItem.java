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
public class UseItem extends BattleAction{
    Item item;
    public UseItem(BattleEntity e, Item i){
        super(e);
        this.item=i;
    }
    @Override
    public boolean execute(BattleEntity target) {
        item.use(target);
        return true;
    }

    @Override
    public String getName() {
        return item.getName();
    }

    @Override
    public int getMpCost() {
        return 0;
    }

    @Override
    public int getElement() {
        return Element.NEUTRAL;
    }

    @Override
    public String getDescription() {
        return item.getDescription();
    }

    @Override
    public int getTarget() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(item.getClass()==ItemLoader.loadItem(ItemLoader.POTION,1).getClass()){
            return BattleAction.ALLY;
        }
        else if(item.getClass()==ItemLoader.loadItem(ItemLoader.PHEONIXDOWN,1).getClass()){
            return BattleAction.ALLY;
        }
        else if(item.getClass()==ItemLoader.loadItem(ItemLoader.FIREBOMB, 1).getClass()){
            return BattleAction.ENEMY;
        }
        else{
            return 0;
        }
        
    }
    
    
    
}
