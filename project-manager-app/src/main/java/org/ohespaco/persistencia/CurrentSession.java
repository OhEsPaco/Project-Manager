package org.ohespaco.persistencia;

import org.ohespaco.dominio.Usuario;

public class CurrentSession {
	private static Usuario user=null;
	private static boolean logged=false;
	private static CurrentSession instancia=null;
	private static String pathCsvUsers;
	

	private CurrentSession() {
		
	}
	
	public static CurrentSession getInstancia() {
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
		CurrentSession.user = user;
	}

	/**
	 * @return the logged
	 */
	public boolean isLogged() {
		return logged;
	}

	/**
	 * @param logged the logged to set
	 */
	public void setLogged(boolean logged) {
		CurrentSession.logged = logged;
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
		CurrentSession.pathCsvUsers = pathCsvUsers;
	}

}
