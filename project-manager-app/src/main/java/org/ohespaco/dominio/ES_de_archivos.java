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

import java.io.FileWriter;
import java.io.IOException;

import org.ohespaco.exceptions.EscrituraErronea;

public class ES_de_archivos {
	/**
	 * escribir_linea(..) recibe una ruta, un flag para borrar o no lo anterior, y
	 * la linea a escribir. Escribe la linea en el archivo.
	 *
	 * @param path
	 * @param borrar_archivo
	 * @param linea
	 * @throws EscrituraErronea
	 */
	public static void escribir_linea(String path, boolean borrar_archivo, String linea) throws EscrituraErronea {

		FileWriter writer = null;

		try {

			if (borrar_archivo == true) {
				writer = new FileWriter(path, false);
			} else {
				writer = new FileWriter(path, true);
			}

			writer.write(linea);

		} catch (IOException e) {
			throw new EscrituraErronea();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				throw new EscrituraErronea();
			}
		}
	}
}
