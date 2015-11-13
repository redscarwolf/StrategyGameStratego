package de.htwg.stratego;

import java.util.Scanner;

import de.htwg.stratego.aview.tui.TextUI;
import de.htwg.stratego.controller.StrategoController;

public final class StrategoApp {

	private static Scanner scanner;
	private static TextUI tui;
	private static StrategoController sc;
	
	public static void main(String[] args) {
		
		
		sc = new StrategoController(3, 3);
		sc.fillField();
		sc.moveChar(2, 0, 0, 0);
		
		tui = new TextUI(sc);
		tui.printTUI();
		
		boolean continu = true;
		scanner = new Scanner(System.in);
		while (continu) {
			continu = tui.processInputLine(scanner.next());
		}
	}
	
}
