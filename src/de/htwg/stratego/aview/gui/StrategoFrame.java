package de.htwg.stratego.aview.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.google.inject.Inject;

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
	
	@Inject
	public StrategoFrame(IStrategoController sc) {
		this.sc = sc;
		sc.addObserver(this);
		
		fieldPanel = new FieldPanel(sc);
		characterInfoPanel = new CharacterInfoPanel();
		statusPanel = new StatusPanel();
		selectPanel = new SelectPanel();
		
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
		repaint();
	}
	
}
