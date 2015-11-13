package de.htwg.stratego.util.observer;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import de.htwg.stratego.aview.tui.TextUI;
import de.htwg.stratego.controller.StrategoController;
import junit.framework.TestCase;

public class ObservableTest extends TestCase {
	private IObserver observer;
	private List<IObserver> myObservers = new ArrayList<>();
	
	@BeforeClass
	public void setUp() {
		observer = new TextUI(new StrategoController(2,3));
	}
	
	@Test
	public void testGetWidth() {
		assertEquals(0,0);
	}
	
	@Test
	public void TestAddObserver() {
//		assertEquals(0,0);
		myObservers.add(observer);
		assertEquals(myObservers.get(0),observer);
	}
}
