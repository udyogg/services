package com.udyogg.services.common.logging;

/**
 * @author Ravi Singh
 * @date 04/04/2020 - 14:21
 * @project contra
 */

public final class LoggerConstants {


    public enum KEYS {

        TRACEID("TraceId"),
        ERRORCODE("errorCode"),
        ERRORMESSAGE("errorMessage"),
        STACKTRACE("stackTrace"),
        REQUESTURI("requestUri"),
        HTTPREQUESMETHOD("httpRequestMethod"),
        HTTPSTATUSCODE("httpStatusCode"),
        TIME_TAKEN("timeTaken"),
        API("Api"),
        RESPONSE_SIZE("responseSize"),
        REQUEST_PATH("requestPath"),
        STATE("state"),
        ACCESS_TOKEN("token"),
        CLIENT_NAME("ClientName"),
        CLIENT_ID("ClientId");

        private final String value;

        KEYS(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }

    }


    public enum APIS {

        CREATE_PASS_API("CreateClubcardPassApi"),
        ES_PROFILE_PROFILE_DETAILS("esprofile_getProfileDetails"),
        IDENTITY_CLUBCARD_DETAILS("identity_getClubcardDetails"),
        LOYALTY_CLUBCARD_POINTS("loyalty_getClubcardPoints");

        private String value;

        APIS(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }


    public enum STATE {

        CREATE_PASS("CREATE_PASS"),
        GET_CLUBCARD_POINTS("GET_CLUBCARD_POINTS"),
        CREATE_IOS_PASS("CREATE_IOS_PASS"),
        CREATE_ANDROID_PASS("CREATE_ANDROID_PASS"),
        UPDATE_ANDROID_PASS("UPDATE_ANDROID_PASS"),
        FETCH_ANDROID_PASS("FETCH_ANDROID_PASS"),
        INSERT_ANDROID_PASS("INSERT_ANDROID_PASS"),
        GET_NAME_DETAILS("GET_NAME_DETAILS"),
        GET_CARD_SET_DETAILS("GET_CARD_SET_DETAILS"),
        FETCH_IMAGE("FETCH_IMAGES_IN_BATCH");

        private String value;

        STATE(String value) {
            this.value = value;
        }

        public String started() {
            return String.format("%s_%s", this.value, "STARTED");
        }

        public String finished() {
            return String.format("%s_%s", this.value, "FINISHED");
        }

        public String failed() {
            return String.format("%s_%s", this.value, "FAILED");
        }

    }
}