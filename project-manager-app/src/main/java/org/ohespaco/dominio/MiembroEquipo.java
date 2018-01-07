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

public class MiembroEquipo {
	private String uuid_proyecto;
	private String uuid_usuario;
	private String rol;
	private String uuid;

	public MiembroEquipo(String uuid, String uuid_proyecto, String uuid_usuario, String rol) {
		this.uuid_proyecto = uuid_proyecto;
		this.uuid_usuario = uuid_usuario;
		this.rol = rol;
		this.uuid = uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public String getUuid_proyecto() {
		return uuid_proyecto;
	}

	public void setUuid_proyecto(String uuid_proyecto) {
		this.uuid_proyecto = uuid_proyecto;
	}

	public String getUuid_usuario() {
		return uuid_usuario;
	}

	public void setUuid_usuario(String uuid_usuario) {
		this.uuid_usuario = uuid_usuario;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		Usuario user = GestorUsuarios.getInstancia("").getUserByUuid(uuid_usuario);
		String str = "";
		if (user != null) {
			str = user.getNombre() + " " + user.getApellidos();
		}
		return str;
	}
}
