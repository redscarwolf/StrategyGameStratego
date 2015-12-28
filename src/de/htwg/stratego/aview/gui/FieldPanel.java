package de.htwg.stratego.aview.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import de.htwg.stratego.controller.impl.StrategoController;

public class FieldPanel extends JPanel {

	private static int WIDTH_DEFAULT = 400;
	private static int HEIGHT_DEFAULT = 400;
	
	public FieldPanel(StrategoController sc) {
		int rows = StrategoController.WIDTH_FIELD;
		int columns = StrategoController.HEIGHT_FIELD;
		
		setMinimumSize(new Dimension(WIDTH_DEFAULT, HEIGHT_DEFAULT));
		setPreferredSize(new Dimension(WIDTH_DEFAULT, HEIGHT_DEFAULT));

		setBackground(Color.GREEN);
		setLayout(new GridLayout(rows, columns));
		
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				CellPanel cellPanel = new CellPanel(row, column, sc);
				cellPanel.addMouseListener(new MyMouseListener());
				add(cellPanel);
			}
		}
		
		
	}
	
	private static class MyMouseListener extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			CellPanel cellPanel = (CellPanel) e.getSource();
			System.out.println("(" + cellPanel.getRow() + ", " + cellPanel.getColumn()+ ")");
		}
		
	}
	
}
