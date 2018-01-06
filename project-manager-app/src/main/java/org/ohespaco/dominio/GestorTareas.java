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

package org.ohespaco.dominio;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.DefaultListModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.csv.CSVRecord;
import org.ohespaco.exceptions.ErrorWritingCSV;
import org.ohespaco.exceptions.EscrituraErronea;
import org.ohespaco.persistencia.CSVAgent;
import org.ohespaco.persistencia.CurrentSession;

public class GestorTareas {
	// Camino al csv de tareas
	private static String path;
	// Hashmap de usuarios
	private static ConcurrentHashMap<String, Tarea> tareas = new ConcurrentHashMap <String, Tarea>();
	// Cabecero del csv
	private static final String HEADER_CSV = "uuid,uuid_tarea_padre,uuid_proyecto,nombre,fecha_creacion,fecha_fin,etiquetas,comentarios,prioridad,estado\n";
	// Instancia global del gestor
	private static GestorTareas instancia = null;
	// List para mostrar en la interfaz
	private static DefaultListModel<Tarea> listaTareas = new DefaultListModel<Tarea>();
	private static final String PADRE_DEFAULT = "DEFAULT0-TASK-0000-0000-000000000000";
	private static DefaultTreeModel tree;
	/**
	 * Constructor de GestorTareas
	 * 
	 * @param path
	 */
	private GestorTareas(String path) {
		this.path = path;
		inicializarCSV();
	}

	/**
	 * Crea o retorna la instancia del gestor
	 * 
	 * @param path
	 * @return
	 */
	public static GestorTareas getInstancia(String path) {
		if (instancia == null) {
			instancia = new GestorTareas(path);
		}
		return instancia;
	}

	/**
	 * Carga las tareas
	 * 
	 * @throws IOException
	 */
	public void imprimirHash() {
		Tarea tarea_aux;
		
		if (!tareas.isEmpty()) {
			for (String key : tareas.keySet()) {
				tarea_aux = tareas.get(key);
				System.out.println(tarea_aux.getUuid()+","+tarea_aux.getUuid_tarea_padre()+","+tarea_aux.getUuid_proyecto()+","+tarea_aux.getNombre()+","+tarea_aux.getEtiquetas()+","+tarea_aux.getComentarios());
			}
		}
		
		System.out.println("//////////////////////");
	}
	public void cargarTareas() throws IOException {

		String uuid, uuid_tarea_padre, uuid_proyecto, nombre, etiquetas, comentarios;
		Date fecha_creacion, fecha_fin;

		int prioridad, estado;
		Tarea task;
		tareas = new ConcurrentHashMap <String, Tarea>();
		CSVAgent agente = new CSVAgent();
		Iterable<CSVRecord> records = agente.readCSV(path);
		for (CSVRecord record : records) {
			uuid = record.get("uuid");
			uuid_tarea_padre = record.get("uuid_tarea_padre");
			uuid_proyecto = record.get("uuid_proyecto");
			nombre = record.get("nombre");
			fecha_creacion = new Date(Long.parseLong(record.get("fecha_creacion")));
			fecha_fin = new Date(Long.parseLong(record.get("fecha_fin")));
			etiquetas = record.get("etiquetas");
			comentarios = record.get("comentarios");
			prioridad = Integer.parseInt(record.get("prioridad"));
			estado = Integer.parseInt(record.get("estado"));

			task = new Tarea(uuid, uuid_tarea_padre, uuid_proyecto, nombre, fecha_creacion, fecha_fin, etiquetas,
					comentarios, prioridad, estado);
			tareas.put(uuid, task);
		}
		listaTareas = new DefaultListModel<Tarea>();
		if (!tareas.isEmpty()) {

			for (String key : tareas.keySet()) {
				task = tareas.get(key);
				listaTareas.addElement(task);
			}
		}

	}

	/**
	 * Retorna la lista de tareas
	 * 
	 * @return
	 */
	public DefaultListModel<Tarea> getDefaultList() {
		return listaTareas;
	}

	/**
	 * Metodo para crear tarea nueva
	 * 
	 * @param nombre
	 * @param usuario
	 * @param fecha_creacion
	 * @param fecha_fin
	 * @param etiquetas
	 * @param subtareas
	 * @param comentarios
	 */
	/*
	 * public Tarea(String uuid, String uuid_tarea_padre,String uuid_proyecto,
	 * String nombre, Date fecha_creacion, Date fecha_fin, String etiquetas, String
	 * comentarios, int prioridad, int estado) {
	 */

	public void crearTarea(String uuid_tarea_padre, String uuid_proyecto, String nombre, Date fecha_creacion,
			Date fecha_fin, String etiquetas, String comentarios, int prioridad, int estado) {
		
		Tarea task = new Tarea(UUID.randomUUID().toString(), uuid_tarea_padre, uuid_proyecto, nombre, fecha_creacion,
				fecha_fin, etiquetas, comentarios, prioridad, estado);
	
		tareas.put(task.getUuid(), task);
		
		listaTareas.addElement(task);
	
		actualizarTree("Tareas",uuid_proyecto);
		
	}

	public void crearTarea(String uuid_proyecto, String nombre, Date fecha_creacion, Date fecha_fin, String etiquetas,
			String comentarios, int prioridad, int estado) {

		Tarea task = new Tarea(UUID.randomUUID().toString(), PADRE_DEFAULT, uuid_proyecto, nombre, fecha_creacion,
				fecha_fin, etiquetas, comentarios, prioridad, estado);

		tareas.put(task.getUuid(), task);
		listaTareas.addElement(task);
		actualizarTree("Tareas",uuid_proyecto);
	}

	/**
	 * Vuelca el hashmap en un archivo csv
	 * 
	 */
	public void guardarTareas() {
		Tarea tarea_aux;
		crearCSV();
		if (!tareas.isEmpty()) {
			for (String key : tareas.keySet()) {
				tarea_aux = tareas.get(key);
				escribirTarea(tarea_aux);
			}
		}
	}

	/**
	 * Edita un usuario existente
	 * 
	 * @param uuid
	 * @param uuid_tarea_padre
	 * @param nombre
	 * @param fecha_creacion
	 * @param fecha_fin
	 * @param etiquetas
	 * @param subtareas
	 * @param comentarios
	 * @param prioridad
	 * @param estado
	 */
	public void editarTarea(String uuid, String nombre, Date fecha_creacion, Date fecha_fin,
			String etiquetas, String comentarios, int prioridad, int estado) {
		Tarea task_aux = tareas.get(uuid);

		if (task_aux != null) {
			task_aux.setNombre(nombre);
			task_aux.setFecha_creacion(fecha_creacion);
			task_aux.setFecha_fin(fecha_fin);
			task_aux.setEtiquetas(etiquetas);

			task_aux.setComentarios(comentarios);
			task_aux.setPrioridad(prioridad);
			task_aux.setEstado(estado);

			tareas.remove(uuid);
			tareas.put(task_aux.getUuid(), task_aux);

			listaTareas = new DefaultListModel<Tarea>();
			for (String key : tareas.keySet()) {
				task_aux = tareas.get(key);
				listaTareas.addElement(task_aux);
			}
			actualizarTree("CAMBIADO",task_aux.getUuid_proyecto());

		}
	}

	/**
	 * Elimina una lista
	 * 
	 * @param task
	 */
	public void borrarTarea(Tarea task) {
		Tarea task_aux;
		if (tareas.get(task.getUuid()) != null) {

			borradoRecursivo( task);
			listaTareas = new DefaultListModel<Tarea>();

			if (!tareas.isEmpty()) {

				for (String key : tareas.keySet()) {
					task_aux = tareas.get(key);
					
					
					listaTareas.addElement(task_aux);

				}
				actualizarTree("CAMBIADO",task.getUuid_proyecto());
			} 

		}
	}
	private void borradoRecursivo(Tarea padre) {
		Tarea task_aux;
		
		if (tareas.get(padre.getUuid()) != null) {
			tareas.remove(padre.getUuid());
			for (String key : tareas.keySet()) {
				task_aux = tareas.get(key);
				if(padre.getUuid().equals(task_aux.getUuid_tarea_padre())) {
					borradoRecursivo(task_aux);
				}
				
			}
			
		}
		
	}

	/**
	 * Añade una lista al csv
	 * 
	 * @param lista
	 */
	public void escribirTarea(Tarea task) {
		CSVAgent agente = new CSVAgent();
		try {

			ArrayList<String> p = new ArrayList<String>();
			p.add(task.getUuid());
			p.add(task.getUuid_tarea_padre());
			p.add(task.getUuid_proyecto());
			p.add(task.getNombre());
			p.add(Long.toString(task.getFecha_creacion().getTime()));
			p.add(Long.toString(task.getFecha_fin().getTime()));
			p.add(task.getEtiquetas());
			p.add(task.getComentarios());
			p.add(Integer.toString(task.getPrioridad()));
			p.add(Integer.toString(task.getEstado()));

			agente.writeToCSV(p, path);

		} catch (ErrorWritingCSV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Crea un csv si no existe y carga las listas
	 */
	private void inicializarCSV() {
		File tmpDir = new File(path);
		if (!tmpDir.exists()) {
			crearCSV();
		}
		try {
			cargarTareas();
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
	 * Retorna el hashmap de tareas
	 * 
	 * @return the tareas
	 */
	public ConcurrentHashMap <String, Tarea> getTareas() {
		return tareas;
	}

	/**
	 * Retorna true si existe el nombre dado
	 * 
	 * @param nombre
	 * @return
	 */
	public boolean existeNombre(String nombre) {
		boolean existe = false;

		if (!tareas.isEmpty()) {
			Tarea task;
			for (String key : tareas.keySet()) {
				task = tareas.get(key);

				if (task.getNombre().equals(nombre)) {
					existe = true;
					break;
				}

			}
		}

		return existe;
	}

	public DefaultTreeModel actualizarTree(String root_label, String uuid_proyecto) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(root_label);
		Tarea task_aux = null;
		if (!tareas.isEmpty()) {

			busquedaDeNodos(PADRE_DEFAULT, uuid_proyecto, task_aux, root);

		}
		tree=new DefaultTreeModel(root);
		
		return tree;

	}

	private void busquedaDeNodos(String uuid_padre, String uuid_proyecto, Tarea task_aux, DefaultMutableTreeNode root) {
		for (String key : tareas.keySet()) {
			task_aux = tareas.get(key);
			if (task_aux.getUuid_tarea_padre().equals(uuid_padre)
					&& task_aux.getUuid_proyecto().equals(uuid_proyecto)) {
				DefaultMutableTreeNode subtarea = new DefaultMutableTreeNode(task_aux);
				root.add(subtarea);
				busquedaDeNodos(task_aux.getUuid(), uuid_proyecto, task_aux, subtarea);

			}

		}
	}

}