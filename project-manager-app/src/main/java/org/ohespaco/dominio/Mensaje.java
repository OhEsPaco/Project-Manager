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

public class Mensaje {
	private String uuid_emisor;
	private String uuid_receptor;
	private String asunto;
	private String mensaje;
	private String uuid;

	public Mensaje(String uuid, String uuid_emisor, String uuid_receptor, String asunto, String mensaje) {

		this.uuid = uuid;
		this.uuid_emisor = uuid_emisor;
		this.uuid_receptor = uuid_receptor;
		this.mensaje = mensaje;
		this.asunto = asunto;
	}

	public String getUuid_emisor() {
		return uuid_emisor;
	}

	public void setUuid_emisor(String uuid_emisor) {
		this.uuid_emisor = uuid_emisor;
	}

	public String getUuid_receptor() {
		return uuid_receptor;
	}

	public void setUuid_receptor(String uuid_receptor) {
		this.uuid_receptor = uuid_receptor;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "[" + asunto + "]";
	}

}
