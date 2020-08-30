package com.udyogg.services.common.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.udyogg.services.common.error.ErrorCodes.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = "timestamp", ignoreUnknown = true)
public final class ErrorResponse {

    private String code;
    private String message;
    private long timeStamp;
    private List<String> errors = new ArrayList<>();

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
        this.timeStamp = Instant.now().toEpochMilli();
    }

}
