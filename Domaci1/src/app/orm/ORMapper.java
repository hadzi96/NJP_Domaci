package app.orm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class ORMapper {

	@SuppressWarnings("rawtypes")
	public boolean insert(Object obj) throws Exception {
		ArrayList<Polje> polja = getPolja(obj);
		String table = getTableName(obj);
		if (polja == null || table == null)
			return false;

		String sqlUpit = "INSERT INTO" + table + "(%s) VALUES (%s)";
		String rows = "";
		String values = "";

		for (Polje p : polja) {
			rows += p.name + ", ";
			values += p.value + ", ";
		}

		rows = rows.substring(0, rows.length() - 2);
		values = values.substring(0, values.length() - 2);

		sqlUpit = String.format(sqlUpit, rows, values);
		System.out.println(sqlUpit);

		return true;
	}

	@SuppressWarnings("rawtypes")
	private ArrayList<Polje> getPolja(Object obj) {
		try {
			Class cl = obj.getClass();
			ArrayList<Field> fields = new ArrayList<>();
			fields = getAllFields(fields, cl);
			ArrayList<Polje> polja = new ArrayList<>();

			for (Field field : fields) {
				Class type = field.getType();
				String name = field.getName();
				field.setAccessible(true);
				Object value = field.get(obj);

				Polje p = new Polje(type.toString(), name, value);
				polja.add(p);
			}

			return polja;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	private String getTableName(Object obj) {
		try {
			Class cl = obj.getClass();
			Annotation[] anotacije = cl.getAnnotations();

			for (Annotation a : anotacije) {
				System.out.println(a.toString());
			}

			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private ArrayList<Field> getAllFields(ArrayList<Field> fields, Class<?> type) {
		fields.addAll(Arrays.asList(type.getDeclaredFields()));

		if (type.getSuperclass() != null) {
			getAllFields(fields, type.getSuperclass());
		}

		return fields;
	}

}
