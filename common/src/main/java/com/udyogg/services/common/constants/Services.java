package com.udyogg.services.common.constants;

/**
 * @author Ravi Singh
 * @created 22/04/2020 - 19:20
 * @project contra
 */
public enum Services {

    /*====================================Profile======================================*/

    ES_PROFILE("esProfile"),
    GAPI_PROFILE("gapiProfile"),

    /*=====================================Identity=====================================*/

    ES_IDENTITY("esIdentity"),

    /*=====================================Loyalty=====================================*/

    ES_LOYALTY("esLoyalty");

    private String value;

    Services(String value) {
        this.value  = value;
    }

    public String value() {
        return this.value;
    }

}
