package com.udyogg.services.common.util;


import com.udyogg.services.common.exception.CustomException;
import com.udyogg.services.common.logging.LoggerConstants;
import com.udyogg.services.common.models.requests.Request;
import com.udyogg.services.common.models.streams.Streams;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

/**
 * @author Ravi Singh
 * @created 15/04/2020 - 21:26
 * @project contra
 */
public final class CommonUtils {

    /**
     * return time in ms or sec
     *
     * @param time
     * @return
     */
    public static String getTimeInString(long time) {
        double t = time/1000.0;
        return String.format("%ss",String.valueOf(t));
    }


    /**
     * return body size in kb of bytes
     *
     * @param size
     * @return
     */
    public static String getSizeInString(long size) {
        if(size > 1000) {
            return String.format("%sKB", String.valueOf(size/1000.0));
        }
        return String.format("%sB", String.valueOf(size));
    }


    /**
     *validate context for fields like traceId, clientId etc.
     *
     * @param fields
     * @param testClass
     */
    public static void  validateFields(Fields[] fields, Object testInstance, String className) {
        Stream.of(fields)
                .forEach(field -> {
                    try {
                        Object value = null;
                        if(field.getName() != null && className != null){
                            Class<?> cls = Class.forName(className);
                            value =cls.getDeclaredMethod(field.getName()).invoke(testInstance);
                        }
                        field.validate((String) value);
                    } catch(Exception e) {
                        throw CustomException.wrap(e);
                    }
                });

    }


    public static <T1 extends Streams, T2 extends Request>  T2 createRequestBody(T1 stream, T2 requestbody) {

        /* create headers */
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(stream.getAccessToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Accept-Language", "application/json");
        httpHeaders.set(LoggerConstants.KEYS.TRACEID.value(), stream.getTraceId());

        /* create uri builder */
        UriComponentsBuilder url =
                UriComponentsBuilder.fromHttpUrl(requestbody.getUrl());

        requestbody.setHeaders(httpHeaders);
        requestbody.setUriBuilder(url);

        return requestbody;
    }

    public static boolean isIsoDate(String str) {

        boolean res;
        try {
            Instant myDate = Instant.parse(str);
            res = true;
        } catch (DateTimeParseException e) {
            res = false;
        }
        return res;
    }
}
