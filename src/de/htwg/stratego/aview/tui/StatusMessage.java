package de.htwg.stratego.aview.tui;

import java.util.HashMap;
import java.util.Map;

import de.htwg.stratego.controller.GameStatus;

public class StatusMessage {

	public static final Map<GameStatus, String> textMap = new HashMap<>();
	
	private StatusMessage() { }

	static {
		textMap.put(GameStatus.WELCOME, "Welcome to HTWG Stratego!");
	}
	
}
