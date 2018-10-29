package app.orm;

import java.lang.reflect.Field;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import app.annotations.Entity;

public class ORMapper {

	public boolean insert(Object obj) {
		try {
			if (!obj.getClass().isAnnotationPresent(Entity.class)) {
				return false;
			}

			ArrayList<Polje> polja = MapperFunctions.getPolja(obj);
			String table = MapperFunctions.getTableName(obj);
			if (polja == null || table == null)
				return false;

			String sqlUpit = "INSERT INTO " + table + "(%s) VALUES (%s)";
			String rows = "";
			String values = "";

			// prilikom prolaska kroz sva polja treba proveriti anotacije svakog polja i
			// izvrsiti izmene na osnovu tih annotacija
			for (Polje p : polja) {

				if (MapperFunctions.isComplexType(p)) {
					insert(p.value);
					Object id = MapperFunctions.getId(p.value);

					p.value = id;
					p.type = id.getClass();
				}

				rows += p.name + ", ";
				if (p.type == String.class)
					values += "'" + p.value + "', ";
				else
					values += p.value + ", ";
			}

			rows = rows.substring(0, rows.length() - 2);
			values = values.substring(0, values.length() - 2);

			sqlUpit = String.format(sqlUpit, rows, values);
			System.out.println(sqlUpit);

			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public boolean update(Object obj, Object entry) {
		try {
			if (!obj.getClass().isAnnotationPresent(Entity.class)) {
				return false;
			}

			String name = MapperFunctions.getNameFromObj(obj, entry);

			System.out.println("update: " + name + "->" + entry);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
