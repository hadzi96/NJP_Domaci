package app.orm;

import java.lang.reflect.Field;

public class Polje {

	public Field field;
	public Class<?> type;
	public String name;
	public Object value;

	public Polje(Field field, Class<?> type, String name, Object value) {
		super();
		this.type = type;
		this.name = name;
		this.value = value;
		this.field = field;
	}

}
