package com.opensource.app.idare.view.fragment;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.FragmentSafePracticesPagerBinding;
import com.opensource.app.idare.utils.Constants;
import com.opensource.app.idare.viewmodel.SafePracticesPagerViewModel;

import static com.opensource.app.idare.utils.Constants.ARRAY_RES_ID;

public class SafePracticesPagerFragment extends IDareBaseFragment implements SafePracticesPagerViewModel.DataListener {

    private static final String TAG = SafePracticesPagerFragment.class.getSimpleName();
    private SafePracticesPagerViewModel viewModel;
    private OnFragmentInteractionListener mListener;

    public SafePracticesPagerFragment() {

    }

    public static SafePracticesPagerFragment newInstance(int arrResId) {
        SafePracticesPagerFragment fragment = new SafePracticesPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARRAY_RES_ID, arrResId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int arrResId = getArguments().getInt(ARRAY_RES_ID);
        FragmentSafePracticesPagerBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_safe_practices_pager, container, false);
        viewModel = new SafePracticesPagerViewModel(getActivity(), arrResId, this);
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
