package de.htwg.stratego.aview.gui;

import java.awt.Color;
import java.awt.FlowLayout;
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
		setLayout(new FlowLayout(FlowLayout.LEADING));
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		panel1.setLayout(new GridLayout(2, 0, 5, 5));
		panel2.setLayout(new GridLayout(2, 0, 5, 5));
		
		nameStatusLabel = new JLabel("Status:");
		namePlayerStatusLabel = new JLabel("Player Status:");
		panel1.add(nameStatusLabel);
		panel1.add(namePlayerStatusLabel);
		
		contentStatusLabel = new JLabel("");
		contentPlayerStatusLabel = new JLabel("");
		panel2.add(contentStatusLabel);
		panel2.add(contentPlayerStatusLabel);
		
		add(panel1);
		add(panel2);
		
		setText(sc.getStatusString(), sc.getPlayerStatusString());
	}
	
	public void setText(String status, String playerStatus) {
		contentStatusLabel.setText(status);
		contentPlayerStatusLabel.setText(playerStatus);
	}
	
}
