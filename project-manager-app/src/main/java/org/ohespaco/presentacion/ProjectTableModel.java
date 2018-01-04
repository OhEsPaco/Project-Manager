package org.ohespaco.presentacion;

import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.ohespaco.dominio.Proyecto;
import org.ohespaco.dominio.Usuario;

public class ProjectTableModel extends JTable {

	private DefaultListModel<Usuario> listaUsuarios = null;

	public ProjectTableModel(DefaultListModel<Usuario> listaUsuarios) {
		super();
		this.listaUsuarios = listaUsuarios;
	}

	@Override
	public int getRowCount() {
		int size = 0;
		if (listaUsuarios != null) {
			size = listaUsuarios.getSize();
		}
		return size;
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object temp = null;
		if (listaUsuarios != null) {
			if (listaUsuarios.getSize() != 0) {
				temp = listaUsuarios.get(rowIndex);
			}
		}
		return temp;
	}

}
