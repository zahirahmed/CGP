package com.eleganzit.cgp.fragments;


import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.cgp.R;
import com.eleganzit.cgp.api.RetrofitAPI;
import com.eleganzit.cgp.api.RetrofitInterface;
import com.eleganzit.cgp.models.StateListData;
import com.eleganzit.cgp.models.StateListResponse;
import com.eleganzit.cgp.utils.CustomAdapter;
import com.eleganzit.cgp.utils.CustomAdapter2;

import java.util.ArrayList;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp2Fragment extends Fragment {

    public static Spinner spinner;
    public static EditText ed_area;
    ProgressDialog progressDialog;
    ArrayList<StateListData> arrayList=new ArrayList<>();

    public SignUp2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_sign_up2, container, false);

        spinner=v.findViewById(R.id.spinner);
        ed_area=v.findViewById(R.id.ed_area);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        getstateList();

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("whereses","onResume 2nd");
    }


    private void getstateList() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<StateListResponse> call = myInterface.stateList("");
        call.enqueue(new Callback<StateListResponse>() {
            @Override
            public void onResponse(Call<StateListResponse> call, Response<StateListResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {


                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String id, name;

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                id = response.body().getData().get(i).getStateId();
                                name = response.body().getData().get(i).getName();

                                StateListData stateListData=new StateListData(id,name);

                                arrayList.add(stateListData);

                            }

                            CustomAdapter2 dataAdapter = new CustomAdapter2(getActivity(), android.R.layout.simple_spinner_item, arrayList,-1)
                            {
                                @Override
                                public boolean isEnabled(int position){
                                    if(position == 0)
                                    {
                                        // Disable the first item from Spinner
                                        // First item will be use for hint
                                        return true;
                                    }
                                    else
                                    {
                                        return true;
                                    }
                                }

                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    TextView view = (TextView) super.getView(position, convertView, parent);
                                    Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.poppins_regular);

                                    view.setTypeface(typeface);

                                    return view;
                                }

                                @Override
                                public View getDropDownView(int position, View convertView,
                                                            ViewGroup parent) {
                                    View view = super.getDropDownView(position, convertView, parent);
                                    Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.poppins_regular);

                                    TextView tv = (TextView) view;
                                    tv.setTypeface(typeface);

                                    return view;
                                }


                            };

                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(dataAdapter);


                        }

                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }



}
