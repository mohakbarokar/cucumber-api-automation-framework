package api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtility {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtility.class);

    // Logs a TRACE level message
    public static void logTrace(String message) {
        logger.trace(message);
    }

    // Logs a DEBUG level message
    public static void logDebug(String message) {
        logger.debug(message);
    }

    // Logs an INFO level message
    public static void logInfo(String message) {
        logger.info(message);
    }

    // Logs a WARN level message
    public static void logWarn(String message) {
        logger.warn(message);
    }

    // Logs an ERROR level message
    public static void logError(String message) {
        logger.error(message);
    }

    // Logs an ERROR level message with an exception
    public static void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
