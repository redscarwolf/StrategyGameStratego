package de.htwg.stratego.aview.gui;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class SelectPanel extends JPanel {

	private String[] s = {"Bomb", "Scout"};
	
	public SelectPanel() {
		setBackground(Color.BLUE);
		
		add(new JComboBox(s));
	}
	
}
