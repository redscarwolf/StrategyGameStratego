package de.htwg.stratego.model.impl.character;

import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.model.impl.Rank;

public class Bomb extends AbstractCharacter {

	public Bomb(Player player) {
		super(Rank.BOMB, false, player);
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Bomb(p));
		}
	}

}
