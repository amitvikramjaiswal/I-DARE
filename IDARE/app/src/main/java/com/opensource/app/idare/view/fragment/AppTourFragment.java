package com.opensource.app.idare.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.FragmentAppTourBinding;
import com.opensource.app.idare.viewmodel.AppTourFragmentViewModel;

/**
 * Created by ajaiswal on 4/4/2016.
 */
public class AppTourFragment extends BaseFragment implements AppTourFragmentViewModel.DataListener {
    private FragmentAppTourBinding binding;
    private AppTourFragmentViewModel viewModel;
    private OnFragmentInteractionListener mListener;

    public AppTourFragment() {
        // Required empty public constructor
    }

    public static AppTourFragment newInstance() {
        AppTourFragment fragment = new AppTourFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_app_tour, container, false);
        viewModel = new AppTourFragmentViewModel(getActivity(), this);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppTourFragment.OnFragmentInteractionListener) {
            mListener = (AppTourFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity.setTitle(getContext().getResources().getString(R.string.app_tour));
    }

    public interface OnFragmentInteractionListener {
    }
}
