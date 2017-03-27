package de.htwg.stratego.controller.impl;

import com.google.inject.Inject;
import de.htwg.stratego.controller.ISingelDeviceStrategoController;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IPlayerFactory;
import de.htwg.stratego.persistence.IDao;


public class SingleDeviceStrategoController extends AbstractStrategoController implements ISingelDeviceStrategoController {

	@Inject
	public SingleDeviceStrategoController(IField field, IPlayerFactory playerFactory, IDao dao) {
		super(field, playerFactory, dao);
	}

	@Override
	public boolean add(int x, int y, int rank) {
		return add(x, y, rank, getCurrentPlayer());
	}

	@Override
	public boolean swap(int x1, int y1, int x2, int y2) {
		return swap(x1, y1, x2, y2, getCurrentPlayer());
	}

	@Override
	public boolean remove(int x, int y) {
		return remove(x, y, getCurrentPlayer());
	}

	@Override
	public boolean move(int fromX, int fromY, int toX, int toY) {
		return move(fromX, fromY, toX, toY, getCurrentPlayer());
	}

	@Override
	public void finish() {
		changeState();
		notifyObservers();
	}

	@Override
	public String toJson() {
		return toJson(getCurrentPlayer());
	}
}
