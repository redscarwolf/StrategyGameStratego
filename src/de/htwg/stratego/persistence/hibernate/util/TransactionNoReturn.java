package de.htwg.stratego.persistence.hibernate.util;

import org.hibernate.Session;

@FunctionalInterface
public interface TransactionNoReturn {
    void toTransact(Session session);
}
