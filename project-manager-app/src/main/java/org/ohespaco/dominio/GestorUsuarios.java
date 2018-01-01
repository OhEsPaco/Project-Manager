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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.validator.routines.EmailValidator;
import org.ohespaco.exceptions.ErrorWritingCSV;
import org.ohespaco.exceptions.EscrituraErronea;
import org.ohespaco.persistencia.CSVAgent;
import org.ohespaco.persistencia.CurrentSession;

public class GestorUsuarios {
	private static String path;
	private static HashMap<String, Usuario> usuarios = new HashMap<String, Usuario>();
	private static final String HEADER_CSV = "uuid,email,pass_hash,nombre,apellidos,rol,contacto,descripcion,foto";
	private static GestorUsuarios instancia = null;
	private static DefaultListModel<Usuario> listaUsuarios = new DefaultListModel<Usuario>();


	private GestorUsuarios(String path) {
		this.path = path;
		inicializarCSV();
	}

	public static GestorUsuarios getInstancia(String path) {
		if (instancia == null) {
			instancia = new GestorUsuarios(path);

		}
		return instancia;
	}

	
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
		
		if (!usuarios.isEmpty()) {
			
			for (String key : usuarios.keySet()) {
				user = usuarios.get(key);
				listaUsuarios.addElement(user);
			}
		}
		

	}
	
	public DefaultListModel<Usuario> getDefaultList(){
		return listaUsuarios;
	}
	public void registrarUsuario(String email,String pass, String nombre,String apellidos,String rol,String contacto,String descripcion,String foto) {
		//UUID.randomUUID().toString();
		Usuario user = new Usuario(UUID.randomUUID().toString(), email, Hash.md5(pass), nombre, apellidos, rol, contacto, descripcion, foto);
		escribirUsuario(user);
		usuarios.put(user.getUuid(), user);
		listaUsuarios.addElement(user);
	}

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
			
			agente.writeToCSV(p,path);
			
			
		} catch (ErrorWritingCSV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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

	// false si no es valido
	public boolean validateString(String txt) {
		String regx = "^[\\p{L} .'-]+$";
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(txt);
		return matcher.find();
	}
	
	// false si no es valido
		public boolean validatePass(String txt) {
			String regx = "^(?=\\S+$).{8,}$";
			Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(txt);
			return matcher.find();
		}
		



	public boolean usuarioValido(Usuario user, String salida) {
		boolean valido = true;
		boolean primero = true;
		String coma = ", ";
		salida = "";
		if (GestorUsuarios.getInstancia("").existeEmail(user.getEmail())
				|| !EmailValidator.getInstance().isValid(user.getEmail())) {
			salida = "email";
			valido = false;
			primero = false;
		}

		if (!validateString(user.getNombre())) {
			if (primero) {
				salida = "nombre";
			} else {
				salida = salida + coma + "email";

			}
			primero = false;
			valido = false;
		}
		if (!validateString(user.getApellidos())) {
			if (primero) {
				salida = "apellidos";
			} else {
				salida = salida + coma + "apellidos";

			}
			primero = false;
			valido = false;
		}
		
		
		
		return valido;

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
