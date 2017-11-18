package com.opensource.app.idare.viewmodel;


import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.opensource.app.idare.R;
import com.opensource.app.idare.application.IDareApp;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.utils.Utils;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;
import com.opensource.app.idare.view.activity.RegisterActivity;
import com.opensource.app.idare.view.fragment.ActiveProfileFragment;
import com.opensource.app.idare.view.fragment.AppTourFragment;
import com.opensource.app.idare.view.fragment.CoreListFragment;
import com.opensource.app.idare.view.fragment.DonateFragment;
import com.opensource.app.idare.view.fragment.InviteToIDareFragment;
import com.opensource.app.idare.view.fragment.PassiveFragment;
import com.opensource.app.idare.view.fragment.SettingsFragment;

/**
 * Created by akokala on 10/31/2017.
 */

public class MainActivityViewModel extends BaseViewModel implements LayoutPopUpViewModel.DataListener {
    private DataListener dataListener;
    private MenuItem mPreviousMenuItem;
    private LayoutPopUpViewModel layoutPopUpViewModel;
    private String userNameFromBundle;

    private ObservableField<LayoutPopUpViewModel> drawerLayoutInflater = new ObservableField<>();
    private ObservableField<Boolean> enableMakePassive = new ObservableField<>(false);

    public MainActivityViewModel(Context context, DataListener dataListener, String userNameFromBundle) {
        super(context);
        this.dataListener = dataListener;
        this.userNameFromBundle = userNameFromBundle;

         /*Intialise the LayoutPopUpViewModel and set the observable field*/
        layoutPopUpViewModel = new LayoutPopUpViewModel(getContext(), this);
        drawerLayoutInflater.set(layoutPopUpViewModel);

        // User is already logged in
        if (userNameFromBundle == null) {
            fetchUserDetails();
        }
    }

    public ObservableField<LayoutPopUpViewModel> getDrawerLayoutInflater() {
        return drawerLayoutInflater;
    }

    public ObservableField<Boolean> getEnableMakePassive() {
        return enableMakePassive;
    }

    // Onclick of make passive
    public View.OnClickListener onClickMakePassive() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open dialog
                dataListener.getDrawer().closeDrawer(Gravity.START);
                layoutPopUpViewModel.showAlertDialog();
            }
        };
    }

    public void toggleMakePassiveButton() {
        if (IDareApp.isActive()) {
            enableMakePassive.set(true);
        } else {
            enableMakePassive.set(false);
        }
    }

    public boolean onNavigationItemSelected(MenuItem menuItem, FragmentTransaction fragmentTransaction) {
        menuItem.setCheckable(true);
        menuItem.setChecked(true);

        if (mPreviousMenuItem != null) {
            mPreviousMenuItem.setChecked(false);
        }
        mPreviousMenuItem = menuItem;
        mPreviousMenuItem = menuItem;
        ActiveProfileFragment activeProfileFragment = ActiveProfileFragment.newInstance();
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.active_profile:
                if (IDareApp.isActive()) {
                    fragment = activeProfileFragment;
                } else {
                    fragment = PassiveFragment.newInstance();
                }
                break;
            case R.id.core_list:
                CoreListFragment coreListFragment = CoreListFragment.newInstance();
                fragment = coreListFragment;
                break;
            case R.id.invite:
                InviteToIDareFragment inviteToIDareFragment = InviteToIDareFragment.newInstance();
                fragment = inviteToIDareFragment;
                break;
            case R.id.app_tour:
                AppTourFragment appTourFragment = AppTourFragment.newInstance();
                fragment = appTourFragment;
                break;
            case R.id.donate:
                DonateFragment donateFragment = DonateFragment.newInstance();
                fragment = donateFragment;
                break;
            case R.id.settings:
                SettingsFragment settingsFragment = SettingsFragment.newInstance();
                fragment = settingsFragment;
                break;
            case R.id.log_out:
                logout();
                break;
            default:
                fragment = activeProfileFragment;
                break;
        }
        if (fragment != null) {
            dataListener.replaceFragment(fragment);
        }
        return true;
    }

    private void logout() {
        dataListener.showAlertDialog(getContext().getResources().getString(R.string.app_name), getContext().getResources().getString(R.string.logout_message), false, getContext().getResources().getString(R.string.btn_logout), getContext().getResources().getString(R.string.btn_cancel), new AlertDialogHandler() {
            @Override
            public void onPositiveButtonClicked() {
                logoutFromApp();
            }

            @Override
            public void onNegativeButtonClicked() {
            }
        });
    }

    private void logoutFromApp() {
        PreferencesManager.getInstance(getContext()).clearAllPreferences();
        relaunch();
    }

    public void relaunch() {
        dataListener.finish();
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        dataListener.startActivity(intent);
    }

    @Override
    public void finish() {
        dataListener.finish();
    }

    @Override
    public void showAlertDialog(View view, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler) {
        dataListener.showAlertDialog(view, positiveButton, negativeButton, alertDialogHandler);
    }

    // Service call to fetch the user
    public void fetchUserDetails() {
        dataListener.showProgress();
        dataListener.hideKeyBoard();
        SessionFacadeImpl.getInstance().fetchUserDetails(getContext(), PreferencesManager.getInstance(getContext()).getUserDetails().getUsername(),
                new IDAREResponseHandler.ResponseListener<UserProfileResponseModel[]>() {
                    @Override
                    public void onSuccess(UserProfileResponseModel[] response) {
                        dataListener.hideProgress();
                        if (response != null) {
                            dataListener.getUserName(response[0].getName());
                        }
                    }
                }, new IDAREResponseHandler.ErrorListener() {
                    @Override
                    public void onError(IDAREErrorWrapper error) {
                        dataListener.hideProgress();
                        dataListener.hideKeyBoard();
                        if (!Utils.isNetworkAvailable(getContext())) {
                            dataListener.showAlertDialog(getContext().getResources().getString(R.string.error_title),
                                    getContext().getResources().getString(R.string.network_error_message), false, getContext().getResources().getString(R.string.positive_button),
                                    null, new AlertDialogHandler() {
                                        @Override
                                        public void onPositiveButtonClicked() {
                                        }

                                        @Override
                                        public void onNegativeButtonClicked() {
                                        }
                                    });
                        } else {
                            dataListener.showAlertDialog(getContext().getResources().getString(R.string.error_title),
                                    getContext().getResources().getString(R.string.error_message), false, getContext().getResources().getString(R.string.positive_button),
                                    null, new AlertDialogHandler() {
                                        @Override
                                        public void onPositiveButtonClicked() {
                                        }

                                        @Override
                                        public void onNegativeButtonClicked() {
                                        }
                                    });
                        }

                    }
                });
    }

    public interface DataListener {

        void replaceFragment(Fragment fragment);

        void startActivity(Intent intent);

        void finish();

        void showProgress();

        void hideProgress();

        void showAlertDialog(View view, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler);

        DrawerLayout getDrawer();

        void hideKeyBoard();

        void getUserName(String userName);

        void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler);
    }
}
