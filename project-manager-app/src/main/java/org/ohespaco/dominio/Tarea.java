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

import java.sql.Date;

import javax.swing.DefaultListModel;

public class Tarea {
	
	private String uuid;
	private String uuid_tarea_padre;
	private String nombre;
	private Date fecha_creacion;
	private Date fecha_fin;
	private String etiquetas;
	private static DefaultListModel<Tarea> subtareas = new DefaultListModel<Tarea>();
	private String comentarios;
	private int prioridad;
	private int estado;
	
	public Tarea(String uuid, String uuid_tarea_padre, String nombre, Date fecha_creacion,
			Date fecha_fin, String etiquetas, DefaultListModel<Tarea> subtareas,
			String comentarios, int prioridad, int estado) {
		
		this.uuid = uuid;
		this.uuid_tarea_padre = uuid_tarea_padre;
		this.nombre = nombre;
		this.fecha_creacion = fecha_creacion;
		this.fecha_fin = fecha_fin;
		this.etiquetas = etiquetas;
		Tarea.subtareas = subtareas;
		this.comentarios = comentarios;
		this.prioridad = prioridad; 
		this.estado = estado;
			
	}

	/**
	 * @return the id
	 */
	public String getUuid() {
		return uuid;
	}
	

	
	/**
	 * @param id
	 *            the id to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	/**
	 * @return the id_tarea_padre
	 */
	public String getUuid_tarea_padre() {
		return uuid_tarea_padre;
	}

	
	/**
	 * @param id_tarea_pdre
	 *            the id_tarea_padre to set
	 */
	public void setUuid_tarea_padre(String uuid_tarea_padre) {
		this.uuid_tarea_padre = uuid_tarea_padre;
	}

	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	
	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	/**
	 * @return the fecha_creacion
	 */
	public Date getFecha_creacion() {
		return fecha_creacion;
	}

	
	/**
	 * @param fecha_creacion
	 *            the fecha_creacion to set
	 */
	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	
	/**
	 * @return the fecha_fin
	 */
	public Date getFecha_fin() {
		return fecha_fin;
	}
	

	/**
	 * @param fecha_fin
	 *            the fecha_fin to set
	 */
	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	
	/**
	 * @return the etiquetas
	 */
	public String getEtiquetas() {
		return etiquetas;
	}

	
	/**
	 * @param etiquetas
	 *            the etiquetas to set
	 */
	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}

	
	/**
	 * @return the subtareas
	 */
	public static DefaultListModel<Tarea> getSubtareas() {
		return subtareas;
	}

	
	/**
	 * @param subtareas
	 *            the sutareas to set
	 */
	public static void setSubtareas(DefaultListModel<Tarea> subtareas) {
		Tarea.subtareas = subtareas;
	}

	
	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}

	
	/**
	 * @param comentarios
	 *            the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	
	
	/**
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}

	
	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	
	/**
	 * @return the prioridad
	 */
	public int getPrioridad() {
		return prioridad;
	}

	
	/**
	 * @param prioridad
	 *            the prioridad to set
	 */
	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}
	

	@Override
	public String toString() {
		return nombre;	
	}
	
}
