package de.htwg.stratego.persistence.hibernate;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name= "cell")
public class TransferCell implements Serializable {

    private Integer id;
    private Integer x;
    private Integer y;
    private Boolean passable;
    private TransferCharacter character;

    public TransferCell() {
    }

    public TransferCell(Integer x, Integer y, Boolean passable) {
        this.x = x;
        this.y = y;
        this.passable = passable;
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

    @OneToOne
    public TransferCharacter getCharacter() {
        return character;
    }

    public void setCharacter(TransferCharacter character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return "TransferCell{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", passable=" + passable +
                ", character=" + character +
                '}';
    }
}
