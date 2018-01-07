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

public class GestorMensajes {

	private static String path;

	private static HashMap<String, Mensaje> mensajes = new HashMap<String, Mensaje>();

	private static final String HEADER_CSV = "uuid,uuid_emisor,uuid_receptor,asunto,mensaje\n";

	private static GestorMensajes instancia = null;

	private static DefaultListModel<Mensaje> listamensajes = new DefaultListModel<Mensaje>();

	private GestorMensajes(String path) {
		GestorMensajes.path = path;
		inicializarCSV();
	}

	public static GestorMensajes getInstancia(String path) {
		if (instancia == null) {
			instancia = new GestorMensajes(path);

		}
		return instancia;
	}

	public void cargarmensajes() throws IOException {

		String uuid, uuid_emisor, uuid_receptor, asunto, mensaje;
		Mensaje msg;
		mensajes = new HashMap<String, Mensaje>();
		CSVAgent agente = new CSVAgent();
		Iterable<CSVRecord> records = agente.readCSV(path);
		for (CSVRecord record : records) {
			uuid = record.get("uuid");
			uuid_emisor = record.get("uuid_emisor");
			uuid_receptor = record.get("uuid_receptor");
			asunto = record.get("asunto");
			mensaje = record.get("mensaje");

			msg = new Mensaje(uuid, uuid_emisor, uuid_receptor, asunto, mensaje);
			mensajes.put(uuid, msg);
		}
		listamensajes = new DefaultListModel<Mensaje>();
		if (!mensajes.isEmpty()) {

			for (String key : mensajes.keySet()) {
				msg = mensajes.get(key);
				listamensajes.addElement(msg);
			}
		}

	}

	public DefaultListModel<Mensaje> getDefaultList(String uuid_receptor) {
		Mensaje msg;
		DefaultListModel<Mensaje> listauser = new DefaultListModel<Mensaje>();
		if (!mensajes.isEmpty()) {

			for (String key : mensajes.keySet()) {
				msg = mensajes.get(key);
				if (msg.getUuid_receptor().equals(uuid_receptor)) {
					listauser.addElement(msg);
				}

			}
		}
		return listauser;
	}

	public void nuevoMensaje(String uuid_emisor, String uuid_receptor, String asunto, String mensaje) {
		Mensaje msg = new Mensaje(UUID.randomUUID().toString(), uuid_emisor, uuid_receptor, asunto, mensaje);

		mensajes.put(msg.getUuid(), msg);
		listamensajes.addElement(msg);
	}

	public void guardarmensajes() {
		Mensaje user_aux;
		crearCSV();
		if (!mensajes.isEmpty()) {
			for (String key : mensajes.keySet()) {
				user_aux = mensajes.get(key);
				escribirMensaje(user_aux);
			}
		}
	}

	public void escribirMensaje(Mensaje msg) {
		CSVAgent agente = new CSVAgent();
		try {

			ArrayList<String> p = new ArrayList<String>();
			p.add(msg.getUuid());
			p.add(msg.getUuid_emisor());
			p.add(msg.getUuid_receptor());
			p.add(msg.getAsunto());
			p.add(msg.getMensaje());

			agente.writeToCSV(p, path);

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
			cargarmensajes();
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

	public HashMap<String, Mensaje> getmensajes() {
		return mensajes;
	}

}
