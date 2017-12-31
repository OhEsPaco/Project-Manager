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
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.validator.routines.EmailValidator;
import org.ohespaco.dominio.GestorUsuarios;
import org.ohespaco.persistencia.CurrentSession;
import javax.swing.JTextArea;

public class JPanelRegistro extends JPanel {

	private JPanel registroPane;
	private JButton btnRegistrarse;
	private JPasswordField passwordField;
	private JTextField emailField;
	private JLabel lblEmail;
	private JLabel lblContrasea;
	private JLabel lblFoto;
	private JLabel lblAviso;
	private JPanel cards;
	private JTextField nombreField;
	private JTextField apellidosField;
	private JTextField rolField;
	private JTextField contactoField;
	private JPasswordField passwordField_2;
	private String foto_path = "/org/ohespaco/recursos/logo.png";
	private JTextArea textDescripcion;
	private boolean changed_foto = false;

	/**
	 * Create the panel.
	 */

	public JPanelRegistro(JPanel cards) {
		this.cards = cards;
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

		btnRegistrarse = new JButton("Registro");
		btnRegistrarse.setForeground(Color.WHITE);
		btnRegistrarse.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRegistrarse.setBackground(new Color(46, 189, 89));
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblAviso.setVisible(false);
				// System.out.println();
				if (!GestorUsuarios.getInstancia("").existeEmail(emailField.getText())
						&& EmailValidator.getInstance().isValid(emailField.getText())) {
					if (GestorUsuarios.getInstancia("").validateString(nombreField.getText())
							&& GestorUsuarios.getInstancia("").validateString(apellidosField.getText())) {
						if (new String(passwordField.getPassword()).equals(new String(passwordField_2.getPassword()))) {
							if (GestorUsuarios.getInstancia("").validatePass(new String(passwordField.getPassword()))) {
								if (rolField.getText().matches(".*\\w.*") && contactoField.getText().matches(".*\\w.*")
										&& textDescripcion.getText().matches(".*\\w.*")) {
									//////////////////////////////////////////////////////////
									/////////////////////////////
									/////////////////////////////
									 //registrarUsuario(String email,String pass, String nombre,String apellidos,String rol,String contacto,String descripcion,String foto) {
									GestorUsuarios.getInstancia("").registrarUsuario(emailField.getText(), new String(passwordField.getPassword()), nombreField.getText(), apellidosField.getText(), rolField.getText(), contactoField.getText(), textDescripcion.getText(), foto_path);
									btnRegistrarse.setText("Registrado con exito");
								} else {
									lblAviso.setText("No puede haber campos vacios");
									lblAviso.setVisible(true);
								}
							} else {
								lblAviso.setText("Contraseña corta");
								lblAviso.setVisible(true);
							}
						} else {
							lblAviso.setText("Las contraseñas no concuerdan");
							lblAviso.setVisible(true);
						}
					} else {
						lblAviso.setText("Nombre o apellidos invalidos");
						lblAviso.setVisible(true);
					}

				} else {

					lblAviso.setText("Email invalido");
					lblAviso.setVisible(true);
				}
			}
		});
		btnRegistrarse.setBounds(309, 421, 258, 30);
		registroPane.add(btnRegistrarse);

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
		lblFoto = new JLabel("New label");
		lblFoto.setBounds(220, 24, 120, 120);
		lblFoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(foto_path)).getImage()
				.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH)));

		registroPane.add(lblFoto);

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

		nombreField = new JTextField();
		nombreField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		nombreField.setColumns(10);
		nombreField.setBounds(23, 250, 258, 30);
		registroPane.add(nombreField);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setForeground(Color.LIGHT_GRAY);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombre.setBounds(23, 234, 258, 14);
		registroPane.add(lblNombre);

		apellidosField = new JTextField();
		apellidosField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		apellidosField.setColumns(10);
		apellidosField.setBounds(23, 307, 258, 30);
		registroPane.add(apellidosField);

		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidos.setForeground(Color.LIGHT_GRAY);
		lblApellidos.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblApellidos.setBounds(23, 291, 258, 14);
		registroPane.add(lblApellidos);

		rolField = new JTextField();
		rolField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		rolField.setColumns(10);
		rolField.setBounds(23, 364, 258, 30);
		registroPane.add(rolField);

		JLabel lblRol = new JLabel("Rol");
		lblRol.setHorizontalAlignment(SwingConstants.CENTER);
		lblRol.setForeground(Color.LIGHT_GRAY);
		lblRol.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRol.setBounds(23, 348, 258, 14);
		registroPane.add(lblRol);

		contactoField = new JTextField();
		contactoField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		contactoField.setColumns(10);
		contactoField.setBounds(23, 421, 258, 30);
		registroPane.add(contactoField);

		JLabel lblContacto = new JLabel("Contacto");
		lblContacto.setHorizontalAlignment(SwingConstants.CENTER);
		lblContacto.setForeground(Color.LIGHT_GRAY);
		lblContacto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblContacto.setBounds(23, 405, 258, 14);
		registroPane.add(lblContacto);

		passwordField_2 = new JPasswordField();
		passwordField_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		passwordField_2.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField_2.setBounds(309, 364, 258, 30);
		registroPane.add(passwordField_2);

		JLabel labelPass = new JLabel("Contraseña");
		labelPass.setHorizontalAlignment(SwingConstants.CENTER);
		labelPass.setForeground(Color.LIGHT_GRAY);
		labelPass.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelPass.setBounds(309, 348, 258, 14);
		registroPane.add(labelPass);

		textDescripcion = new JTextArea();
		textDescripcion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		textDescripcion.setLineWrap(true);
		textDescripcion.setBounds(309, 193, 258, 88);
		registroPane.add(textDescripcion);

		JLabel lblHablanosDeT = new JLabel("Hablanos de tí");
		lblHablanosDeT.setHorizontalAlignment(SwingConstants.CENTER);
		lblHablanosDeT.setForeground(Color.LIGHT_GRAY);
		lblHablanosDeT.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHablanosDeT.setBounds(309, 177, 258, 14);
		registroPane.add(lblHablanosDeT);

		JLabel lblAtras = new JLabel("Atrás");
		lblAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblAtras.setForeground(new Color(46, 189, 89));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblAtras.setForeground(Color.LIGHT_GRAY);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				/////////////////////////////////////////////////////////////
				// PULSACION DE ATRAS
				///////////////////////////////////////////////////////////////
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, "login");

				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(registroPane);
				Point posicion = topFrame.getLocationOnScreen();
				topFrame.setBounds((int) posicion.getX(), (int) posicion.getY(), 310, 450);
				
				MainFrame.resetLogin();
			}
		});
		lblAtras.setHorizontalAlignment(SwingConstants.LEFT);
		lblAtras.setForeground(Color.LIGHT_GRAY);
		lblAtras.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAtras.setBounds(23, 16, 74, 14);
		registroPane.add(lblAtras);

		JLabel lblCambiarFoto = new JLabel("Cambiar foto");
		lblCambiarFoto.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				lblCambiarFoto.setForeground(new Color(46, 189, 89));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblCambiarFoto.setForeground(Color.LIGHT_GRAY);
			}

			@Override
			public void mousePressed(MouseEvent e) {

				JFileChooser jfch = new JFileChooser();
				jfch.setAcceptAllFileFilterUsed(false);
				jfch.addChoosableFileFilter(new FileNameExtensionFilter("PNG image files", "png"));
				try {
					if (jfch.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						foto_path = jfch.getSelectedFile().getAbsolutePath();
						lblFoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(foto_path).getImage()
								.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH)));
						changed_foto = true;

					}
				} catch (HeadlessException ex) {

				}
			}

		});
		lblCambiarFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblCambiarFoto.setForeground(Color.LIGHT_GRAY);
		lblCambiarFoto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCambiarFoto.setBounds(220, 152, 120, 14);
		registroPane.add(lblCambiarFoto);

	}

	public void initComponents() {

		passwordField.setText("");
		emailField.setText("");
		foto_path = "/org/ohespaco/recursos/logo.png";
		changed_foto = false;
		lblFoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(foto_path)).getImage()
				.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH)));
		lblAviso.setText("");
		nombreField.setText("");
		apellidosField.setText("");
		rolField.setText("");
		contactoField.setText("");
		passwordField_2.setText("");
		textDescripcion.setText("");
		btnRegistrarse.setText("Registrarse");
	}
}
