package org.ohespaco.presentacion;

import javax.swing.JPanel;
import java.awt.Color;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;

import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.ohespaco.persistencia.CurrentSession;

import java.awt.FlowLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Dialog;

import javax.swing.Box;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

public class JPanelPrincipal extends JPanel {
private JPanel panelsur=null;
private JLabel lblUsuariobottombar=null;
private JLabel lblLogintimebottombar=null;
private Component horizontalStrut=null;
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
			public void mousePressed(MouseEvent e) {
			//////////////////////Lanza un gestor de personas//////////
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panelnor);
				
				PersonasFrame pers = new PersonasFrame(topFrame, true);
				JDialog jd = new JDialog(pers, "Dialogo modal", Dialog.ModalityType.DOCUMENT_MODAL);
				
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
		
	    panelsur = new JPanel();
	    panelsur.setBackground(new Color(46, 189, 89));
	    panelsur.setForeground(Color.BLACK);
		add(panelsur, BorderLayout.SOUTH);
		panelsur.addComponentListener(new ResizeListener());
	    panelsur.setLayout(new GridLayout(0, 2, 0, 0));
	    lblUsuariobottombar = new JLabel("Usuario_bottombar");
	    lblUsuariobottombar.setHorizontalAlignment(SwingConstants.CENTER);
	    lblUsuariobottombar.setForeground(Color.BLACK);
	    lblUsuariobottombar.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelsur.add(lblUsuariobottombar);
		
		
		
		lblLogintimebottombar = new JLabel("Logintime_bottombar");
		lblLogintimebottombar.setForeground(Color.BLACK);
		lblLogintimebottombar.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLogintimebottombar.setHorizontalAlignment(SwingConstants.CENTER);
		panelsur.add(lblLogintimebottombar);

	}
	class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
         if(CurrentSession.getInstancia().getUser()!=null) {
        	 lblUsuariobottombar.setText("Bienvenido, "+CurrentSession.getInstancia().getUser().getNombre());
        	 lblLogintimebottombar.setText("Ultimo login: "+CurrentSession.getInstancia().getLogin_time());
         }
        	
        	/*  System.out.println("Cambio de tamaño");
           if(panelsur!=null) {
        	 
        	  if( panelsur.getWidth()>700&& panelsur.getHeight()>700) {
        		  lblUsuariobottombar.setFont(new Font("Tahoma", Font.BOLD, 14));
        		  lblLogintimebottombar.setFont(new Font("Tahoma", Font.BOLD, 14));
        	  }else {
        		  lblLogintimebottombar.setFont(new Font("Tahoma", Font.BOLD, 11));
        		  lblUsuariobottombar.setFont(new Font("Tahoma", Font.BOLD, 11));
        	  }
        	  
        	
        	
        	  
           }*/
        }
}
}
