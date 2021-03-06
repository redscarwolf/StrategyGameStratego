package de.htwg.stratego.model;

import de.htwg.stratego.controller.state.GameState;
import de.htwg.stratego.controller.state.impl.PlayerTurn;
import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.Game;
import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameTest extends TestCase {

    private int currentPlayer;
    private IPlayer[] player;
    private EGameState gameState;
    private IField field;

    private IGame game;

    @BeforeClass
    public void setUp() {
        currentPlayer = 1;
        player = new IPlayer[2];
        gameState = EGameState.PLAYER_START;
        field = new Field(10, 10);

        game = new Game(0, currentPlayer, player, gameState, field);
    }

    @Test
    public void testGetCurrentPlayer() {
        assertEquals(currentPlayer, game.getCurrentPlayer());
    }

    @Test
    public void testGetPlayer() {
        assertEquals(player, game.getPlayer());
    }

    @Test
    public void testGetGameState() {
        assertEquals(gameState, game.getGameState());
    }

    @Test
    public void testGetField() {
        assertEquals(field, game.getField());
    }

}
