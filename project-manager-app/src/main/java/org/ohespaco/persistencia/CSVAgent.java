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
package org.ohespaco.persistencia;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.ohespaco.exceptions.ErrorWritingCSV;

public class CSVAgent {

	private final String PATH;
	private final CSVFormat CSV_FILE_FORMAT_READ = CSVFormat.EXCEL.withHeader();
	private final CSVFormat CSV_FILE_FORMAT_WRITE = CSVFormat.EXCEL;

	/**
	 * CSVAgent permite leer y escribir documentos en formato CSV.
	 * 
	 * @param PATH
	 */
	public CSVAgent(String PATH) {
		this.PATH = PATH;
	}

	/**
	 * Lee un archivo CSV y devuelve una estructura iterable.
	 * 
	 * @return Iterable<CSVRecord>
	 * @throws IOException
	 */
	public Iterable<CSVRecord> readCSV() throws IOException {
		Reader in = new FileReader(this.PATH);
		return CSV_FILE_FORMAT_READ.parse(in);
	}

	/**
	 * Escribe un archivo CSV a partir de un ArrayList.
	 * 
	 * @param data
	 * @throws ErrorWritingCSV
	 */
	public void writeToCSV(ArrayList<String> data) throws ErrorWritingCSV {
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;

		try {

			fileWriter = new FileWriter(PATH, true);
			csvFilePrinter = new CSVPrinter(fileWriter, CSV_FILE_FORMAT_WRITE);
			csvFilePrinter.printRecord(data);

		} catch (IOException e) {

			throw new ErrorWritingCSV();

		} finally {

			try {

				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();

			} catch (IOException e) {

				throw new ErrorWritingCSV();

			}

		}

	}

}
