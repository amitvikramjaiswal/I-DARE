package com.opensource.app.idare.view.fragment;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.FragmentSafePracticesBinding;
import com.opensource.app.idare.viewmodel.SafePracticesViewModel;

public class SafePracticesFragment extends IDareBaseFragment implements SafePracticesViewModel.DataListener {

    private static final String TAG = SafePracticesFragment.class.getSimpleName();
    private FragmentSafePracticesBinding binding;
    private SafePracticesViewModel viewModel;
    private OnFragmentInteractionListener mListener;

    public SafePracticesFragment() {

    }

    public static SafePracticesFragment newInstance() {
        SafePracticesFragment fragment = new SafePracticesFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_safe_practices, container, false);
        viewModel = new SafePracticesViewModel(getActivity(), this);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SafePracticesFragment.OnFragmentInteractionListener) {
            mListener = (SafePracticesFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity.setTitle(getContext().getResources().getString(R.string.safe_practices));
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return mListener.getSupportFragmentManager();
    }

    public interface OnFragmentInteractionListener {

        FragmentManager getSupportFragmentManager();
    }
}