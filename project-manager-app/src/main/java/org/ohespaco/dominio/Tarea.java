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

import java.util.Date;

public class Tarea {

	private String uuid;
	private String uuid_tarea_padre;
	private String nombre;
	private Date fecha_creacion;
	private Date fecha_fin;
	private String etiquetas;
	private String comentarios;
	private int prioridad;
	private int estado;
	private String uuid_proyecto;

	public Tarea(String uuid, String uuid_tarea_padre, String uuid_proyecto, String nombre, Date fecha_creacion,
			Date fecha_fin, String etiquetas, String comentarios, int prioridad, int estado) {

		this.uuid = uuid;
		this.uuid_tarea_padre = uuid_tarea_padre;
		this.uuid_proyecto = uuid_proyecto;
		this.nombre = nombre;
		this.fecha_creacion = fecha_creacion;
		this.fecha_fin = fecha_fin;
		this.etiquetas = etiquetas;
		this.comentarios = comentarios;
		this.prioridad = prioridad;
		this.estado = estado;

	}

	public String getUuid_proyecto() {
		return uuid_proyecto;
	}

	public void setUuid_proyecto(String uuid_proyecto) {
		this.uuid_proyecto = uuid_proyecto;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid_tarea_padre() {
		return uuid_tarea_padre;
	}

	public void setUuid_tarea_padre(String uuid_tarea_padre) {
		this.uuid_tarea_padre = uuid_tarea_padre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFecha_creacion() {
		return fecha_creacion;
	}

	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public String getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}

	@Override
	public String toString() {
		return nombre;
	}

}
