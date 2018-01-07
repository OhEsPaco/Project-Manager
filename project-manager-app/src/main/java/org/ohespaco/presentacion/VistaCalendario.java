package org.ohespaco.presentacion;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
		Noseque();

	}
	
	
	/*public void rellenoTabla() {
		JOptionPane.showMessageDialog(null, "LLEGO AQUI " + lista_calendario.getSize());				

		Calendar cal;
		cal = DateToCalendar(task.getFecha_creacion());
		

		//Iterator<Tarea> it =  lista_calendario.iterator();
		
		if(lista_calendario.getSize() == 0) {
			JOptionPane.showMessageDialog(null, "No hay tareas Disponibles para esta fecha");
		}else {

			for (int i = 0; i<lista_calendario.getSize();i++) {
				JOptionPane.showMessageDialog(null, "LLEGO AQUI");				
				day = cal.DAY_OF_MONTH;
				JOptionPane.showMessageDialog(null, "Operación realizada correctamente");

				if (day <= 7) {
					calendario.setValueAt(("tarea "+ i), 3, day -1);
					//tabl[3][day-1] = task.getNombre();	
					JOptionPane.showMessageDialog(null, "Operación realizada correctamente");

				}else if (day <= 14) {
					calendario.setValueAt(("tarea "+ i), 4, day -1);

					//calendario.setValueAt(task.getNombre(), 4, day-1);
					//tabl[4][day-1] = task.getNombre();
					JOptionPane.showMessageDialog(null, "Operación realizada correctamente");

				}else if (day <= 21) {
					calendario.setValueAt(("tarea "+ i), 5, day -1);

					//calendario.setValueAt(task.getNombre(), 5, day-1);

					//tabl[5][day-1] = task.getNombre();
					JOptionPane.showMessageDialog(null, "Operación realizada correctamente");

				}else if (day<= 28) {
					calendario.setValueAt(("tarea "+ i), 6, day -1);

					//calendario.setValueAt(task.getNombre(), 6, day-1);
					JOptionPane.showMessageDialog(null, "Operación realizada correctamente");


					//tabl[6][day-1] = task.getNombre();
				}else {
					calendario.setValueAt(("tarea "+ i), 3, day -1);

					//calendario.setValueAt(task.getNombre(), 7, day-1);
					JOptionPane.showMessageDialog(null, "Operación realizada correctamente");


					//tabl[7][day-1] = task.getNombre();

				}
			}
		}
	}*/
			
	public void Noseque() {

		Calendar cal;
	String[] meses = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo",
            "Junio","Julio","Agosto","Septiembre",
            "Octubre", "Noviembre", "Diciembre"
        };				        
        String resp = (String) JOptionPane.showInputDialog(null, "Seleccione un mes para ver las tareas correspondientes",
        		"mes", JOptionPane.DEFAULT_OPTION, null, meses, meses[0]);				        
        String anio = JOptionPane.showInputDialog(null, "Escriba el año del que desa mostrar las tareas", "Seleccione el año", JOptionPane.INFORMATION_MESSAGE);
        month = (pos(meses, resp))+1;
        year = Integer.parseInt(anio);
        lista_calendario = GestorTareas.getInstancia("").tareasCalendario(month, year);
        
        
        
        if(lista_calendario.getSize() == 0) {
			JOptionPane.showMessageDialog(null, "No hay tareas Disponibles para esta fecha");
		}else {

			for (int i = 0; i<lista_calendario.getSize();i++) {
				task = lista_calendario.getElementAt(i);
				cal =  dateToCalendar(task.getFecha_creacion());
				JOptionPane.showMessageDialog(null, "LLEGO AQUI");				
				day = cal.DAY_OF_MONTH;
				JOptionPane.showMessageDialog(null, "Operación realizada correctamente");

				if (day <= 7) {
					calendario.setValueAt(task.getNombre(), 3, day -1);
					//tabl[3][day-1] = task.getNombre();	
					JOptionPane.showMessageDialog(null, "Operación realizada correctamente");

				}else if (day <= 14) {
					calendario.setValueAt(task.getNombre(), 4, day -1);

					//calendario.setValueAt(task.getNombre(), 4, day-1);
					//tabl[4][day-1] = task.getNombre();
				}else if (day <= 21) {
					calendario.setValueAt(task.getNombre(), 5, day -1);

					//calendario.setValueAt(task.getNombre(), 5, day-1);

					//tabl[5][day-1] = task.getNombre();

				}else if (day<= 28) {
					calendario.setValueAt(task.getNombre(), 6, day -1);

					//calendario.setValueAt(task.getNombre(), 6, day-1);


					//tabl[6][day-1] = task.getNombre();
				}else {
					calendario.setValueAt(("tarea "+ i), 3, day -1);

					//calendario.setValueAt(task.getNombre(), 7, day-1);
					JOptionPane.showMessageDialog(null, "Operación realizada correctamente");


					//tabl[7][day-1] = task.getNombre();

				}
			}
		}
        
        
        }
	
	
	public static Calendar dateToCalendar(java.util.Date date ) 
	{ 
	 Calendar cal = null;
	 try {   
	  DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	  date = (Date)formatter.parse(date.toString()); 
	  cal=Calendar.getInstance();
	  cal.setTime(date);
	  }
	  catch (ParseException e)
	  {
	      System.out.println("Exception :"+e);  
	  }  
	  return cal;
	 }
        
        
        
        public int pos (String[] meses, String mes) {
    		int p = 0;
    		for (int i = 0; i < meses.length; i++ ) {
    			if (mes == meses[i]) {
    				p = i;
    			}
    		}
    		return p;
    	}
		
	
	
	

}
