package com.opensource.app.idare.service.Impl;


import com.opensource.app.idare.service.SessionFacade;

public class SessionFacadeImpl implements SessionFacade {

    private static SessionFacade sessionFacade;

    public static synchronized SessionFacade getInstance() {
        // Thread safe singleton, avoiding the race condition

        if (sessionFacade == null) {
            sessionFacade = (SessionFacade) new SessionFacadeImpl();

        }

        return sessionFacade;
    }

}
