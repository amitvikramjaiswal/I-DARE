package com.opensource.app.idare.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.app.idare.R;
import com.opensource.app.idare.application.IDareApp;
import com.opensource.app.idare.databinding.FragmentMyAccountActiveBinding;
import com.opensource.app.idare.viewmodel.ActiveProfileFragmentViewModel;

/**
 * Created by ajaiswal on 3/18/2016.
 */
public class ActiveProfileFragment extends BaseFragment implements ActiveProfileFragmentViewModel.DataListener {
    private FragmentMyAccountActiveBinding binding;
    private ActiveProfileFragmentViewModel viewModel;
    private OnFragmentInteractionListener mListener;

    public ActiveProfileFragment() {
        // Required empty public constructor
    }

    public static ActiveProfileFragment newInstance() {
        ActiveProfileFragment fragment = new ActiveProfileFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ActiveProfileFragment.OnFragmentInteractionListener) {
            mListener = (ActiveProfileFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account_active, container, false);
        viewModel = new ActiveProfileFragmentViewModel(getActivity(), this);
        IDareApp.setIsActive(true);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity.setTitle(getContext().getResources().getString(R.string.active_profile));
    }

    public interface OnFragmentInteractionListener {
    }

}
