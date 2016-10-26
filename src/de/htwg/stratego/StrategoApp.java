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
	private static  StrategoFrame gui;
	private static IStrategoController sc;
	private static StrategoApp instance = null;
	
	private StrategoApp() {
		// Set up Google Guice Dependency Injector
		Injector injector = Guice.createInjector(new StrategoModule());
		sc = injector.getInstance(IStrategoController.class);
	
		tui = new TextUI(sc);
		//gui = new StrategoFrame(sc);
	}
	
	public static void main(String[] args) {
		StrategoApp strategoApp = StrategoApp.getInstance();
		
		strategoApp.tui.printTUI();
		boolean continu = true;
		scanner = new Scanner(System.in);
		while (continu) {
			continu = strategoApp.tui.processInputLine(scanner.nextLine());
		}
	}
	
	public static StrategoApp getInstance() {
		if (instance == null) {
			instance = new StrategoApp();
		}
		return instance;
	}
	
	public TextUI getTui() {
		return tui;
	}
	
	public IStrategoController getIStrategoController() {
		return sc;
	}
}
