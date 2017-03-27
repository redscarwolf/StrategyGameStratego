package de.htwg.stratego.model.impl;

import de.htwg.stratego.controller.state.GameState;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IGame;
import de.htwg.stratego.model.IPlayer;

public class Game implements IGame {

    private int currentPlayer;
    private IPlayer[] player;
    private GameState gameState;
    private IField field;

    public Game(int currentPlayer, IPlayer[] player, GameState gameState, IField field) {
        this.currentPlayer = currentPlayer;
        this.player = player;
        this.gameState = gameState;
        this.field = field;
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
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public IField getField() {
        return field;
    }
}
