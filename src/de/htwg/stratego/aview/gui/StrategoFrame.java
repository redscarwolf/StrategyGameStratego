package de.htwg.stratego.aview.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import de.htwg.stratego.controller.IStrategoController;
import de.htwg.stratego.util.observer.Event;
import de.htwg.stratego.util.observer.IObserver;

public class StrategoFrame extends JFrame implements IObserver {
	private static final long serialVersionUID = 1L;

//	private static final int WIDTH = 800;
//	private static final int HEIGHT = 600;

	private IStrategoController sc;

	private FieldPanel fieldPanel;
	private CharacterInfoPanel characterInfoPanel;
	private StatusPanel statusPanel;
	private SelectPanel selectPanel;
	
	public StrategoFrame(IStrategoController sc) {
		this.sc = sc;
		sc.addObserver(this);
		
		characterInfoPanel = new CharacterInfoPanel();
		statusPanel = new StatusPanel(sc);
		selectPanel = new SelectPanel(sc);
		fieldPanel = new FieldPanel(sc, selectPanel);
		
		add(fieldPanel, BorderLayout.CENTER);
		add(characterInfoPanel, BorderLayout.WEST);
		add(statusPanel, BorderLayout.NORTH);
		add(selectPanel, BorderLayout.EAST);
		
		setTitle("HTWG Stratego");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setVisible(true);
	}

	@Override
	public void update(Event e) {
		statusPanel.setText(sc.getStatusString(), sc.getPlayerStatusString());
		repaint();
	}
	
}
