package de.htwg.stratego.aview.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import de.htwg.stratego.controller.impl.StrategoController;
import de.htwg.stratego.model.ICell;
import de.htwg.stratego.model.ICharacter;

public class CellPanel extends JPanel {

	private int row;
	private int column;
	
	private StrategoController sc;
	
	public CellPanel(int row, int column, StrategoController sc) {
		this.row = row;
		this.column = column;
		this.sc = sc;
//		Graphics g = img.getGraphics();
//		g.setColor(Color.BLACK);
//		g.drawRect(0, 0, 40, 40);
//		g.dispose();
		
//		setBackground(Color.CYAN);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		ICell cell = sc.getField().getCell(column, row);
		ICharacter character = cell.getCharacter();
		if (character != null) {
			if (character.isVisible()) {
				g.drawString(character.toString(), 20, 20);
			} else {
				g.drawString(character.toString(), 20, 20);
			}
		}
		if (cell.isPassable()) {
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
