package app.aspects;

import java.util.ArrayList;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import app.annotations.Entity;
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
			System.out.println("ubacioo");
		}

		return retVal;
	}

	@After("set(* *) && @annotation(app.annotations.Column) && args(newValue)")
	public void interceptWrite(JoinPoint thisJoinPoint, Object newValue) {

		if (!thisJoinPoint.getTarget().getClass().isAnnotationPresent(Entity.class))
			return;

	}

}
