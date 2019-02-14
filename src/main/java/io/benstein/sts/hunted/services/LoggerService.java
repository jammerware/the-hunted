package io.benstein.sts.hunted.services;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public final class LoggerService {
    public static <T> Logger getLogger(Class<T> incomingClass) {
        return LogManager.getLogger(incomingClass.getSimpleName());
    }
}