package com.nhance.technician.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhance.technician.R;

/**
 * Created by afsarhussain on 10/07/17.
 */

public class RootFragment extends Fragment {

    public static final String TAG = RootFragment.class.getName();
    public static final String FRAGMENT_TAG = "INVOICE_GENERATOR_FRAGMENT";
    public SearchSRNFragment searchSRNFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_root, container, false);

        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        searchSRNFragment = new SearchSRNFragment();
        transaction.replace(R.id.root_frame, searchSRNFragment, FRAGMENT_TAG);
        transaction.addToBackStack(this.getClass().getName());
        transaction.commit();

        return view;
    }

}