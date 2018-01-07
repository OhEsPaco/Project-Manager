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

import static javax.swing.UIManager.setLookAndFeel;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;

public class JFrameMain extends JFrame {

	private static JPanel cards;
	private static JPanelLogin log;
	private static JPanelRegistro reg;
	private static JPanelPrincipal princ;

	public static void launch() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					JFrameMain frame = new JFrameMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JFrameMain() {

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(JFrameMain.class.getResource("/org/ohespaco/recursos/noicon.png")));

		try {
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
		cards.add(princ, "principal");

		setContentPane(cards);

	}

	public static void resetLogin() {
		log.initComponents();
	}

	public static void resetRegistro() {
		reg.initComponents();
	}

}
