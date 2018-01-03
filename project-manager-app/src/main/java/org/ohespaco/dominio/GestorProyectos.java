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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.swing.DefaultListModel;

import org.apache.commons.csv.CSVRecord;
import org.ohespaco.exceptions.ErrorWritingCSV;
import org.ohespaco.exceptions.EscrituraErronea;
import org.ohespaco.persistencia.CSVAgent;

public class GestorProyectos {
	//Camino al csv de proyectos
	private static String path;
	//List para mostrar en la interfaz
	private static DefaultListModel<Proyecto> listaProyectos = new DefaultListModel<Proyecto>();
	//Instancia global del gestor
	private static GestorProyectos instancia = null;
	//Hashmap de pryecto
	private static HashMap<String, Proyecto> proyectos = new HashMap<String, Proyecto>();	
	//Cabecero del csv
	private static final String HEADER_CSV = "uuid,nombre,descripcion,fecha_creacion,responsable\n";
	
	/**
	 * Constructor de GestorUsuarios
	 * 
	 * @param path
	 */
	private GestorProyectos(String path) {
		this.path = path;
		inicializarCSV();
	}
	
	/**
	 * Crea o retorna la instancia del gestor
	 * 
	 * @param path
	 * @return
	 */
	public static GestorProyectos getInstancia(String path) {
		if (instancia == null) {
			instancia = new GestorProyectos(path);
		}
		return instancia;
	}
	
	/**
	 * Carga los proyectos
	 * 
	 * @throws IOException
	 */
	public void cargarProyectos() throws IOException {

		String uuid, nombre, descripcion, fecha_creacion, responsable = null;
		Proyecto proyect;
		proyectos = new HashMap<String, Proyecto>();
		CSVAgent agente = new CSVAgent();
		Iterable<CSVRecord> records = agente.readCSV(path);
		for (CSVRecord record : records) {
			uuid = record.get("uuid");
			nombre = record.get("nombre");
			descripcion = record.get("descripcion");
			fecha_creacion = record.get("fecha_creacion");
			responsable = record.get("responsable");
			proyect = new Proyecto(uuid, nombre, descripcion, fecha_creacion, responsable);
			proyectos.put(uuid, proyect);
		}
		listaProyectos = new DefaultListModel<Proyecto>();
		if (!proyectos.isEmpty()) {

			for (String key : proyectos.keySet()) {
				proyect = proyectos.get(key);
				listaProyectos.addElement(proyect);
			}
		}

	}
	
	/**
	 * Retorna la lista de proyectos
	 * 
	 * @return
	 */
	public DefaultListModel<Proyecto> getDefaultList() {
		return listaProyectos;
	}
	
	/**
	 * Metodo para añadir un proyecto nuevo
	 * 

	 * @param nombre
	 * @param descripcion
	 * @param fecha_creacion
	 * @param miembros
	 * @param responsable
	 */
	public void crearProyecto(String nombre, String descripcion, String fecha_creacion, String responsable) {
		Proyecto proyect = new Proyecto(UUID.randomUUID().toString(), nombre, descripcion,fecha_creacion, responsable);
		escribirProyecto(proyect);
		proyectos.put(proyect.getUuid(), proyect);
		listaProyectos.addElement(proyect);
	}

	/**
	 * Vuelca el hashmap en un archivo csv
	 * 
	 */
	public void volcarProyectos() {
		Proyecto proyect_aux;
		crearCSV();
		if (!proyectos.isEmpty()) {
			for (String key : proyectos.keySet()) {
				proyect_aux = proyectos.get(key);
				escribirProyecto(proyect_aux);
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
	public void editarProyecto(String uuid, String nombre, String descripcion, String fecha_creacion, String miembros, String responsable) {
		Proyecto proyect_aux = proyectos.get(uuid);

		if (proyect_aux != null) {
			proyect_aux.setNombre(nombre);
			proyect_aux.setDescripcion(descripcion);
			proyect_aux.setFecha_creacion(fecha_creacion);
			proyect_aux.setResponsable(responsable);

			proyectos.remove(uuid);
			proyectos.put(proyect_aux.getUuid(), proyect_aux);

			listaProyectos = new DefaultListModel<Proyecto>();
			for (String key : proyectos.keySet()) {
				proyect_aux = proyectos.get(key);
				listaProyectos.addElement(proyect_aux);

			}

			volcarProyectos();

		}
	}
	
	/**
	 * Elimina un proyecto
	 * 
	 * @param proyect
	 */
	public void borrarProyecto(Proyecto proyect) {
		Proyecto proyect_aux;
		if (proyectos.get(proyect.getUuid()) != null) {

			proyectos.remove(proyect.getUuid());
			listaProyectos = new DefaultListModel<Proyecto>();

			if (!proyectos.isEmpty()) {

				crearCSV();
				for (String key : proyectos.keySet()) {
					proyect_aux = proyectos.get(key);
					listaProyectos.addElement(proyect_aux);
					escribirProyecto(proyect_aux);
				}
			} else {
				crearCSV();
			}

		}
	}
	
	
	/**
	 * Añade un usuario al csv
	 * @param proyect
	 */
	public void escribirProyecto(Proyecto proyect) {
		CSVAgent agente = new CSVAgent();
		try {

			ArrayList<String> p = new ArrayList<String>();
			p.add(proyect.getUuid());
			p.add(proyect.getDescripcion());
			p.add(proyect.getFecha_creacion());
			p.add(proyect.getResponsable());
			
			agente.writeToCSV(p, path);

		} catch (ErrorWritingCSV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Crea un csv si no existe y carga los proyectos
	 */
	private void inicializarCSV() {
		File tmpDir = new File(path);
		if (!tmpDir.exists()) {
			crearCSV();
		}
		try {
			cargarProyectos();
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
	 * Retorna el hashmap de proyectos
	 * 
	 * @return the proyectos
	 */
	public HashMap<String, Proyecto> getProyectos() {
		return proyectos;
	}
	
	/**
	 * Retorna true si existe el nombre del proyecto dado
	 * 
	 * @param nombre
	 * @return
	 */
	public boolean existeNombre(String nombre) {
		boolean existe = false;

		if (!proyectos.isEmpty()) {
			Proyecto proyect;
			for (String key : proyectos.keySet()) {
				proyect = proyectos.get(key);

				if (proyect.getNombre().equals(nombre)) {
					existe = true;
					break;
				}

			}
		}

		return existe;
	}
		
}
