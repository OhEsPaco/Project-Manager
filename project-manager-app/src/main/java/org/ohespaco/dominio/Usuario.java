package org.ohespaco.dominio;

public class Usuario {
	private String id;
	private String email;
	private String pass_hash;
	private String nombre;
	private String apellidos;
	private String rol;
	private String contacto;
	private String descripcion;
	private String foto;

	public Usuario(String id, String email, String pass_hash, String nombre, String apellidos, String rol, String contacto,
			String descripcion, String foto) {
		this.id = id;
		this.email = email;
		this.pass_hash = pass_hash;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.rol = rol;
		this.contacto = contacto;
		this.descripcion = descripcion;
		this.foto = foto;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the pass_hash
	 */
	public String getPass_hash() {
		return pass_hash;
	}

	/**
	 * @param pass_hash
	 *            the pass_hash to set
	 */
	public void setPass_hash(String pass_hash) {
		this.pass_hash = pass_hash;
	}

	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos
	 *            the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol
	 *            the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * @return the contacto
	 */
	public String getContacto() {
		return contacto;
	}

	/**
	 * @param contacto
	 *            the contacto to set
	 */
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the foto
	 */
	public String getFoto() {
		return foto;
	}

	/**
	 * @param foto
	 *            the foto to set
	 */
	public void setFoto(String foto) {
		this.foto = foto;
	}

}
