package de.htwg.stratego.persistence.hibernate.util;

import de.htwg.stratego.model.IGame;
import org.hibernate.Session;

@FunctionalInterface
public interface TransactionWithReturn {
    IGame toTransact(Session session);
}
