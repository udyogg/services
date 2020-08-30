package com.udyogg.services.common.logging;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.Markers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ravi Singh
 * @created 17/08/2020 - 12:50 pm
 * @project contra
 */
@Slf4j
final class LocalLogger implements ILogger {

    private static final Logger prettyLogger = LoggerFactory.getLogger("prettyLogger");
    private static final Logger colorLogger = LoggerFactory.getLogger("colorLogger");

    public static Logger getPrettyLogger() {
        return prettyLogger;
    }

    public static Logger getColorLogger() {
        return colorLogger;
    }

    public void info(LoggerContext context, String message) {
        log.info(Markers.appendEntries(context.build()), message);
    }

    public void error(LoggerContext context, String message) {
        prettyLogger.error(Markers.appendEntries(context.build()), message);
    }

    public void error(LoggerContext context, String message, Throwable ex) {
        prettyLogger.error(Markers.appendEntries(context.build()), message, ex);
    }

    public void error(String message, Throwable ex) {
        prettyLogger.error(message, ex);
    }

    public void debug(LoggerContext context, String message) {
        log.debug(Markers.appendEntries(context.build()), message);
    }

    public void incomingApiCall(LoggerContext context, String message) {
        String timeTaken = context.build().get(LoggerConstants.KEYS.TIME_TAKEN.value());
        String traceid = context.build().get(LoggerConstants.KEYS.TRACEID.value());
        String  requestMethod = context.build().get(LoggerConstants.KEYS.HTTPREQUESMETHOD.value());
        String uri = context.build().get(LoggerConstants.KEYS.REQUESTURI.value());
        String status = context.build().get(LoggerConstants.KEYS.HTTPSTATUSCODE.value());
        String responseSize = context.build().get(LoggerConstants.KEYS.RESPONSE_SIZE.value());

        colorLogger.info(String.format("%s <---%s<--- %s %s %s %s %s", traceid, "001", requestMethod, uri, status, timeTaken, responseSize));
    }

    public void outgoingApiCall(LoggerContext context, String message) {

        String traceId = context.build().get(LoggerConstants.KEYS.TRACEID.value());
        String requestMethod = context.build().get(LoggerConstants.KEYS.HTTPREQUESMETHOD.value());
        String requestUri = context.build().get(LoggerConstants.KEYS.REQUESTURI.value());


        colorLogger.info(String.format("%s --->%s---> %s %s", traceId, "001", requestMethod , requestUri));
    }

}
