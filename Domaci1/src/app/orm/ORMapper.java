package app.orm;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ORMapper {

	@SuppressWarnings("rawtypes")
	public boolean insert(Object obj) throws Exception {
		Class cl = obj.getClass();

		Field[] fields = cl.getDeclaredFields();
		for (Field field : fields) {
			Class type = field.getType();
			String name = field.getName();
			field.setAccessible(true);
			Object value = field.get(obj);

			System.out.println(type + " | " + name + " | " + value + "\n");
		}

		return true;
	}


}
