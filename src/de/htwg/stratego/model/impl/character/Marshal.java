package de.htwg.stratego.model.impl.character;

import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.model.impl.Rank;

public class Marshal extends AbstractCharacter {

	public Marshal(Player player) {
		super(Rank.MARSHAL, true, player, "10marshal.png");
	}
	
	public static void buildSeveral(int number, Player p) {
		for (int i = 0; i < number; i++) {
			p.getCharacterList().add(new Marshal(p));
		}
	}
}
