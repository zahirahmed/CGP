package com.eleganzit.cgp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.cgp.api.RetrofitAPI;
import com.eleganzit.cgp.api.RetrofitInterface;
import com.eleganzit.cgp.fragments.SignUp1Fragment;
import com.eleganzit.cgp.fragments.SignUp2Fragment;
import com.eleganzit.cgp.fragments.SignUp3Fragment;
import com.eleganzit.cgp.fragments.SignUp4Fragment;
import com.eleganzit.cgp.models.LoginResponse;
import com.eleganzit.cgp.models.RegisterResponse;
import com.eleganzit.cgp.utils.UserLoggedInSession;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView txt_register,txt_fp;
    RelativeLayout submit;
    EditText ed_username,ed_password;

    ProgressDialog progressDialog;
    UserLoggedInSession userLoggedInSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_register=findViewById(R.id.txt_register);
        submit=findViewById(R.id.submit);
        txt_fp=findViewById(R.id.txt_fp);

        ed_username=findViewById(R.id.ed_username);
        ed_password=findViewById(R.id.ed_password);

        userLoggedInSession=new UserLoggedInSession(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });

        txt_fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,FPActivity.class));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ed_username.getText().toString().equalsIgnoreCase("") ||
                ed_password.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(LoginActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loginUser();
                }
            }
        });
    }


    private void loginUser() {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<LoginResponse> call = myInterface.loginUser(
                ed_username.getText().toString()+"",
                ed_password.getText().toString()+""
        );
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String id,ginning_name,state, area = "", email,mobile,expance,expance_unit;
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                id = String.valueOf(response.body().getData().get(i).getUserId());
                                ginning_name = response.body().getData().get(i).getGinningName();
                                state = response.body().getData().get(i).getState();
                                area = response.body().getData().get(i).getArea();
                                email = response.body().getData().get(i).getEmail();
                                mobile = response.body().getData().get(i).getMobile();
                                expance = response.body().getData().get(i).getExpance();
                                expance_unit = response.body().getData().get(i).getExpance_unit();

                                userLoggedInSession.createLoginSession(id, ginning_name,state, area,email,mobile,"login",expance,expance_unit);

                            }

                        }
                    } else {

                        Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(LoginActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

}
