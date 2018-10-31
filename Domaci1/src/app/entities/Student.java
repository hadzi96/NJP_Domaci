package app.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import app.annotations.Column;
import app.annotations.Entity;
import app.annotations.OneToMany;
import app.annotations.OneToOne;
import app.annotations.Table;

@Entity
@Table(name = "student")
public class Student extends BasicEntity {
	@Column(name = "IME")
	private String ime;

	private float nekiParametar = 55.5f;

	@OneToOne
	private Ocena ocena = new Ocena("Napredna Java", 10);

	@OneToMany
	private Collection<Ocena> skupOcena = new ArrayList<>();

	private Date datum = new java.sql.Date(System.currentTimeMillis());

	@Column(name = "prezime")
	private String prezime;

	public Student(String ime, String prezime) {
		super(1);
		this.ime = ime;
		this.prezime = prezime;

		skupOcena.add(ocena);
		skupOcena.add(ocena);
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

	public float getNekiParametar() {
		return nekiParametar;
	}

	public void setNekiParametar(float nekiParametar) {
		this.nekiParametar = nekiParametar;
	}

	public Ocena getOcena() {
		return ocena;
	}

	public void setOcena(Ocena ocena) {
		this.ocena = ocena;
	}

	public Collection<Ocena> getSkupOcena() {
		return skupOcena;
	}

	public void setSkupOcena(Collection<Ocena> skupOcena) {
		this.skupOcena = skupOcena;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

}
