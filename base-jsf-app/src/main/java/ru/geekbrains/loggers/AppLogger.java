package ru.geekbrains.loggers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.Arrays;

public class AppLogger {

    @AroundInvoke
    public Object printLog(InvocationContext ctx) throws Exception {
        Logger logger = LoggerFactory.getLogger(ctx.getClass());
        logger.info("User called method: " + ctx.getMethod().getName());
        logger.info("Method parameters: " + Arrays.toString(ctx.getParameters()));
        return ctx.proceed();
    }
}