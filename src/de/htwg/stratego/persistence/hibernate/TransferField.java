package de.htwg.stratego.persistence.hibernate;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "field")
public class TransferField implements Serializable {

    private Integer id;
    private Integer width;
    private Integer height;
    private List<TransferCell> cells;

    public TransferField() {
    }

    public TransferField(Integer width, Integer height) {
        this.width = width;
        this.height = height;
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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @OneToMany
    public List<TransferCell> getCells() {
        return cells;
    }

    public void setCells(List<TransferCell> cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        return "TransferField{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", cells=" + cells +
                '}';
    }

    public List<TransferCell>  getAllCellsFrom(TransferPlayer player) {
        ArrayList<TransferCell> result = new ArrayList<>();
        if (cells.isEmpty()) {
            return null;
        }
        for (TransferCell cell : cells) {
            TransferCharacter character = cell.getCharacter();
            if (character != null)
                if (character.getPlayer() == player){
                    result.add(cell);
                }
        }
        return result;
    }
}
