package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

public class SimpleTextViewModel extends IDareBaseViewModel {

    private Context context;
    private DataListener dataListener;
    private String title;
    private String body;
    private ObservableField<String> ofBody = new ObservableField<>("");

    public SimpleTextViewModel(Context context, DataListener dataListener) {
        super(context);
        this.context = context;
        this.dataListener = dataListener;
    }

    public SimpleTextViewModel build(String title, String body) {
        this.title = title;
        this.body = body;
        return this;
    }

    public void setData() {
        dataListener.setTitle(title);
        ofBody.set(body);
    }

    public ObservableField<String> getOfBody() {
        return ofBody;
    }

    public void setOfBody(ObservableField<String> ofBody) {
        this.ofBody = ofBody;
    }

    public interface DataListener extends IDareBaseViewModel.DataListener {
        void setTitle(String title);
    }
}
