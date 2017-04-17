package de.htwg.stratego.persistence.hibernate.util;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IGame;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.persistence.hibernate.TransferCharacter;
import de.htwg.stratego.persistence.hibernate.TransferPlayer;

import java.util.*;

public class HibernateUtilTransferPlayerMap {
    public static final int PLAYER_SIZE = 2;
    private TransferPlayer[] transferPlayerArray = new TransferPlayer[PLAYER_SIZE];
    private IPlayer[] playerArray = new IPlayer[PLAYER_SIZE];

    public HibernateUtilTransferPlayerMap(IGame game) {
        initTransferPlayerArray(game);
    }

    private void initTransferPlayerArray(IGame game){
        IPlayer[] player = game.getPlayer();
        for (int i = 0; i < player.length; i++) {
            playerArray[i] = player[i];
            transferPlayerArray[i] = copyPlayer(player[i]);
        }
    }

    public TransferPlayer getTransferPlayer(IPlayer oldPlayer) {
        for (int i = 0; i < playerArray.length; i++) {
            if (playerArray[i] == oldPlayer) {
               return transferPlayerArray[i];
            }
        }
        return null;
    }

    public TransferPlayer[] getAllTransferPlayer() {
        return transferPlayerArray;
    }

    private TransferPlayer copyPlayer (IPlayer player) {
        TransferPlayer tPlayer = new TransferPlayer(player.getName(), player.toString(),player.getSetupFinished());
        List<ICharacter> characterList = player.getCharacterList();
        List<TransferCharacter> tCharacterList = createTCharacterList(characterList, tPlayer);
        tPlayer.setCharacterList(tCharacterList);
        return tPlayer;
    }

    private List<TransferCharacter> createTCharacterList(List<ICharacter> characterList, TransferPlayer tPlayer) {
        List<TransferCharacter> tCharacterList = new ArrayList<>();
        for (ICharacter oldCharacter:
                characterList) {
            tCharacterList.add(new TransferCharacter(oldCharacter, tPlayer));
        }
        return tCharacterList;
    }

}
