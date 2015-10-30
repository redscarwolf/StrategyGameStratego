package de.htwg.stratego.util.observer;

public interface IObservable {

	public void addObserver(IObserver obeserver);
	public void removeObserver(IObserver observer);
	public void notifyObservers();
	public void notifyObservers(Event e);
	
}
