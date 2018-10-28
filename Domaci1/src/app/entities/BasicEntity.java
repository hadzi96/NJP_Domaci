package app.entities;

import app.annotations.Column;
import app.annotations.EntitySuperClass;
import app.annotations.Id;

@EntitySuperClass
public abstract class BasicEntity {
	@Id
	@Column(name = "ID")
	private int id;

	public BasicEntity(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicEntity other = (BasicEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
