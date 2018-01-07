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
import org.ohespaco.dominio.GestorMiembrosTareas;
import org.ohespaco.dominio.GestorUsuarios;
import org.ohespaco.dominio.MiembroTarea;
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
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
public class GestionarPersonasTarea extends JDialog {

	private JPanel contentPane;
	private final String DEFAULT_FOTO_PATH="/org/ohespaco/recursos/user_icon.png";
	private JTextField txtRolEnLa;
	private JLabel lblNombre;
private JTextArea txtrRol;
private JCheckBox chckbxAsignadoAEsta;
private MiembroTarea m_anterior;
	/**
	 * Create the frame.
	 */
	public GestionarPersonasTarea(javax.swing.JFrame parent, boolean modal,String uuid_proyecto,String uuid_tarea) {
		super(parent,modal);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			//	lista.setModel(GestorEquipo.getInstancia("").getMiembrosEquipoProyecto(uuid_proyecto));
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
		setBounds(100, 100, 466, 456);
		
		
	//	setUndecorated(true);
		//setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		contentPane = new JPanel();
	//	contentPane.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 166, 429);
		contentPane.add(scrollPane);
		JLabel lblFoto = new JLabel("");
		JList list = new JList(GestorEquipo.getInstancia("").getMiembrosEquipoProyectoUser(uuid_proyecto));
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if( m_anterior!=null) {
					if(chckbxAsignadoAEsta.isSelected()) {
						m_anterior.setRol(txtrRol.getText());
						GestorMiembrosTareas.getInstancia("").editarAsociacion(m_anterior);
						m_anterior=null;
					}
				}
				//lblAviso.setVisible(false);
				Usuario user = (Usuario) list.getSelectedValue();
				if (user != null) {
					
				
					
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
					
					lblNombre.setText(user.getNombre()+" "+user.getApellidos());
					if(GestorMiembrosTareas.getInstancia("").estaEnTarea(uuid_tarea, user.getUuid())) {
						MiembroTarea t;
						 chckbxAsignadoAEsta.setSelected(true);
						 t=GestorMiembrosTareas.getInstancia("").getMemberByUuid(user.getUuid(), uuid_tarea);
						 txtrRol.setText(t.getRol());
						 m_anterior=t;
						
					}else {
						txtrRol.setText("");
					    chckbxAsignadoAEsta.setSelected(false);
					}
					
					
					//txtrRol.setText(t);
					// lblFoto;

				
					
				}
				
				
				
				
				
			}
		});;
		scrollPane.setViewportView(list);
		
		
		lblFoto.setIcon(
				new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/user_icon.png"))
						.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
		lblFoto.setBounds(231, 11, 150, 150);
		contentPane.add(lblFoto);
		
		 lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(167, 166, 293, 14);
		contentPane.add(lblNombre);
		
	    chckbxAsignadoAEsta = new JCheckBox("Asignado a esta tarea");
	    chckbxAsignadoAEsta.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		Usuario user = (Usuario) list.getSelectedValue();
				//System.out.println("Checked? " + chckbxAsignadoAEsta.isSelected());
				if(user!=null) {
					if(chckbxAsignadoAEsta.isSelected()) {
						//asignar a tarea
						
					GestorMiembrosTareas.getInstancia("").addMiembro(uuid_proyecto, user.getUuid(), uuid_tarea, txtrRol.getText());	
					m_anterior=GestorMiembrosTareas.getInstancia("").getMemberByUuid(user.getUuid(), uuid_tarea);
					System.out.println("aqui2");
					}else {
						MiembroTarea m=GestorMiembrosTareas.getInstancia("").getMemberByUuid(user.getUuid(), uuid_tarea);
						GestorMiembrosTareas.getInstancia("").eliminarAsociacion(m);
						System.out.println("aqui");
					}
				}else {
					
				}
	    		
	    	}
	    });
		chckbxAsignadoAEsta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				
				
				
			}
		});
		chckbxAsignadoAEsta.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxAsignadoAEsta.setBounds(167, 207, 293, 23);
		contentPane.add(chckbxAsignadoAEsta);
		
		txtRolEnLa = new JTextField();
		txtRolEnLa.setEditable(false);
		txtRolEnLa.setText("Rol en la tarea");
		txtRolEnLa.setHorizontalAlignment(SwingConstants.CENTER);
		txtRolEnLa.setBounds(167, 237, 293, 20);
		contentPane.add(txtRolEnLa);
		txtRolEnLa.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(167, 256, 293, 173);
		contentPane.add(scrollPane_1);
		


		txtrRol = new JTextArea();
		txtrRol.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane_1.setViewportView(txtrRol);
	}
}
