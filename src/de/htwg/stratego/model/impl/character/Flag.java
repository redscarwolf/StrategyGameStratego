package de.htwg.stratego.model.impl.character;

import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Rank;

public class Flag extends AbstractCharacter {
	
	public Flag(IPlayer player) {
		super(Rank.FLAG, false, player, "00flag.png");
	}
	
	public static void buildSeveral(int number, IPlayer p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Flag(p));
		}
	}

}
