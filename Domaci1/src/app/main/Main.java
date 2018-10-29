package app.main;

import app.entities.Student;
import app.orm.ORMapper;

public class Main {

	public static void main(String[] args) {

		Student st = new Student("Filip", "Hadzi-Ristic");
		System.out.println("----------");
		st.setIme("Ficko");
		ORMapper mapper = new ORMapper();

		try {
			mapper.insert(st);

			st.setIme("Ficko");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
