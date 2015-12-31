package de.htwg.stratego.aview.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import de.htwg.stratego.controller.IStrategoController;

public class FieldPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int ADD = 0;
	private static final int REMOVE = 1;
	private static final int MOVE = 2;
	
	private static int WIDTH_DEFAULT = 400;
	private static int HEIGHT_DEFAULT = 400;
	private IStrategoController sc;
	private SelectPanel selectPanel;
	private boolean isFirstClick = true;
	private int fromX;
	private int fromY;
	
	public FieldPanel(IStrategoController sc, SelectPanel selectPanel) {
		this.sc = sc;
		this.selectPanel = selectPanel;
		int rows = sc.getFieldWidth();
		int columns = sc.getFieldHeight();
		
		setMinimumSize(new Dimension(WIDTH_DEFAULT, HEIGHT_DEFAULT));
		setPreferredSize(new Dimension(WIDTH_DEFAULT, HEIGHT_DEFAULT));

		setBackground(Color.GREEN);
		setLayout(new GridLayout(rows, columns));
		
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				CellPanel cellPanel = new CellPanel(row, column, sc);
				addMouseListenerToCellPanel(cellPanel);
				// cellPanel.addMouseListener(new MyMouseListener());
				
				add(cellPanel);
			}
		}
	}
	
	private void addMouseListenerToCellPanel(CellPanel cellPanel) {
		cellPanel.addMouseListener(
				new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						CellPanel cellPanel = (CellPanel) e.getSource();
						int x = cellPanel.getColumn();
						int y = cellPanel.getRow();
						
						if (selectPanel.getSelectedMethod() == ADD) {
							sc.add(x, y, selectPanel.getSelectedCharacterRank());
							System.out.println("#### ADD ####");
							return;
						}
						
						if (selectPanel.getSelectedMethod() == REMOVE) {
							sc.removeNotify(x, y);
							System.out.println("#### REMOVE ####");
							return;
						}
						
						if (selectPanel.getSelectedMethod() == MOVE && isFirstClick) {
							fromX = x;
							fromY = y;
							isFirstClick = false;
							System.out.println("#### MOVE FIRST ####");
							return;
						}
						if (selectPanel.getSelectedMethod() == MOVE && !isFirstClick){
							sc.move(fromX, fromY, x, y);
							isFirstClick = true;
							System.out.println("#### MOVE ####");
							return;
						}
					}
				});
	}
}
