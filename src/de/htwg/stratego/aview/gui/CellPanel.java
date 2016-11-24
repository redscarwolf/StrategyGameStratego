package de.htwg.stratego.aview.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import de.htwg.stratego.controller.IStrategoController;
import de.htwg.stratego.model.ICharacter;

public class CellPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int row;
	private int column;
	
	private IStrategoController sc;
	
	public CellPanel(int row, int column, IStrategoController sc) {
		this.row = row;
		this.column = column;
		this.sc = sc;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if (sc.containsCharacter(column, row)) {
			ICharacter character = sc.getCharacter(column, row);
			
			if (character.isVisible()) {
				if (character.belongsTo(sc.getPlayerOne())) {
					g.setColor(Color.RED);
				} else {
					g.setColor(Color.BLUE);
				}
				g.fillRect(0, 0, 50, 50);
				g.drawImage(character.getImage(), 8, 2, null);
				g.setColor(Color.WHITE);
				g.fillRect(2, 25, 20, 12);
				g.setColor(Color.BLACK);
				g.drawString(character.toString(), 2, 35);
			} else {
				g.drawString(character.toString(), 20, 20);
			}
		}
		if (sc.isPassable(column, row)) {
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
		}
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
}
