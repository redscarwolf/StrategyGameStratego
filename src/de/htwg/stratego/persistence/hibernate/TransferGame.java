package de.htwg.stratego.persistence.hibernate;
import de.htwg.stratego.model.EGameState;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.*;

@Entity
@Table(name = "game")
public class TransferGame implements  Serializable{

    private Integer id;
    private EGameState gameState;
    private TransferField field;
    private Integer currentPlayer;
    private TransferPlayer[] player;

    public TransferGame() {
    }

    public TransferGame(EGameState gameState, TransferField field, Integer currentPlayer, TransferPlayer[] player) {
        this.gameState = gameState;
        this.field = field;
        this.currentPlayer = currentPlayer;
        this.player = player;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
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

    @OneToOne
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

    @OneToMany
    @OrderColumn
    public TransferPlayer[] getPlayer() {
        return player;
    }

    public void setPlayer(TransferPlayer[] player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "TransferGame{" +
                "id=" + id +
                ", gameState=" + gameState +
                ", field=" + field +
                ", currentPlayer=" + currentPlayer +
                ", player=" + Arrays.toString(player) +
                '}';
    }
}
