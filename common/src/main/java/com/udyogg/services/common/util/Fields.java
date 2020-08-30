package com.udyogg.services.common.util;


import com.udyogg.services.common.error.ErrorCodes.ErrorCode;
import com.udyogg.services.common.exception.CustomException;

/**
 * @author Ravi Singh
 * @created 22/04/2020 - 15:15
 * @project contra
 */
public enum Fields {

    /* Missing Trace ID */
    TRACEID{
        @Override
        public void validate(String input) {
            if(input == null) {
                throw new CustomException(ErrorCode.MISSING_TRACE_ID);
            }
        }
        @Override
        public String getName() {
            return "getTraceId";
        }
    },

    /* Missing Access Token */
    ACCESS_TOKEN{
        @Override
        public void validate(String input) {
            if(input == null) {
                throw new CustomException(ErrorCode.MISSING_ACCESS_TOKEN);
            }

        }

        @Override
        public String getName() {
            return "getAccessToken";
        }
    },

    /* Missing Client Id */
    CLIENT_ID{
        @Override
        public void validate(String input) {
            if(input == null) {
                throw new CustomException(ErrorCode.MISSING_CLIENT_ID);
            }
        }

        @Override
        public String getName() {
            return "getClientId";
        }
    };

    public abstract String getName();
    public abstract void validate(String input);
}
