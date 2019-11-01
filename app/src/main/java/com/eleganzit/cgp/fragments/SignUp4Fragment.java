package com.eleganzit.cgp.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.eleganzit.cgp.R;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp4Fragment extends Fragment {

    public static EditText ed_username,ed_password;

    public SignUp4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_sign_up4, container, false);

        ed_username=v.findViewById(R.id.ed_username);
        ed_password=v.findViewById(R.id.ed_password);

        return v;
    }

}
