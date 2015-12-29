package de.htwg.stratego;

import java.util.Scanner;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.stratego.aview.gui.StrategoFrame;
import de.htwg.stratego.aview.tui.TextUI;
import de.htwg.stratego.controller.IStrategoController;

public final class StrategoApp {

	private static Scanner scanner;
	private static TextUI tui;
	
	@SuppressWarnings("unused")
	private static StrategoFrame gui;
	private static IStrategoController sc;
	
	public static void main(String[] args) {
		// Set up Google Guice Dependency Injector
		Injector injector = Guice.createInjector(new StrategoModule());
		sc = injector.getInstance(IStrategoController.class);
		
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
