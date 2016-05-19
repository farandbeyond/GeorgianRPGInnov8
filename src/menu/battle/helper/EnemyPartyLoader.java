package menu.battle.helper;

import java.util.Random;

public class EnemyPartyLoader {
	public static final int
				SNAKESBAT=1,
				SLIMES2=2,
				GOBLINS2=3,
				SNAKESLIME=4,
				SNAKE1=5,
				SLIME1=6,
				GOBLINSNAKESLIME=7;
	
	private static Random r = new Random();
	
	public static Party getEnemyParty(int partyToGet){
		Party party = new Party(3);
		switch(partyToGet){
		case 1:
			party.add(EntityLoader.loadEntity(EntityLoader.BAT));
		case 5:
			party.add(EntityLoader.loadEntity(EntityLoader.SNAKE));
			return party;
		case 2:
			party.add(EntityLoader.loadEntity(EntityLoader.SLIME));
		case 6:
			party.add(EntityLoader.loadEntity(EntityLoader.SLIME));
			return party;
		case 7:
			party.add(EntityLoader.loadEntity(EntityLoader.GOBLIN));
		case 4:
			party.add(EntityLoader.loadEntity(EntityLoader.SNAKE));
			party.add(EntityLoader.loadEntity(EntityLoader.SLIME));
			return party;
		case 3:
			party.add(EntityLoader.loadEntity(EntityLoader.GOBLIN));
			party.add(EntityLoader.loadEntity(EntityLoader.GOBLIN));
			return party;
			
		}
		return null;
	}
	public static Party getRandomEnemyParty(){
		return getEnemyParty(r.nextInt(7)+1);
	}
				
}
