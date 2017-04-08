package de.htwg.stratego.persistence.hibernate;
import de.htwg.stratego.model.EGameState;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "game")
public class TransferGame implements  Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column
    private EGameState gameState;

    @Column
    private TransferField field;

    @Column
    public Integer currentPlayer;

    @Column
    public TransferPlayer[] player;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EGameState getGameState() {
        return gameState;
    }

    public void setGameState(EGameState gameState) {
        this.gameState = gameState;
    }

    public TransferField getField() {
        return field;
    }

    public void setField(TransferField field) {
        this.field = field;
    }

    public Integer getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Integer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public TransferPlayer[] getPlayer() {
        return player;
    }

    public void setPlayer(TransferPlayer[] player) {
        this.player = player;
    }
}
