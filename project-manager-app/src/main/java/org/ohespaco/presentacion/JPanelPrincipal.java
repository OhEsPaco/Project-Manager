package org.ohespaco.presentacion;

import javax.swing.JPanel;
import java.awt.Color;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;

import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.FlowLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JPanelPrincipal extends JPanel {

	/**
	 * Create the panel.
	 */
	public JPanelPrincipal() {
		
		
	
	
		setBackground((new Color(46, 47, 51)));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelIZ = new JPanel();
		add(panelIZ, BorderLayout.WEST);
		
		JPanel panelnor = new JPanel();
		panelnor.setBackground(Color.WHITE);
		add(panelnor, BorderLayout.NORTH);
		panelnor.setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.setFont(new Font("Tahoma", Font.BOLD, 11));
		panelnor.add(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(mnArchivo);
		
		JMenu mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);
		
		JMenu mnTareas = new JMenu("Tareas");
		menuBar.add(mnTareas);
		
		JMenu mnPersonas = new JMenu("Personas");
		menuBar.add(mnPersonas);
		
		JMenuItem mntmListarPersonas = new JMenuItem("Gestionar personas");
		mntmListarPersonas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PersonasFrame pers = new PersonasFrame();
				pers.setVisible(true);
				
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				PersonasFrame pers = new PersonasFrame();
				pers.setVisible(true);
			}
		});
		mnPersonas.add(mntmListarPersonas);
		
		JMenu mnVer = new JMenu("Ver");
		menuBar.add(mnVer);
		
		JMenu mnSesin = new JMenu("Sesión");
		menuBar.add(mnSesin);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JPanel panelder = new JPanel();
		add(panelder, BorderLayout.EAST);
		
		JPanel panelsur = new JPanel();
		add(panelsur, BorderLayout.SOUTH);

	}
}
