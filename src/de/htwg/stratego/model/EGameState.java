package de.htwg.stratego.model;

/**
 * Für die persitenz Schicht, damit der GameState vom Controller abgespeichert werden kann.
 * Wenn man den GameState als Object abspeichern würde, gäbe es einen OutOfMemoryError in der Datenbank db4o.
 */
public enum EGameState {

    PLAYER_START,
    PLAYER_TRANSFER,
    PLAYER_TURN,
    PLAYER_WIN

}
