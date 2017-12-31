package org.ohespaco.presentacion;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.ohespaco.dominio.GestorUsuarios;
import org.ohespaco.persistencia.CurrentSession;
import javax.swing.JTextArea;

public class JPanelRegistro extends JPanel {

	private JPanel registroPane;
	private JButton btnEntrar;
	private JPasswordField passwordField;
	private JTextField emailField;
	private JLabel lblEmail;
	private JLabel lblContrasea;
	private JLabel lblNewLabel;
	private JLabel lblAviso;
	private JPanel cards;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JPasswordField passwordField_1;
	/**
	 * Create the panel.
	 */

	public JPanelRegistro(JPanel cards) {
		this.cards=cards;
		createRegistro();
	}
	
	
	public JPanel getRegistroPane() {
		return registroPane;
	}
	public void createRegistro() {
		registroPane = new JPanel();
		registroPane.setBackground(new Color(46, 47, 51));
		registroPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		registroPane.setLayout(null);

		btnEntrar = new JButton("REGISTRO");
		btnEntrar.setForeground(Color.WHITE);
		btnEntrar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEntrar.setBackground(new Color(46, 189, 89));
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnEntrar.setBounds(309, 421, 258, 30);
		registroPane.add(btnEntrar);

		passwordField = new JPasswordField();
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setBounds(309, 307, 258, 30);
		registroPane.add(passwordField);

		emailField = new JTextField();
		emailField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		emailField.setBounds(23, 193, 258, 30);
		registroPane.add(emailField);
		emailField.setColumns(10);

		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setForeground(Color.LIGHT_GRAY);
		lblEmail.setBounds(23, 177, 258, 14);
		registroPane.add(lblEmail);

		lblContrasea = new JLabel("Contraseña");
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasea.setForeground(Color.LIGHT_GRAY);
		lblContrasea.setBounds(309, 292, 258, 14);
		registroPane.add(lblContrasea);
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(220, 24, 120, 120);
		lblNewLabel.setIcon(new ImageIcon(
				new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/logo.png")).getImage()
						.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH)));

		registroPane.add(lblNewLabel);

		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(Color.GRAY);
		comboBox.setForeground(Color.LIGHT_GRAY);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Castellano", "Inglés" }));
		comboBox.setBounds(428, 11, 139, 24);
		registroPane.add(comboBox);

		lblAviso = new JLabel("Email o contraseña incorrectos");
		lblAviso.setForeground(Color.RED);
		lblAviso.setHorizontalAlignment(SwingConstants.CENTER);
		lblAviso.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAviso.setBounds(309, 400, 258, 14);
		lblAviso.setVisible(false);
		registroPane.add(lblAviso);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(23, 250, 258, 30);
		registroPane.add(textField);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setForeground(Color.LIGHT_GRAY);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombre.setBounds(23, 234, 258, 14);
		registroPane.add(lblNombre);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(23, 307, 258, 30);
		registroPane.add(textField_1);
		
		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidos.setForeground(Color.LIGHT_GRAY);
		lblApellidos.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblApellidos.setBounds(23, 291, 258, 14);
		registroPane.add(lblApellidos);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(23, 364, 258, 30);
		registroPane.add(textField_2);
		
		JLabel lblRol = new JLabel("Rol");
		lblRol.setHorizontalAlignment(SwingConstants.CENTER);
		lblRol.setForeground(Color.LIGHT_GRAY);
		lblRol.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRol.setBounds(23, 348, 258, 14);
		registroPane.add(lblRol);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(23, 421, 258, 30);
		registroPane.add(textField_3);
		
		JLabel lblContacto = new JLabel("Contacto");
		lblContacto.setHorizontalAlignment(SwingConstants.CENTER);
		lblContacto.setForeground(Color.LIGHT_GRAY);
		lblContacto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblContacto.setBounds(23, 405, 258, 14);
		registroPane.add(lblContacto);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField_1.setBounds(309, 364, 258, 30);
		registroPane.add(passwordField_1);
		
		JLabel label = new JLabel("Contraseña");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.LIGHT_GRAY);
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(309, 348, 258, 14);
		registroPane.add(label);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(309, 193, 258, 88);
		registroPane.add(textArea);
		
		JLabel lblHablanosDeT = new JLabel("Hablanos de tí");
		lblHablanosDeT.setHorizontalAlignment(SwingConstants.CENTER);
		lblHablanosDeT.setForeground(Color.LIGHT_GRAY);
		lblHablanosDeT.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHablanosDeT.setBounds(309, 177, 258, 14);
		registroPane.add(lblHablanosDeT);
		
		JLabel lblAtras = new JLabel("Atrás");
		lblAtras.setHorizontalAlignment(SwingConstants.LEFT);
		lblAtras.setForeground(Color.LIGHT_GRAY);
		lblAtras.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAtras.setBounds(23, 16, 74, 14);
		registroPane.add(lblAtras);
		
		JLabel lblCambiarFoto = new JLabel("Cambiar foto");
		lblCambiarFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblCambiarFoto.setForeground(Color.LIGHT_GRAY);
		lblCambiarFoto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCambiarFoto.setBounds(220, 152, 120, 14);
		registroPane.add(lblCambiarFoto);

	}
}
