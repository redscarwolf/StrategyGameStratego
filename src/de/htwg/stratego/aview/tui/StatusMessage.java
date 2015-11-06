package de.htwg.stratego.aview.tui;

import java.util.HashMap;

import de.htwg.stratego.controller.GameStatus;

public class StatusMessage {

	static HashMap<GameStatus, String> textMap = new HashMap<>();
	
	public StatusMessage() {
		textMap.put(GameStatus.WELCOME, "Welcome to HTWG Stratego!");
	}
	
}
