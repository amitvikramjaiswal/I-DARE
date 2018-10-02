package com.opensource.app.idare.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.FragmentDonateBinding;
import com.opensource.app.idare.viewmodel.DonateViewModel;

/**
 * Created by akokala on 11/2/2017.
 */

public class DonateFragment extends IDareBaseFragment implements DonateViewModel.DataListener {
    private FragmentDonateBinding binding;
    private DonateViewModel viewModel;
    private OnFragmentInteractionListener mListener;

    public DonateFragment() {
        // Required empty public constructor
    }

    public static DonateFragment newInstance() {
        DonateFragment fragment = new DonateFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_donate, container, false);
        viewModel = new DonateViewModel(getActivity(), this);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DonateFragment.OnFragmentInteractionListener) {
            mListener = (DonateFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity.setTitle(getContext().getResources().getString(R.string.donate));
    }

    public interface OnFragmentInteractionListener {
    }
}

