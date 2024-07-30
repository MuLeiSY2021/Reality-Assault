package indi.muleisy.ra.pub.utils.log;

import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class LogUtli {

    private static final ConcurrentHashMap<String,Logger> LOGGER_MAP = new ConcurrentHashMap<>();

    public static Logger Logging(Class<?> clazz) {
        return LOGGER_MAP.computeIfAbsent(clazz.getName(), Logger::getLogger);
    }
}
