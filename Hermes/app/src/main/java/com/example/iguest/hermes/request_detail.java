package com.example.iguest.hermes;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iguest.hermes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class request_detail extends Fragment {


    public request_detail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_detail, container, false);
    }

}
