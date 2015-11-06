package de.htwg.stratego.controller;

import org.junit.BeforeClass; //erstellt nur eine Klasse und verwendet diese weiter
import org.junit.Test;
import junit.framework.TestCase;

import de.htwg.stratego.model.*;

public class StrategoControllerTest extends TestCase {
	private StrategoController controller;
	private Field field;
	
	@BeforeClass
	public void setUp() {
		controller = new StrategoController(2,3);
		field = new Field(2,3);
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
//		//TODO:
//	}
}
