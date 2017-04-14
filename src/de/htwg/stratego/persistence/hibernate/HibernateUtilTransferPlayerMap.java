package de.htwg.stratego.persistence.hibernate;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IGame;
import de.htwg.stratego.model.IPlayer;

import java.util.*;

public class HibernateUtilTransferPlayerMap {
    private Map<IPlayer, TransferPlayer> transferPlayerMap = new HashMap<>();


    public HibernateUtilTransferPlayerMap(IGame game) {
        initTransferPlayerMap(game);
    }

    private void initTransferPlayerMap(IGame game) {
        for (IPlayer p :
                game.getPlayer()) {
            transferPlayerMap.put(p, copyPlayer(p));
        }
    }

    public TransferPlayer getTransferPlayer(IPlayer oldPlayer) {
        return transferPlayerMap.get(oldPlayer);
    }

    public TransferPlayer[] getAllTransferPlayer() {
        Collection<TransferPlayer> values = transferPlayerMap.values();
        return values.toArray(new TransferPlayer[values.size()]);
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
