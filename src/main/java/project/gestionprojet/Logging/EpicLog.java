package project.gestionprojet.Logging;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.FileWriter;
import java.io.IOException;

@Aspect
@Component
public class EpicLog {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String LOG_FILE_PATH = "src/main/resources/LogFiles/EpicLog";

    @Around("execution(* project.gestionprojet.ServiceImpl.EpicServiceImpl.createEpic(..))")
    public Object logCreateEpic(ProceedingJoinPoint joinPoint) throws Throwable {
        return logOperation(joinPoint, "Create Epic");
    }

    @Around("execution(* project.gestionprojet.ServiceImpl.EpicServiceImpl.updateEpic(..))")
    public Object logUpdateEpic(ProceedingJoinPoint joinPoint) throws Throwable {
        return logOperation(joinPoint, "Update Epic");
    }

    @Around("execution(* project.gestionprojet.ServiceImpl.EpicServiceImpl.deleteEpic(..))")
    public Object logDeleteEpic(ProceedingJoinPoint joinPoint) throws Throwable {
        return logOperation(joinPoint, "Delete Epic");
    }

    @Around("execution(* project.gestionprojet.ServiceImpl.EpicServiceImpl.findAllEpicByProductBacklog(..))")
    public Object logFindAllEpicByProductBacklog(ProceedingJoinPoint joinPoint) throws Throwable {
        return logOperation(joinPoint, "Find All Epic By Product Backlog");
    }

    @Around("execution(* project.gestionprojet.ServiceImpl.EpicServiceImpl.getAllEpics(..))")
    public Object logGetAllEpics(ProceedingJoinPoint joinPoint) throws Throwable {
        return logOperation(joinPoint, "Get All Epics");
    }

    private Object logOperation(ProceedingJoinPoint joinPoint, String operation) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();

        String httpMethod = request.getMethod();
        String requestUrl = request.getRequestURL().toString();

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        String logMessage = String.format("[%s] %s - %s: %s in %dms with result: %s",
                httpMethod,
                requestUrl,
                operation,
                joinPoint.getSignature().toShortString(),
                executionTime,
                result);

        logger.info(logMessage);
        writeLogToFile(logMessage);

        return result;
    }

    private void writeLogToFile(String logMessage) {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE_PATH, true)) {
            fileWriter.write(logMessage + "\n");
        } catch (IOException e) {
            logger.error("Failed to write log to file", e);
        }
    }
}