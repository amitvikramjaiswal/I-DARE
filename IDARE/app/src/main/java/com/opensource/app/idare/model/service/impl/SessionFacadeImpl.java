package com.opensource.app.idare.model.service.impl;


import com.opensource.app.idare.model.service.SessionFacade;

public class SessionFacadeImpl {

    private static SessionFacade sessionFacade;

    public static synchronized SessionFacade getInstance() {
        // Thread safe singleton, avoiding the race condition

        if (sessionFacade == null) {
            sessionFacade = (SessionFacade) new SessionFacadeImpl();

        }

        return sessionFacade;
    }

}
