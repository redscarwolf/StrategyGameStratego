package de.htwg.stratego.persistence.hibernate;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name= "field")
public class TransferField implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column
    private Integer width;

    @Column
    private Integer height;

    @OneToMany(mappedBy = "field")
    @Column(name= "cell")
    private List<TransferCell> cells;

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

    public List<TransferCell> getCells() {
        return cells;
    }

    public void setCells(List<TransferCell> cells) {
        this.cells = cells;
    }
}
