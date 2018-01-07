package org.ohespaco.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.ohespaco.dominio.GestorEquipo;
import org.ohespaco.dominio.GestorUsuarios;
import org.ohespaco.dominio.Usuario;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Window.Type;
import java.io.File;

import static javax.swing.UIManager.setLookAndFeel;
import static javax.swing.UIManager.*;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class GestionarEquipoFrame extends JDialog {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField apellidosField;
	private JTextField emailField;
	private final String DEFAULT_FOTO_PATH="/org/ohespaco/recursos/user_icon.png";
	

	/**
	 * Create the frame.
	 */
	public GestionarEquipoFrame(javax.swing.JFrame parent, boolean modal,String uuid_proyecto,JList lista) {
		super(parent,modal);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				lista.setModel(GestorEquipo.getInstancia("").getMiembrosEquipoProyecto(uuid_proyecto));
			}
		});
		setResizable(false);
		/*try {
			for (javax.swing.UIManager.LookAndFeelInfo info : getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					setLookAndFeel(info.getClassName());
					break;
				}

			}
			// setLookAndFeel("Nimbus");

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| javax.swing.UnsupportedLookAndFeelException ex) {
		//	getLogger(MainFrame.class.getName()).log(SEVERE, null, ex);
		}*/
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 466, 392);
		
		
	//	setUndecorated(true);
		//setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		contentPane = new JPanel();
	//	contentPane.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 166, 366);
		contentPane.add(scrollPane);
		JLabel lblFoto = new JLabel("");
		JList list = new JList(GestorUsuarios.getInstancia("").getDefaultList());
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				//lblAviso.setVisible(false);
				Usuario user = (Usuario) list.getSelectedValue();
				if (user != null) {
					
					emailField.setText(user.getEmail());

					File f = new File(user.getFoto());
					if (f.exists() && !f.isDirectory()) {
						lblFoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(user.getFoto()).getImage()
								.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH)));
					} else {
						
						////////////////////////////////////////////////////////////////////////////////////////////////////////
						lblFoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource(DEFAULT_FOTO_PATH))
								.getImage()
								.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH)));
					}

					// lblFoto;

					txtNombre.setText(user.getNombre());
					apellidosField.setText(user.getApellidos());
					
				}
				
				
				
				
				
			}
		});;
		scrollPane.setViewportView(list);
		
		
		lblFoto.setIcon(
				new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/user_icon.png"))
						.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
		lblFoto.setBounds(231, 11, 150, 150);
		contentPane.add(lblFoto);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(186, 180, 252, 14);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombre.setEditable(false);
		txtNombre.setBounds(186, 193, 252, 25);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel apellidosLabel = new JLabel("Apellidos");
		apellidosLabel.setHorizontalAlignment(SwingConstants.CENTER);
		apellidosLabel.setBounds(186, 228, 252, 14);
		contentPane.add(apellidosLabel);
		
		apellidosField = new JTextField();
		apellidosField.setHorizontalAlignment(SwingConstants.CENTER);
		apellidosField.setEditable(false);
		apellidosField.setColumns(10);
		apellidosField.setBounds(186, 241, 252, 25);
		contentPane.add(apellidosField);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(186, 274, 252, 14);
		contentPane.add(lblEmail);
		
		emailField = new JTextField();
		emailField.setHorizontalAlignment(SwingConstants.CENTER);
		emailField.setEditable(false);
		emailField.setColumns(10);
		emailField.setBounds(186, 287, 252, 25);
		contentPane.add(emailField);
		
		JButton btnAadirPersonaA = new JButton("Añadir persona a proyecto");
		btnAadirPersonaA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario user = (Usuario) list.getSelectedValue();
				if (user != null) {
					if(!GestorEquipo.getInstancia("").estaEnProyecto(uuid_proyecto, user.getUuid())) {
						GestorEquipo.getInstancia("").addMiembro(uuid_proyecto, user.getUuid(), "Rol en el equipo");
						JOptionPane.showMessageDialog(null, "Persona añadida con exito.");
					}else {
						
					JOptionPane.showMessageDialog(null, "Este usuario ya está en este proyecto.", "Error",
							JOptionPane.ERROR_MESSAGE);

				}
				
					
					
				}else {
					
				}
				
				
				//GestorEquipo.getInstancia("").imprimirHash();
				
				
			}
		});
		btnAadirPersonaA.setBounds(186, 329, 252, 25);
		contentPane.add(btnAadirPersonaA);
	}
}
