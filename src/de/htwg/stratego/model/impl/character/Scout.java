package de.htwg.stratego.model.impl.character;

import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.model.impl.Rank;

public class Scout extends AbstractCharacter {

	public Scout(Player player) {
		super(Rank.SCOUT, true, player, "02scout.png");
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Scout(p));
		}
	}

}
