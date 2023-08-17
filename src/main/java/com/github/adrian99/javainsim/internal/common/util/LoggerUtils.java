/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.common.util;

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
