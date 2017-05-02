package de.htwg.stratego.persistence.hibernate;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IGame;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.Game;
import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.model.impl.Rank;
import de.htwg.stratego.model.impl.CharacterFactory;
import de.htwg.stratego.persistence.hibernate.util.HibernateUtilTransferPlayerMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class HibernateUtilTransferPlayerMapTest {
    private IGame game;
    private IPlayer playerOne;
    private IPlayer playerTwo;
    private ICharacter character1;
    private ICharacter character2;

    private HibernateUtilTransferPlayerMap tpm;

    @Before
    public void setUp() throws Exception {
        this.game = setUpGame();
        tpm = new HibernateUtilTransferPlayerMap(game);
    }

    @After
    public void afterEachTest() throws Exception {
        tpm = null;
    }

    private IGame setUpGame() {
        IField field = new Field(3, 2);
        playerOne = new Player("PlayerOne", "#");
        playerTwo = new Player("PlayerTwo", "!");
        IPlayer players[] = {playerOne, playerTwo};
        character1 = CharacterFactory.create(Rank.SERGEANT, playerOne);
        character2 = CharacterFactory.create(Rank.SERGEANT, playerTwo);

        playerOne.addCharacter(character1);
        playerTwo.addCharacter(character2);

        field.getCell(0, 0).setCharacter(character1);
        field.getCell(1, 1).setCharacter(character2);

        int currentPlayer = 0;
        return new Game(0, currentPlayer, players, null, field);
    }

    @Test
    public void getTransferPlayer_AddPlayerOne_getPlayerOneFromMap() throws Exception {
        IPlayer player = playerOne;
        TransferPlayer expectedTransferPlayer = getTransferPlayerFrom(player);
        assertEquals(expectedTransferPlayer.toString(), tpm.getTransferPlayer(player).toString());
    }

    private TransferPlayer getTransferPlayerFrom(IPlayer player) {
        TransferPlayer expectedTransferPlayer = new TransferPlayer(player.getName(), player.toString(), player.getSetupFinished());
        ArrayList<TransferCharacter> tcList = new ArrayList<>();
        tcList.add(new TransferCharacter(character1, expectedTransferPlayer));
        expectedTransferPlayer.setCharacterList(tcList);

        return expectedTransferPlayer;
    }

    @Test
    public void getAllTransferPlayer() throws Exception {
        TransferPlayer[] allTransferPlayer = tpm.getAllTransferPlayer();
        assertEquals(getTransferPlayerFrom(playerOne).toString(), allTransferPlayer[0].toString());
        assertEquals(getTransferPlayerFrom(playerTwo).toString(), allTransferPlayer[1].toString());
    }


}