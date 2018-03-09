package com.psm.lifeorganizer.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.psm.lifeorganizer.LO1Application;

/**
 * Created by pmaestri on 10/11/2016.
 */
public abstract class ParentFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LO1Application application = (LO1Application) getActivity().getApplication();
        application.getInjector().injectMembers(this);
    }

    @Override
    public void onResume() {

        super.onResume();



    }

}
