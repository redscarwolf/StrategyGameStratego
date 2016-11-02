package de.htwg.stratego.model.impl.character;

import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Rank;

public class Captain extends AbstractCharacter {

	public Captain(IPlayer player) {
		super(Rank.CAPTAIN, true, player, "06captain.png");
	}
	
	public static void buildSeveral(int number, IPlayer p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Captain(p));
		}
	}

}
