package de.htwg.stratego.aview.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import de.htwg.stratego.controller.ISingelDeviceStrategoController;

public class SelectPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private ISingelDeviceStrategoController sc;
	private String[] strSelectCharacterRank = {"Flag","Spy","Scout","Miner",
			"Sergeant","Lieutenant","Captain","Major","Colonel","General","Marshal","Bomb"};
	private String[] strSelectMethod = {"add","remove","move"};
	private JComboBox<String> selectCharRankComboBox;
	private JComboBox<String> selectMethodComboBox;
	
	public SelectPanel(ISingelDeviceStrategoController sc) {
		this.sc = sc;
		setBackground(Color.BLUE);
		
		selectMethodComboBox = new JComboBox<>(strSelectMethod);
		selectMethodComboBox.setSelectedIndex(0);
		add(selectMethodComboBox);
		
		selectCharRankComboBox = new JComboBox<>(strSelectCharacterRank);
		selectCharRankComboBox.setSelectedIndex(0);
		add(selectCharRankComboBox);
		
		JButton finishButton = new JButton("Finish Turn");
		finishButton.addActionListener(this);
		add(finishButton);
	}
	
	public int getSelectedCharacterRank() {
		return selectCharRankComboBox.getSelectedIndex();
	}
	
	public int getSelectedMethod() {
		return selectMethodComboBox.getSelectedIndex();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		sc.finish();
	}
}
