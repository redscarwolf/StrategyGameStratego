package de.htwg.stratego.aview.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import de.htwg.stratego.controller.IStrategoController;

public class FieldPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private static final int ADD = 0;
	private static final int REMOVE = 1;
	private static final int MOVE = 2;
	
	private static final int WIDTH_DEFAULT = 400;
	private static final int HEIGHT_DEFAULT = 400;
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
				cellPanel.addMouseListener(this);
				add(cellPanel);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		CellPanel cellPanel = (CellPanel) e.getSource();
		int x = cellPanel.getColumn();
		int y = cellPanel.getRow();
		
		if (selectPanel.getSelectedMethod() == ADD) {
			sc.add(x, y, selectPanel.getSelectedCharacterRank());
			return;
		}
		
		if (selectPanel.getSelectedMethod() == REMOVE) {
			sc.removeNotify(x, y);
			return;
		}
		
		if (selectPanel.getSelectedMethod() == MOVE && isFirstClick) {
			fromX = x;
			fromY = y;
			isFirstClick = false;
			return;
		}
		if (selectPanel.getSelectedMethod() == MOVE && !isFirstClick){
			sc.move(fromX, fromY, x, y);
			isFirstClick = true;
			return;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		return;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		return;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		return;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		return;
	}
}
