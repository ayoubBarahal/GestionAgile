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
public class ProductBacklogLog {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String LOG_FILE_PATH = "src/main/resources/LogFiles/ProductBacklogLog";

    @Around("execution(* project.gestionprojet.ServiceImpl.ProductBacklogServiceImpl.addProductBacklog(..))")
    public Object logAddProductBacklog(ProceedingJoinPoint joinPoint) throws Throwable {
        return logOperation(joinPoint, "Add Product Backlog");
    }

    @Around("execution(* project.gestionprojet.ServiceImpl.ProductBacklogServiceImpl.findProductBacklogByNom(..))")
    public Object logFindProductBacklogByNom(ProceedingJoinPoint joinPoint) throws Throwable {
        return logOperation(joinPoint, "Find Product Backlog By Nom");
    }

    @Around("execution(* project.gestionprojet.ServiceImpl.ProductBacklogServiceImpl.updateProductBacklog(..))")
    public Object logUpdateProductBacklog(ProceedingJoinPoint joinPoint) throws Throwable {
        return logOperation(joinPoint, "Update Product Backlog");
    }

    @Around("execution(* project.gestionprojet.ServiceImpl.ProductBacklogServiceImpl.deleteProductBacklog(..))")
    public Object logDeleteProductBacklog(ProceedingJoinPoint joinPoint) throws Throwable {
        return logOperation(joinPoint, "Delete Product Backlog");
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