package app.orm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;

import app.annotations.Entity;
import app.annotations.Id;
import app.annotations.Table;

public class ORMapper {

	public boolean insert(Object obj) {
		if (!obj.getClass().isAnnotationPresent(Entity.class)) {
			return false;
		}

		ArrayList<Polje> polja = getPolja(obj);
		String table = getTableName(obj);
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

	@SuppressWarnings("rawtypes")
	private ArrayList<Polje> getPolja(Object obj) {
		try {
			Class cl = obj.getClass();
			ArrayList<Field> fields = new ArrayList<>();
			fields = getAllFields(fields, cl);
			ArrayList<Polje> polja = new ArrayList<>();

			for (Field field : fields) {
				Class<?> type = field.getType();
				String name = field.getName();
				field.setAccessible(true);
				Object value = field.get(obj);

				// ovo treba premestiti negde drugde jer ova metoda samo treba da vrati polja
				if (type.isAnnotationPresent(Entity.class)) {
					Object id = getId(value);
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

	@SuppressWarnings("rawtypes")
	private String getTableName(Object obj) {
		try {
			String tableName = null;
			Class cl = obj.getClass();
			Annotation[] anotacije = cl.getAnnotations();

			for (Annotation a : anotacije) {
				if (a instanceof Table) {
					tableName = ((Table) a).name();
				}
			}

			if (tableName == null || tableName.equals("")) {
				tableName = cl.getName().split("\\.")[cl.getName().split("\\.").length - 1];
			}

			return tableName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private ArrayList<Field> getAllFields(ArrayList<Field> fields, Class<?> type) {

		if (type.getSuperclass() != null) {
			getAllFields(fields, type.getSuperclass());
		}
		// fields.addAll(Arrays.asList(type.getDeclaredFields()));

		for (Field f : type.getDeclaredFields()) {

			if (f.getType() != JoinPoint.StaticPart.class) {
				fields.add(f);
			}
		}
		return fields;
	}

	private Object getId(Object obj) {
		try {
			Class<?> cl = obj.getClass();
			ArrayList<Field> fields = new ArrayList<>();
			fields = getAllFields(fields, cl);

			for (Field f : fields) {
				Annotation[] annotations = f.getAnnotations();
				for (Annotation a : annotations) {
					if (a instanceof Id) {
						f.setAccessible(true);
						Object value = f.get(obj);
						return value;
					}
				}
			}

		} catch (Exception e) {
			// e.printStackTrace();
		}

		return null;
	}

}
