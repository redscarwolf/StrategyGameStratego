package de.htwg.stratego;

import java.util.Scanner;

import de.htwg.stratego.aview.gui.StrategoFrame;
import de.htwg.stratego.aview.tui.TextUI;
import de.htwg.stratego.controller.impl.StrategoController;
import de.htwg.stratego.model.impl.FieldFactory;

public final class StrategoApp {

	private static Scanner scanner;
	private static TextUI tui;
	
	@SuppressWarnings("unused")
	private static StrategoFrame gui;
	private static StrategoController sc;
	
	public static void main(String[] args) {
		
		sc = new StrategoController(new FieldFactory());
//		sc.fillField();
		
		tui = new TextUI(sc);
		tui.printTUI();
		
		gui = new StrategoFrame(sc);
		
//		sc.fillField();
		
		boolean continu = true;
		scanner = new Scanner(System.in);
		while (continu) {
			continu = tui.processInputLine(scanner.nextLine());
		}
	}
	
}
