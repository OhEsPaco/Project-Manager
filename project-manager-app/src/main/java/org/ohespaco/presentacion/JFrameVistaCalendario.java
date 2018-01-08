package org.ohespaco.presentacion;

import static javax.swing.UIManager.setLookAndFeel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.ohespaco.dominio.GestorTareas;
import org.ohespaco.dominio.Tarea;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;

import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

public class JFrameVistaCalendario extends JFrame {

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
	public static void launch() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					JFrameVistaCalendario frame = new JFrameVistaCalendario("");
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
	public JFrameVistaCalendario(String project) {
		this.project = project;
		try {
			setLookAndFeel(new SubstanceGraphiteLookAndFeel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		GestorTareas.getInstancia("tasks.csv");
		// System.out.println(mes_selected);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 695, 547);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblLunes = new JLabel("Lunes");
		lblLunes.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblLunes);

		JLabel lblMartes = new JLabel("Martes");
		lblMartes.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblMartes);

		JLabel lblMiercoles = new JLabel("Miercoles");
		lblMiercoles.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblMiercoles);

		JLabel lblJueves = new JLabel("Jueves");
		lblJueves.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblJueves);

		JLabel lblViernes = new JLabel("Viernes");
		lblViernes.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblViernes);

		JLabel lblSabado = new JLabel("Sabado");
		lblSabado.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblSabado);

		JLabel lblDomingo = new JLabel("Domingo");
		lblDomingo.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblDomingo);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(5, 7, 0, 0));

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JButton btnAnterior = new JButton("Anterior");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
				mes_selected--;
				if (mes_selected < 1) {
					mes_selected = 12;
					year_selected--;
				}

				cargarCalendario(year_selected, mes_selected);
				lblLblmes.setText(mes_selected + "-" + year_selected);
			}
		});
		panel_2.add(btnAnterior);

		lblLblmes = new JLabel("");
		lblLblmes.setFont(new Font("Tahoma", Font.BOLD, 17));
		panel_2.add(lblLblmes);
		lblLblmes.setText(mes_selected + "-" + year_selected);

		JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
				mes_selected++;
				if (mes_selected > 12) {
					mes_selected = 1;
					year_selected++;
				}

				cargarCalendario(year_selected, mes_selected);
				lblLblmes.setText(mes_selected + "-" + year_selected);
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
			aux.setText("");
		}
		lblLblmes.setText("");
	}

	private void cargarCalendario(int year, int mes) {

		int mes_aux = mes - 1;

		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, mes_aux);
		c.set(Calendar.DAY_OF_MONTH, 1);
		int inicio = diaSemana(c.get(Calendar.DAY_OF_WEEK));
		int diaSemana = inicio + 1;

		ConcurrentHashMap<String, Tarea> ht_tareas = GestorTareas.getInstancia("").getTareas();

		Tarea task;
		int j = 0;
		for (int i = inicio; i < 35; i++, j++) {
			JTextArea Dia = dias.get(i);

			if (j < c.getActualMaximum(Calendar.DAY_OF_MONTH)) {
				String salida;

				salida = Integer.toString(j + 1) + "\n";

				if (!ht_tareas.isEmpty()) {

					for (String key : ht_tareas.keySet()) {
						task = ht_tareas.get(key);

						Date date = task.getFecha_creacion();
						LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

						int day = localDate.getDayOfMonth();
						int month = localDate.getMonth().getValue();
						int t_year = localDate.getYear();

						if (day == j + 1 && month == mes && year == t_year && task.getUuid_proyecto().equals(project)) {
							salida = salida + "Inicio: " + task.getNombre() + "\n";
						}
						date = task.getFecha_fin();
						localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						day = localDate.getDayOfMonth();
						month = localDate.getMonth().getValue();
						if (day == j + 1 && month == mes && year == t_year && task.getUuid_proyecto().equals(project)) {
							salida = salida + "Fin: " + task.getNombre() + "\n";
						}
					}
				}
				Dia.setText(salida);
				diaSemana++;
				if (diaSemana > 7) {
					diaSemana = 1;
				}

			} else {
				break;
			}
		}

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
