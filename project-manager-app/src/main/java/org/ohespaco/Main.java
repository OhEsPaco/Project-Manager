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

package org.ohespaco;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.csv.CSVRecord;
import org.ohespaco.dominio.Hash;
import org.ohespaco.exceptions.ErrorWritingCSV;
import org.ohespaco.persistencia.CSVAgent;
import org.ohespaco.persistencia.CurrentSession;
import org.ohespaco.presentacion.Login;

public class Main {

	public static void main(String[] args) {
		//CSVAgent agente = new CSVAgent("C:\\Users\\pacog\\Documents\\pruebas/users.csv");
		/*try {
			
			ArrayList<String> p = new ArrayList<String>();
			p.add(UUID.randomUUID().toString());
			p.add("pacogrc5@gmail.com");
			p.add(Hash.md5("github"));
			p.add("Francisco");
			p.add("Garcia Sanchez");
			p.add("CEO");
			p.add("Ciudad Real");
			p.add("CEO de vaporware");
			p.add("PLACEHOLDER");
			
			agente.writeToCSV(p);
			
			
		} catch (ErrorWritingCSV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		CurrentSession sesion = CurrentSession.getInstancia();
		sesion.setPathCsvUsers("users.csv");
		Login log = new Login();
		log.setVisible(true);
	}

}
