package de.htwg.stratego.aview.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.htwg.stratego.controller.IStrategoController;

public class StatusPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel nameStatusLabel;
	private JLabel contentStatusLabel;
	private JLabel namePlayerStatusLabel;
	private JLabel contentPlayerStatusLabel;
	
	public StatusPanel(IStrategoController sc) {
		setBackground(Color.RED);
		setLayout(new GridLayout(2, 0));
		
		nameStatusLabel = new JLabel("Status:");
		add(nameStatusLabel);
		
		contentStatusLabel = new JLabel("");
		add(contentStatusLabel);
		
		
		namePlayerStatusLabel = new JLabel("Player Status:");
		add(namePlayerStatusLabel);
		
		contentPlayerStatusLabel = new JLabel("");
		add(contentPlayerStatusLabel);
	}
	
	public void setText(String status, String playerStatus) {
		contentStatusLabel.setText(status);
		contentPlayerStatusLabel.setText(playerStatus);
	}
	
}
