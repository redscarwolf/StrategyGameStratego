package de.htwg.stratego.model.impl.character;

import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Rank;

public class Sergeant extends AbstractCharacter {

	public Sergeant(IPlayer player) {
		super(Rank.SERGEANT, true, player, "04sergeant.png");
	}
	
	public static void buildSeveral(int number, IPlayer p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Sergeant(p));
		}
	}
}
