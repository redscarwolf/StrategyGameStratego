package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.EGameState;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IGame;
import de.htwg.stratego.model.IPlayer;

import java.util.Arrays;

public class Game implements IGame {

    private int id;
    private int currentPlayer;
    private IPlayer[] player;
    private EGameState gameState;
    private IField field;

    public Game(int id, int currentPlayer, IPlayer[] player, EGameState gameState, IField field) {
        this.id = id;
        this.currentPlayer = currentPlayer;
        this.player = player;
        this.gameState = gameState;
        this.field = field;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public IPlayer[] getPlayer() {
        return player;
    }

    @Override
    public EGameState getGameState() {
        return gameState;
    }

    @Override
    public IField getField() {
        return field;
    }

    @Override
    public String toString() {
        return "Game{" +
                "currentPlayer=" + currentPlayer +
                ", player=" + Arrays.toString(player) +
                ", gameState=" + gameState +
                ", field=" + field +
                '}';
    }
}
