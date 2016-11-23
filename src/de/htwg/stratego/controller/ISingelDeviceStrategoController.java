package de.htwg.stratego.controller;

public interface ISingelDeviceStrategoController extends IStrategoController {
    boolean add(int x, int y, int rank);
    boolean swap(int x1, int y1, int x2, int y2);
    boolean remove(int x, int y);
    boolean move(int fromX, int fromY, int toX, int toY);
    void finish();
    String toJson();
}
