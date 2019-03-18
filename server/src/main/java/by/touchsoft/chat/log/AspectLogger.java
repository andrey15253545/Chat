package by.touchsoft.chat.log;

import by.touchsoft.chat.model.User;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogger {

    private static final String COMMAND_EXECUTE = "%s execute command %s, answer - %s";
    private static final String MESSAGE_DOES_NOT_SEND_EXCEPTION = "text does not send";
    private static final String CONTROLLER_REQUEST_PROCESSED = "request \"%s\" processed : %s";
    private static final String PROCESSED = "method \"%s\" : %s";

    @AfterReturning(value = "execution(String by.touchsoft.chat.command.impl.*.execute(..)) && args(user,arg)", returning = "answer",
            argNames = "point,answer,user,arg" )
    public void logCommand(JoinPoint point, String answer, User user, String arg){
        Logger logger = Logger.getLogger(point.getTarget().getClass());
        logger.info(String.format(COMMAND_EXECUTE, user, arg, answer));
    }

    @AfterReturning(value = "execution(* by.touchsoft.chat.controller.*.*(..))", returning = "retObj")
    public void logControllerMethod(JoinPoint joinPoint, Object retObj){
        Logger logger = Logger.getLogger(joinPoint.getTarget().getClass());
        logger.info(String.format(CONTROLLER_REQUEST_PROCESSED,joinPoint.getSignature().getName(), retObj));
    }

    @AfterThrowing(value = "execution(* by.touchsoft.chat.response.impl.WebSocketResponseImpl.*(..))", throwing = "ex")
    public void logResponseException(JoinPoint joinPoint, Exception ex){
        Logger logger = Logger.getLogger(joinPoint.getTarget().getClass());
        logger.warn(MESSAGE_DOES_NOT_SEND_EXCEPTION, ex);
    }

    @AfterReturning(value = "execution(* by.touchsoft.chat.services.*.*(..))", returning = "retObj")
    public void logServiceMethod(JoinPoint joinPoint, Object retObj){
        Logger logger = Logger.getLogger(joinPoint.getTarget().getClass());
        logger.info(String.format(PROCESSED,joinPoint.getSignature().getName(), retObj));
    }

}
