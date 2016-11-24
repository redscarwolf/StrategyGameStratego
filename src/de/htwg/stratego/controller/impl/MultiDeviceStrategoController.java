package de.htwg.stratego.controller.impl;

import com.google.inject.Inject;
import de.htwg.stratego.controller.IMultiDeviceStrategoController;
import de.htwg.stratego.model.IField;
import de.htwg.stratego.model.IPlayer;
import de.htwg.stratego.model.IPlayerFactory;

public class MultiDeviceStrategoController extends AbstractStrategoController implements IMultiDeviceStrategoController {

    @Inject
    public MultiDeviceStrategoController(IField field, IPlayerFactory playerFactory) {
        super(field, playerFactory);
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY, IPlayer player) {
        if (player == getCurrentPlayer()) {
            boolean isMoveSuccessful = super.move(fromX, fromY, toX, toY, player);
            if (isMoveSuccessful) {
                changeState();
            }
            notifyObservers();
            return isMoveSuccessful;
        } else {
            statusMessage = "It's not your turn.";
            notifyObservers();
            return false;
        }
    }

    @Override
    public boolean finish(IPlayer player) {
        if (getGameState().isFinishAllowed() && !player.hasSetupFinished()) {
            player.setSetupFinished(true);
            changeState();
            if (getPlayerOne().hasSetupFinished() && getPlayerTwo().hasSetupFinished()) {
                changeState();
            }
            return true;
        }
        return false;
    }
    
    @Override
    public String toJson(IPlayer player) {
    	toggleVisibilityOfCharacters(player, true);
    	return super.toJson(player);
    }
}
