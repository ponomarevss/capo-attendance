package ru.sspo.aspect.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    @Pointcut("@annotation(ru.sspo.aspect.logging.Logging)")
    public void loggingMethodsPointcut() {}

    @Pointcut("@within(ru.sspo.aspect.logging.Logging)")
    public void loggingTypePointcut() {}

    @Before(value = "loggingMethodsPointcut() || loggingTypePointcut()")
    public void logArgsTypesAndValues(JoinPoint joinPoint) {
            Object[] args = joinPoint.getArgs();
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            log.atInfo().log(
                    "{}#{} -> {}",
                    methodSignature.getDeclaringTypeName(),
                    methodSignature.getName(),
                    argsToString(args)
            );
    }

    private static String argsToString(Object[] args) {
        StringBuilder argsSb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                argsSb.append(", ");
            }
            argsSb.append(args[i].getClass().getSimpleName()).append(" = ").append(args[i]);
        }
        return argsSb.toString();
    }
}
