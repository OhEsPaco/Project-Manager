package org.ohespaco.presentacion;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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

public class Login extends JFrame {

	private JPanel contentPane;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JPasswordField passwordField;
	private JTextField textField;
	private JLabel lblUsuario;
	private JLabel lblContrasea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : getInstalledLookAndFeels()) {
	               if ("Windows".equals(info.getName())) {
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
	public Login() {
		setTitle("Project Manager");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 215, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnNewButton = new JButton("Entrar");
		btnNewButton.setBounds(109, 130, 89, 23);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Registro");
		btnNewButton_1.setBounds(10, 130, 89, 23);
		contentPane.add(btnNewButton_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 92, 188, 20);
		contentPane.add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(10, 43, 188, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(91, 24, 46, 14);
		contentPane.add(lblUsuario);
		
		lblContrasea = new JLabel("Contraseña");
		lblContrasea.setBounds(83, 75, 67, 14);
		contentPane.add(lblContrasea);
	}
}
