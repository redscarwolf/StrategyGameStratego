package de.htwg.stratego.aview.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import de.htwg.stratego.controller.ISingelDeviceStrategoController;
import de.htwg.stratego.util.observer.Event;
import de.htwg.stratego.util.observer.IObserver;

public class StrategoFrame extends JFrame implements IObserver {
	private static final long serialVersionUID = 1L;

	private ISingelDeviceStrategoController sc;

	private FieldPanel fieldPanel;
	private CharacterInfoPanel characterInfoPanel;
	private StatusPanel statusPanel;
	private SelectPanel selectPanel;
	
	private JMenuBar menuBar;

	private JMenu gameMenu;
	private JMenu editMenu;

	private JMenuItem newMenuItem;
	private JMenuItem undoMenuItem;
	private JMenuItem loadMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem exitMenuItem;
	
	public StrategoFrame(final ISingelDeviceStrategoController sc) {
		this.sc = sc;
		sc.addObserver(this);
		
		menuBar = new JMenuBar();

		gameMenu = new JMenu("Game");
		editMenu = new JMenu("Edit");

		newMenuItem = new JMenuItem("New");
		loadMenuItem = new JMenuItem("Load");
		saveMenuItem = new JMenuItem("Save");
		exitMenuItem = new JMenuItem("Exit");

		undoMenuItem = new JMenuItem("Undo");

		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		
		newMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.reset();
			}
		});

		loadMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.load();
			}
		});

		saveMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.save();
			}
		});

		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		undoMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.undo();
			}
		});

		gameMenu.add(newMenuItem);
		gameMenu.addSeparator();
		gameMenu.add(loadMenuItem);
		gameMenu.add(saveMenuItem);
		gameMenu.addSeparator();
		gameMenu.add(exitMenuItem);

		editMenu.add(undoMenuItem);

		menuBar.add(gameMenu);
		menuBar.add(editMenu);
		
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
