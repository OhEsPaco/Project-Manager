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

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.ohespaco.dominio.GestorUsuarios;

public class JPanelLogin extends JPanel {
	private JPanel loginPane;
	private JButton btnEntrar;
	private JPasswordField passwordField;
	private JTextField emailField;
	private JLabel lblEmail;
	private JLabel lblContrasea;
	private JLabel lblNewLabel;
	private JLabel lblAviso;
	private JPanel cards;
	public JPanelLogin(JPanel cards) {

		this.cards = cards;
		createLogin();
	}

	public JPanel getLoginPane() {
		return loginPane;
	}

	private void createLogin() {
		loginPane = new JPanel();

		loginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		loginPane.setLayout(null);

		btnEntrar = new JButton(Messages.getString("JPanelLogin.entrar")); //$NON-NLS-1$
		btnEntrar.setToolTipText(Messages.getString("JPanelLogin.btnEntrar.toolTipText")); //$NON-NLS-1$
		btnEntrar.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$

		btnEntrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					GestorUsuarios.getInstancia("").cargarUsuarios(); //$NON-NLS-1$
					if (GestorUsuarios.getInstancia("").login(emailField.getText(), //$NON-NLS-1$
							new String(passwordField.getPassword()))) {

						CardLayout cl = (CardLayout) (cards.getLayout());
						cl.show(cards, "principal"); //$NON-NLS-1$
						JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(loginPane);
						Point posicion = topFrame.getLocationOnScreen();

						topFrame.setBounds((int) posicion.getX(), (int) posicion.getY(), 1300, 700);
						topFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
						topFrame.setResizable(true);

					} else {
						lblAviso.setVisible(true);

					}
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnEntrar.setBounds(23, 333, 258, 30);
		loginPane.add(btnEntrar);

		passwordField = new JPasswordField();
		
	
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setBounds(23, 278, 258, 30);
		loginPane.add(passwordField);

		emailField = new JTextField();
		
		emailField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		emailField.setBounds(23, 212, 258, 30);
		loginPane.add(emailField);
		emailField.setColumns(10);

		lblEmail = new JLabel(Messages.getString("JPanelLogin.email")); //$NON-NLS-1$
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		// lblEmail.setForeground(Color.DARK_GRAY);
		lblEmail.setBounds(23, 195, 258, 14);
		loginPane.add(lblEmail);

		lblContrasea = new JLabel(Messages.getString("JPanelLogin.9")); //$NON-NLS-1$
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);

		lblContrasea.setBounds(23, 261, 258, 14);
		loginPane.add(lblContrasea);
		lblNewLabel = new JLabel("New label"); //$NON-NLS-1$
		lblNewLabel.setBounds(90, 64, 120, 120);
		lblNewLabel.setIcon(new ImageIcon(
				new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/logo.png")).getImage() //$NON-NLS-1$
						.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH)));

		loginPane.add(lblNewLabel);

		JLabel lblRegistro = new JLabel(Messages.getString("JPanelLogin.registrate")); //$NON-NLS-1$
		lblRegistro.setFont(new Font("Tahoma", Font.PLAIN, 11)); //$NON-NLS-1$
		lblRegistro.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, "registro"); //$NON-NLS-1$
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(loginPane);
				Point posicion = topFrame.getLocationOnScreen();

				topFrame.setBounds((int) posicion.getX(), (int) posicion.getY(), 600, 500);
				JFrameMain.resetRegistro();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblRegistro.setForeground(new Color(46, 189, 89));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblRegistro.setForeground(new Color(112, 154, 208));
			}
		});
		lblRegistro.setForeground(new Color(112, 154, 208));
		lblRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistro.setBounds(23, 385, 258, 14);
		loginPane.add(lblRegistro);

		lblAviso = new JLabel(Messages.getString("JPanelLogin.aviso")); //$NON-NLS-1$
		lblAviso.setForeground(Color.RED);
		lblAviso.setHorizontalAlignment(SwingConstants.CENTER);
		lblAviso.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		lblAviso.setBounds(23, 314, 258, 14);
		lblAviso.setVisible(false);
		loginPane.add(lblAviso);

	}
	

	public void initComponents() {
		emailField.setText(""); //$NON-NLS-1$
		passwordField.setText(""); //$NON-NLS-1$
		lblAviso.setVisible(false);
	}

}
