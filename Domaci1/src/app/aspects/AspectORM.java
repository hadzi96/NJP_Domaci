package app.aspects;

import java.util.ArrayList;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

import app.annotations.Entity;
import app.orm.ORMapper;

@Aspect
public class AspectORM {

	ArrayList<Object> insertedObj = new ArrayList<>();

	@After("set(* *) && @annotation(app.annotations.Column) && args(newValue)")
	public void interceptWrite(JoinPoint thisJoinPoint, Object newValue) {

		if (!thisJoinPoint.getTarget().getClass().isAnnotationPresent(Entity.class))
			return;

		if (!insertedObj.contains(thisJoinPoint.getTarget())) {
			if (ORMapper.insert(thisJoinPoint.getTarget()))
				insertedObj.add(thisJoinPoint.getTarget());
		} else {
			System.out.println("UPDATE!!");
		}

	}

}
