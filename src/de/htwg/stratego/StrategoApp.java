package de.htwg.stratego;

import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.stratego.aview.gui.StrategoFrame;
import de.htwg.stratego.aview.tui.TextUI;
import de.htwg.stratego.controller.IStrategoController;

class StrategoApp {

	private static Scanner scanner;
	private static TextUI tui;
	private static IStrategoController sc;
	
	private StrategoApp() {
	}
	
	public static void main(String[] args) {
		// Set up Google Guice Dependency Injector
		Injector injector = Guice.createInjector(new StrategoModule());
		sc = injector.getInstance(IStrategoController.class);
	
		tui = new TextUI(sc);
		tui.printTUI();

		new StrategoFrame(sc);

		boolean continu = true;
		scanner = new Scanner(System.in);
		while (continu) {
			continu = tui.processInputLine(scanner.nextLine());
		}
	}
	
}
