package com.opensource.app.idare.utils;

import java.lang.reflect.Type;

/**
 * URL enumerator. Base URL is loaded from build.gradle based on profile.
 * The relative url & version are configurable.
 */
public enum URLs {

    URL_CREATE_ACCOUNT("", null, null, null) {
        @Override
        public String fullURL() {
            return Utility.SERVICE_URL + String.format(this.getRelURL());
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
