package de.htwg.stratego.model.impl;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;

public class CharacterFactory {

    public static ICharacter create(int rank, IPlayer player) {
        switch (rank) {
            case(Rank.FLAG):
            case(Rank.BOMB):
                return new Character(rank, false, player);
            case(Rank.SPY):
            case(Rank.SCOUT):
            case(Rank.MINER):
            case(Rank.SERGEANT):
            case(Rank.LIEUTENANT):
            case(Rank.CAPTAIN):
            case(Rank.MAJOR):
            case(Rank.COLONEL):
            case(Rank.GENERAL):
            case(Rank.MARSHAL):
                return new Character(rank, true, player);
        }
        throw new IllegalArgumentException("Rank does not exist");
    }

}
