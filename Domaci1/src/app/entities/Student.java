package app.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import app.annotations.Column;
import app.annotations.Entity;
import app.annotations.ManyToOne;
import app.annotations.OneToMany;
import app.annotations.OneToOne;
import app.annotations.Table;

@Entity
@Table(name = "Student")
public class Student extends BasicEntity {
	@Column(name = "ime")
	private String name;

	private String prezime;

	@Column
	@OneToOne
	private Profesor mentor;

	@ManyToOne
	private Fakultet fakultet;

	@Column
	@OneToMany
	private Collection<Ocena> ocene = new ArrayList<>();

	@Column
	private Date datum = new java.sql.Date(System.currentTimeMillis());

	public Student(int id, String name, String prezime, Profesor mentor, Fakultet fakultet, Collection<Ocena> ocene,
			Date datum) {
		super(id);
		this.name = name;
		this.prezime = prezime;
		this.mentor = mentor;
		this.fakultet = fakultet;
		this.ocene = ocene;
		this.datum = datum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Profesor getMentor() {
		return mentor;
	}

	public void setMentor(Profesor mentor) {
		this.mentor = mentor;
	}

	public Fakultet getFakultet() {
		return fakultet;
	}

	public void setFakultet(Fakultet fakultet) {
		this.fakultet = fakultet;
	}

	public Collection<Ocena> getOcene() {
		return ocene;
	}

	public void setOcene(Collection<Ocena> ocene) {
		this.ocene = ocene;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

}
