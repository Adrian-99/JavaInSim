package pl.adrian.internal.packets.util;

import org.slf4j.Logger;

/**
 * This class contains useful methods for dealing with logger.
 */
public class LoggerUtils {
    private LoggerUtils() {}

    /**
     * Logs stacktrace of exception at debug logger level.
     * @param logger logger to log to
     * @param step name of the step when exception occurred
     * @param exception thrown exception
     */
    public static void logStacktrace(Logger logger, String step, Exception exception) {
        if (logger.isDebugEnabled()) {
            var messageBuilder = new StringBuilder(exception.getClass().getSimpleName())
                    .append(" thrown in ")
                    .append(step)
                    .append(": ")
                    .append(exception.getMessage());
            for (var stackTraceElement : exception.getStackTrace()) {
                messageBuilder.append("\nat ")
                        .append(stackTraceElement.toString());
            }
            logger.debug(messageBuilder.toString());
        }
    }
}
