package de.htwg.stratego.util.observer;

import org.junit.BeforeClass;
import org.junit.Test;
import junit.framework.TestCase;

public class ObservableTest extends TestCase {
	private boolean ping = false;
	private TestObserver testObserver;
	private Observable observable;
	
	class TestObserver implements IObserver {
		//@Override
		public void update(Event e) {
			ping = true;
		}
	}
	
	@BeforeClass
	public void setUp() {
		testObserver = new TestObserver();
		observable = new Observable();
		observable.addObserver(testObserver);
	}
	
	@Test
	public void testNotifyObservers() {
		assertFalse(ping);
		observable.notifyObservers();
		assertTrue(ping);
	}
	
	@Test
	public void testRemoveObserver() {
		assertFalse(ping);
		observable.removeObserver(testObserver);
		observable.notifyObservers();
		assertFalse(ping);
	}
}
