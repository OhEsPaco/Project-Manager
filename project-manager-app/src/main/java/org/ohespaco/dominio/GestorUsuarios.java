package org.ohespaco.dominio;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.csv.CSVRecord;
import org.ohespaco.persistencia.CSVAgent;
import org.ohespaco.persistencia.CurrentSession;

public class GestorUsuarios {
	private String path;
	private HashMap<String, Usuario> usuarios = new HashMap<String, Usuario>();

	public GestorUsuarios(String path) {
		this.path = path;
	}

	public void cargarUsuarios() throws IOException {

		String id, email, pass_hash, nombre, apellidos, rol, contacto, descripcion, foto;
		Usuario user;
		usuarios = new HashMap<String, Usuario>();
		CSVAgent agente = new CSVAgent(path);
		Iterable<CSVRecord> records = agente.readCSV();
		for (CSVRecord record : records) {
			id = record.get("id");
			email = record.get("email");
			pass_hash = record.get("pass_hash");
			nombre = record.get("nombre");
			apellidos = record.get("apellidos");
			rol = record.get("rol");
			contacto = record.get("contacto");
			descripcion = record.get("descripcion");
			foto = record.get("foto");
			user = new Usuario(id, email, pass_hash, nombre, apellidos, rol, contacto, descripcion, foto);
			usuarios.put(id, user);
		}

	}

	/**
	 * @return the usuarios
	 */
	public HashMap<String, Usuario> getUsuarios() {
		return usuarios;
	}

	public boolean login (String email, String pass) {
		boolean logged=false;
		
		if(!usuarios.isEmpty()) {
			Usuario user;
			String pash_hash=Hash.md5(pass);
			CurrentSession sesion=CurrentSession.getInstancia();
			for (String key : usuarios.keySet()) {
				user = usuarios.get(key);
				if(user.getEmail().equals(email)&&user.getPass_hash().equals(pash_hash)) {
					sesion.setLogged(true);
					logged=true;
					sesion.setUser(user);
					break;
				}
			}

		}
		return logged;
	}
}
