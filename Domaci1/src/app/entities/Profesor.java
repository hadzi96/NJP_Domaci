package app.entities;

import app.annotations.Column;
import app.annotations.Entity;

@Entity
public class Profesor extends BasicEntity {
	@Column
	private String ime;

	public Profesor(int id, String ime) {
		super(id);
		this.ime = ime;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

}
