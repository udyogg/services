package com.udyogg.services.common.connector;


import com.udyogg.services.common.logging.LoggerConstants;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.net.URI;

@Builder
@Getter
public final class ConnectorRequestBody {

    private URI uri;

    private HttpMethod method;

    private HttpHeaders headers;

    private Object body;

    private LoggerConstants.STATE state;

    private LoggerConstants.APIS api;

}

