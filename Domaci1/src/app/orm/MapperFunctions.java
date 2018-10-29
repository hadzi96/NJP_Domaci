package app.orm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.aspectj.lang.JoinPoint;

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

}
