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

import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;

import static java.util.logging.Level.SEVERE;

import static java.util.logging.Logger.getLogger;

import java.awt.CardLayout;
import java.awt.Toolkit;

public class MainFrame extends JFrame {

	private JPanel cards;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public MainFrame() {

		setIconImage(
				Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/org/ohespaco/recursos/noicon.png")));
		try {
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
		}

		setTitle("Project Manager");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 310, 450);
		setLayout(new CardLayout());
		
		
		cards = new JPanel(new CardLayout());
		
		JPanelLogin log = new JPanelLogin(cards);
		JPanelRegistro reg = new JPanelRegistro(cards);
		
		cards.add(log.getLoginPane(), "login");
		cards.add(reg.getRegistroPane(), "registro");
		setContentPane(cards);
		

	}

	
	
}
