package de.htwg.stratego.model.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;

public class Character implements ICharacter {

	protected int rank;
	protected boolean moveable;
	protected boolean visible;
	protected IPlayer player;

	public Character(int rank, boolean moveable, boolean visible, IPlayer player) {
		this.rank = rank;
		this.moveable = moveable;
		this.visible = visible;
		this.player = player;
	}

	public Character(int rank, boolean moveable, IPlayer player) {
		this(rank, moveable, true, player);
	}

	@Override
	public IPlayer getPlayer() {
		return player;
	}
	
	@Override
	public int getRank() {
		return rank;
	}
	
	@Override
	public boolean isMoveable() {
		return moveable;
	}
	
	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}
	
	@Override
	public boolean belongsTo(IPlayer player) {
		return this.player == player;
	}
	
	@Override
	public String toString() {
		if (visible) {
			return String.format("%s%2s", player, Integer.toString(rank));
		}
		return " X ";
	}
	
}
