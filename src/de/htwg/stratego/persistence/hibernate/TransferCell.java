package de.htwg.stratego.persistence.hibernate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class TransferCell implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column
    private Integer x;

    @Column
    private Integer y;

    @Column
    private Boolean passable;

    @Column
    private TransferCharacter character;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Boolean getPassable() {
        return passable;
    }

    public void setPassable(Boolean passable) {
        this.passable = passable;
    }

    public TransferCharacter getCharacter() {
        return character;
    }

    public void setCharacter(TransferCharacter character) {
        this.character = character;
    }
}
