package com.udyogg.services.common.logging;

import java.util.HashMap;

/**
 * @author Ravi Singh
 * @created 29/05/2020 - 12:38
 * @project contra
 */
public final class LoggerContext {

    private HashMap<String, String> logMap;

    private LoggerContext() {
        this.logMap = new HashMap<>();
    }

    public static LoggerContext builder() {
        return new LoggerContext();
    }

    public LoggerContext add(LoggerConstants.KEYS key, String  value) {
        this.logMap.put(key.value(), value);
        return this;
    }

    public HashMap<String, String> build() {
        return logMap;
    }
}
