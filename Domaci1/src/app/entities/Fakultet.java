package app.entities;

import app.annotations.Column;
import app.annotations.Entity;

@Entity
public class Fakultet extends BasicEntity {

	@Column
	private String naziv;

	@Column
	private String univerzitet;

	public Fakultet(int id, String naziv, String univerzitet) {
		super(id);
		this.naziv = naziv;
		this.univerzitet = univerzitet;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getUniverzitet() {
		return univerzitet;
	}

	public void setUniverzitet(String univerzitet) {
		this.univerzitet = univerzitet;
	}

}
