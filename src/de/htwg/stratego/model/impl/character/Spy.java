package de.htwg.stratego.model.impl.character;

import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Rank;

public class Spy extends AbstractCharacter {

	public Spy(IPlayer player) {
		super(Rank.SPY, true, player, "01spy.png");
	}
	
	public static void buildSeveral(int number, IPlayer p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Spy(p));
		}
	}
}
