package de.htwg.stratego.util.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable implements IObservable {

	private List<IObserver> myObservers = new ArrayList<>();
	
	@Override
	public void addObserver(IObserver observer) {
		myObservers.add(observer);
	}

	@Override
	public void removeObserver(IObserver observer) {
		myObservers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		notifyObservers(null);
	}

	@Override
	public void notifyObservers(Event e) {
		for (IObserver observer : myObservers) {
			observer.update(e);
		}
	}

	
	
}
