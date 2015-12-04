package de.htwg.stratego.controller;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;

import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.FieldFactory;
import junit.framework.TestCase;

public class StrategoControllerTest extends TestCase {
	private StrategoController controller;
	private Field field;
	
	@BeforeClass
	public void setUp() {
		controller = new StrategoController(new FieldFactory());
		field = new Field(10,10);
		//fill with Chars
//		field.getCell(0, 0).setCharacter(new Flag(1));
//		field.getCell(2, 0).setCharacter(new Sergeant(1));
	}
	
	@Test
	public void testGetStatus() {
		assertEquals(GameStatus.WELCOME, controller.getStatus());
	}
	
	@Test
	public void testGetField() {
		assertEquals(field, controller.getField());
	}
	
//	@Test
//	public void testFillField() {
//		controller.fillField();
//		//Rang gleichheit und Spieler gleich
//		assertEquals(0,controller.getField().getCell(0,0).getCharacter().getRang());
//		assertEquals(1, controller.getField().getCell(0,0).getCharacter().getPlayer());
//		//Rang gleichheit und Spieler gleich
//		assertEquals(4,controller.getField().getCell(2,0).getCharacter().getRang());
//		assertEquals(1, controller.getField().getCell(2,0).getCharacter().getPlayer());		
//	}
	
	@Test
	public void testGetFieldString() {
		assertEquals(controller.getField().getFieldString(controller.getPlayerOne()), controller.getFieldString());
	}
	
	@Test
	public void testMoveChar() {
		
	}
}
