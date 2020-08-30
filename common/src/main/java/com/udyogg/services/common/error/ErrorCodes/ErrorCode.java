package com.udyogg.services.common.error.ErrorCodes;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.udyogg.services.common.constants.Constants.TEAM_CODE;


/**
 * The Enum ErrorCodee
 *
 * @author RaviSingh
 */
public enum ErrorCode {

    INTERNAL_ERROR("001", ErrorMessage.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),

    MISSING_ACCESS_TOKEN("002", ErrorMessage.MISSING_ACCESS_TOKEN.getMessage(), HttpStatus.BAD_REQUEST),

    MISSING_TRACE_ID("003", ErrorMessage.MISSING_TRACE_ID.getMessage(), HttpStatus.BAD_REQUEST),

    MISSING_CLIENT_ID("004", ErrorMessage.MISSING_CLIENT_ID.getMessage(), HttpStatus.BAD_REQUEST),

    UNAUTHORIZED_ACCESS_TOKEN("005", ErrorMessage.UNAUTHORIZED_ACCESS_TOKEN.getMessage(), HttpStatus.UNAUTHORIZED),

    BAD_REQUEST_ERROR("006", ErrorMessage.BAD_REQUEST_ERROR.getMessage(), HttpStatus.BAD_REQUEST),

    API_SERVER_ERROR("007", ErrorMessage.API_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),

    TIMEOUT_ERROR("008", ErrorMessage.TIMEOUT_ERROR.getMessage(), HttpStatus.GATEWAY_TIMEOUT),

    INVALID_SERVICE("009", ErrorMessage.INVALID_SERVICE.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),


    /* =============  01 :- Clubcard pass Errors ============== */

    UPDATE_PASS_FAILED("01.001", ErrorMessage.GOOGLE_SERVICE_UNAVAILABLE.getMessage(), HttpStatus.SERVICE_UNAVAILABLE),

    MISSING_CERTIFICATE_FILE("01.002", ErrorMessage.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),

    FETCH_PASS_FAILED("01.003", ErrorMessage.GOOGLE_SERVICE_UNAVAILABLE.getMessage(), HttpStatus.SERVICE_UNAVAILABLE),

    INSERT_NEW_PASS_FAILED("01.004", ErrorMessage.GOOGLE_SERVICE_UNAVAILABLE.getMessage(), HttpStatus.SERVICE_UNAVAILABLE),

    INVALID_DEVICE_TYPE("01.005", ErrorMessage.INVALID_DEVICE.getMessage(), HttpStatus.BAD_REQUEST),

    SECURITY_ISSUE_WITH_CERTIFICATE("01.006", ErrorMessage.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_EXPIRY_TIME("01.007", ErrorMessage.INVALID_EXPIRY_TIME.getMessage(), HttpStatus.BAD_REQUEST),

    INVALID_BARCODE_AND_CLUBCARD_NuMBER("01.008", ErrorMessage.INVALID_BARCODE_AND_CLUBCARD.getMessage(), HttpStatus.BAD_REQUEST),

    INVALID_IOS_CERTIIFCATES("01.009", ErrorMessage.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_IOS_PASS("01.010", ErrorMessage.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_CLUBCARD_NUMBER("01.011", ErrorMessage.INVALID_CLUBCARD_NUMBER.getMessage(), HttpStatus.BAD_REQUEST),

    NOT_LEVEL_16_TOKEN("01.012", ErrorMessage.NOT_LEVEL_16_TOKEN.getMessage(), HttpStatus.UNAUTHORIZED);


    @Getter private String code;

    @Getter private String message;

    @Getter private HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return String.format("%d.%s.%s", this.getHttpStatus().value(), TEAM_CODE, this.getCode());
    }


     private enum ErrorMessage {

        INVALID_SERVICE("invalid-service"),

        NOT_LEVEL_16_TOKEN("requires-level-16-access-token"),

        INVALID_CLUBCARD_NUMBER("clubcard-number-doesn't-match-with-customer-clubcards"),

        TIMEOUT_ERROR("timeout-error"),

        INVALID_BARCODE_AND_CLUBCARD("both-mobile-barcode-and-clubcard-number-can-not-be-null"),

        INVALID_EXPIRY_TIME("invalid-expiry-time"),

        INVALID_DEVICE("invalid-device-type-valid-are-ios-and-android"),

        GOOGLE_SERVICE_UNAVAILABLE("Google-service-is-not-available"),

        UNAUTHORIZED_ACCESS_TOKEN("unauthorized-token"),

        API_SERVER_ERROR("api-internal-server-error"),

        BAD_REQUEST_ERROR("bad-request"),

        INTERNAL_SERVER_ERROR("internal-server-error"),

        MISSING_ACCESS_TOKEN("missing-access-token"),

        MISSING_TRACE_ID("missing-trace-id"),

        MISSING_CLIENT_ID("missing-client-id");

        @Getter
        private String message;

        ErrorMessage(String message) {
            this.message = message;
        }
    }


}
