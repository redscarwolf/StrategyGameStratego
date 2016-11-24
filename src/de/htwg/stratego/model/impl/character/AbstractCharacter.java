package de.htwg.stratego.model.impl.character;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.htwg.stratego.model.ICharacter;
import de.htwg.stratego.model.IPlayer;

public abstract class AbstractCharacter implements ICharacter {

	protected int rank;
	protected boolean moveable;
	protected boolean visible;
	protected IPlayer player;
	protected BufferedImage image = null;

	public AbstractCharacter(int rank, boolean moveable, IPlayer player, String imagePath) {
		this.rank = rank;
		this.moveable = moveable;
		this.player = player;
		visible = true;
		try {
			image = ImageIO.read(new File("graphics/" + imagePath));
		} catch (IOException e) {
			//TODO logging?
			System.out.println("error loading graphic");
		}
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
	
	@Override
	public BufferedImage getImage() {
		return image;
	}
	
}
