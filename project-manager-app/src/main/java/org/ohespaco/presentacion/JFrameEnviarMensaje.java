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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.ohespaco.dominio.GestorMensajes;
import org.ohespaco.dominio.GestorUsuarios;
import org.ohespaco.dominio.Usuario;
import org.ohespaco.persistencia.CurrentSession;

public class JFrameEnviarMensaje extends JDialog {

	private JPanel contentPane;
	private JTextField receptorEmail;
	private JTextField asunto;
	private JTextField txtTextoDelMensaje;

	public JFrameEnviarMensaje(javax.swing.JFrame parent, boolean modal) {
		super(parent, modal);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 609, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 150, 416);
		contentPane.add(scrollPane);

		JList list = new JList(GestorUsuarios.getInstancia("").getDefaultList());
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Usuario user_dest = (Usuario) list.getSelectedValue();
				if (user_dest != null) {
					receptorEmail.setText(user_dest.getEmail());

				}
			}
		});
		scrollPane.setViewportView(list);

		JLabel lblPara = new JLabel("Para: ");
		lblPara.setBounds(160, 11, 72, 25);
		contentPane.add(lblPara);

		receptorEmail = new JTextField();
		receptorEmail.setEditable(false);
		receptorEmail.setBounds(235, 11, 358, 25);
		contentPane.add(receptorEmail);
		receptorEmail.setColumns(10);

		JLabel lblAsunto = new JLabel("Asunto:");
		lblAsunto.setBounds(160, 57, 72, 25);
		contentPane.add(lblAsunto);

		asunto = new JTextField();
		asunto.setColumns(10);
		asunto.setBounds(235, 57, 358, 25);
		contentPane.add(asunto);

		txtTextoDelMensaje = new JTextField();
		txtTextoDelMensaje.setEditable(false);
		txtTextoDelMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		txtTextoDelMensaje.setText("Texto del mensaje");
		txtTextoDelMensaje.setBounds(149, 102, 454, 25);
		contentPane.add(txtTextoDelMensaje);
		txtTextoDelMensaje.setColumns(10);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(149, 126, 454, 249);
		contentPane.add(scrollPane_1);

		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane_1.setViewportView(textArea);

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Usuario user_dest = (Usuario) list.getSelectedValue();

				if (user_dest != null) {
					receptorEmail.setText(user_dest.getEmail());
					if (asunto.getText().matches(".*\\w.*")) {
						if (CurrentSession.getInstancia().getUser().getUuid().equals(user_dest.getUuid())) {
							JOptionPane.showMessageDialog(null, "No te puedes enviar mensajes a ti mismo.", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {

							GestorMensajes.getInstancia("").nuevoMensaje(
									CurrentSession.getInstancia().getUser().getUuid(), user_dest.getUuid(),
									asunto.getText(), textArea.getText());
							JOptionPane.showMessageDialog(null, "Mensaje enviado!.");
						}

					} else {
						JOptionPane.showMessageDialog(null, "El asunto no puede estar vacio.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Tienes que seleccionar un receptor.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnEnviar.setBounds(335, 386, 89, 23);
		contentPane.add(btnEnviar);
	}
}
