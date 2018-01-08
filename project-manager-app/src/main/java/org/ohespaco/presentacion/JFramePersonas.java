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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.validator.routines.EmailValidator;
import org.ohespaco.dominio.GestorEquipo;
import org.ohespaco.dominio.GestorUsuarios;
import org.ohespaco.dominio.Usuario;
import org.ohespaco.persistencia.CurrentSession;

public class JFramePersonas extends JDialog {

	private JPanel registroPane;
	private JButton btnRegistrarse;
	private JPasswordField passwordField;
	private JTextField emailField;
	private JLabel lblEmail;
	private JLabel lblContrasea;
	private JLabel lblFoto;
	private JLabel lblAviso;
	private JTextField nombreField;
	private JTextField apellidosField;
	private JTextField rolField;
	private JTextField contactoField;
	private JPasswordField passwordField_2;
	private JList<Usuario> listPersonas;
	private String foto_path = "/org/ohespaco/recursos/user_icon.png"; //$NON-NLS-1$
	private final String DEFAULT_FOTO_PATH = "/org/ohespaco/recursos/user_icon.png"; //$NON-NLS-1$
	private JTextArea textDescripcion;
	private boolean aplicar = false;
	private JButton btnBorrar;

	public JFramePersonas(javax.swing.JFrame parent, boolean modal, JList listEquipo, String project_uuid) {
		super(parent, modal);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (project_uuid != null) {
					listEquipo.setModel(GestorEquipo.getInstancia("").getMiembrosEquipoProyecto(project_uuid)); //$NON-NLS-1$
				}

			}
		});

		setTitle(Messages.getString("JFramePersonas.3")); //$NON-NLS-1$
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 779, 499);

		registroPane = new JPanel();

		registroPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		registroPane.setLayout(null);
		setContentPane(registroPane);
		btnRegistrarse = new JButton(Messages.getString("JFramePersonas.4")); //$NON-NLS-1$

		btnRegistrarse.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$

		btnRegistrarse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblAviso.setVisible(false);

				if (aplicar == false) {
					if (!GestorUsuarios.getInstancia("").existeEmail(emailField.getText()) //$NON-NLS-1$
							&& EmailValidator.getInstance().isValid(emailField.getText())) {
						if (GestorUsuarios.getInstancia("").validateString(nombreField.getText()) //$NON-NLS-1$
								&& GestorUsuarios.getInstancia("").validateString(apellidosField.getText())) { //$NON-NLS-1$
							if (new String(passwordField.getPassword())
									.equals(new String(passwordField_2.getPassword()))) {
								if (GestorUsuarios.getInstancia("") //$NON-NLS-1$
										.validatePass(new String(passwordField.getPassword()))) {
									if (rolField.getText().matches(".*\\w.*") //$NON-NLS-1$
											&& contactoField.getText().matches(".*\\w.*") //$NON-NLS-1$
											&& textDescripcion.getText().matches(".*\\w.*")) { //$NON-NLS-1$

										GestorUsuarios.getInstancia("").registrarUsuario(emailField.getText(), //$NON-NLS-1$
												new String(passwordField.getPassword()), nombreField.getText(),
												apellidosField.getText(), rolField.getText(), contactoField.getText(),
												textDescripcion.getText(), foto_path);
										listPersonas.setModel(GestorUsuarios.getInstancia("").getDefaultList()); //$NON-NLS-1$

									} else {
										lblAviso.setText(Messages.getString("JFramePersonas.15")); //$NON-NLS-1$
										lblAviso.setVisible(true);
									}
								} else {
									lblAviso.setText(Messages.getString("JFramePersonas.16")); //$NON-NLS-1$
									lblAviso.setVisible(true);
								}
							} else {
								lblAviso.setText(Messages.getString("JFramePersonas.17")); //$NON-NLS-1$
								lblAviso.setVisible(true);
							}
						} else {
							lblAviso.setText(Messages.getString("JFramePersonas.18")); //$NON-NLS-1$
							lblAviso.setVisible(true);
						}

					} else {

						lblAviso.setText(Messages.getString("JFramePersonas.19")); //$NON-NLS-1$
						lblAviso.setVisible(true);
					}
				} else {

					Usuario user = listPersonas.getSelectedValue();
					if (EmailValidator.getInstance().isValid(emailField.getText())) {
						if (GestorUsuarios.getInstancia("").validateString(nombreField.getText()) //$NON-NLS-1$
								&& GestorUsuarios.getInstancia("").validateString(apellidosField.getText())) { //$NON-NLS-1$

							if (rolField.getText().matches(".*\\w.*") && contactoField.getText().matches(".*\\w.*") //$NON-NLS-1$ //$NON-NLS-2$
									&& textDescripcion.getText().matches(".*\\w.*")) { //$NON-NLS-1$

								if (new String(passwordField.getPassword())
										.equals(new String(passwordField_2.getPassword()))) {
									if (GestorUsuarios.getInstancia("") //$NON-NLS-1$
											.validatePass(new String(passwordField.getPassword()))) {

										if (new String(passwordField.getPassword()).equals(user.getPass_hash())) {
											GestorUsuarios.getInstancia("").editarUsuario(user.getUuid(), //$NON-NLS-1$
													emailField.getText(), new String(passwordField.getPassword()),
													nombreField.getText(), apellidosField.getText(), rolField.getText(),
													contactoField.getText(), textDescripcion.getText(), foto_path,
													false);

											listPersonas.setModel(GestorUsuarios.getInstancia("").getDefaultList()); //$NON-NLS-1$

										} else {
											GestorUsuarios.getInstancia("").editarUsuario(user.getUuid(), //$NON-NLS-1$
													emailField.getText(), new String(passwordField.getPassword()),
													nombreField.getText(), apellidosField.getText(), rolField.getText(),
													contactoField.getText(), textDescripcion.getText(), foto_path,
													true);

											listPersonas.setModel(GestorUsuarios.getInstancia("").getDefaultList()); //$NON-NLS-1$

										}

										aplicar = false;
										btnRegistrarse.setText(Messages.getString("JFramePersonas.30")); //$NON-NLS-1$
										btnBorrar.setEnabled(false);
									} else {
										lblAviso.setText(Messages.getString("JFramePersonas.31")); //$NON-NLS-1$
										lblAviso.setVisible(true);
									}
								} else {
									lblAviso.setText(Messages.getString("JFramePersonas.32")); //$NON-NLS-1$
									lblAviso.setVisible(true);
								}

							} else {
								lblAviso.setText(Messages.getString("JFramePersonas.33")); //$NON-NLS-1$
								lblAviso.setVisible(true);
							}

						} else {
							lblAviso.setText(Messages.getString("JFramePersonas.34")); //$NON-NLS-1$
							lblAviso.setVisible(true);
						}

					} else {

						lblAviso.setText(Messages.getString("JFramePersonas.35")); //$NON-NLS-1$
						lblAviso.setVisible(true);
					}
				}

			}
		});
		btnRegistrarse.setBounds(617, 421, 129, 30);
		registroPane.add(btnRegistrarse);

		passwordField = new JPasswordField();
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setBounds(488, 307, 258, 30);
		registroPane.add(passwordField);

		emailField = new JTextField();
		emailField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		emailField.setBounds(194, 193, 258, 30);
		registroPane.add(emailField);
		emailField.setColumns(10);

		lblEmail = new JLabel(Messages.getString("JFramePersonas.36")); //$NON-NLS-1$
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);

		lblEmail.setBounds(194, 177, 258, 14);
		registroPane.add(lblEmail);

		lblContrasea = new JLabel(Messages.getString("JFramePersonas.38")); //$NON-NLS-1$
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);

		lblContrasea.setBounds(488, 291, 258, 14);
		registroPane.add(lblContrasea);
		lblFoto = new JLabel("New label"); //$NON-NLS-1$
		lblFoto.setBounds(403, 33, 120, 120);
		lblFoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(DEFAULT_FOTO_PATH)).getImage()
				.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH)));

		registroPane.add(lblFoto);

		lblAviso = new JLabel(Messages.getString("JFramePersonas.41")); //$NON-NLS-1$

		lblAviso.setHorizontalAlignment(SwingConstants.CENTER);
		lblAviso.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		lblAviso.setBounds(488, 405, 258, 14);
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
		nombreField.setBounds(194, 250, 258, 30);
		registroPane.add(nombreField);

		JLabel lblNombre = new JLabel(Messages.getString("JFramePersonas.43")); //$NON-NLS-1$
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);

		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		lblNombre.setBounds(194, 234, 258, 14);
		registroPane.add(lblNombre);

		apellidosField = new JTextField();
		apellidosField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		apellidosField.setColumns(10);
		apellidosField.setBounds(194, 307, 258, 30);
		registroPane.add(apellidosField);

		JLabel lblApellidos = new JLabel(Messages.getString("JFramePersonas.45")); //$NON-NLS-1$
		lblApellidos.setHorizontalAlignment(SwingConstants.CENTER);

		lblApellidos.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		lblApellidos.setBounds(194, 291, 258, 14);
		registroPane.add(lblApellidos);

		rolField = new JTextField();
		rolField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		rolField.setColumns(10);
		rolField.setBounds(194, 364, 258, 30);
		registroPane.add(rolField);

		JLabel lblRol = new JLabel(Messages.getString("JFramePersonas.47")); //$NON-NLS-1$
		lblRol.setHorizontalAlignment(SwingConstants.CENTER);

		lblRol.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		lblRol.setBounds(194, 348, 258, 14);
		registroPane.add(lblRol);

		contactoField = new JTextField();
		contactoField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		contactoField.setColumns(10);
		contactoField.setBounds(194, 421, 258, 30);
		registroPane.add(contactoField);

		JLabel lblContacto = new JLabel(Messages.getString("JFramePersonas.49")); //$NON-NLS-1$
		lblContacto.setHorizontalAlignment(SwingConstants.CENTER);

		lblContacto.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		lblContacto.setBounds(194, 405, 258, 14);
		registroPane.add(lblContacto);

		passwordField_2 = new JPasswordField();
		passwordField_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		passwordField_2.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField_2.setBounds(488, 364, 258, 30);
		registroPane.add(passwordField_2);

		JLabel labelPass = new JLabel(Messages.getString("JFramePersonas.51")); //$NON-NLS-1$
		labelPass.setHorizontalAlignment(SwingConstants.CENTER);

		labelPass.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		labelPass.setBounds(488, 348, 258, 14);
		registroPane.add(labelPass);

		textDescripcion = new JTextArea();
		textDescripcion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		textDescripcion.setLineWrap(true);
		textDescripcion.setBounds(488, 196, 258, 88);
		registroPane.add(textDescripcion);

		JLabel lblHablanosDeT = new JLabel(Messages.getString("JFramePersonas.53")); //$NON-NLS-1$
		lblHablanosDeT.setHorizontalAlignment(SwingConstants.CENTER);

		lblHablanosDeT.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		lblHablanosDeT.setBounds(488, 177, 258, 14);
		registroPane.add(lblHablanosDeT);

		JLabel lblAtras = new JLabel(Messages.getString("JFramePersonas.55")); //$NON-NLS-1$
		lblAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblAtras.setForeground(new Color(46, 189, 89));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblAtras.setForeground(Color.DARK_GRAY);
			}

			@Override
			public void mousePressed(MouseEvent e) {

				limpiar();

			}
		});
		lblAtras.setHorizontalAlignment(SwingConstants.LEFT);
		lblAtras.setForeground(Color.DARK_GRAY);
		lblAtras.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		lblAtras.setBounds(194, 11, 110, 14);
		registroPane.add(lblAtras);

		JLabel lblCambiarFoto = new JLabel("Cambiar foto"); //$NON-NLS-1$
		lblCambiarFoto.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				lblCambiarFoto.setForeground(new Color(46, 189, 89));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblCambiarFoto.setForeground(Color.DARK_GRAY);
			}

			@Override
			public void mousePressed(MouseEvent e) {

				JFileChooser jfch = new JFileChooser();
				jfch.setAcceptAllFileFilterUsed(false);
				jfch.addChoosableFileFilter(new FileNameExtensionFilter(Messages.getString("JFramePersonas.58"), "png")); //$NON-NLS-1$ //$NON-NLS-2$
				try {
					if (jfch.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						foto_path = jfch.getSelectedFile().getAbsolutePath();
						lblFoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(foto_path).getImage()
								.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH)));

					}
				} catch (HeadlessException ex) {

					lblFoto.setIcon(new ImageIcon(
							new javax.swing.ImageIcon(getClass().getResource(DEFAULT_FOTO_PATH)).getImage()
									.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH)));

				}
			}

		});
		lblCambiarFoto.setHorizontalAlignment(SwingConstants.CENTER);

		lblCambiarFoto.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$
		lblCambiarFoto.setBounds(413, 152, 110, 14);
		registroPane.add(lblCambiarFoto);

		btnBorrar = new JButton(Messages.getString("JFramePersonas.61")); //$NON-NLS-1$
		btnBorrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Usuario user = listPersonas.getSelectedValue();

				Component frame = null;
				if (user != null) {
					if (user.getUuid().equals(CurrentSession.getInstancia().getUser().getUuid())) {

						JOptionPane.showMessageDialog(frame, Messages.getString("JFramePersonas.62"), //$NON-NLS-1$
								Messages.getString("JFramePersonas.63"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$

					} else {
						String mensage = Messages.getString("JFramePersonas.64") + user.getEmail() + Messages.getString("JFramePersonas.65"); //$NON-NLS-1$ //$NON-NLS-2$
						Object[] options = { Messages.getString("JFramePersonas.66"), Messages.getString("JFramePersonas.67") }; //$NON-NLS-1$ //$NON-NLS-2$

						int n = JOptionPane.showOptionDialog(frame, mensage, Messages.getString("JFramePersonas.68"), JOptionPane.YES_NO_OPTION, //$NON-NLS-1$
								JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

						if (n == JOptionPane.YES_OPTION) {
							GestorUsuarios.getInstancia("").borrarUsuario(user); //$NON-NLS-1$
							listPersonas.setModel(GestorUsuarios.getInstancia("").getDefaultList()); //$NON-NLS-1$
							limpiar();
						}

					}

				}

			}
		});
		btnBorrar.setEnabled(false);

		btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$

		btnBorrar.setBounds(488, 421, 129, 30);
		registroPane.add(btnBorrar);

		listPersonas = new JList(GestorUsuarios.getInstancia("").getDefaultList()); //$NON-NLS-1$

		listPersonas.setFont(new Font("Tahoma", Font.BOLD, 11)); //$NON-NLS-1$

		listPersonas.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				lblAviso.setVisible(false);
				Usuario user = listPersonas.getSelectedValue();
				if (user != null) {
					passwordField.setText(user.getPass_hash());
					passwordField_2.setText(user.getPass_hash());
					emailField.setText(user.getEmail());

					File f = new File(user.getFoto());
					if (f.exists() && !f.isDirectory()) {
						lblFoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(user.getFoto()).getImage()
								.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH)));
					} else {

						lblFoto.setIcon(
								new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(DEFAULT_FOTO_PATH))
										.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
												Image.SCALE_SMOOTH)));
					}

					nombreField.setText(user.getNombre());
					apellidosField.setText(user.getApellidos());
					rolField.setText(user.getRol());

					contactoField.setText(user.getContacto());
					foto_path = user.getFoto();
					textDescripcion.setText(user.getDescripcion());
					aplicar = true;
					btnRegistrarse.setText(Messages.getString("JFramePersonas.74")); //$NON-NLS-1$
					btnBorrar.setEnabled(true);
				}

			}
		});
		listPersonas.setBounds(0, 0, 171, 471);

		registroPane.add(listPersonas);

		JScrollPane scrollPane = new JScrollPane(listPersonas);
		scrollPane.setBounds(0, 0, 171, 471);
		registroPane.add(scrollPane);
	}

	public void limpiar() {

		aplicar = false;
		btnRegistrarse.setText(Messages.getString("JFramePersonas.75")); //$NON-NLS-1$
		btnBorrar.setEnabled(false);
		passwordField.setText(""); //$NON-NLS-1$
		passwordField_2.setText(""); //$NON-NLS-1$
		emailField.setText(""); //$NON-NLS-1$
		foto_path = DEFAULT_FOTO_PATH;
		lblFoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(foto_path)).getImage()
				.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH)));

		nombreField.setText(""); //$NON-NLS-1$
		apellidosField.setText(""); //$NON-NLS-1$
		rolField.setText(""); //$NON-NLS-1$

		contactoField.setText(""); //$NON-NLS-1$

		textDescripcion.setText(""); //$NON-NLS-1$
		listPersonas.clearSelection();
	}
}
