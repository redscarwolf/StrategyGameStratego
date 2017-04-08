package de.htwg.stratego.persistence.hibernate;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "player")
public class TransferPlayer implements Serializable {

    private Long id;
    private String name;
    private List<TransferCharacter> characterList;
    private String symbol;
    private boolean setupFinished;

    public TransferPlayer() {
    }

    public TransferPlayer(String name, String symbol, boolean setupFinished) {
        this.name = name;
        this.symbol = symbol;
        this.setupFinished = setupFinished;
    }

    @OneToMany(mappedBy = "player")
    public List<TransferCharacter> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<TransferCharacter> characterList) {
        this.characterList = characterList;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getSetupFinished() {
        return setupFinished;
    }

    public void setSetupFinished(boolean setupFinished) {
        this.setupFinished = setupFinished;
    }
}
