package org.ohespaco.presentacion;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class JDialogAcercaDe extends JDialog {

	private JPanel contentPane;

	public JDialogAcercaDe(javax.swing.JFrame parent, boolean modal) {
		super(parent, modal);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 544, 351);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(188, 11, 150, 150);
		lblLogo.setIcon(
				new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/org/ohespaco/recursos/logo.png")) //$NON-NLS-1$
						.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));

		panel.add(lblLogo);

		JLabel lblProjectManager = new JLabel("Project Manager");
		lblProjectManager.setHorizontalAlignment(SwingConstants.CENTER);
		lblProjectManager.setBounds(10, 172, 498, 20);
		panel.add(lblProjectManager);

		JLabel lblFranciscoManuelGarca = new JLabel("Francisco Manuel García Sánchez-Belmonte");
		lblFranciscoManuelGarca.setHorizontalAlignment(SwingConstants.CENTER);
		lblFranciscoManuelGarca.setBounds(0, 203, 518, 20);
		panel.add(lblFranciscoManuelGarca);

		JLabel lblAdrinBustosMarn = new JLabel("Adrián Bustos Marín");
		lblAdrinBustosMarn.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdrinBustosMarn.setBounds(0, 234, 518, 20);
		panel.add(lblAdrinBustosMarn);

		JLabel label = new JLabel("2018");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 265, 518, 20);
		panel.add(label);

		JLabel lblV = new JLabel("V1.2");
		lblV.setBounds(10, 11, 46, 14);
		panel.add(lblV);
	}

}
