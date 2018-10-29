package app.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import app.annotations.Entity;

public class ORMapper {

	public boolean insert(Object obj) {
		if (!obj.getClass().isAnnotationPresent(Entity.class)) {
			return false;
		}

		ArrayList<Polje> polja = getPolja(obj);
		String table = MapperFunctions.getTableName(obj);
		if (polja == null || table == null)
			return false;

		String sqlUpit = "INSERT INTO " + table + "(%s) VALUES (%s)";
		String rows = "";
		String values = "";

		// prilikom prolaska kroz sva polja treba proveriti anotacije svakog polja i
		// izvrsiti izmene na osnovu tih annotacija
		for (Polje p : polja) {
			rows += p.name + ", ";
			if (p.type.equals("class java.lang.String"))
				values += "'" + p.value + "', ";
			else
				values += p.value + ", ";
		}

		rows = rows.substring(0, rows.length() - 2);
		values = values.substring(0, values.length() - 2);

		sqlUpit = String.format(sqlUpit, rows, values);
		System.out.println(sqlUpit);

		return true;
	}

	public boolean update(Object obj, Object entry) {
		try {
			if (!obj.getClass().isAnnotationPresent(Entity.class)) {
				return false;
			}

			String name = MapperFunctions.getNameFromObj(obj, entry);

			System.out.println(name + "->" + entry);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	private ArrayList<Polje> getPolja(Object obj) {
		try {
			Class cl = obj.getClass();
			ArrayList<Field> fields = new ArrayList<>();
			fields = MapperFunctions.getAllFields(fields, cl);
			ArrayList<Polje> polja = new ArrayList<>();

			for (Field field : fields) {
				Class<?> type = field.getType();
				String name = field.getName();
				field.setAccessible(true);
				Object value = field.get(obj);

				// ovo treba premestiti negde drugde jer ova metoda samo treba da vrati polja
				if (type.isAnnotationPresent(Entity.class)) {
					Object id = MapperFunctions.getId(value);
					if (id == null) {
						throw new Exception("Podpolje " + name + " ne sadrzi polje sa id anotacijom");
					} else {
						insert(value);
						value = id;
						type = id.getClass();
					}

				}

				Polje p = new Polje(type.toString(), name, value);
				polja.add(p);
			}

			return polja;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

}
