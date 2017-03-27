package de.htwg.stratego.controller.impl;

import de.htwg.stratego.controller.state.impl.PlayerTurn;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.PlayerFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import junit.framework.TestCase;

public class MultiDeviceStrategoControllerTest extends TestCase {

    private MultiDeviceStrategoController sc;
    private IField field;

    private IPlayer playerOne;
    private IPlayer playerTwo;


    @BeforeClass
    public void setUp() {
        sc = new MultiDeviceStrategoController(new Field(10, 10), new PlayerFactory(), null);
        field = sc.getIField();

        playerOne = sc.getPlayerOne();
        playerTwo = sc.getPlayerTwo();

        int xPlayerOne = 1;
        int yPlayerOne = 1;
        sc.add(xPlayerOne, yPlayerOne, 2, playerOne);

        sc.add(2, 2, 0, playerOne);

        int xPlayerTwo = 8;
        int yPlayerTwo = 8;
        sc.add(xPlayerTwo, yPlayerTwo, 2, playerTwo);

        sc.add(7, 7, 0, playerTwo);
    }

    @Test
    public void testAdd() throws Exception {
        int xPlayerOne = 0;
        int yPlayerOne = 0;
        assertTrue(sc.add(xPlayerOne, yPlayerOne, 2, playerOne));
        assertTrue(sc.containsCharacter(xPlayerOne, yPlayerOne));

        int xPlayerTwo = 9;
        int yPlayerTwo = 9;
        assertTrue(sc.add(xPlayerTwo, yPlayerTwo, 2, playerTwo));
        assertTrue(sc.containsCharacter(xPlayerTwo, yPlayerTwo));
    }

    @Test
    public void testMove() throws Exception {
        sc.changeState(); // finished Add of PlayerOne
        sc.changeState(); // finished Add of PlayerTwo
        sc.changeState(); // finished Transfer
        assertTrue(sc.move(1, 1, 1 , 2, playerOne));
        assertTrue(sc.move(8, 8, 8 , 7, playerTwo));

        assertFalse(sc.move(8, 7, 8 , 8, playerTwo));
    }

    @Test
    public void testFinish() throws Exception {
        assertTrue(sc.finish(playerOne));
        assertFalse(sc.finish(playerOne));

        assertTrue(sc.finish(playerTwo));
        assertFalse(sc.finish(playerTwo));
        assertTrue(sc.getGameState() instanceof PlayerTurn);
    }

}