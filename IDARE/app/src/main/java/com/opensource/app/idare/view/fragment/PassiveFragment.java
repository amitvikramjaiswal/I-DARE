package com.opensource.app.idare.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opensource.app.idare.R;
import com.opensource.app.idare.application.IDareApp;
import com.opensource.app.idare.databinding.FragmentMyAccountPassiveBinding;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;
import com.opensource.app.idare.view.activity.NearBySafeHouseActivity;
import com.opensource.app.idare.viewmodel.PassiveProfileFragmentViewModel;

/**
 * Created by akokala on 11/6/2017.
 */

public class PassiveFragment extends BaseFragment implements PassiveProfileFragmentViewModel.DataListener {

    private FragmentMyAccountPassiveBinding binding;
    private PassiveProfileFragmentViewModel viewModel;
    private OnFragmentInteractionListener mListener;

    public PassiveFragment() {
        // Required empty public constructor
    }

    public static PassiveFragment newInstance() {
        PassiveFragment fragment = new PassiveFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account_passive, container, false);
        viewModel = new PassiveProfileFragmentViewModel(getActivity(), this);
        IDareApp.setIsActive(false);
        binding.setViewModel(viewModel);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ActiveProfileFragment.OnFragmentInteractionListener) {
            mListener = (PassiveFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.setValues();
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        mListener.replaceFragment(fragment);
    }

    @Override
    public TextView getTextView() {
        return binding.tvWelcomeTitle;
    }

    @Override
    public void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler) {
        mListener.showAlertDialog(title, message, cancelable, positiveButton, negativeButton, alertDialogHandler);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity.setTitle(getContext().getResources().getString(R.string.active_profile));
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.clear();
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_near_by, menu);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_near_by, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_safe_house:
                showNearBySafeHouses();
                break;
        }
        return true;
    }

    private void showNearBySafeHouses() {
        startActivity(NearBySafeHouseActivity.getStartIntent(context));
    }


    public interface OnFragmentInteractionListener {

        void replaceFragment(Fragment fragment);

        void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler);
    }
}
