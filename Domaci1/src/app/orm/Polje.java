package app.orm;

public class Polje {

	public Class<?> type;
	public String name;
	public Object value;

	public Polje(Class<?> type, String name, Object value) {
		super();
		this.type = type;
		this.name = name;
		this.value = value;
	}

}
