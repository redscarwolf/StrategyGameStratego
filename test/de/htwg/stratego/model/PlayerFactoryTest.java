package de.htwg.stratego.model;

import junit.framework.TestCase;

import java.awt.Color;

import org.junit.BeforeClass;
import org.junit.Test;

import de.htwg.stratego.model.impl.PlayerFactory;

public class PlayerFactoryTest extends TestCase {

	private PlayerFactory pf;

	@BeforeClass
	public void setUp() {
		pf = new PlayerFactory();
	}

	@Test
	public void testCreate() {
		assertTrue(pf.create() instanceof IPlayer);
	}

	@Test
	public void testCreateWithSymbol() {
		assertTrue(pf.create("Player", "#") instanceof IPlayer);
	}

}
