package com.opensource.app.idare.view.fragment;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opensource.app.idare.R;
import com.opensource.app.idare.databinding.FragmentSimpleTextBinding;
import com.opensource.app.idare.viewmodel.SimpleTextViewModel;

/**
 * Common Fragment with only a textview.
 */
public class SimpleTextFragment extends IDareBaseFragment implements SimpleTextViewModel.DataListener {

    private static final String TAG = SimpleTextFragment.class.getSimpleName();
    private static final String ARG_TITLE = "TITLE";
    private static final String ARG_BODY = "BODY";

    private OnFragmentInteractionListener mListener;
    private String title;
    private String body;
    private FragmentSimpleTextBinding binding;
    private SimpleTextViewModel viewModel;

    public SimpleTextFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @param body  Parameter 2.
     * @return A new instance of fragment SimpleTextFragment.
     */
    public static SimpleTextFragment newInstance(String title, String body) {
        SimpleTextFragment fragment = new SimpleTextFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_BODY, body);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            body = getArguments().getString(ARG_BODY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_simple_text, container, false);
        viewModel = new SimpleTextViewModel(getActivity(), this).build(title, body);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.setData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setTitle(String title) {
        getActivity().setTitle(title);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
//
    }
}
