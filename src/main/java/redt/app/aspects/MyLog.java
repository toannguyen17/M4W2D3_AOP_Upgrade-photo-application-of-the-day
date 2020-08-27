package redt.app.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import redt.app.model.Feedback;

@Component
@Aspect
public class MyLog {
    @AfterThrowing(pointcut = "within(redt.app.controller.HomeController)", throwing="exception")
    public void log(JoinPoint joinPoint, Throwable exception){
        System.out.println("-------------------------------------");
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        System.out.println(className + "." + methodName);

        for(Object obj: joinPoint.getArgs()){
            if (obj instanceof Feedback){
                System.out.println(obj);
            }
        }

//        System.out.println("Inside afterThrowingAdvice() method....= " + joinPoint.getSignature().getName() + " method");
        System.out.println("Exception= " + exception);
    }
}
