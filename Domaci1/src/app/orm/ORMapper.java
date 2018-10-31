package app.orm;

import java.lang.reflect.Field;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import app.annotations.Entity;
import app.annotations.ManyToMany;
import app.annotations.ManyToOne;
import app.annotations.OneToMany;
import app.annotations.OneToOne;

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
					if (p.field.isAnnotationPresent(OneToOne.class)) {
						OneToOneInsert(p);

						rows += p.name + ", ";
						if (p.type == String.class)
							values += "'" + p.value + "', ";
						else
							values += p.value + ", ";
					} else {
						if (p.field.isAnnotationPresent(OneToMany.class)) {
							sqlUpit += OneToManyInsert(obj, p);
						} else {
							if (p.field.isAnnotationPresent(ManyToOne.class)) {
								ManyToOneInsert(p);
							} else {
								if (p.field.isAnnotationPresent(ManyToMany.class)) {
									ManyToManyInsert(p);
								}
							}
						}
					}
				} else {
					// sredjivanje query stringa
					rows += p.name + ", ";
					if (p.type == String.class)
						values += "'" + p.value + "', ";
					else
						values += p.value + ", ";
				}
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

	private void OneToOneInsert(Polje p) {
		insert(p.value);
		Object id = MapperFunctions.getId(p.value);

		p.value = id;
		p.type = id.getClass();
	}

	private String OneToManyInsert(Object obj, Polje p) throws Exception {
		if (!(p.value instanceof Collection<?>)) {
			throw new Exception("Podpolje " + p.name + " nije collection.. OneToMany disallowed");
		}
		Collection<Object> col = (Collection<Object>) p.value;
		Iterator<Object> iterator = col.iterator();
		if (col.isEmpty()) {
			return "";
		}
		Object o = null;
		while (iterator.hasNext()) {
			o = iterator.next();
			if (!(insert(o))) {
				throw new Exception("Collection " + p.name + " mora da sadrzi Entitije");
			}
		}

		String retSql = "";

		iterator = col.iterator();
		while (iterator.hasNext()) {
			o = iterator.next();

			String tableName = MapperFunctions.getTableName(obj) + "_" + MapperFunctions.getTableName(o);

			String firstId = MapperFunctions.getTableName(obj) + "ID";
			String secondId = MapperFunctions.getTableName(o) + "ID";

			String firstIdVal = MapperFunctions.getId(obj).toString();
			String secondIdVal = MapperFunctions.getId(o).toString();
			String sql = String.format("\nINSERT INTO %s (%s, %s) VALUES (%s, %s)", tableName, firstId, secondId,
					firstIdVal, secondIdVal);

			retSql += sql;
		}

		return retSql;
	}

	private void ManyToOneInsert(Polje p) {

	}

	private void ManyToManyInsert(Polje p) {

	}

}
