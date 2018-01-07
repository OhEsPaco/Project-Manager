package org.ohespaco.dominio;

public class MiembroTarea {
	private String uuid;
	private String uuid_proyecto;
	private String uuid_usuario;
	private String uuid_tarea;
	private String rol;

	public MiembroTarea(String uuid, String uuid_proyecto, String uuid_usuario, String uuid_tarea, String rol) {
		super();
		this.uuid = uuid;
		this.uuid_proyecto = uuid_proyecto;
		this.uuid_usuario = uuid_usuario;
		this.uuid_tarea = uuid_tarea;
		this.rol = rol;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid_proyecto() {
		return uuid_proyecto;
	}

	public void setUuid_proyecto(String uuid_proyecto) {
		this.uuid_proyecto = uuid_proyecto;
	}

	public String getUuid_usuario() {
		return uuid_usuario;
	}

	public void setUuid_usuario(String uuid_usuario) {
		this.uuid_usuario = uuid_usuario;
	}

	public String getUuid_tarea() {
		return uuid_tarea;
	}

	public void setUuid_tarea(String uuid_tarea) {
		this.uuid_tarea = uuid_tarea;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}
