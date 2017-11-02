package com.opensource.app.idare.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.FragmentInviteToIdareBinding;
import com.opensource.app.idare.viewmodel.InviteToIDareViewModel;

/**
 * Created by akokala on 11/2/2017.
 */

public class InviteToIDareFragment extends BaseFragment implements InviteToIDareViewModel.DataListener {
    private FragmentInviteToIdareBinding binding;
    private InviteToIDareViewModel viewModel;
    private OnFragmentInteractionListener mListener;

    public InviteToIDareFragment() {
        // Required empty public constructor
    }

    public static InviteToIDareFragment newInstance() {
        InviteToIDareFragment fragment = new InviteToIDareFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invite_to_idare, container, false);
        viewModel = new InviteToIDareViewModel(getActivity(), this);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InviteToIDareFragment.OnFragmentInteractionListener) {
            mListener = (InviteToIDareFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
