package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.LayoutSearchCoreUserBinding;
import com.opensource.app.idare.view.activity.ActiveModeActivity;
import com.opensource.library.sosmodelib.utils.AlertDialogHandler;

/**
 * Created by akokala on 11/2/2017.
 */

public class CoreGroupViewModel extends IDareBaseViewModel {
    private DataListener dataListener;

    public CoreGroupViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
    }

    public View.OnClickListener onAddClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataListener.startActivity(ActiveModeActivity.getStartIntent(context));
//                LayoutSearchCoreUserBinding searchCoreUserBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_search_core_user, null, false);
//                SearchCoreUserViewModel viewModel = new SearchCoreUserViewModel(getContext(), CoreGroupViewModel.this);
//                searchCoreUserBinding.setViewModel(viewModel);
//                dataListener.showAlertDialog(searchCoreUserBinding.getRoot(), getContext().getString(R.string.btn_ok), null, new AlertDialogHandler() {
//                    @Override
//                    public void onPositiveButtonClicked() {
//
//                    }
//
//                    @Override
//                    public void onNegativeButtonClicked() {
//
//                    }
//                });
            }
        };
    }

    public interface DataListener extends IDareBaseViewModel.DataListener {

    }
}
