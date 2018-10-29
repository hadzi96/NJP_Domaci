package app.aspects;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import app.annotations.Entity;
import app.orm.MapperFunctions;
import app.orm.ORMapper;

@Aspect
public class AspectORM {

	ArrayList<Object> insertedObj = new ArrayList<>();

	@Around("execution(public boolean ORMapper.insert(Object))")
	public boolean interceptInsert(ProceedingJoinPoint pjp) {
		Object[] cached = pjp.getArgs();
		Object r = pjp.proceed(cached);
		boolean retVal = (boolean) r;

		if (retVal) {
			insertedObj.add(cached[0]);
		}

		return retVal;
	}

	@After("set(* *) && @annotation(app.annotations.Column) && args(newValue)")
	public void interceptWrite(JoinPoint thisJoinPoint, Object newValue) {

		if (insertedObj.contains(thisJoinPoint.getTarget())) {
			try {
				Object obj = thisJoinPoint.getTarget();
				Class<?> cl = obj.getClass();
				ArrayList<Field> fields = new ArrayList<>();
				fields = MapperFunctions.getAllFields(fields, cl);
				String name = "";

				for (Field field : fields) {
					field.setAccessible(true);
					Object value = field.get(obj);

					if (value == newValue) {
						name = field.getName();
						break;
					}

				}

				System.out.println("Update [" + name + "] to -> " + newValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
