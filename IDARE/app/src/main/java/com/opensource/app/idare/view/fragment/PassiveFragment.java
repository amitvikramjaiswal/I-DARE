package com.opensource.app.idare.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.FragmentMyAccountPassiveBinding;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;
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
        binding.setViewModel(viewModel);
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
    public void replaceFragment(Fragment fragment) {
        mListener.replaceFragment(fragment);
    }

    @Override
    public void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler) {
        mListener.showAlertDialog(title, message, cancelable, positiveButton, negativeButton, alertDialogHandler);
    }

    public interface OnFragmentInteractionListener {

        void replaceFragment(Fragment fragment);

        void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler);
    }
}