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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;

import org.apache.commons.csv.CSVRecord;
import org.ohespaco.exceptions.ErrorWritingCSV;
import org.ohespaco.exceptions.EscrituraErronea;
import org.ohespaco.persistencia.CSVAgent;
import org.ohespaco.persistencia.CurrentSession;


public class GestorUsuarios {
	//Camino al csv de usuarios
	private static String path;
	//Hashmap de usuarios
	private static HashMap<String, Usuario> usuarios = new HashMap<String, Usuario>();
	//Cabecero del csv
	private static final String HEADER_CSV = "uuid,email,pass_hash,nombre,apellidos,rol,contacto,descripcion,foto\n";
	//Instancia global del gestor
	private static GestorUsuarios instancia = null;
	//List para mostrar en la interfaz
	private static DefaultListModel<Usuario> listaUsuarios = new DefaultListModel<Usuario>();

	/**
	 * Constructor de GestorUsuarios
	 * 
	 * @param path
	 */
	private GestorUsuarios(String path) {
		this.path = path;
		inicializarCSV();
	}

	/**
	 * Crea o retorna la instancia del gestor
	 * 
	 * @param path
	 * @return
	 */
	public static GestorUsuarios getInstancia(String path) {
		if (instancia == null) {
			instancia = new GestorUsuarios(path);

		}
		return instancia;
	}

	/**
	 * Carga los usuarios
	 * 
	 * @throws IOException
	 */
	public void cargarUsuarios() throws IOException {

		String uuid, email, pass_hash, nombre, apellidos, rol, contacto, descripcion, foto;
		Usuario user;
		usuarios = new HashMap<String, Usuario>();
		CSVAgent agente = new CSVAgent();
		Iterable<CSVRecord> records = agente.readCSV(path);
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
		listaUsuarios = new DefaultListModel<Usuario>();
		if (!usuarios.isEmpty()) {

			for (String key : usuarios.keySet()) {
				user = usuarios.get(key);
				listaUsuarios.addElement(user);
			}
		}

	}

	/**
	 * Retorna la lista de usuarios
	 * 
	 * @return
	 */
	public DefaultListModel<Usuario> getDefaultList() {
		return listaUsuarios;
	}

	/**
	 * Metodo para registrar un usuario nuevo
	 * 
	 * @param email
	 * @param pass
	 * @param nombre
	 * @param apellidos
	 * @param rol
	 * @param contacto
	 * @param descripcion
	 * @param foto
	 */
	public void registrarUsuario(String email, String pass, String nombre, String apellidos, String rol,
			String contacto, String descripcion, String foto) {
		Usuario user = new Usuario(UUID.randomUUID().toString(), email, Hash.md5(pass), nombre, apellidos, rol,
				contacto, descripcion, foto);
		escribirUsuario(user);
		usuarios.put(user.getUuid(), user);
		listaUsuarios.addElement(user);
	}

	/**
	 * Vuelca el hashmap en un archivo csv
	 * 
	 */
	public void volcarUsuarios() {
		Usuario user_aux;
		crearCSV();
		if (!usuarios.isEmpty()) {
			for (String key : usuarios.keySet()) {
				user_aux = usuarios.get(key);
				escribirUsuario(user_aux);
			}
		}
	}

	/**
	 * Edita un usuario existente
	 * 
	 * @param uuid
	 * @param email
	 * @param pass
	 * @param nombre
	 * @param apellidos
	 * @param rol
	 * @param contacto
	 * @param descripcion
	 * @param foto
	 * @param cambiar_contraseña
	 */
	public void editarUsuario(String uuid, String email, String pass, String nombre, String apellidos, String rol,
			String contacto, String descripcion, String foto, boolean cambiar_contraseña) {
		Usuario user_aux = usuarios.get(uuid);

		if (user_aux != null) {
			user_aux.setEmail(email);
			user_aux.setNombre(nombre);
			user_aux.setApellidos(apellidos);
			user_aux.setRol(rol);
			user_aux.setContacto(contacto);
			user_aux.setDescripcion(descripcion);
			user_aux.setFoto(foto);
			if (cambiar_contraseña) {
				user_aux.setPass_hash(Hash.md5(pass));
			}

			usuarios.remove(uuid);
			usuarios.put(user_aux.getUuid(), user_aux);

			listaUsuarios = new DefaultListModel<Usuario>();
			for (String key : usuarios.keySet()) {
				user_aux = usuarios.get(key);
				listaUsuarios.addElement(user_aux);

			}

			if (CurrentSession.getInstancia().getUser().equals(uuid)) {
				CurrentSession.getInstancia().setUser(usuarios.get(uuid));
			}

			volcarUsuarios();

		}
	}

	/**
	 * Elimina un usuario
	 * 
	 * @param user
	 */
	public void borrarUsuario(Usuario user) {
		Usuario user_aux;
		if (usuarios.get(user.getUuid()) != null) {

			usuarios.remove(user.getUuid());
			listaUsuarios = new DefaultListModel<Usuario>();

			if (!usuarios.isEmpty()) {

				crearCSV();
				for (String key : usuarios.keySet()) {
					user_aux = usuarios.get(key);
					listaUsuarios.addElement(user_aux);
					escribirUsuario(user_aux);
				}
			} else {
				crearCSV();
			}

		}
	}

	/**
	 * Añade un usuario al csv
	 * @param user
	 */
	public void escribirUsuario(Usuario user) {
		CSVAgent agente = new CSVAgent();
		try {

			ArrayList<String> p = new ArrayList<String>();
			p.add(user.getUuid());
			p.add(user.getEmail());
			p.add(user.getPass_hash());
			p.add(user.getNombre());
			p.add(user.getApellidos());
			p.add(user.getRol());
			p.add(user.getContacto());
			p.add(user.getDescripcion());
			p.add(user.getFoto());

			agente.writeToCSV(p, path);

		} catch (ErrorWritingCSV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Crea un csv si no existe y carga los usuarios
	 */
	private void inicializarCSV() {
		File tmpDir = new File(path);
		if (!tmpDir.exists()) {
			crearCSV();
		}
		try {
			cargarUsuarios();
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
	 * Retorna el hashmap de usuarios
	 * 
	 * @return the usuarios
	 */
	public HashMap<String, Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * Retorna true si existe el email dado
	 * 
	 * @param email
	 * @return
	 */
	public boolean existeEmail(String email) {
		boolean existe = false;

		if (!usuarios.isEmpty()) {
			Usuario user;
			for (String key : usuarios.keySet()) {
				user = usuarios.get(key);

				if (user.getEmail().equals(email)) {
					existe = true;
					break;
				}

			}
		}

		return existe;
	}

	
	/**
	 * 
	 * @param txt
	 * @return
	 */
	public boolean validateString(String txt) {
		// false si no es valido
		String regx = "^[\\p{L} .'-]+$";
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(txt);
		return matcher.find();
	}

	
	/**
	 * @param txt
	 * @return
	 */
	public boolean validatePass(String txt) {
		// false si no es valido
		String regx = "^(?=\\S+$).{8,}$";
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(txt);
		return matcher.find();
	}

	

	/**
	 * @param email
	 * @param pass
	 * @return
	 */
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
					sesion.setLogin_time();
					break;
				}
			}

		}
		return logged;
	}
}
