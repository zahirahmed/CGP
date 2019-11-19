package com.eleganzit.cgp.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.cgp.HomeActivity;
import com.eleganzit.cgp.R;
import com.eleganzit.cgp.RegistrationActivity;
import com.eleganzit.cgp.api.RetrofitAPI;
import com.eleganzit.cgp.api.RetrofitInterface;
import com.eleganzit.cgp.models.LoginResponse;
import com.eleganzit.cgp.models.StateListData;
import com.eleganzit.cgp.models.StateListResponse;
import com.eleganzit.cgp.utils.CustomAdapter2;
import com.eleganzit.cgp.utils.UserLoggedInSession;

import java.util.ArrayList;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    EditText ed_ginning,ed_area,ed_expance,ed_email;
    TextView txt_username,txt_unit;
    Button btn_save;
    Spinner spinner;
    LinearLayout lin_change_password;
    ProgressDialog progressDialog;
    UserLoggedInSession userLoggedInSession;
    ArrayList<StateListData> arrayList=new ArrayList<>();
    LinearLayout layout1;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_edit_profile, container, false);

        HomeActivity.txt_title.setText("Edit Profile");
        HomeActivity.share.setVisibility(View.GONE);
        HomeActivity.filter.setVisibility(View.GONE);

        lin_change_password=v.findViewById(R.id.lin_change_password);
        txt_username=v.findViewById(R.id.txt_username);
        txt_unit=v.findViewById(R.id.txt_unit);
        ed_ginning=v.findViewById(R.id.ed_ginning);
        ed_email=v.findViewById(R.id.ed_email);
        ed_area=v.findViewById(R.id.ed_area);
        ed_expance=v.findViewById(R.id.ed_expance);
        spinner=v.findViewById(R.id.spinner);
        btn_save=v.findViewById(R.id.btn_save);
        layout1=v.findViewById(R.id.layout1);
        userLoggedInSession=new UserLoggedInSession(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        //spinner.setEnabled(false);
        layout1.setClickable(true);
        cgpgetUser();
        getstateList();
        lin_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.change_password);

                TextView btn_ok=dialog.findViewById(R.id.btn_ok);
                TextView cancel=dialog.findViewById(R.id.cancel);
                final EditText ed_newpassword=dialog.findViewById(R.id.ed_newpassword);
                final EditText ed_cpassword=dialog.findViewById(R.id.ed_cpassword);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (ed_newpassword.getText().toString().trim().equals("") || ed_newpassword.getText().toString().trim().length() < 6) {

                            ed_newpassword.setError("Password must contain atleast 6 characters");

                            ed_newpassword.requestFocus();

                        } else if (!ed_newpassword.getText().toString().trim().equals(ed_cpassword.getText().toString().trim())) {

                            ed_cpassword.setError("Password doesn't match");

                            ed_cpassword.requestFocus();

                        }
                        else
                        {

                            userUpdate2(dialog,ed_cpassword.getText().toString());
                            //changePassword(dialog,userSessionManager.getUserDetails().get(UserSessionManager.KEY_EMAIL),ed_crntpassword.getText().toString(),ed_newpassword.getText().toString());
                        }

                    }
                });

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                Window window = dialog.getWindow();
                window.setBackgroundDrawableResource(android.R.color.transparent);

                dialog.show();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_area.getText().toString().equalsIgnoreCase("")||ed_email.getText().toString().equalsIgnoreCase("")||ed_expance.getText().toString().equalsIgnoreCase("")||ed_ginning.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Please fill all the details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    userUpdate();
                }
            }
        });

        return v;
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
                            
                            if(userLoggedInSession.getUserDetails().get(UserLoggedInSession.STATE).equalsIgnoreCase("punjab")){
                                spinner.setSelection(0);
                            }
                            else if(userLoggedInSession.getUserDetails().get(UserLoggedInSession.STATE).equalsIgnoreCase("haryana")){
                                spinner.setSelection(1);
                            }
                            else if(userLoggedInSession.getUserDetails().get(UserLoggedInSession.STATE).equalsIgnoreCase("Rajasthan(Upper)")){
                                spinner.setSelection(2);
                            }
                            else if(userLoggedInSession.getUserDetails().get(UserLoggedInSession.STATE).equalsIgnoreCase("Rajasthan(Lower)")){
                                spinner.setSelection(3);
                            }
                            else if(userLoggedInSession.getUserDetails().get(UserLoggedInSession.STATE).equalsIgnoreCase("gujarat")){
                                spinner.setSelection(4);
                            }
                            else if(userLoggedInSession.getUserDetails().get(UserLoggedInSession.STATE).equalsIgnoreCase("Maharashtra")){
                                spinner.setSelection(5);
                            }
                            else if(userLoggedInSession.getUserDetails().get(UserLoggedInSession.STATE).equalsIgnoreCase("Madhya Pradesh")){
                                spinner.setSelection(6);
                            }
                            else if(userLoggedInSession.getUserDetails().get(UserLoggedInSession.STATE).equalsIgnoreCase("Karnataka")){
                                spinner.setSelection(7);
                            }
                            else if(userLoggedInSession.getUserDetails().get(UserLoggedInSession.STATE).equalsIgnoreCase("Andhra Pradesh")){
                                spinner.setSelection(8);
                            }
                            else if(userLoggedInSession.getUserDetails().get(UserLoggedInSession.STATE).equalsIgnoreCase("Telangana")){
                                spinner.setSelection(9);
                            }
                            else if(userLoggedInSession.getUserDetails().get(UserLoggedInSession.STATE).equalsIgnoreCase("Tamil Nadu")){
                                spinner.setSelection(10);
                            }
                            else if(userLoggedInSession.getUserDetails().get(UserLoggedInSession.STATE).equalsIgnoreCase("Odisha")){
                                spinner.setSelection(11);
                            }
                            else if(userLoggedInSession.getUserDetails().get(UserLoggedInSession.STATE).equalsIgnoreCase("Other")){
                                spinner.setSelection(12);
                            }

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


    private void userUpdate() {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<LoginResponse> call = myInterface.userUpdate(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                ed_ginning.getText().toString()+"",
                "",
                ed_area.getText().toString()+"",
                ed_expance.getText().toString()+"",
                ed_email.getText().toString()
        );
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String id,ginning_name,state, area = "", email,mobile;
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                id = String.valueOf(response.body().getData().get(i).getUserId());
                                ginning_name = response.body().getData().get(i).getGinningName();
                                state = response.body().getData().get(i).getState();
                                area = response.body().getData().get(i).getArea();
                                email = response.body().getData().get(i).getEmail();
                                mobile = response.body().getData().get(i).getMobile();

                                userLoggedInSession.updateLoginSession(id, ginning_name,state, area,email,mobile);
                                Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            }

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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void userUpdate2(final Dialog dialog, String password) {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<LoginResponse> call = myInterface.userUpdate(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                password

        );
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    dialog.dismiss();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                     else {

                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                dialog.dismiss();

                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void cgpgetUser() {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<LoginResponse> call = myInterface.cgpgetUser(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+""
        );
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String id,ginning_name,state, area = "", username,expance,expance_unit;
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                ginning_name = response.body().getData().get(i).getGinningName();
                                username = response.body().getData().get(i).getUsername();
                                area = response.body().getData().get(i).getArea();
                                expance = response.body().getData().get(i).getExpance();
                                expance_unit = response.body().getData().get(i).getExpance_unit();
                                ed_email.setText(""+response.body().getData().get(i).getEmail());
                                ed_ginning.setText(ginning_name+"");
                                ed_area.setText(area+"");
                                ed_expance.setText((Html.fromHtml("<b>" + expance+"</b> ")));
                                txt_unit.setText((Html.fromHtml("<font color='#707070'>"+" "+expance_unit+"</font>")));
                                //ed_expance.setText((Html.fromHtml("<b>" + "35"+"</b>"+"<font color='#707070'>"+" "+" Rs/"+"20kg"+"</font>"+"  or  <b>" + "175"+"</b>"+"<font color='#707070'>"+" "+" Rs/"+"100kg"+"</font>")));
                                txt_username.setText(username+"");

                            }

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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

}
