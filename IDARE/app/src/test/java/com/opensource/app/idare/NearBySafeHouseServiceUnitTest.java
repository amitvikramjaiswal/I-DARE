package com.opensource.app.idare;

import android.content.Context;
import android.util.Log;

import com.opensource.app.idare.model.data.entity.NearBySafeHouseListEntity;
import com.opensource.app.idare.model.service.NearBySafeHouseService;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.utils.IDAREErrorWrapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NearBySafeHouseServiceUnitTest {

    private static final String TAG = NearBySafeHouseServiceUnitTest.class.getSimpleName();
    @Mock
    Context context;

    @Mock
    NearBySafeHouseService nearBySafeHouseService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInitiateSafeHousesSearch() {
        nearBySafeHouseService.initiateSafeHousesSearch(context, true, new IDAREResponseHandler.ResponseListener<NearBySafeHouseListEntity>() {
            @Override
            public void onSuccess(NearBySafeHouseListEntity response) {
                Log.d(TAG, response.getNearBySafeHouseResultEntities().toString());
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

}
