package app.entities;

import app.annotations.Column;
import app.annotations.Entity;
import app.annotations.Id;
import app.annotations.OneToOne;
import app.annotations.Table;

@Entity
public class Ocena {

	@Id
	private int idOcene;

	private String predmet;
	@Column
	private int vrednost;

	public Ocena(int id, String predmet, int vrednost) {
		this.predmet = predmet;
		this.vrednost = vrednost;
		this.idOcene = id;
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
