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

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.ohespaco.dominio.GestorUsuarios;
import org.ohespaco.persistencia.CurrentSession;

import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;
import java.util.logging.Level;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class Login extends JFrame {

	private JPanel contentPane;
	private JButton btnEntrar;
	private JPasswordField passwordField;
	private JTextField emailField;
	private JLabel lblEmail;
	private JLabel lblContrasea;
	private JLabel lblNewLabel;
	private JLabel lblAviso;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Login() {
	
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/org/ohespaco/recursos/noicon.png")));
		 try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : getInstalledLookAndFeels()) {
	               if ("Nimbus".equals(info.getName())) {
	                    setLookAndFeel(info.getClassName());
	                    break;
	                }
	              
	            }
	            //setLookAndFeel("Nimbus");
	            
	        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
	            getLogger(Login.class.getName()).log(SEVERE, null, ex);
	        }
		
		setTitle("Project Manager");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 310, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(46, 47, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnEntrar = new JButton("Entrar");
		btnEntrar.setForeground(Color.WHITE);
		btnEntrar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEntrar.setBackground(new Color(46, 189, 89));
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CurrentSession sesion = CurrentSession.getInstancia();
				GestorUsuarios gestor = new GestorUsuarios(sesion.getPathCsvUsers());
				try {
					gestor.cargarUsuarios();
					if(gestor.login(emailField.getText(), new String(passwordField.getPassword()))) {
						MainWindow main=new MainWindow();
						main.setVisible(true);
						dispose();
						
					}else {
						lblAviso.setVisible(true);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEntrar.setBounds(23, 333, 258, 30);
		contentPane.add(btnEntrar);
		
		passwordField = new JPasswordField();
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setBounds(23, 278, 258, 30);
		contentPane.add(passwordField);
		
		emailField = new JTextField();
		emailField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAviso.setVisible(false);
			}
		});
		emailField.setBounds(23, 212, 258, 30);
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setForeground(Color.LIGHT_GRAY);
		lblEmail.setBounds(23, 195, 258, 14);
		contentPane.add(lblEmail);
		
		lblContrasea = new JLabel("Contraseña");
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasea.setForeground(Color.LIGHT_GRAY);
		lblContrasea.setBounds(23, 261, 258, 14);
		contentPane.add(lblContrasea);
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(90, 64, 120, 120);
		lblNewLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/logo.png")).getImage().getScaledInstance(lblNewLabel.getWidth(),  lblNewLabel.getHeight(), Image.SCALE_SMOOTH)));

		
		contentPane.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(Color.GRAY);
		comboBox.setForeground(Color.LIGHT_GRAY);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Castellano", "Inglés"}));
		comboBox.setBounds(142, 11, 139, 24);
		contentPane.add(comboBox);
		
		JLabel lblRegistro = new JLabel("¿Aún no tienes cuenta? Registrate");
		lblRegistro.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				/////////////////////////////////////////////////////////////
				//PULSACION DE REGISTRARSE
				///////////////////////////////////////////////////////////////
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblRegistro.setForeground(new Color(46, 189, 89));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblRegistro.setForeground(Color.LIGHT_GRAY);
			}
		});
		lblRegistro.setForeground(Color.LIGHT_GRAY);
		lblRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistro.setBounds(23, 385, 258, 14);
		contentPane.add(lblRegistro);
		
		lblAviso = new JLabel("Email o contraseña incorrectos");
		lblAviso.setForeground(Color.RED);
		lblAviso.setHorizontalAlignment(SwingConstants.CENTER);
		lblAviso.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAviso.setBounds(23, 314, 258, 14);
		lblAviso.setVisible(false);
		contentPane.add(lblAviso);
	}
}
