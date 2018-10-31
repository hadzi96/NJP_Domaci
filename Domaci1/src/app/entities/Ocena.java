package app.entities;

import app.annotations.Column;
import app.annotations.Entity;
import app.annotations.Id;
import app.annotations.OneToOne;
import app.annotations.Table;

@Entity
public class Ocena {

	@Id
	private int idOcene = 10;
	@Column
	private String predmet;
	private int vrednost;

	public Ocena(String predmet, int vrednost) {
		super();
		this.predmet = predmet;
		this.vrednost = vrednost;
	}

	public int getId() {
		return idOcene;
	}

	public void setId(int id) {
		this.idOcene = id;
	}

	public String getPredmet() {
		return predmet;
	}

	public void setPredmet(String predmet) {
		this.predmet = predmet;
	}

	public int getVrednost() {
		return vrednost;
	}

	public void setVrednost(int vrednost) {
		this.vrednost = vrednost;
	}

}
