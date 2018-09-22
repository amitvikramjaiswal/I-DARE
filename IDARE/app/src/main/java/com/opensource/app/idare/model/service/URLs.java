package com.opensource.app.idare.model.service;


import com.opensource.app.idare.model.data.entity.NearBySafeHouseListEntity;
import com.opensource.app.idare.model.data.entity.ProfilePic;
import com.opensource.app.idare.model.data.entity.RegisterDeviceResponse;
import com.opensource.app.idare.model.data.entity.TriggerNotificationResponseModel;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;

import java.lang.reflect.Type;

import static com.opensource.app.idare.utils.Constants.APP_KEY;
import static com.opensource.app.idare.utils.Constants.NEAR_BY_SEARCH_BASE_URL;
import static com.opensource.app.idare.utils.Constants.SERVICE_URL;

/**
 * URL enumerator. Base URL is loaded from build.gradle based on profile.
 * The relative url & version are configurable.
 */
public enum URLs {

    URL_CREATE_ACCOUNT("/user/%s", null, UserProfileResponseModel.class, null) {
        @Override
        public String fullURL() {
            return SERVICE_URL + String.format(this.getRelURL(), APP_KEY);
        }
    }, URL_IS_PASSWORD_EXISTS("/user/%s", null, UserProfileResponseModel[].class, null) {
        @Override
        public String fullURL() {
            return SERVICE_URL + String.format(this.getRelURL(), APP_KEY);
        }
    }, URL_UNREGISTER_FOR_PUSH("/appdata/%s/unregister-device", null, RegisterDeviceResponse.class, null) {
        @Override
        public String fullURL() {
            return SERVICE_URL + String.format(this.getRelURL(), APP_KEY);
        }
    }, URL_REGISTER_FOR_PUSH("/appdata/%s/register-device", null, RegisterDeviceResponse.class, null) {
        @Override
        public String fullURL() {
            return SERVICE_URL + String.format(this.getRelURL(), APP_KEY);
        }
    }, URL_UPDATE_PROFILE("/appdata/%s/iDareUsers", null, UserProfileResponseModel.class, null) {
        @Override
        public String fullURL() {
            return SERVICE_URL + String.format(this.getRelURL(), APP_KEY);
        }
    }, URL_FETCH_USERS("/appdata/%s/iDareUsers", null, UserProfileResponseModel[].class, null) {
        @Override
        public String fullURL() {
            return SERVICE_URL + String.format(this.getRelURL(), APP_KEY);
        }
    }, URL_FETCH_NEARBY_USERS("/appdata/%s/iDareUsers", null, UserProfileResponseModel[].class, null) {
        @Override
        public String fullURL() {
            return SERVICE_URL + String.format(this.getRelURL(), APP_KEY);
        }
    }, URL_TRIGGER_NOTIFICATION("/rpc/%s/custom/trigger-notification", null, TriggerNotificationResponseModel.class, null) {
        @Override
        public String fullURL() {
            return SERVICE_URL + String.format(this.getRelURL(), APP_KEY);
        }
    }
    , URL_IS_USER_EXISTS("/user/%s", null, UserProfileResponseModel[].class, null) {
        @Override
        public String fullURL() {
            return SERVICE_URL + String.format(this.getRelURL(), APP_KEY);
        }
    }, URL_NEAR_BY_SEARCH_BASE_URL(null, null, NearBySafeHouseListEntity.class, null) {
        @Override
        public String fullURL() {
            return NEAR_BY_SEARCH_BASE_URL;
        }
    }, URL_UPLOAD_PROFILE_PIC("/appdata/%s/profilePics", null, ProfilePic.class, null) {
        @Override
        public String fullURL() {
            return SERVICE_URL + String.format(this.getRelURL(), APP_KEY);
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
