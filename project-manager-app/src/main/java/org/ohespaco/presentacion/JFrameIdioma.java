package org.ohespaco.presentacion;

import static javax.swing.UIManager.setLookAndFeel;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.ohespaco.dominio.GestorEquipo;
import org.ohespaco.dominio.GestorMensajes;
import org.ohespaco.dominio.GestorMiembrosTareas;
import org.ohespaco.dominio.GestorProyectos;
import org.ohespaco.dominio.GestorTareas;
import org.ohespaco.dominio.GestorUsuarios;
import org.ohespaco.persistencia.CurrentSession;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;

public class JFrameIdioma extends JFrame {

	private JPanel contentPane;
	private JTextField txtSeleccinDeIdioma;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrameIdioma frame = new JFrameIdioma();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JFrameIdioma() {
		try {
			setLookAndFeel(new SubstanceGraphiteLookAndFeel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 349, 266);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Inglés");
		btnNewButton.setToolTipText("English");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Messages.setIdioma("en");
				// Inicializa la sesion actual y los gestores
				CurrentSession.getInstancia();
				GestorUsuarios.getInstancia("users.csv");
				GestorProyectos.getInstancia("projects.csv");
				GestorTareas.getInstancia("tasks.csv");
				GestorEquipo.getInstancia("equipo.csv");
				GestorMiembrosTareas.getInstancia("members.csv");
				GestorMensajes.getInstancia("mensajes.csv");
				// Crea y lanza un JFrameMain
				JFrameMain.launch();
				dispose();
			}
		});
		btnNewButton.setBounds(182, 202, 150, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblEs = new JLabel();
		lblEs.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/es.png")).getImage()
				.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
		lblEs.setBounds(10, 41, 150, 150);
		contentPane.add(lblEs);
		
		JLabel lblUk = new JLabel();
		lblUk.setBounds(182, 41, 150, 150);
		lblUk.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/uk.png")).getImage()
				.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
		
		contentPane.add(lblUk);
		
		txtSeleccinDeIdioma = new JTextField();
		txtSeleccinDeIdioma.setToolTipText("Languaje selection");
		txtSeleccinDeIdioma.setText("Selección de idioma");
		txtSeleccinDeIdioma.setEditable(false);
		txtSeleccinDeIdioma.setHorizontalAlignment(SwingConstants.CENTER);
		txtSeleccinDeIdioma.setBounds(0, 0, 343, 30);
		contentPane.add(txtSeleccinDeIdioma);
		txtSeleccinDeIdioma.setColumns(10);
		
		JButton btnEspaol = new JButton("Español");
		btnEspaol.setToolTipText("Spanish");
		btnEspaol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Inicializa la sesion actual y los gestores
				CurrentSession.getInstancia();
				GestorUsuarios.getInstancia("users.csv");
				GestorProyectos.getInstancia("projects.csv");
				GestorTareas.getInstancia("tasks.csv");
				GestorEquipo.getInstancia("equipo.csv");
				GestorMiembrosTareas.getInstancia("members.csv");
				GestorMensajes.getInstancia("mensajes.csv");
				// Crea y lanza un JFrameMain
				JFrameMain.launch();
				dispose();
			}
		});
		btnEspaol.setBounds(10, 202, 150, 23);
		contentPane.add(btnEspaol);
	}
}
