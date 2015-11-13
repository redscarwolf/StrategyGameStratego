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
		//TODO: check is move inside of Field 
		//get Cells and get Characters
		Cell fromCell = field.getCell(fromX, fromY);
		Cell toCell = field.getCell(toX, toY);
		Character fromCharacter = fromCell.getCharacter();
		Character toCharacter = toCell.getCharacter();
		
		//Conditions of fromCharacter
		//TODO: is selected character != null ;
		//TODO: is Char moveable 
		//TODO: is character a char of the player
		
		//Conditions of toCharacter
		if (toCharacter == null) {
			// if Cell is empty move Character to new position
			fromCell.setCharacter(null);
			toCell.setCharacter(fromCharacter);
		} else {
			// if Cell is not empty fight with toCharacter
			fight(fromCell, toCell);
		}
		notifyObservers();
	}
	
	private void fight(Cell c1, Cell c2) {
		//TODO gültige Zellen überprüfen
		//TODO sind auf beiden Zellen Characters
		// get Character rank
		int r1 = c1.getCharacter().getRang();
		int r2 = c2.getCharacter().getRang();
		
		if (r1 > r2) {
			//success
			remove(c2.getX(),c2.getY());
		} if (r1 < r2) {
			// lost
			remove(c1.getX(),c1.getY());
		} else {
			// equal both lose
			remove(c1.getX(),c1.getY());
			remove(c2.getX(),c2.getY());
			
		}
	}
	
	public String getFieldString() {
		return field.toString();
	}
	
}
