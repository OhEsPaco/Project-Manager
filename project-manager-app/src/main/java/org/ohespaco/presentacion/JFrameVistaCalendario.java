package org.ohespaco.presentacion;

import static javax.swing.UIManager.setLookAndFeel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.ohespaco.dominio.GestorTareas;
import org.ohespaco.dominio.Tarea;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;

public class JFrameVistaCalendario extends JDialog {

	private JPanel contentPane;
	private ArrayList<JTextArea> dias;
	private Calendar c = Calendar.getInstance();
	private int year_selected = Calendar.getInstance().get(Calendar.YEAR);
	private int mes_selected = Calendar.getInstance().get(Calendar.MONTH) + 1;
	private JLabel lblLblmes;
	private String project;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void launch() { EventQueue.invokeLater(new Runnable() {
	 * 
	 * @Override public void run() { try { JFrameVistaCalendario frame = new
	 * JFrameVistaCalendario(""); //$NON-NLS-1$ frame.setVisible(true); } catch
	 * (Exception e) { e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the frame.
	 * 
	 * 
	 */

	public JFrameVistaCalendario(javax.swing.JFrame parent, boolean modal, String project) {
		super(parent, modal);

		this.project = project;
		try {
			setLookAndFeel(new SubstanceGraphiteLookAndFeel());
		} catch (Exception e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 695, 547);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblLunes = new JLabel(Messages.getString("JFrameVistaCalendario.1")); //$NON-NLS-1$
		lblLunes.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblLunes);

		JLabel lblMartes = new JLabel(Messages.getString("JFrameVistaCalendario.2")); //$NON-NLS-1$
		lblMartes.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblMartes);

		JLabel lblMiercoles = new JLabel(Messages.getString("JFrameVistaCalendario.3")); //$NON-NLS-1$
		lblMiercoles.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblMiercoles);

		JLabel lblJueves = new JLabel(Messages.getString("JFrameVistaCalendario.4")); //$NON-NLS-1$
		lblJueves.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblJueves);

		JLabel lblViernes = new JLabel(Messages.getString("JFrameVistaCalendario.5")); //$NON-NLS-1$
		lblViernes.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblViernes);

		JLabel lblSabado = new JLabel(Messages.getString("JFrameVistaCalendario.6")); //$NON-NLS-1$
		lblSabado.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblSabado);

		JLabel lblDomingo = new JLabel(Messages.getString("JFrameVistaCalendario.7")); //$NON-NLS-1$
		lblDomingo.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblDomingo);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(5, 7, 0, 0));

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JButton btnAnterior = new JButton(Messages.getString("JFrameVistaCalendario.8")); //$NON-NLS-1$
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
				mes_selected--;
				if (mes_selected < 1) {
					mes_selected = 12;
					year_selected--;
				}
				if (mes_selected < 10) {
					lblLblmes.setText("0" + mes_selected + "-" + year_selected); //$NON-NLS-1$
				} else {
					lblLblmes.setText(mes_selected + "-" + year_selected); //$NON-NLS-1$
				}
				cargarCalendario(year_selected, mes_selected);

			}
		});
		panel_2.add(btnAnterior);

		lblLblmes = new JLabel(""); //$NON-NLS-1$

		lblLblmes.setFont(new Font("Tahoma", Font.BOLD, 17)); //$NON-NLS-1$
		panel_2.add(lblLblmes);
		if (mes_selected < 10) {
			lblLblmes.setText("0" + mes_selected + "-" + year_selected); //$NON-NLS-1$
		} else {
			lblLblmes.setText(mes_selected + "-" + year_selected); //$NON-NLS-1$
		}

		JButton btnSiguiente = new JButton(Messages.getString("JFrameVistaCalendario.13")); //$NON-NLS-1$
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
				mes_selected++;
				if (mes_selected > 12) {
					mes_selected = 1;
					year_selected++;
				}
				if (mes_selected < 10) {
					lblLblmes.setText("0" + mes_selected + "-" + year_selected); //$NON-NLS-1$
				} else {
					lblLblmes.setText(mes_selected + "-" + year_selected); //$NON-NLS-1$
				}
				cargarCalendario(year_selected, mes_selected);

			}
		});
		panel_2.add(btnSiguiente);

		Calendar cal = Calendar.getInstance();

		dias = new ArrayList<JTextArea>();
		JScrollPane scrollPane_1;
		JTextArea Dia;
		for (int i = 0; i < 35; i++) {
			scrollPane_1 = new JScrollPane();
			Dia = new JTextArea();

			Dia.setEditable(false);
			Dia.setBackground(Color.LIGHT_GRAY);
			dias.add(Dia);
			scrollPane_1.add(Dia);
			scrollPane_1.setViewportView(Dia);
			panel_1.add(scrollPane_1);
		}
		cargarCalendario(year_selected, mes_selected);

	}

	private void limpiar() {
		for (int i = 0; i < 35; i++) {

			JTextArea aux = dias.get(i);
			aux.setBackground(Color.LIGHT_GRAY);
			aux.setText(""); //$NON-NLS-1$

		}
		lblLblmes.setText(""); //$NON-NLS-1$
	}

	private void cargarCalendario(int year, int mes) {

		int mes_aux = mes - 1;

		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, mes_aux);
		c.set(Calendar.DAY_OF_MONTH, 1);
		int inicio = diaSemana(c.get(Calendar.DAY_OF_WEEK));
		int diaSemana = inicio + 1;

		ConcurrentHashMap<String, Tarea> ht_tareas = GestorTareas.getInstancia("").getTareas(); //$NON-NLS-1$

		Tarea task;
		int j = 0;
		for (int i = inicio; i < 35; i++, j++) {
			JTextArea Dia = dias.get(i);
			boolean color = false;
			boolean color_fin = false;
			if (j < c.getActualMaximum(Calendar.DAY_OF_MONTH)) {
				String salida;
				Color bg = UIManager.getColor("Panel.background");
				Dia.setBackground(bg);
				salida = Integer.toString(j + 1) + "\n"; //$NON-NLS-1$

				

					for (String key : ht_tareas.keySet()) {
						task = ht_tareas.get(key);

						Date date = task.getFecha_creacion();
						LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

						int day = localDate.getDayOfMonth();
						int month = localDate.getMonth().getValue();
						int t_year = localDate.getYear();

						if (day == j + 1 && month == mes && year == t_year && task.getUuid_proyecto().equals(project)) {
							salida = salida + Messages.getString("JFrameVistaCalendario.19") + task.getNombre() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
							color = true;
						}
						date = task.getFecha_fin();
						localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						day = localDate.getDayOfMonth();
						month = localDate.getMonth().getValue();
						if (day == j + 1 && month == mes && year == t_year && task.getUuid_proyecto().equals(project)) {
							salida = salida + Messages.getString("JFrameVistaCalendario.21") + task.getNombre() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
							color_fin = true;
						}
					}
					Dia.setText(salida);
					if (color == true) {

						Dia.setBackground(new Color(60, 179, 113));
					}
					if (color_fin == true) {
						Dia.setBackground(Color.RED);
					}
					if (color == true && color_fin == true) {
						Dia.setBackground(Color.BLUE);
					}

					color_fin = false;
					color = false;

				

			}

			diaSemana++;
			if (diaSemana > 7) {
				diaSemana = 1;
			}

		}

		// Dia.setBackground(Color.RED);

	}

	private int diaSemana(int dia) {
		int inicio = 0;
		switch (dia) {
		case Calendar.MONDAY:
			inicio = 0;
			break;
		case Calendar.TUESDAY:
			inicio = 1;
			break;
		case Calendar.WEDNESDAY:
			inicio = 2;
			break;
		case Calendar.THURSDAY:
			inicio = 3;
			break;
		case Calendar.FRIDAY:
			inicio = 4;
			break;
		case Calendar.SATURDAY:
			inicio = 5;
			break;
		case Calendar.SUNDAY:
			inicio = 6;
			break;

		}
		return inicio;
	}

}
