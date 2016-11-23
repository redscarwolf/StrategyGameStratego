package de.htwg.stratego.controller;

import de.htwg.stratego.model.IPlayer;

public interface IMultiDeviceStrategoController extends IStrategoController {
    boolean add(int x, int y, int rank, IPlayer player);
    boolean swap(int x1, int y1, int x2, int y2, IPlayer player);
    boolean remove(int x, int y, IPlayer player);
    boolean move(int fromX, int fromY, int toX, int toY, IPlayer player);
    boolean finish(IPlayer player);
    String toJson(IPlayer player);
}
