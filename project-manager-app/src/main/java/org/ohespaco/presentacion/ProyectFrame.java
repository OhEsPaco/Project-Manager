package org.ohespaco.presentacion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;
import java.awt.event.ActionEvent;

import org.ohespaco.dominio.GestorProyectos;
import org.ohespaco.dominio.Proyecto;
import javax.swing.JFormattedTextField;

public class ProyectFrame extends JDialog {

	private JPanel ProyectoPanel = new JPanel();
	private JTextField nombreField;
	private JTextField miembrosField;
	private JTextField descripcionField;
	private JTextField responsableField;
	private JLabel lblNombre;
	private JLabel lblMiembros;
	private JLabel lblResponsable;
	private JLabel lblFecha;
	private JLabel lblDescripcion;
	private JLabel lblAviso;
	private JLabel lblInfo;
	private JButton btnBorrar;
	private JButton btnCrear;
	private LocalDate fecha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ProyectFrame dialog = new ProyectFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("deprecation")
	public ProyectFrame() {
		setTitle("Crear Proyecto");
		setBounds(100, 100, 471, 325);
		getContentPane().setLayout(null);
		ProyectoPanel.setBackground(Color.DARK_GRAY);
		ProyectoPanel.setBounds(0, 0, 455, 286);
		ProyectoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(ProyectoPanel);
		ProyectoPanel.setLayout(null);
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setForeground(Color.LIGHT_GRAY);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombre.setBounds(74, 71, 111, 14);
		ProyectoPanel.add(lblNombre);
		
		lblDescripcion = new JLabel("Descripción");
		lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescripcion.setForeground(Color.LIGHT_GRAY);
		lblDescripcion.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDescripcion.setBounds(281, 71, 111, 14);
		ProyectoPanel.add(lblDescripcion);
		
		lblMiembros = new JLabel("Miembros");
		lblMiembros.setHorizontalAlignment(SwingConstants.CENTER);
		lblMiembros.setForeground(Color.LIGHT_GRAY);
		lblMiembros.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMiembros.setBounds(74, 155, 111, 14);
		ProyectoPanel.add(lblMiembros);
		
		lblAviso = new JLabel("El nombre del proyecto ya existe!");
		lblAviso.setVisible(false);
		lblAviso.setHorizontalAlignment(SwingConstants.CENTER);
		lblAviso.setForeground(Color.RED);
		lblAviso.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAviso.setBounds(130, 227, 209, 14);
		
		ProyectoPanel.add(lblAviso);
		
		
		
		lblFecha = new JLabel("");
		
		lblFecha.setForeground(Color.LIGHT_GRAY);
		lblFecha.setBounds(229, 202, 134, 14);
		ProyectoPanel.add(lblFecha);
		
		lblResponsable = new JLabel("Responsable");
		lblResponsable.setHorizontalAlignment(SwingConstants.CENTER);
		lblResponsable.setForeground(Color.LIGHT_GRAY);
		lblResponsable.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblResponsable.setBounds(74, 111, 111, 14);
		ProyectoPanel.add(lblResponsable);
		
		nombreField = new JTextField();
		nombreField.setBounds(37, 86, 169, 20);
		ProyectoPanel.add(nombreField);
		nombreField.setColumns(10);
		
		miembrosField = new JTextField();
		miembrosField.setColumns(10);
		miembrosField.setBounds(37, 171, 169, 20);
		ProyectoPanel.add(miembrosField);
		
		descripcionField = new JTextField();
		descripcionField.setColumns(10);
		descripcionField.setBounds(234, 86, 211, 105);
		ProyectoPanel.add(descripcionField);
		
		responsableField = new JTextField();
		responsableField.setColumns(10);
		responsableField.setBounds(37, 128, 169, 20);
		ProyectoPanel.add(responsableField);
		
		btnBorrar = new JButton("Limpiar Campos");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nombreField.setText(" ");
				miembrosField.setText(" ");
				descripcionField.setText(" ");
				responsableField.setText(" ");
				
				
			}
		});
		
		btnCrear = new JButton("Crear Proyecto");
		btnCrear.setBackground(Color.GREEN);
		btnCrear.setBounds(236, 252, 105, 23);
		ProyectoPanel.add(btnCrear);
		btnBorrar.setBackground(Color.RED);
		btnBorrar.setBounds(130, 252, 107, 23);
		ProyectoPanel.add(btnBorrar);
		
		lblInfo = new JLabel("La fecha de cracion del archivo será:");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setForeground(Color.LIGHT_GRAY);
		lblInfo.setBounds(47, 202, 176, 14);
		ProyectoPanel.add(lblInfo);
	
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fecha = LocalDate.now();
				if (!GestorProyectos.getInstancia("").existeNombre(nombreField.getText()))
					GestorProyectos.getInstancia("").crearProyecto(nombreField.getText(), descripcionField.getText()
							, fecha, responsableField.getText());
			}
		});
	}
}
