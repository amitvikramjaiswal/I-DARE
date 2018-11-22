package com.opensource.app.idare.view.fragment;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.FragmentCoreGroupBinding;
import com.opensource.app.idare.viewmodel.CoreGroupViewModel;

/**
 * Created by ajaiswal on 4/4/2016.
 */
public class CoreGroupFragment extends IDareBaseFragment implements CoreGroupViewModel.DataListener {
    private FragmentCoreGroupBinding binding;
    private CoreGroupViewModel viewModel;
    private OnFragmentInteractionListener mListener;

    public CoreGroupFragment() {
        // Required empty public constructor
    }

    public static CoreGroupFragment newInstance() {
        CoreGroupFragment fragment = new CoreGroupFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_core_group, container, false);
        viewModel = new CoreGroupViewModel(getActivity(), this);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CoreGroupFragment.OnFragmentInteractionListener) {
            mListener = (CoreGroupFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity.setTitle(getContext().getResources().getString(R.string.core_group));
    }


    public interface OnFragmentInteractionListener {
    }

}
