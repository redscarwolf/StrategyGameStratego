package de.htwg.stratego.model.impl.character;

import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Rank;

public class Scout extends AbstractCharacter {

	public Scout(IPlayer player) {
		super(Rank.SCOUT, true, player, "02scout.png");
	}
	
	public static void buildSeveral(int number, IPlayer p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Scout(p));
		}
	}

}
