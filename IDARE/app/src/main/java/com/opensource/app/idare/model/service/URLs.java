package com.opensource.app.idare.model.service;

import java.lang.reflect.Type;

import static com.opensource.app.idare.utils.Utility.APP_KEY;
import static com.opensource.app.idare.utils.Utility.SERVICE_URL;

/**
 * URL enumerator. Base URL is loaded from build.gradle based on profile.
 * The relative url & version are configurable.
 */
public enum URLs {

    URL_CREATE_ACCOUNT("/user/", null, null, null) {
        @Override
        public String fullURL() {
            return SERVICE_URL + String.format(this.getRelURL()) + APP_KEY;
        }
    }, URL_REGISTER_FOR_PUSH("/register-device", null, null, null) {
        @Override
        public String fullURL() {
            return SERVICE_URL + APP_KEY + String.format(this.getRelURL());
        }
    };

    private final String relURL;
    private final String version;
    private final Type type;

    private final String requestParam;

    URLs(String desc, String version, Type type, String requestParam) {
        this.relURL = desc;
        this.version = version;
        this.type = type;
        this.requestParam = requestParam;
    }

    public abstract String fullURL();

    public String getVersion() {
        return this.version;
    }

    public String getRelURL() {
        return this.relURL;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public Type getType() {
        return type;
    }
}