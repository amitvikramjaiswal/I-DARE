package com.opensource.app.idare.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.FragmentWhileWalkingBinding;
import com.opensource.app.idare.viewmodel.WhileWalkingViewModel;

public class WhileWalkingFragment extends BaseFragment implements WhileWalkingViewModel.DataListener {

    private static final String TAG = WhileWalkingFragment.class.getSimpleName();
    private FragmentWhileWalkingBinding binding;
    private WhileWalkingViewModel viewModel;
    private OnFragmentInteractionListener mListener;
    private FragmentWhileWalkingBinding b;

    public WhileWalkingFragment() {

    }

    public static WhileWalkingFragment newInstance() {
        WhileWalkingFragment fragment = new WhileWalkingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_while_walking, container, false);
        viewModel = new WhileWalkingViewModel(getActivity(), this);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SafePracticesFragment.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener {

    }
}
