package app.entities;

import app.annotations.Column;
import app.annotations.Entity;
import app.annotations.Table;

@Entity
@Table(name = "student")
public class Student extends BasicEntity {
	@Column(name = "ime")
	private String ime;

	@Column(name = "prezime")
	private String prezime;

	public Student(String ime, String prezime) {
		super();
		this.ime = ime;
		this.prezime = prezime;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

}
