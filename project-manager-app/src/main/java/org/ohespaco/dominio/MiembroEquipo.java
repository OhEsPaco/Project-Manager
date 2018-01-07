package org.ohespaco.dominio;

public class MiembroEquipo {
	private String uuid_proyecto;
	private String uuid_usuario;
	private String rol;
	private String uuid;

	public MiembroEquipo(String uuid,String uuid_proyecto, String uuid_usuario, String rol) {
		this.uuid_proyecto = uuid_proyecto;
		this.uuid_usuario = uuid_usuario;
		this.rol = rol;
		this.uuid=uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
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

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	public String toString() {
		Usuario user=GestorUsuarios.getInstancia("").getUserByUuid(uuid_usuario);
		String str="";
		if(user!=null) {
			str=user.getNombre()+" "+user.getApellidos();
		}
		return str;
	}
}
