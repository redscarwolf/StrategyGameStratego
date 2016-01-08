package de.htwg.stratego.aview.gui;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.htwg.stratego.controller.IStrategoController;

public class CharacterInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextArea playerOneTextArea;
	private JTextArea playerTwoTextArea;

	public CharacterInfoPanel(IStrategoController sc) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel pl1Panel = new JPanel();
		pl1Panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(pl1Panel);
		JLabel plOneLabel = new JLabel("Player 1:");
		pl1Panel.add(plOneLabel);
		playerOneTextArea = new JTextArea();
		pl1Panel.add(playerOneTextArea);
		
		JPanel pl2Panel = new JPanel();
		pl2Panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(pl2Panel);
		JLabel plTwoLabel = new JLabel("Player 2:");
		pl2Panel.add(plTwoLabel);
		playerTwoTextArea = new JTextArea();
		pl2Panel.add(playerTwoTextArea);
		
		setText(sc.getCharacterListString(sc.getPlayer()[0]), sc.getCharacterListString(sc.getPlayer()[1]));
	}
	
	public void setText(String playerOne, String playerTwo) {
		playerOneTextArea.setText(playerOne);
		playerTwoTextArea.setText(playerTwo);
	}
}
