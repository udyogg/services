package com.udyogg.services.common.connector;


import com.google.gson.Gson;
import com.udyogg.services.common.constants.Constants;
import com.udyogg.services.common.error.ErrorCodes.ErrorCode;
import com.udyogg.services.common.exception.CustomException;
import com.udyogg.services.common.logging.CustomLogger;
import com.udyogg.services.common.logging.LoggerConstants;
import com.udyogg.services.common.logging.LoggerContext;
import com.udyogg.services.common.util.CommonUtils;
import lombok.Getter;
import lombok.Setter;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;

@Component
public final class ClassicConnector {
    @Autowired
    private WebClient webClient;

    @Getter
    @Setter
    private boolean ignoreHttpError;

    private boolean validate(ConnectorRequestBody connectorRequestBody) {
        return true;
    }

    private <T extends Object> long getContentLength(T content) {
        String json = content instanceof String ? (String) content : new Gson().toJson(content);
        return json.getBytes(StandardCharsets.UTF_8).length;
    }

    private <T extends Object> Mono<ResponseEntity<T>> makeApiCall(ConnectorRequestBody connectorRequestBody, Class<T> clz) {
        final long startTime = System.currentTimeMillis();

        /* Adding content Length */
        if(StringUtils.isEmpty(Long.toString(connectorRequestBody.getHeaders().getContentLength())) && connectorRequestBody.getBody() != null) {
            connectorRequestBody.getHeaders().setContentLength(getContentLength(connectorRequestBody.getBody()));
        }

        /* API Call Started */
         LoggerContext loggerContext = LoggerContext.builder()
                .add(LoggerConstants.KEYS.HTTPREQUESMETHOD, connectorRequestBody.getMethod().name())
                .add(LoggerConstants.KEYS.REQUESTURI, connectorRequestBody.getUri().toString())
                .add(LoggerConstants.KEYS.TRACEID, connectorRequestBody.getHeaders().get(LoggerConstants.KEYS.TRACEID.value()).toString())
                 .add(LoggerConstants.KEYS.API, connectorRequestBody.getApi().value())
                .add(LoggerConstants.KEYS.STATE, connectorRequestBody.getState().started());
         CustomLogger.outgoingApiCall(loggerContext, Constants.API_CALL_STARTED);
        return webClient.method(connectorRequestBody.getMethod())
                .uri(connectorRequestBody.getUri())
                .headers(h -> updateHeaders(connectorRequestBody.getHeaders(), h))
                .exchange()
                .flatMap(r -> r.toEntity(clz))
                .doOnSuccess(r -> {
                    long timeTaken = System.currentTimeMillis() - startTime;
                    long responseSize = getContentLength(r.getBody());
                    loggerContext.add(LoggerConstants.KEYS.TIME_TAKEN, CommonUtils.getTimeInString(timeTaken))
                            .add(LoggerConstants.KEYS.HTTPSTATUSCODE, String.valueOf(r.getStatusCode().value()))
                            .add(LoggerConstants.KEYS.STATE, connectorRequestBody.getState().finished())
                            .add(LoggerConstants.KEYS.RESPONSE_SIZE, CommonUtils.getSizeInString(responseSize));
                    if(!isIgnoreHttpError()) {
                        this.handle4XXOr5XXError(r);
                    }
                    CustomLogger.incomingApiCall(loggerContext, Constants.API_CALL_ENDED);
                })
                .doOnError( err -> {
                    CustomException error = CustomException.wrap(err);
                    loggerContext.add(LoggerConstants.KEYS.ERRORCODE, error.getErrorCode().getErrorCode())
                            .add(LoggerConstants.KEYS.ERRORMESSAGE, error.getErrorCode().getMessage())
                            .add(LoggerConstants.KEYS.HTTPSTATUSCODE, String.valueOf(error.getErrorCode().getHttpStatus().value()))
                            .add(LoggerConstants.KEYS.STATE, connectorRequestBody.getState().failed());
                    CustomLogger.incomingApiCall(loggerContext, Constants.API_CALL_FAILED);
                });

    }


    private void updateHeaders(HttpHeaders headers, HttpHeaders h) {
        if (MapUtils.isNotEmpty(headers)) {
            h.addAll(headers);
        }
    }

    private <T extends Object> void handle4XXOr5XXError(ResponseEntity<T> response) {
        int status = response.getStatusCodeValue();

        switch (status){
            case 400:
                throw new CustomException(ErrorCode.BAD_REQUEST_ERROR);
            case 401:
                throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS_TOKEN);
            case 500:
                throw new CustomException(ErrorCode.API_SERVER_ERROR);
            case 504:
                throw new CustomException(ErrorCode.TIMEOUT_ERROR);
        }
    }

    /**
     * make api call using spring async web client.
     *
     * @param connectorRequestBody
     * @return
     */
   public <T extends Object> Mono<ResponseEntity<T>> makeCall(ConnectorRequestBody connectorRequestBody, Class<T> clz) {
      return  Mono.just(connectorRequestBody)
               .flatMap((body) -> {
                   if(!this.validate(body)) {
                       Mono.error(new CustomException(ErrorCode.INTERNAL_ERROR));
                   }
                  return Mono.just(body);
               })
              .flatMap(body -> this.makeApiCall(body, clz)).publishOn(Schedulers.parallel());
   }

}