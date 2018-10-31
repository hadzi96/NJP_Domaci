package app.orm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.aspectj.lang.JoinPoint;

import app.annotations.Column;
import app.annotations.Entity;
import app.annotations.Id;
import app.annotations.Table;

public class MapperFunctions {

	@SuppressWarnings("rawtypes")
	public static String getTableName(Object obj) {
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

	public static ArrayList<Field> getAllFields(ArrayList<Field> fields, Class<?> type) {

		if (type.getSuperclass() != null) {
			getAllFields(fields, type.getSuperclass());
		}

		// izbacujemo polja koja se javljaju usled JoinPoint-a
		for (Field f : type.getDeclaredFields()) {

			if (f.getType() != JoinPoint.StaticPart.class) {
				fields.add(f);
			}
		}
		return fields;
	}

	public static Object getId(Object obj) {
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

	public static String getNameFromObj(Object holder, Object obj) throws Exception {
		Class<?> cl = holder.getClass();
		ArrayList<Field> fields = new ArrayList<>();
		fields = MapperFunctions.getAllFields(fields, cl);
		String name = null;

		for (Field field : fields) {
			field.setAccessible(true);
			Object value = field.get(holder);

			if (value == obj) {
				name = field.getName();
				break;
			}

		}

		return name;
	}

	public static boolean isComplexType(Polje p) throws Exception {
		if (p.value instanceof Collection<?>)
			return true;

		if (p.type.isAnnotationPresent(Entity.class)) {

			Object id = MapperFunctions.getId(p.value);
			if (id == null) {
				throw new Exception("Podpolje " + p.name + " ne sadrzi polje sa id anotacijom");
			} else {
				return true;
			}

		}
		// System.out.println("false: " + p.name);
		return false;
	}

	@SuppressWarnings("rawtypes")
	public static ArrayList<Polje> getPolja(Object obj) {
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

				if (field.isAnnotationPresent(Column.class)) {
					Column col = field.getAnnotation(Column.class);
					if (!col.name().equals("") && col.name() != null) {
						name = col.name();
					}
				}

				Polje p = new Polje(field, type, name, value);
				polja.add(p);
			}

			return polja;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

}
