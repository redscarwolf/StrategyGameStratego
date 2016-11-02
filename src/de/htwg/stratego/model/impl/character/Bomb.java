package de.htwg.stratego.model.impl.character;

import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Rank;

public class Bomb extends AbstractCharacter {

	public Bomb(IPlayer player) {
		super(Rank.BOMB, false, player, "11bomb.png");
	}
	
	public static void buildSeveral(int number, IPlayer p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Bomb(p));
		}
	}

}
