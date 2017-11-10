package com.opensource.app.idare.utils;

import com.opensource.app.idare.pojo.UserContext;

/**
 * Created by akokala on 11/10/2017.
 */

public class Session {

    public static UserContext userContext;

    private static Session session;

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    //Use resetSession() instead of destroySessionInfo()
    public static void destroySessionInfo() {
        Session.session = null;
    }

    public static UserContext getUserContext() {
        return userContext;
    }

    public static void setUserContext(UserContext userContext) {
        Session.userContext = userContext;
    }
}
