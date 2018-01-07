/*
Copyright (c) 2017 
Francisco Manuel Garcia Sanchez-Belmonte
Adrian Bustos Marin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package org.ohespaco.presentacion;



import javax.swing.JFrame;
import javax.swing.JPanel;

import org.ohespaco.dominio.GestorUsuarios;
import org.ohespaco.persistencia.CurrentSession;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;

import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;

import static java.util.logging.Level.SEVERE;

import static java.util.logging.Logger.getLogger;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

public class MainFrame extends JFrame {

	private static JPanel cards;
	private static JPanelLogin log;
	private static JPanelRegistro reg;
	private static JPanelPrincipal princ;

	/**
	 * Launch the application.
	 */
public static void launch(){
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/org/ohespaco/recursos/noicon.png")));
		//WebLookAndFeel.install();
		/*	try {
			for (javax.swing.UIManager.LookAndFeelInfo info : getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					setLookAndFeel(info.getClassName());
					break;
				}

			}
			// setLookAndFeel("Nimbus");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| javax.swing.UnsupportedLookAndFeelException ex) {
			getLogger(MainFrame.class.getName()).log(SEVERE, null, ex);
		}*/
	
		/*try {
		    setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}*/
		try{
		    setLookAndFeel(new SubstanceGraphiteLookAndFeel());
		} catch (Exception e) {
		    e.printStackTrace();
		}

		setTitle("Project Manager");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 450);
		getContentPane().setLayout(new CardLayout());
		
		
		cards = new JPanel(new CardLayout());
		
	    log = new JPanelLogin(cards);
		reg = new JPanelRegistro(cards);
		princ = new JPanelPrincipal();
		
		setResizable(true);
		
		cards.add(log.getLoginPane(), "login");
		cards.add(reg.getRegistroPane(), "registro");
		cards.add(princ,"principal");
		
		
		setContentPane(cards);
	
	}
	public static void resetLogin() {
		log.initComponents();
	}
	public static void resetRegistro() {
		reg.initComponents();
	}

	
	
}
