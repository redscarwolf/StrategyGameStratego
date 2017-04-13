package de.htwg.stratego.persistence.hibernate;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "transfercharacter")
public class TransferCharacter implements Serializable {

    private Long id;
    private Integer rank;
    private boolean moveable;
    private boolean visible;
    private TransferPlayer player;

    public TransferCharacter() {
    }

    public TransferCharacter(Integer rank, boolean movable) {
        this.rank = rank;
        this.moveable = movable;
    }

    public TransferCharacter(ICharacter character, TransferPlayer player) {
        this.rank = character.getRank();
        this.moveable = character.isMoveable();
        this.visible = character.isVisible();

        this.player = player;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public boolean getMoveable() {
        return moveable;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @ManyToOne
    @JoinColumn(name = "player_id")
    public TransferPlayer getPlayer() {
        return player;
    }

    public void setPlayer(TransferPlayer player) {
        this.player = player;
    }
}
