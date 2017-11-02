package com.opensource.app.idare.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.FragmentSettingsBinding;
import com.opensource.app.idare.viewmodel.SettingsViewModel;

/**
 * Created by akokala on 11/2/2017.
 */

public class SettingsFragment extends BaseFragment implements SettingsViewModel.DataListener {
    private FragmentSettingsBinding binding;
    private SettingsViewModel viewModel;
    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        viewModel = new SettingsViewModel(getActivity(), this);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SettingsFragment.OnFragmentInteractionListener) {
            mListener = (SettingsFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
