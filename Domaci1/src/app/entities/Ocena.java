package app.entities;

import app.annotations.Entity;
import app.annotations.Id;

@Entity
public class Ocena {

	@Id
	private int id = 10;
	private String predmet;
	private int vrednost;

	public Ocena(String predmet, int vrednost) {
		super();
		this.predmet = predmet;
		this.vrednost = vrednost;
	}

}
