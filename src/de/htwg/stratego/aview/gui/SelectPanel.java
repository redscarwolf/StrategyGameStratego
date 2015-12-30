package de.htwg.stratego.aview.gui;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class SelectPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String[] strSelectCharacterRank = {"Flag","Spy","Scout","Miner",
			"Sergeant","Lieutenant","Captain","Major","Colonel","General","Marshal","Bomb"};
	private String[] strSelectMethod = {"add","remove","move"};
	private JComboBox<String> selectCharRankComboBox;
	private JComboBox<String> selectMethodComboBox;
	
	public SelectPanel() {
		setBackground(Color.BLUE);
		
		selectMethodComboBox = new JComboBox<>(strSelectMethod);
		selectMethodComboBox.setSelectedIndex(0);
		add(selectMethodComboBox);
		
		selectCharRankComboBox = new JComboBox<>(strSelectCharacterRank);
		selectCharRankComboBox.setSelectedIndex(0);
		add(selectCharRankComboBox);
	}
	
	public int getSelectedCharacterRank() {
		return selectCharRankComboBox.getSelectedIndex();
	}
	
	public int getSelectedMethod() {
		return selectMethodComboBox.getSelectedIndex();
	}
	
}
