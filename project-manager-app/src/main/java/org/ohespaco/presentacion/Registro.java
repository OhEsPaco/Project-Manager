package org.ohespaco.presentacion;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;
import java.util.logging.Level;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Registro extends JFrame {

	private JPanel contentPane;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JPasswordField passwordField;
	private JTextField textField;
	private JLabel lblUsuario;
	private JLabel lblContrasea;
	private JLabel lblNewLabel;
	private JPasswordField passwordField_1;
	private JLabel lblRepetirContrasea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : getInstalledLookAndFeels()) {
	               if ("Nimbus".equals(info.getName())) {
	                    setLookAndFeel(info.getClassName());
	                    break;
	                }
	               System.out.println(info.getName());
	            }
	            //setLookAndFeel("Nimbus");
	            
	        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
	            getLogger(Login.class.getName()).log(SEVERE, null, ex);
	        }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Registro() {
		setTitle("Project Manager");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 309, 389);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnNewButton = new JButton("Atras");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login m=new Login();
				m.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(23, 314, 123, 23);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Registrarse");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Login m=new Login();
				m.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(158, 314, 123, 23);
		contentPane.add(btnNewButton_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(23, 231, 258, 20);
		contentPane.add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(23, 179, 258, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(126, 161, 67, 14);
		contentPane.add(lblUsuario);
		
		lblContrasea = new JLabel("Contraseña");
		lblContrasea.setBounds(115, 214, 83, 14);
		contentPane.add(lblContrasea);
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(87, 29, 122, 121);
		lblNewLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/project-manager.png")).getImage().getScaledInstance(lblNewLabel.getWidth(),  lblNewLabel.getHeight(), Image.SCALE_SMOOTH)));

		
		contentPane.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Castellano", "Inglés"}));
		comboBox.setBounds(23, 0, 139, 24);
		contentPane.add(comboBox);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(23, 283, 258, 20);
		contentPane.add(passwordField_1);
		
		lblRepetirContrasea = new JLabel("Repetir contraseña");
		lblRepetirContrasea.setBounds(98, 262, 111, 14);
		contentPane.add(lblRepetirContrasea);
	}
}
