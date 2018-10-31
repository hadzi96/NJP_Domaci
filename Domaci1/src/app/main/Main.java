package app.main;

import java.util.ArrayList;
import java.util.Date;

import app.entities.Fakultet;
import app.entities.Ocena;
import app.entities.Profesor;
import app.entities.Student;
import app.orm.ORMapper;

public class Main {

	public static void main(String[] args) {

		Ocena o1 = new Ocena(1, "KTG", 6);
		Ocena o2 = new Ocena(2, "NJP", 10);
		Ocena o3 = new Ocena(3, "BD", 8);

		ArrayList<Ocena> ocene = new ArrayList<>();
		ocene.add(o1);
		ocene.add(o2);
		ocene.add(o3);

		Profesor prof = new Profesor(0, "Milan Vidakovic");
		Profesor prof1 = new Profesor(1, "Paxy");

		Fakultet fax = new Fakultet(4, "Racunarski Fakultet", "Union");

		Student s = new Student(0, "Filip", "Hadzi-Ristic", prof, fax, ocene, new Date(System.currentTimeMillis()));
		ORMapper mapper = new ORMapper();

		try {
			mapper.insert(s);
			System.out.println("------------------------------------------------------");
			mapper.insert(prof1);
			Thread.sleep(3000);
			System.out.println("\n------------------------------------------------------");

			s.setName("FILIP");
			o1.setVrednost(8);

			Thread.sleep(1000);
			s.setMentor(prof1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
