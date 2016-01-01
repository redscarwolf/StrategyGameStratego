package de.htwg.stratego.aview.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
	
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenuItem newMenuItem;
	private JMenuItem undoMenuItem;
	private JMenuItem exitMenuItem;
	
	public StrategoFrame(IStrategoController sc) {
		this.sc = sc;
		sc.addObserver(this);
		
		menuBar = new JMenuBar();
		gameMenu = new JMenu("Game");
		newMenuItem = new JMenuItem("New");
		undoMenuItem = new JMenuItem("Undo");
		exitMenuItem = new JMenuItem("Exit");
		
		newMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.reset();
			}
		});
		
		undoMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		gameMenu.add(newMenuItem);
		gameMenu.add(undoMenuItem);
		gameMenu.addSeparator();
		gameMenu.add(exitMenuItem);
		menuBar.add(gameMenu);
		
		characterInfoPanel = new CharacterInfoPanel(sc);
		statusPanel = new StatusPanel(sc);
		selectPanel = new SelectPanel(sc);
		fieldPanel = new FieldPanel(sc, selectPanel);
		
		setJMenuBar(menuBar);
		add(fieldPanel, BorderLayout.CENTER);
		add(characterInfoPanel, BorderLayout.SOUTH);
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
		characterInfoPanel.setText(
				sc.getCharacterListString(sc.getPlayerOne()),
				sc.getCharacterListString(sc.getPlayerTwo()));
		repaint();
	}
	
}
