package org.ohespaco.presentacion;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.ohespaco.dominio.GestorTareas;
import org.ohespaco.dominio.Tarea;

import java.awt.Color;

public class VistaCalendario extends JFrame {

	private JPanel contentPane;
	private JTable calendario;
	private int year, month;
	private DefaultListModel<Tarea> lista_calendario;
	private JPanelPrincipal jp = new JPanelPrincipal();
	int day;
	Tarea task = null;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public VistaCalendario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 145);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		calendario = new JTable();
		calendario.setBackground(Color.BLACK);
		calendario.setForeground(Color.GREEN);
		
		
		
		
		/*calendario.setModel(new DefaultTableModel(
				tabla = rellenoTabla()
			,
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		calendario.getColumnModel().getColumn(3).setMinWidth(24);
		calendario.getColumnModel().getColumn(5).setMinWidth(75);
		contentPane.add(calendario, BorderLayout.CENTER);
		
	}*/
		calendario.setModel(new DefaultTableModel(
				new String[][] {
				{"    LUNES", "MARTES", "  MIERCOLES", "      JUEVES", "     VIERNES", "     SABADO", "   DOMINGO"},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		calendario.getColumnModel().getColumn(3).setMinWidth(24);
		calendario.getColumnModel().getColumn(5).setMinWidth(75);
		contentPane.add(calendario, BorderLayout.CENTER);
		rellenoTabla();
	}
	
	
	public void rellenoTabla() {
		lista_calendario = jp.retornarLista();
		for (int i = 0; i<lista_calendario.size();i++) {
			day = task.getFecha_creacion().getDay();
			if (day <= 7) {
				calendario.setValueAt(task.getNombre(), 3, day -1);
				//tabl[3][day-1] = task.getNombre();				
			}else if (day <= 14) {
				calendario.setValueAt(task.getNombre(), 4, day-1);
				//tabl[4][day-1] = task.getNombre();
			}else if (day <= 21) {
				calendario.setValueAt(task.getNombre(), 5, day-1);

				//tabl[5][day-1] = task.getNombre();
			}else if (day<= 28) {
				calendario.setValueAt(task.getNombre(), 6, day-1);

				//tabl[6][day-1] = task.getNombre();
			}else {
				calendario.setValueAt(task.getNombre(), 7, day-1);

				//tabl[7][day-1] = task.getNombre();

			}
		}
		
	}
		
	
	
	

}
