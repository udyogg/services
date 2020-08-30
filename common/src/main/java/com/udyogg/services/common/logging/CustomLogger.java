package com.udyogg.services.common.logging;
import org.slf4j.Logger;

public final class CustomLogger {

    private static final LocalLogger localLogger = new LocalLogger();

    private CustomLogger() {
        // private constructor
    }

    private static ILogger getLogger() {
        if ("ppe".equals(System.getenv("ACTIVE_PROFILE")) || "prod".equals(System.getenv("ACTIVE_PROFILE"))) {
            return localLogger;
        }
        return localLogger;
    }

    public static Logger getColorLogger() {
        return LocalLogger.getColorLogger();
    }

    public static Logger getPrettyLogger() {
        return LocalLogger.getPrettyLogger();
    }

    public static void info(LoggerContext context, String message) {
        getLogger().info(context, message);
    }

    public static void error(LoggerContext context, String message) {
        getLogger().error(context, message);
    }

    public static void error(LoggerContext context, String message, Throwable ex) {
        getLogger().error(context, message, ex);
    }

    public static void error(String message, Throwable ex) {
        getLogger().error(message, ex);
    }

    public static void debug(LoggerContext context, String message) {
        getLogger().debug(context, message);
    }

    public static void incomingApiCall(LoggerContext context, String message) {
        getLogger().incomingApiCall(context, message);
    }

    public static void outgoingApiCall(LoggerContext context, String message) {
        getLogger().outgoingApiCall(context, message);
    }

}
