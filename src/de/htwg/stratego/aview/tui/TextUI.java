package de.htwg.stratego.aview.tui;

import de.htwg.stratego.controller.GameStatus;
import de.htwg.stratego.controller.StrategoController;
import de.htwg.stratego.util.observer.Event;
import de.htwg.stratego.util.observer.IObserver;

public class TextUI implements IObserver {

	private StrategoController sc;
	
	public TextUI(StrategoController sc) {
		this.sc = sc;
		sc.addObserver(this);
	}
	
	@Override
	public void update(Event e) {
		printTUI();
	}
	
	public boolean processInputLine(String input) {
		boolean continu = true;
		
		switch(input) {
		case "q":
			continu = false;
			break;			
		default:
			System.out.println("Illegal command!");
		}
		
		return continu;
	}
	
	public void printTUI() {
		System.out.println(StatusMessage.textMap.get(GameStatus.WELCOME));
		System.out.println(sc.getFieldString());
	}

}
