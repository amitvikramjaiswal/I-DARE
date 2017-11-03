package com.opensource.app.idare.viewmodel;

import android.content.Context;

import com.opensource.app.idare.viewmodel.to.RegisterTO;

/**
 * Created by amitvikramjaiswal on 25/05/16.
 */
public class RegisterViewModel extends BaseViewModel {

    private Context context;
    private RegisterTO registerTO;

    public RegisterViewModel(Context context, RegisterTO registerTO) {
        super(context);
        this.context = context;
        this.registerTO = registerTO;
    }

    public RegisterTO getRegisterTO() {
        return registerTO;
    }

    public void setRegisterTO(RegisterTO registerTO) {
        this.registerTO = registerTO;
    }

    public void onMobileTextChanged() {

    }

    @Override
    public void onDestroy() {

    }
}
