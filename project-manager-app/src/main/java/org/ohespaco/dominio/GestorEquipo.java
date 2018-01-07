/*Copyright (c) 2018
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

package org.ohespaco.dominio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.swing.DefaultListModel;

import org.apache.commons.csv.CSVRecord;
import org.ohespaco.exceptions.ErrorWritingCSV;
import org.ohespaco.exceptions.EscrituraErronea;
import org.ohespaco.persistencia.CSVAgent;

public class GestorEquipo {

	private static String path;

	private static DefaultListModel<MiembroEquipo> listaEquipo = new DefaultListModel<MiembroEquipo>();

	private static GestorEquipo instancia = null;

	private static HashMap<String, MiembroEquipo> Equipo = new HashMap<String, MiembroEquipo>();

	private static final String HEADER_CSV = "uuid,uuid_proyecto,uuid_usuario,rol\n";

	private GestorEquipo(String path) {
		GestorEquipo.path = path;
		inicializarCSV();
	}

	public void usuarioEliminado(String uuid_user) {

		MiembroEquipo miembro_aux;
		GestorMiembrosTareas.getInstancia("").miembroEquipoEliminado(uuid_user);
		if (!Equipo.isEmpty()) {
			listaEquipo = new DefaultListModel<MiembroEquipo>();

			for (String key : Equipo.keySet()) {

				miembro_aux = Equipo.get(key);

				if (miembro_aux.getUuid_usuario().equals(uuid_user)) {
					Equipo.remove(miembro_aux.getUuid());
				}

			}

			if (!Equipo.isEmpty()) {

				for (String key : Equipo.keySet()) {
					miembro_aux = Equipo.get(key);
					listaEquipo.addElement(miembro_aux);

				}
			}

		}

	}

	public static GestorEquipo getInstancia(String path) {
		if (instancia == null) {
			instancia = new GestorEquipo(path);
		}
		return instancia;
	}

	public void cargarEquipo() throws IOException {

		String uuid, uuid_proyecto, uuid_usuario, rol = null;

		MiembroEquipo miembro;
		Equipo = new HashMap<String, MiembroEquipo>();
		CSVAgent agente = new CSVAgent();
		Iterable<CSVRecord> records = agente.readCSV(path);
		for (CSVRecord record : records) {
			uuid = record.get("uuid");
			uuid_proyecto = record.get("uuid_proyecto");
			uuid_usuario = record.get("uuid_usuario");
			rol = record.get("rol");
			miembro = new MiembroEquipo(uuid, uuid_proyecto, uuid_usuario, rol);
			Equipo.put(uuid, miembro);
		}
		listaEquipo = new DefaultListModel<MiembroEquipo>();
		if (!Equipo.isEmpty()) {

			for (String key : Equipo.keySet()) {
				miembro = Equipo.get(key);
				listaEquipo.addElement(miembro);
			}
		}

	}

	public DefaultListModel<MiembroEquipo> getDefaultList() {
		return listaEquipo;
	}

	public DefaultListModel<MiembroEquipo> getMiembrosEquipoProyecto(String uuid_proyecto) {
		MiembroEquipo miembro;
		DefaultListModel<MiembroEquipo> listaEquipo_aux = new DefaultListModel<MiembroEquipo>();
		if (!Equipo.isEmpty()) {

			for (String key : Equipo.keySet()) {
				miembro = Equipo.get(key);
				if (miembro.getUuid_proyecto().equals(uuid_proyecto)) {
					listaEquipo_aux.addElement(miembro);
				}

			}

		}
		return listaEquipo_aux;
	}

	public DefaultListModel<Usuario> getMiembrosEquipoProyectoUser(String uuid_proyecto) {
		MiembroEquipo miembro;
		Usuario user;
		DefaultListModel<Usuario> listaEquipo_aux = new DefaultListModel<Usuario>();
		if (!Equipo.isEmpty()) {

			for (String key : Equipo.keySet()) {
				miembro = Equipo.get(key);
				if (miembro.getUuid_proyecto().equals(uuid_proyecto)) {
					user = GestorUsuarios.getInstancia("").getUserByUuid(miembro.getUuid_usuario());
					listaEquipo_aux.addElement(user);
				}

			}

		}
		return listaEquipo_aux;
	}

	public boolean estaEnProyecto(String uuid_proyecto, String uuid_usuario) {
		boolean esta = false;
		MiembroEquipo miembro;

		if (!Equipo.isEmpty()) {

			for (String key : Equipo.keySet()) {
				miembro = Equipo.get(key);
				if (miembro.getUuid_proyecto().equals(uuid_proyecto)
						&& miembro.getUuid_usuario().equals(uuid_usuario)) {
					esta = true;
					break;
				}

			}

		}

		return esta;
	}

	public void addMiembro(String uuid_proyecto, String uuid_usuario, String rol) {
		MiembroEquipo miembro = new MiembroEquipo(UUID.randomUUID().toString(), uuid_proyecto, uuid_usuario, rol);

		Equipo.put(miembro.getUuid(), miembro);
		listaEquipo.addElement(miembro);
	}

	public void guardarEquipos() {
		MiembroEquipo miembro;
		crearCSV();
		if (!Equipo.isEmpty()) {
			for (String key : Equipo.keySet()) {
				miembro = Equipo.get(key);
				escribirEquipo(miembro);
			}
		}
	}

	public void editarAsociacion(MiembroEquipo miembro) {

		if (miembro != null) {

			Equipo.remove(miembro.getUuid());
			Equipo.put(miembro.getUuid(), miembro);

			listaEquipo = new DefaultListModel<MiembroEquipo>();
			for (String key : Equipo.keySet()) {
				miembro = Equipo.get(key);
				listaEquipo.addElement(miembro);

			}

		}
	}

	public void eliminarAsociacion(MiembroEquipo miembro) {
		MiembroEquipo miembro_aux;
		if (Equipo.get(miembro.getUuid()) != null) {

			Equipo.remove(miembro.getUuid());
			listaEquipo = new DefaultListModel<MiembroEquipo>();
			GestorMiembrosTareas.getInstancia("").miembroEquipoEliminado(miembro.getUuid_usuario());
			if (!Equipo.isEmpty()) {

				for (String key : Equipo.keySet()) {
					miembro_aux = Equipo.get(key);
					listaEquipo.addElement(miembro_aux);

				}
			}

		}
	}

	private void escribirEquipo(MiembroEquipo miembro) {
		CSVAgent agente = new CSVAgent();
		try {

			ArrayList<String> p = new ArrayList<String>();
			p.add(miembro.getUuid());
			p.add(miembro.getUuid_proyecto());
			p.add(miembro.getUuid_usuario());
			p.add(miembro.getRol());
			agente.writeToCSV(p, path);

		} catch (ErrorWritingCSV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void inicializarCSV() {
		File tmpDir = new File(path);
		if (!tmpDir.exists()) {
			crearCSV();
		}
		try {
			cargarEquipo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void crearCSV() {
		try {
			ES_de_archivos.escribir_linea(path, true, HEADER_CSV);
		} catch (EscrituraErronea e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, MiembroEquipo> getEquipo() {
		return Equipo;
	}

}
