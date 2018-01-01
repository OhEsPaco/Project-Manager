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

import java.util.Date;

import org.ohespaco.dominio.Usuario;

public class CurrentSession {
	private static Usuario user=null;
	private static boolean logged=false;
	private static CurrentSession instancia=null;
	private static String pathCsvUsers;
	private static Date login_time;



	private CurrentSession() {
		
	}
	
	public static CurrentSession getInstancia() {
		if(instancia==null) {
			instancia=new CurrentSession();
		}
		return instancia;
	}

	/**
	 * @return the user
	 */
	public Usuario getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Usuario user) {
		this.user = user;
	}

	/**
	 * @return the logged
	 */
	public boolean isLogged() {
		return logged;
	}
	public Date getLogin_time() {
		return login_time;
	}

	public void setLogin_time() {
		long currentDateTime = System.currentTimeMillis();
		login_time=new Date(currentDateTime);
		
	}

	/**
	 * @param logged the logged to set
	 */
	public void setLogged(boolean logged) {
		this.logged = logged;
	}
	
	/**
	 * @return the pathCsvUsers
	 */
	public String getPathCsvUsers() {
		return pathCsvUsers;
	}

	/**
	 * @param pathCsvUsers the pathCsvUsers to set
	 */
	public void setPathCsvUsers(String pathCsvUsers) {
		this.pathCsvUsers = pathCsvUsers;
	}

}
