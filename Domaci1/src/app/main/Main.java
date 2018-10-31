package app.main;

import app.entities.Student;
import app.orm.ORMapper;

public class Main {

	public static void main(String[] args) {

		Student st = new Student("Filip", "Hadzi-Ristic");
		System.out.println("----------");
		st.setIme("FILIP");
		ORMapper mapper = new ORMapper();

		try {
			mapper.insert(st);
			// Thread.sleep(3000);
			st.setIme("Ficko");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
