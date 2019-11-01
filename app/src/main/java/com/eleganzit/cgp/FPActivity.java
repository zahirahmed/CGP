package com.eleganzit.cgp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.eleganzit.cgp.api.RetrofitAPI;
import com.eleganzit.cgp.api.RetrofitInterface;
import com.eleganzit.cgp.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FPActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fp);

        final EditText ed_email=findViewById(R.id.ed_email);
        RelativeLayout submit=findViewById(R.id.submit);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ed_email.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(FPActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    fpass(ed_email.getText().toString());
                }
            }
        });
    }


    private void fpass(String email) {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<LoginResponse> call = myInterface.fpass(
                email+""
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

                            }

                        }
                        Toast.makeText(FPActivity.this, "Password has been sent to your email", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {

                        Toast.makeText(FPActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(FPActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(FPActivity.this, "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

}
