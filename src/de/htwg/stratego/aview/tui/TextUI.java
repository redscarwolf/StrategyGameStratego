package de.htwg.stratego.aview.tui;

import java.util.regex.Pattern;

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

		boolean correctInput = Pattern.matches("q|h|f|(a \\d \\d \\d\\d?)|(r \\d \\d)|(m \\d \\d \\d \\d)", input);
		if (!correctInput) {
			System.out.println("Illegal command!");
			return continu;
		}

		String[] inputStrings = input.split(" ");

		switch (inputStrings[0]) {
		case "q":
			continu = false;
			break;
		case "h":
			printHelp();
			break;
		case "f":
			sc.changeStateNotify();
			break;
		case "a":
			sc.add(Integer.valueOf(inputStrings[1]), Integer.valueOf(inputStrings[2]),
					Integer.valueOf(inputStrings[3]));
			break;
		case "r":
			sc.removeNotify(Integer.valueOf(inputStrings[1]), Integer.valueOf(inputStrings[2]));
			break;
		case "m":
			sc.move(Integer.valueOf(inputStrings[1]), Integer.valueOf(inputStrings[2]),
					Integer.valueOf(inputStrings[3]), Integer.valueOf(inputStrings[4]));
			break;
		default:
			System.out.println("Illegal command!");
		}

		return continu;
	}

	private void printHelp() {
		StringBuilder sb = new StringBuilder();
		sb.append("--------------------\n");
		sb.append("HELP\n");
		sb.append("--------------------\n");
		sb.append("a [x] [y] [rank] - Adds a character to the field\n");
		sb.append("r [x] [y] - Removes a character from the field\n");
		sb.append("m [x1] [y1] [x2] [y2] - Moves a character from (x1,y1) to (x2,y2)\n");
		sb.append("f - Finish your construction phase\n");
		sb.append("q - Quit game\n");
		System.out.println(sb);
		System.out.print("Command (Type \"h\" for help): ");
	}

	public void printTUI() {
		System.out.println(StatusMessage.textMap.get(sc.getStatus()));
		System.out.println(sc.toStringPlayerStatus());
		System.out.print(sc.getFieldString());
		System.out.println("Characters Player1: " + sc.toStringCharacterList(sc.getPlayerOne()));
		System.out.println("Characters Player2: " + sc.toStringCharacterList(sc.getPlayerTwo()));
		System.out.print("Command (Type \"h\" for help): ");
	}

}
