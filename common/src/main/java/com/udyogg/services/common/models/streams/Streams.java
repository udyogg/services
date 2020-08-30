package com.udyogg.services.common.models.streams;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author Ravi Singh
 * @created 22/04/2020 - 14:09
 * @project contra
 */
@Getter
@Setter
@SuperBuilder
public class Streams {

    private String traceId;

    private String accessToken;

    private String clientId;


}
