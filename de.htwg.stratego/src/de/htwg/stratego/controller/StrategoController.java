package de.htwg.stratego.controller;

import de.htwg.stratego.model.Cell;
import de.htwg.stratego.model.Character;
import de.htwg.stratego.model.Field;
import de.htwg.stratego.model.Flag;
import de.htwg.stratego.model.Sergeant;
import de.htwg.stratego.util.observer.Observable;

public class StrategoController extends Observable {
	private GameStatus status = GameStatus.WELCOME;
	private Field field;
	
	public StrategoController(int width, int height) {
		setField(width,height);
	}
	
	public GameStatus getStatus() {
		return status;
	}
	
	public Field getField() {
		return field;
	}
	
	public void setField(int width, int height) {
		this.field = new Field(width,height);
		//TODO: abfragen von illegalen größen -1 etc.S
	}
	
	public void fillField() {
		field.getCell(0, 0).setCharacter(new Flag(1));
		field.getCell(2, 0).setCharacter(new Sergeant(1));
	}
	
	public void moveChar(int fromX, int fromY, int toX,
			int toY) {
		//TODO: check Field 
		Cell fromCell = field.getCell(fromX, fromY);
		Cell toCell = field.getCell(toX, toY);
		
		
		Character fromCharacter = fromCell.getCharacter();
		Character toCharacter = toCell.getCharacter();
		
		//TODO: character != null ; isMove ; character zu spieler passt
		
		if (toCharacter == null) {
			fromCell.setCharacter(null);
			toCell.setCharacter(fromCharacter);
		} else {
			
		}
		
	}
	
	public Character fight(Character c1, Character c2) {
		
		
		
		return null;
	}
	
	public String getFieldString() {
		return field.toString();
	}
	
}
