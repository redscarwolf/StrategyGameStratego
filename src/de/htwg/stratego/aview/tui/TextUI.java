package de.htwg.stratego.aview.tui;

import java.util.regex.Pattern;

import de.htwg.stratego.controller.ISingelDeviceStrategoController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.htwg.stratego.util.observer.Event;
import de.htwg.stratego.util.observer.IObserver;

public class TextUI implements IObserver {

	private ISingelDeviceStrategoController sc;
	private Logger logger = LogManager.getLogger(TextUI.class.getName());

	public TextUI(ISingelDeviceStrategoController sc) {
		this.sc = sc;
		sc.addObserver(this);
	}

	@Override
	public void update(Event e) {
		printTUI();
	}

	public boolean processInputLine(String input) {
		boolean continu = true;

		boolean correctInput = Pattern.matches("q|h|f|(a_\\d_\\d_\\d\\d?)|(r_\\d_\\d)|(m_\\d_\\d_\\d_\\d)", input);
		if (!correctInput) {
			logger.info("Illegal command!");
			return continu;
		}

		String[] inputStrings = input.split("_");

		switch (inputStrings[0]) {
		case "q":
			continu = false;
			break;
		case "h":
			printHelp();
			break;
		case "f":
			sc.finish();
			break;
		case "a":
			sc.add(Integer.valueOf(inputStrings[1]), Integer.valueOf(inputStrings[2]),
					Integer.valueOf(inputStrings[3]));
			break;
		case "r":
			sc.remove(Integer.valueOf(inputStrings[1]), Integer.valueOf(inputStrings[2]));
			break;
		case "m":
			sc.move(Integer.valueOf(inputStrings[1]), Integer.valueOf(inputStrings[2]),
					Integer.valueOf(inputStrings[3]), Integer.valueOf(inputStrings[4]));
			break;
        case "s":
            sc.save();
            break;
        case "l":
            sc.load();
            break;

		default:
			logger.info("Illegal command!");
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
		sb.append("f - Finish your construction phase, or start next turn\n");
		sb.append("s - Save game\n");
		sb.append("l - Load game\n");
		sb.append("q - Quit game\n");
		logger.info(sb);
		logger.info("Command (Type \"h\" for help): ");
	}

	public String printTUI() {
		String statusString = sc.getStatusString();
		String playerStatusString = sc.getPlayerStatusString();
		String fieldString = "\n" + sc.getFieldString();
		String charactersPlayer1 = "Characters Player1: " + sc.getCharacterListString(sc.getPlayerOne());
		String charactersPlayer2 = "Characters Player2: " + sc.getCharacterListString(sc.getPlayerTwo());
		String commandString = "Command (Type \"h\" for help): ";
		
		logger.info(statusString);
		logger.info(playerStatusString);
		logger.info(fieldString);
		logger.info(charactersPlayer1);
		logger.info(charactersPlayer2);
		logger.info(commandString);
		
		return statusString + playerStatusString + fieldString + charactersPlayer1 + charactersPlayer2 + commandString;
	}

}
