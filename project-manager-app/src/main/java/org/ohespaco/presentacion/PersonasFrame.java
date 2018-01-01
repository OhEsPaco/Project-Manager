package org.ohespaco.presentacion;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.validator.routines.EmailValidator;
import org.ohespaco.dominio.GestorUsuarios;
import org.ohespaco.dominio.Usuario;

import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class PersonasFrame extends JFrame {

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
	private JList listPersonas;
	private String foto_path = "/org/ohespaco/recursos/user_icon.png";
	private JTextArea textDescripcion;
	private boolean aplicar = false;
	private JButton btnBorrar;

	/**
	 * Create the frame.
	 */
	public PersonasFrame() {

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

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 846, 512);

		registroPane = new JPanel();
		registroPane.setBackground(new Color(46, 47, 51));
		registroPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		registroPane.setLayout(null);
		setContentPane(registroPane);
		btnRegistrarse = new JButton("Añadir");
		btnRegistrarse.setForeground(Color.WHITE);
		btnRegistrarse.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRegistrarse.setBackground(new Color(46, 189, 89));
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblAviso.setVisible(false);

				if (aplicar == false) {
					if (!GestorUsuarios.getInstancia("").existeEmail(emailField.getText())
							&& EmailValidator.getInstance().isValid(emailField.getText())) {
						if (GestorUsuarios.getInstancia("").validateString(nombreField.getText())
								&& GestorUsuarios.getInstancia("").validateString(apellidosField.getText())) {
							if (new String(passwordField.getPassword())
									.equals(new String(passwordField_2.getPassword()))) {
								if (GestorUsuarios.getInstancia("")
										.validatePass(new String(passwordField.getPassword()))) {
									if (rolField.getText().matches(".*\\w.*")
											&& contactoField.getText().matches(".*\\w.*")
											&& textDescripcion.getText().matches(".*\\w.*")) {
										//////////////////////////////////////////////////////////
										/////////////////////////////
										/////////////////////////////
										// registrarUsuario(String email,String pass, String nombre,String
										////////////////////////////////////////////////////////// apellidos,String
										////////////////////////////////////////////////////////// rol,String
										////////////////////////////////////////////////////////// contacto,String
										////////////////////////////////////////////////////////// descripcion,String
										////////////////////////////////////////////////////////// foto) {
										GestorUsuarios.getInstancia("").registrarUsuario(emailField.getText(),
												new String(passwordField.getPassword()), nombreField.getText(),
												apellidosField.getText(), rolField.getText(), contactoField.getText(),
												textDescripcion.getText(), foto_path);
										listPersonas.setModel(GestorUsuarios.getInstancia("").getDefaultList());

										// btnRegistrarse.setText("Registrado con exito");
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
				} else {
					////////////////////////////////////////////////////////////////////////////////////
					// CONTINUAR POR AQUI////////////////////////////
					//////////////////////////////////////////////////////

					Usuario user = (Usuario) listPersonas.getSelectedValue();
					if (EmailValidator.getInstance().isValid(emailField.getText())) {
						if (GestorUsuarios.getInstancia("").validateString(nombreField.getText())
								&& GestorUsuarios.getInstancia("").validateString(apellidosField.getText())) {

							if (rolField.getText().matches(".*\\w.*") && contactoField.getText().matches(".*\\w.*")
									&& textDescripcion.getText().matches(".*\\w.*")) {

								if (new String(passwordField.getPassword())
										.equals(new String(passwordField_2.getPassword()))) {
									if (GestorUsuarios.getInstancia("")
											.validatePass(new String(passwordField.getPassword()))) {
										
										
										if(new String(passwordField.getPassword())
										.equals(user.getPass_hash())) {
											//No ha cambiado el password
										}else {
											//Ha cambiado el password
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
								lblAviso.setText("No puede haber campos vacios");
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

				// System.out.println();

			}
		});
		btnRegistrarse.setBounds(674, 421, 129, 30);
		registroPane.add(btnRegistrarse);

		passwordField = new JPasswordField();
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setBounds(545, 307, 258, 30);
		registroPane.add(passwordField);

		emailField = new JTextField();
		emailField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		emailField.setBounds(255, 193, 258, 30);
		registroPane.add(emailField);
		emailField.setColumns(10);

		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setForeground(Color.LIGHT_GRAY);
		lblEmail.setBounds(255, 177, 258, 14);
		registroPane.add(lblEmail);

		lblContrasea = new JLabel("Contraseña");
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasea.setForeground(Color.LIGHT_GRAY);
		lblContrasea.setBounds(545, 291, 258, 14);
		registroPane.add(lblContrasea);
		lblFoto = new JLabel("New label");
		lblFoto.setBounds(467, 32, 120, 120);
		lblFoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(foto_path)).getImage()
				.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH)));

		registroPane.add(lblFoto);

		lblAviso = new JLabel("Email o contraseña incorrectos");
		lblAviso.setForeground(Color.RED);
		lblAviso.setHorizontalAlignment(SwingConstants.CENTER);
		lblAviso.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAviso.setBounds(545, 405, 258, 14);
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
		nombreField.setBounds(255, 250, 258, 30);
		registroPane.add(nombreField);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setForeground(Color.LIGHT_GRAY);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombre.setBounds(255, 234, 258, 14);
		registroPane.add(lblNombre);

		apellidosField = new JTextField();
		apellidosField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		apellidosField.setColumns(10);
		apellidosField.setBounds(255, 307, 258, 30);
		registroPane.add(apellidosField);

		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidos.setForeground(Color.LIGHT_GRAY);
		lblApellidos.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblApellidos.setBounds(255, 291, 258, 14);
		registroPane.add(lblApellidos);

		rolField = new JTextField();
		rolField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		rolField.setColumns(10);
		rolField.setBounds(255, 364, 258, 30);
		registroPane.add(rolField);

		JLabel lblRol = new JLabel("Rol");
		lblRol.setHorizontalAlignment(SwingConstants.CENTER);
		lblRol.setForeground(Color.LIGHT_GRAY);
		lblRol.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRol.setBounds(255, 348, 258, 14);
		registroPane.add(lblRol);

		contactoField = new JTextField();
		contactoField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		contactoField.setColumns(10);
		contactoField.setBounds(255, 421, 258, 30);
		registroPane.add(contactoField);

		JLabel lblContacto = new JLabel("Contacto");
		lblContacto.setHorizontalAlignment(SwingConstants.CENTER);
		lblContacto.setForeground(Color.LIGHT_GRAY);
		lblContacto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblContacto.setBounds(255, 405, 258, 14);
		registroPane.add(lblContacto);

		passwordField_2 = new JPasswordField();
		passwordField_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		passwordField_2.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField_2.setBounds(545, 364, 258, 30);
		registroPane.add(passwordField_2);

		JLabel labelPass = new JLabel("Repetir contraseña");
		labelPass.setHorizontalAlignment(SwingConstants.CENTER);
		labelPass.setForeground(Color.LIGHT_GRAY);
		labelPass.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelPass.setBounds(545, 348, 258, 14);
		registroPane.add(labelPass);

		textDescripcion = new JTextArea();
		textDescripcion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		textDescripcion.setLineWrap(true);
		textDescripcion.setBounds(545, 196, 258, 88);
		registroPane.add(textDescripcion);

		JLabel lblHablanosDeT = new JLabel("Hablanos de tí");
		lblHablanosDeT.setHorizontalAlignment(SwingConstants.CENTER);
		lblHablanosDeT.setForeground(Color.LIGHT_GRAY);
		lblHablanosDeT.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHablanosDeT.setBounds(545, 177, 258, 14);
		registroPane.add(lblHablanosDeT);

		JLabel lblAtras = new JLabel("Limpiar campos");
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
				// PULSACION DE LIMPIAR
				///////////////////////////////////////////////////////////////
				aplicar = false;
				btnRegistrarse.setText("Añadir");
				btnBorrar.setEnabled(false);
				passwordField.setText("");
				passwordField_2.setText("");
				emailField.setText("");

				lblFoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(foto_path)).getImage()
						.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH)));

				nombreField.setText("");
				apellidosField.setText("");
				rolField.setText("");

				contactoField.setText("");

				textDescripcion.setText("");
				listPersonas.clearSelection();

			}
		});
		lblAtras.setHorizontalAlignment(SwingConstants.LEFT);
		lblAtras.setForeground(Color.LIGHT_GRAY);
		lblAtras.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAtras.setBounds(255, 12, 110, 14);
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

					}
				} catch (HeadlessException ex) {

				}
			}

		});
		lblCambiarFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblCambiarFoto.setForeground(Color.LIGHT_GRAY);
		lblCambiarFoto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCambiarFoto.setBounds(477, 152, 110, 14);
		registroPane.add(lblCambiarFoto);

		listPersonas = new JList(GestorUsuarios.getInstancia("").getDefaultList());
		listPersonas.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				Usuario user = (Usuario) listPersonas.getSelectedValue();
				if (user != null) {
					passwordField.setText(user.getPass_hash());
					passwordField_2.setText(user.getPass_hash());
					emailField.setText(user.getEmail());

					File f = new File(user.getFoto());
					if (f.exists() && !f.isDirectory()) {
						lblFoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(user.getFoto()).getImage()
								.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH)));
					} else {
						lblFoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(foto_path))
								.getImage()
								.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH)));
					}

					// lblFoto;

					nombreField.setText(user.getNombre());
					apellidosField.setText(user.getApellidos());
					rolField.setText(user.getRol());

					contactoField.setText(user.getContacto());

					textDescripcion.setText(user.getDescripcion());
					aplicar = true;
					btnRegistrarse.setText("Aplicar");
					btnBorrar.setEnabled(true);
				}

			}
		});
		listPersonas.setBounds(10, 11, 210, 440);

		registroPane.add(listPersonas);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setEnabled(false);
		btnBorrar.setForeground(Color.WHITE);
		btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBorrar.setBackground(Color.RED);
		btnBorrar.setBounds(545, 421, 129, 30);
		registroPane.add(btnBorrar);
	}
}