package com.udyogg.services.common.models.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Ravi Singh
 * @created 22/04/2020 - 16:22
 * @project contra
 */
@Getter
@Setter
@SuperBuilder
public class Request {

    private UriComponentsBuilder uriBuilder;

    private HttpHeaders headers;

    private Object body;

    private String url;

}
