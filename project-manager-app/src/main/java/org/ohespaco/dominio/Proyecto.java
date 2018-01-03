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
import java.time.LocalDate;

public class Proyecto {
	
	private String uuid;
	private String nombre;
	private String descripcion;
	private LocalDate fecha_creacion;
	private String responsable;
	
	public Proyecto(String uuid, String nombre, String descripcion, LocalDate fecha_creacion, String responsable) {
		this.uuid = uuid;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha_creacion = fecha_creacion;
		this.responsable = responsable;		
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
	 * @return the descripcion
	 */

	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * @param descripcion
	 *            the descripcion to set
	 */

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	/**
	 * @return the fecha_creacion
	 */

	public LocalDate getFecha_creacion() {
		return fecha_creacion;
	}
	
	/**
	 * @param fecha_creacion
	 *            the fecha_creacion to set
	 */

	public void setFecha_creacion(LocalDate fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}	
	
	/**
	 * @return the responsable
	 */

	public String getResponsable() {
		return responsable;
	}
	
	/**
	 * @param responsable
	 *            the responsable to set
	 */

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	
}
