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

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.ohespaco.dominio.GestorEquipo;
import org.ohespaco.dominio.GestorMiembrosTareas;
import org.ohespaco.dominio.MiembroTarea;
import org.ohespaco.dominio.Usuario;

public class JFrameGestionarPersonasTarea extends JDialog {

	private JPanel contentPane;
	private final String DEFAULT_FOTO_PATH = "/org/ohespaco/recursos/user_icon.png"; //$NON-NLS-1$
	private JTextField txtRolEnLa;
	private JLabel lblNombre;
	private JTextArea txtrRol;
	private JCheckBox chckbxAsignadoAEsta;
	private MiembroTarea m_anterior;

	public JFrameGestionarPersonasTarea(javax.swing.JFrame parent, boolean modal, String uuid_proyecto,
			String uuid_tarea) {
		super(parent, modal);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			}
		});
		setResizable(false);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 466, 456);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 166, 429);
		contentPane.add(scrollPane);
		JLabel lblFoto = new JLabel(""); //$NON-NLS-1$
		JList list = new JList(GestorEquipo.getInstancia("").getMiembrosEquipoProyectoUser(uuid_proyecto)); //$NON-NLS-1$
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (m_anterior != null) {
					if (chckbxAsignadoAEsta.isSelected()) {
						m_anterior.setRol(txtrRol.getText());
						GestorMiembrosTareas.getInstancia("").editarAsociacion(m_anterior); //$NON-NLS-1$
						m_anterior = null;
					}
				}

				Usuario user = (Usuario) list.getSelectedValue();
				if (user != null) {

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

					lblNombre.setText(user.getNombre() + " " + user.getApellidos()); //$NON-NLS-1$
					if (GestorMiembrosTareas.getInstancia("").estaEnTarea(uuid_tarea, user.getUuid())) { //$NON-NLS-1$
						MiembroTarea t;
						chckbxAsignadoAEsta.setSelected(true);
						t = GestorMiembrosTareas.getInstancia("").getMemberByUuid(user.getUuid(), uuid_tarea); //$NON-NLS-1$
						txtrRol.setText(t.getRol());
						m_anterior = t;

					} else {
						txtrRol.setText(""); //$NON-NLS-1$
						chckbxAsignadoAEsta.setSelected(false);
					}

				}

			}
		});

		scrollPane.setViewportView(list);

		lblFoto.setIcon(
				new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/user_icon.png")) //$NON-NLS-1$
						.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
		lblFoto.setBounds(231, 11, 150, 150);
		contentPane.add(lblFoto);

		lblNombre = new JLabel(Messages.getString("JFrameGestionarPersonasTarea.9")); //$NON-NLS-1$
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(167, 166, 293, 14);
		contentPane.add(lblNombre);

		chckbxAsignadoAEsta = new JCheckBox(Messages.getString("JFrameGestionarPersonasTarea.10")); //$NON-NLS-1$
		chckbxAsignadoAEsta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Usuario user = (Usuario) list.getSelectedValue();
				;
				if (user != null) {
					if (chckbxAsignadoAEsta.isSelected()) {

						GestorMiembrosTareas.getInstancia("").addMiembro(uuid_proyecto, user.getUuid(), uuid_tarea, //$NON-NLS-1$
								txtrRol.getText());
						m_anterior = GestorMiembrosTareas.getInstancia("").getMemberByUuid(user.getUuid(), uuid_tarea); //$NON-NLS-1$
						
					} else {
						MiembroTarea m = GestorMiembrosTareas.getInstancia("").getMemberByUuid(user.getUuid(), //$NON-NLS-1$
								uuid_tarea);
						GestorMiembrosTareas.getInstancia("").eliminarAsociacion(m); //$NON-NLS-1$
					
					}
				} else {

				}

			}
		});
		chckbxAsignadoAEsta.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

			}
		});
		chckbxAsignadoAEsta.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxAsignadoAEsta.setBounds(167, 207, 293, 23);
		contentPane.add(chckbxAsignadoAEsta);

		txtRolEnLa = new JTextField();
		txtRolEnLa.setEditable(false);
		txtRolEnLa.setText(Messages.getString("JFrameGestionarPersonasTarea.15")); //$NON-NLS-1$
		txtRolEnLa.setHorizontalAlignment(SwingConstants.CENTER);
		txtRolEnLa.setBounds(167, 237, 293, 20);
		contentPane.add(txtRolEnLa);
		txtRolEnLa.setColumns(10);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(167, 256, 293, 173);
		contentPane.add(scrollPane_1);

		txtrRol = new JTextArea();
		txtrRol.setFont(new Font("Tahoma", Font.PLAIN, 11)); //$NON-NLS-1$
		scrollPane_1.setViewportView(txtrRol);
	}
}
