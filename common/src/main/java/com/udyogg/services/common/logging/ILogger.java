package com.udyogg.services.common.logging;

/**
 * @author Ravi Singh
 * @created 17/08/2020 - 1:37 pm
 * @project contra
 */
 interface ILogger {

     void info(LoggerContext context, String message);

     void error(LoggerContext context, String message);

     void error(LoggerContext context, String message, Throwable ex);

     void error(String message, Throwable ex);

     void debug(LoggerContext context, String message);

     void incomingApiCall(LoggerContext context, String message);

     void outgoingApiCall(LoggerContext context, String message);

}
