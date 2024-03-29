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

public class GestorMiembrosTareas {
	// Camino al csv de Tarea
	private static String path;
	// List para mostrar en la interfaz
	private static DefaultListModel<MiembroTarea> listaTarea = new DefaultListModel<MiembroTarea>();
	// Instancia global del gestor
	private static GestorMiembrosTareas instancia = null;
	// Hashmap de pryecto
	private static HashMap<String, MiembroTarea> Tarea = new HashMap<String, MiembroTarea>();
	// Cabecero del csv
	private static final String HEADER_CSV = "uuid,uuid_proyecto,uuid_usuario,uuid_tarea,rol\n";
	private static final String TASK_DEFAULT = "DEFAULT0-TASK-0000-0000-000000000000";
	// MiembroTarea(String uuid, String uuid_proyecto, String uuid_usuario, String
	// uuid_tarea, String rol) {

	/*
	 * public void imprimirHash() { MiembroTarea aux;
	 * 
	 * if (!Tarea.isEmpty()) { for (String key : Tarea.keySet()) { aux =
	 * Tarea.get(key);
	 * System.out.println(aux.getUuid_proyecto()+" "+aux.getUuid_usuario()+" "+aux.
	 * getRol()); } }
	 * 
	 * System.out.println("//////////////////////"); }
	 */
	/**
	 * Constructor de GestorUsuarios
	 * 
	 * @param path
	 */
	private GestorMiembrosTareas(String path) {
		this.path = path;
		inicializarCSV();
	}

	public boolean estaEnTarea(String uuid_tarea, String uuid_usuario) {
		boolean esta = false;
		MiembroTarea miembro;

		if (!Tarea.isEmpty()) {

			for (String key : Tarea.keySet()) {
				miembro = Tarea.get(key);
				if (miembro.getUuid_tarea().equals(uuid_tarea) && miembro.getUuid_usuario().equals(uuid_usuario)) {
					esta = true;
					break;
				}

			}

		}

		return esta;
	}

	/**
	 * Crea o retorna la instancia del gestor
	 * 
	 * @param path
	 * @return
	 */
	public static GestorMiembrosTareas getInstancia(String path) {
		if (instancia == null) {
			instancia = new GestorMiembrosTareas(path);
		}
		return instancia;
	}

	public MiembroTarea getMemberByUuid(String uuid_usuario, String uuid_tarea) {
		MiembroTarea user = null;
		if (!Tarea.isEmpty()) {
			for (String key : Tarea.keySet()) {
				user = Tarea.get(key);
				if (user.getUuid_usuario().equals(uuid_usuario) && user.getUuid_tarea().equals(uuid_tarea)) {
					break;
				}
			}
		}
		return user;
	}

	/**
	 * Carga los Tarea
	 * 
	 * @throws IOException
	 */
	public void cargarTarea() throws IOException {

		String uuid, uuid_proyecto, uuid_usuario, uuid_tarea, rol = null;

		MiembroTarea miembro;
		Tarea = new HashMap<String, MiembroTarea>();
		CSVAgent agente = new CSVAgent();
		Iterable<CSVRecord> records = agente.readCSV(path);
		for (CSVRecord record : records) {
			uuid = record.get("uuid");
			uuid_proyecto = record.get("uuid_proyecto");
			uuid_usuario = record.get("uuid_usuario");
			uuid_tarea = record.get("uuid_tarea");
			rol = record.get("rol");
			miembro = new MiembroTarea(uuid, uuid_proyecto, uuid_usuario, uuid_tarea, rol);
			Tarea.put(uuid, miembro);
		}
		listaTarea = new DefaultListModel<MiembroTarea>();
		if (!Tarea.isEmpty()) {

			for (String key : Tarea.keySet()) {
				miembro = Tarea.get(key);
				listaTarea.addElement(miembro);
			}
		}

	}

	/**
	 * Retorna la lista de Tarea
	 * 
	 * @return
	 */
	public DefaultListModel<MiembroTarea> getDefaultList() {
		return listaTarea;
	}

	/*
	 * public boolean estaEnProyecto(String uuid_proyecto, String uuid_usuario) {
	 * boolean esta = false; MiembroTarea miembro;
	 * 
	 * if (!Tarea.isEmpty()) {
	 * 
	 * for (String key : Tarea.keySet()) { miembro = Tarea.get(key); if
	 * (miembro.getUuid_proyecto().equals(uuid_proyecto) &&
	 * miembro.getUuid_usuario().equals(uuid_usuario)) { esta = true; break; }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * return esta; }
	 */

	/*
	 * public DefaultListModel<MiembroTarea> getMiembrosNoEnProyecto(String
	 * uuid_proyecto){ MiembroTarea miembro; DefaultListModel<MiembroTarea>
	 * listaTarea_aux = new DefaultListModel<MiembroTarea>(); if (!Tarea.isEmpty())
	 * {
	 * 
	 * for (String key : Tarea.keySet()) { miembro = Tarea.get(key); if
	 * (!miembro.getUuid_proyecto().equals(uuid_proyecto)) {
	 * listaTarea_aux.addElement(miembro); }
	 * 
	 * }
	 * 
	 * } return listaTarea_aux;
	 * 
	 * }
	 */
	/**
	 * Metodo para añadir un tarea nuevo
	 * 
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param fecha_creacion
	 * @param miembros
	 * @param responsable
	 */
	public void miembroEquipoEliminado(String uuid_user) {

		MiembroTarea miembro_aux;

		if (!Tarea.isEmpty()) {
			listaTarea = new DefaultListModel<MiembroTarea>();

			for (String key : Tarea.keySet()) {

				miembro_aux = Tarea.get(key);

				if (miembro_aux.getUuid_usuario().equals(uuid_user)) {
					Tarea.remove(miembro_aux.getUuid());
				}

			}

			if (!Tarea.isEmpty()) {

				for (String key : Tarea.keySet()) {
					miembro_aux = Tarea.get(key);
					listaTarea.addElement(miembro_aux);

				}
			}

		}

	}

	public void addMiembro(String uuid_proyecto, String uuid_usuario, String uuid_tarea, String rol) {
		MiembroTarea miembro = new MiembroTarea(UUID.randomUUID().toString(), uuid_proyecto, uuid_usuario, uuid_tarea,
				rol);

		Tarea.put(miembro.getUuid(), miembro);
		listaTarea.addElement(miembro);
	}

	/**
	 * Vuelca el hashmap en un archivo csv
	 * 
	 */
	public void guardarTareas() {
		MiembroTarea miembro;
		crearCSV();
		if (!Tarea.isEmpty()) {
			for (String key : Tarea.keySet()) {
				miembro = Tarea.get(key);
				escribirTarea(miembro);
			}
		}
	}

	/**
	 * Edita un usuario existente
	 * 
	 * @param uuid
	 * @param descripcion
	 * @param fecha_cracion
	 * @param miembros
	 * @param responsable
	 */
	public void editarAsociacion(MiembroTarea miembro) {

		if (miembro != null) {

			Tarea.remove(miembro.getUuid());
			Tarea.put(miembro.getUuid(), miembro);

			listaTarea = new DefaultListModel<MiembroTarea>();
			for (String key : Tarea.keySet()) {
				miembro = Tarea.get(key);
				listaTarea.addElement(miembro);

			}

		}
	}

	/**
	 * Elimina un tarea
	 * 
	 * @param proyect
	 */
	public void eliminarAsociacion(MiembroTarea miembro) {
		MiembroTarea miembro_aux;
		if (Tarea.get(miembro.getUuid()) != null) {

			Tarea.remove(miembro.getUuid());
			listaTarea = new DefaultListModel<MiembroTarea>();

			if (!Tarea.isEmpty()) {

				for (String key : Tarea.keySet()) {
					miembro_aux = Tarea.get(key);
					listaTarea.addElement(miembro_aux);

				}
			}

		}
	}

	/**
	 * Añade un usuario al csv
	 * 
	 * @param proyect
	 */
	private void escribirTarea(MiembroTarea miembro) {
		CSVAgent agente = new CSVAgent();
		try {

			ArrayList<String> p = new ArrayList<String>();
			p.add(miembro.getUuid());
			p.add(miembro.getUuid_proyecto());
			p.add(miembro.getUuid_usuario());
			p.add(miembro.getUuid_tarea());
			p.add(miembro.getRol());
			agente.writeToCSV(p, path);

		} catch (ErrorWritingCSV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Crea un csv si no existe y carga los Tarea
	 */
	private void inicializarCSV() {
		File tmpDir = new File(path);
		if (!tmpDir.exists()) {
			crearCSV();
		}
		try {
			cargarTarea();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Crea un csv nuevo
	 */
	private void crearCSV() {
		try {
			ES_de_archivos.escribir_linea(path, true, HEADER_CSV);
		} catch (EscrituraErronea e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Retorna el hashmap de Tarea
	 * 
	 * @return the Tarea
	 */
	public HashMap<String, MiembroTarea> getTarea() {
		return Tarea;
	}

}
