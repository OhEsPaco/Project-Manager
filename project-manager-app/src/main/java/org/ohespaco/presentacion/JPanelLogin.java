package org.ohespaco.presentacion;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

	/**
	 * Create the panel.
	 */
	public JPanelLogin(JPanel cards) {
		
		this.cards=cards;
		createLogin();
	}
	
	
	public JPanel getLoginPane() {
		return loginPane;
	}


	private void createLogin() {
		loginPane = new JPanel();
		loginPane.setBackground(new Color(46, 47, 51));
		loginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		loginPane.setLayout(null);

		btnEntrar = new JButton("Entrar");
		btnEntrar.setForeground(Color.WHITE);
		btnEntrar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEntrar.setBackground(new Color(46, 189, 89));
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			
				try {
					GestorUsuarios.getInstancia("").cargarUsuarios();
					if (GestorUsuarios.getInstancia("").login(emailField.getText(), new String(passwordField.getPassword()))) {
						//////////////////////////////////////////////
						//////////////////////////////////////////////
						//////////////////////////////////////////////
						
					} else {
						lblAviso.setVisible(true);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
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

		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setForeground(Color.LIGHT_GRAY);
		lblEmail.setBounds(23, 195, 258, 14);
		loginPane.add(lblEmail);

		lblContrasea = new JLabel("Contraseña");
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasea.setForeground(Color.LIGHT_GRAY);
		lblContrasea.setBounds(23, 261, 258, 14);
		loginPane.add(lblContrasea);
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(90, 64, 120, 120);
		lblNewLabel.setIcon(new ImageIcon(
				new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/logo.png")).getImage()
						.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH)));

		loginPane.add(lblNewLabel);

		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(Color.GRAY);
		comboBox.setForeground(Color.LIGHT_GRAY);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Castellano", "Inglés" }));
		comboBox.setBounds(142, 11, 139, 24);
		loginPane.add(comboBox);

		JLabel lblRegistro = new JLabel("¿Aún no tienes cuenta? Registrate");
		lblRegistro.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				/////////////////////////////////////////////////////////////
				// PULSACION DE REGISTRARSE
				///////////////////////////////////////////////////////////////
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, "registro");
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(loginPane);
				topFrame.setBounds(100, 100, 600, 500);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblRegistro.setForeground(new Color(46, 189, 89));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblRegistro.setForeground(Color.LIGHT_GRAY);
			}
		});
		lblRegistro.setForeground(Color.LIGHT_GRAY);
		lblRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistro.setBounds(23, 385, 258, 14);
		loginPane.add(lblRegistro);

		lblAviso = new JLabel("Email o contraseña incorrectos");
		lblAviso.setForeground(Color.RED);
		lblAviso.setHorizontalAlignment(SwingConstants.CENTER);
		lblAviso.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAviso.setBounds(23, 314, 258, 14);
		lblAviso.setVisible(false);
		loginPane.add(lblAviso);

	}

}
