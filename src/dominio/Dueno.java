package dominio;

import java.io.Serializable;

public class Dueno implements Serializable {
	private int id;
	private String nombre;
	private String ci;
	private String telefono;
	private String email;

	public Dueno(int id, String nombre, String ci, String telefono, String email) {
		this.id = id;
		this.nombre = nombre;
		this.ci = ci;
		this.telefono = telefono;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
