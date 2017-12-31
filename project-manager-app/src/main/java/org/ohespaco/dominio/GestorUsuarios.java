package org.ohespaco.dominio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

import org.apache.commons.csv.CSVRecord;
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

import org.ohespaco.exceptions.EscrituraErronea;
import org.ohespaco.persistencia.CSVAgent;
import org.ohespaco.persistencia.CurrentSession;

public class GestorUsuarios {
	private String path;
	private HashMap<String, Usuario> usuarios = new HashMap<String, Usuario>();
	private final String HEADER_CSV = "uuid,email,pass_hash,nombre,apellidos,rol,contacto,descripcion,foto";

	public GestorUsuarios(String path) {
		this.path = path;
		inicializarCSV();
	}

	public void cargarUsuarios() throws IOException {

		String uuid, email, pass_hash, nombre, apellidos, rol, contacto, descripcion, foto;
		Usuario user;
		usuarios = new HashMap<String, Usuario>();
		CSVAgent agente = new CSVAgent(path);
		Iterable<CSVRecord> records = agente.readCSV();
		for (CSVRecord record : records) {
			uuid = record.get("uuid");
			email = record.get("email");
			pass_hash = record.get("pass_hash");
			nombre = record.get("nombre");
			apellidos = record.get("apellidos");
			rol = record.get("rol");
			contacto = record.get("contacto");
			descripcion = record.get("descripcion");
			foto = record.get("foto");
			user = new Usuario(uuid, email, pass_hash, nombre, apellidos, rol, contacto, descripcion, foto);
			usuarios.put(uuid, user);
		}

	}

	private void inicializarCSV() {
		File tmpDir = new File(path);
		if (!tmpDir.exists()) {
			crearCSV();
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

	/**
	 * @return the usuarios
	 */
	public HashMap<String, Usuario> getUsuarios() {
		return usuarios;
	}

	public boolean login(String email, String pass) {
		boolean logged = false;

		if (!usuarios.isEmpty()) {
			Usuario user;
			String pash_hash = Hash.md5(pass);
			CurrentSession sesion = CurrentSession.getInstancia();
			for (String key : usuarios.keySet()) {
				user = usuarios.get(key);
				if (user.getEmail().equals(email) && user.getPass_hash().equals(pash_hash)) {
					sesion.setLogged(true);
					logged = true;
					sesion.setUser(user);
					break;
				}
			}

		}
		return logged;
	}
}
