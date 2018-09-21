package com.opensource.app.idare.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.opensource.app.idare.R;
import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.utils.Utils;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;
import com.opensource.app.idare.view.activity.EditProfileActivity;
import com.opensource.app.idare.view.fragment.ActiveProfileFragment;

/**
 * Created by akokala on 11/6/2017.
 */

public class PassiveProfileFragmentViewModel extends BaseViewModel {
    private DataListener dataListener;

    public PassiveProfileFragmentViewModel(Context context, DataListener dataListener) {
        super(context);
        this.dataListener = dataListener;
    }

    // OnClick of Make Active
    public View.OnClickListener makeActiveClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Onclick  - Check location is enabled, then make app Active
                onMakeActiveClicked();
            }
        };
    }

    private void onMakeActiveClicked() {
        if (Utils.isLocationServicesEnabled(getContext())) {
            dataListener.replaceFragment(ActiveProfileFragment.newInstance());
        } else {
            dataListener.showAlertDialog(null, getContext().getResources().getString(R.string.active_mode_message_from_passive), false, getContext().getResources().getString(R.string.btn_ok),
                    getContext().getResources().getString(R.string.btn_cancel), new AlertDialogHandler() {
                        @Override
                        public void onPositiveButtonClicked() {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            dataListener.startActivity(intent);
                        }

                        @Override
                        public void onNegativeButtonClicked() {

                        }
                    });
        }
    }

    // Set the user value in passiveUser Screen
    public void setValues() {
        String name = PreferencesManager.getInstance(getContext()).getUserDetails() == null ? "" : PreferencesManager.getInstance(getContext()).getUserDetails().getName();
        String welcomeTitle = getContext().getResources().getString(R.string.welcome_title, name);
        SpannableString spannableString = new SpannableString(welcomeTitle);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 8, welcomeTitle.length(), 0);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // OnEdit click Opens the EditProfile Screen with Prepopulated values
                dataListener.startActivity(EditProfileActivity.getStartIntent(getContext(), null));
            }
        }, 8, welcomeTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        dataListener.getTextView().setHighlightColor(Color.BLACK);
        dataListener.getTextView().setMovementMethod(LinkMovementMethod.getInstance());
        dataListener.getTextView().setText(spannableString);
    }

    public interface DataListener extends BaseViewModel.DataListener {

        void replaceFragment(Fragment fragment);

        TextView getTextView();

    }
}
