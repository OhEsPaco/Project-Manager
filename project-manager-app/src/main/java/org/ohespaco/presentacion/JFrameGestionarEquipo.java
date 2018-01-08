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

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.ohespaco.dominio.GestorEquipo;
import org.ohespaco.dominio.GestorUsuarios;
import org.ohespaco.dominio.Usuario;

public class JFrameGestionarEquipo extends JDialog {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField apellidosField;
	private JTextField emailField;
	private final String DEFAULT_FOTO_PATH = "/org/ohespaco/recursos/user_icon.png"; //$NON-NLS-1$

	public JFrameGestionarEquipo(javax.swing.JFrame parent, boolean modal, String uuid_proyecto, JList lista) {
		super(parent, modal);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				lista.setModel(GestorEquipo.getInstancia("").getMiembrosEquipoProyecto(uuid_proyecto)); //$NON-NLS-1$
			}
		});
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 466, 392);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 166, 366);
		contentPane.add(scrollPane);
		JLabel lblFoto = new JLabel(""); //$NON-NLS-1$
		JList list = new JList(GestorUsuarios.getInstancia("").getDefaultList()); //$NON-NLS-1$
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				// lblAviso.setVisible(false);
				Usuario user = (Usuario) list.getSelectedValue();
				if (user != null) {

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

					txtNombre.setText(user.getNombre());
					apellidosField.setText(user.getApellidos());

				}

			}
		});
		;
		scrollPane.setViewportView(list);

		lblFoto.setIcon(
				new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/user_icon.png")) //$NON-NLS-1$
						.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
		lblFoto.setBounds(231, 11, 150, 150);
		contentPane.add(lblFoto);

		JLabel lblNombre = new JLabel(Messages.getString("JFrameGestionarEquipo.5")); //$NON-NLS-1$
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(186, 180, 252, 14);
		contentPane.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombre.setEditable(false);
		txtNombre.setBounds(186, 193, 252, 25);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);

		JLabel apellidosLabel = new JLabel(Messages.getString("JFrameGestionarEquipo.6")); //$NON-NLS-1$
		apellidosLabel.setHorizontalAlignment(SwingConstants.CENTER);
		apellidosLabel.setBounds(186, 228, 252, 14);
		contentPane.add(apellidosLabel);

		apellidosField = new JTextField();
		apellidosField.setHorizontalAlignment(SwingConstants.CENTER);
		apellidosField.setEditable(false);
		apellidosField.setColumns(10);
		apellidosField.setBounds(186, 241, 252, 25);
		contentPane.add(apellidosField);

		JLabel lblEmail = new JLabel(Messages.getString("JFrameGestionarEquipo.7")); //$NON-NLS-1$
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(186, 274, 252, 14);
		contentPane.add(lblEmail);

		emailField = new JTextField();
		emailField.setHorizontalAlignment(SwingConstants.CENTER);
		emailField.setEditable(false);
		emailField.setColumns(10);
		emailField.setBounds(186, 287, 252, 25);
		contentPane.add(emailField);

		JButton btnAadirPersonaA = new JButton(Messages.getString("JFrameGestionarEquipo.8")); //$NON-NLS-1$
		btnAadirPersonaA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Usuario user = (Usuario) list.getSelectedValue();
				if (user != null) {
					if (!GestorEquipo.getInstancia("").estaEnProyecto(uuid_proyecto, user.getUuid())) { //$NON-NLS-1$
						GestorEquipo.getInstancia("").addMiembro(uuid_proyecto, user.getUuid(), Messages.getString("JFrameGestionarEquipo.11")); //$NON-NLS-1$ //$NON-NLS-2$
						JOptionPane.showMessageDialog(null, Messages.getString("JFrameGestionarEquipo.12")); //$NON-NLS-1$
					} else {

						JOptionPane.showMessageDialog(null, Messages.getString("JFrameGestionarEquipo.13"), Messages.getString("JFrameGestionarEquipo.14"), //$NON-NLS-1$ //$NON-NLS-2$
								JOptionPane.ERROR_MESSAGE);

					}

				} else {

				}

			}
		});
		btnAadirPersonaA.setBounds(186, 329, 252, 25);
		contentPane.add(btnAadirPersonaA);
	}
}
