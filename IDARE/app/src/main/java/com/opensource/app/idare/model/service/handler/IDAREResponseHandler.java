package com.opensource.app.idare.model.service.handler;

import com.opensource.app.idare.utils.IDAREErrorWrapper;

/**
 * Created by akokala on 10/31/2017.
 */

public class IDAREResponseHandler {

    public interface ResponseListener<T> {
        void onSuccess(T response);
    }

    public interface ErrorListener {
        void onError(IDAREErrorWrapper error);
    }
}
