package de.htwg.stratego.model.impl.character;

import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.model.impl.Rank;

public class Major extends AbstractCharacter {

	public Major(Player player) {
		super(Rank.MAJOR, true, player, "07major.png");
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Major(p));
		}
	}

}
