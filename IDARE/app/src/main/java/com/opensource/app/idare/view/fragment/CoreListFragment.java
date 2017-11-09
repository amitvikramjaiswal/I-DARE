package com.opensource.app.idare.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.FragmentCoreListBinding;
import com.opensource.app.idare.viewmodel.CoreListViewModel;

/**
 * Created by ajaiswal on 4/4/2016.
 */
public class CoreListFragment extends BaseFragment implements CoreListViewModel.DataListener {
    private FragmentCoreListBinding binding;
    private CoreListViewModel viewModel;
    private OnFragmentInteractionListener mListener;

    public CoreListFragment() {
        // Required empty public constructor
    }

    public static CoreListFragment newInstance() {
        CoreListFragment fragment = new CoreListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_core_list, container, false);
        viewModel = new CoreListViewModel(getActivity(), this);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CoreListFragment.OnFragmentInteractionListener) {
            mListener = (CoreListFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity.setTitle(getContext().getResources().getString(R.string.core_list));
    }


    public interface OnFragmentInteractionListener {
    }

}
