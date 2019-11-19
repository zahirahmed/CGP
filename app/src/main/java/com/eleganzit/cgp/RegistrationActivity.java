package com.eleganzit.cgp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.cgp.api.RetrofitAPI;
import com.eleganzit.cgp.api.RetrofitInterface;
import com.eleganzit.cgp.fragments.SignUp1Fragment;
import com.eleganzit.cgp.fragments.SignUp2Fragment;
import com.eleganzit.cgp.fragments.SignUp3Fragment;
import com.eleganzit.cgp.fragments.SignUp4Fragment;
import com.eleganzit.cgp.models.RegisterResponse;
import com.eleganzit.cgp.utils.NonSwipeableViewPager;
import com.eleganzit.cgp.utils.UserLoggedInSession;
import com.eleganzit.cgp.utils.viewPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {


    NonSwipeableViewPager view_pager;
    RelativeLayout btn_next;
    TextView txt_next;

    ProgressDialog progressDialog;
    UserLoggedInSession userLoggedInSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        view_pager=findViewById(R.id.view_pager);
        btn_next = findViewById(R.id.btn_next);
        txt_next = findViewById(R.id.txt_next);
        setupViewPager(view_pager);
        view_pager.addOnPageChangeListener(viewPagerPageChangeListener);
        view_pager.setOffscreenPageLimit(0);
        userLoggedInSession=new UserLoggedInSession(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_next.getText().toString().equalsIgnoreCase("submit")){
                    if(SignUp4Fragment.ed_username.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(RegistrationActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
                    }
                    else if(SignUp4Fragment.ed_password.getText().toString().equalsIgnoreCase("") || SignUp4Fragment.ed_password.getText().toString().length()<6){
                        Toast.makeText(RegistrationActivity.this, "Password must contain atleast 6 characters", Toast.LENGTH_LONG).show();
                    }
                    else {
                        registerUser();
                    }
                }
                else {

                    if(view_pager.getCurrentItem()==0){
                        if(SignUp1Fragment.ed_ginning.getText().toString().equalsIgnoreCase("")){
                            Toast.makeText(RegistrationActivity.this, "Please enter ginning name", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            view_pager.setCurrentItem(getItem(+1), true);
                        }
                    }
                    else if(view_pager.getCurrentItem()==1){
                        if (SignUp2Fragment.spinner.getSelectedItem()!=null) {
                            if (SignUp2Fragment.spinner.getSelectedItem().toString().equalsIgnoreCase("") || SignUp2Fragment.spinner.getSelectedItem().toString() == null) {
                                Toast.makeText(RegistrationActivity.this, "Please select state", Toast.LENGTH_SHORT).show();
                            } else if (SignUp2Fragment.ed_area.getText().toString().equalsIgnoreCase("")) {
                                Toast.makeText(RegistrationActivity.this, "Please enter area", Toast.LENGTH_SHORT).show();
                            } else {
                                view_pager.setCurrentItem(getItem(+1), true);
                            }
                        }
                        else {
                            Toast.makeText(RegistrationActivity.this, "Select Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(view_pager.getCurrentItem()==2){
                        if(SignUp3Fragment.ed_email.getText().toString().equalsIgnoreCase("Select Gmail account")){
                            Toast.makeText(RegistrationActivity.this, "Please select email", Toast.LENGTH_SHORT).show();
                        }
                        else if(SignUp3Fragment.ed_mobile.getText().toString().length()!=10){
                            Toast.makeText(RegistrationActivity.this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            view_pager.setCurrentItem(getItem(+1), true);
                        }
                    }
                    else
                    {
                        view_pager.setCurrentItem(getItem(+1), true);
                    }

                }
            }
        });

    }


    private void registerUser() {

        Log.d("sdgfsdf",SignUp1Fragment.ed_ginning.getText().toString()+
                SignUp2Fragment.spinner.getSelectedItem().toString()+
                SignUp2Fragment.ed_area.getText().toString()+
                SignUp3Fragment.ed_email.getText().toString()+
                SignUp3Fragment.ed_mobile.getText().toString()+
                SignUp4Fragment.ed_username.getText().toString()+
                SignUp4Fragment.ed_password.getText().toString());

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<RegisterResponse> call = myInterface.registerUser(
                SignUp1Fragment.ed_ginning.getText().toString()+"",
                SignUp2Fragment.spinner.getSelectedItem().toString()+"",
                SignUp2Fragment.ed_area.getText().toString()+"",
                SignUp3Fragment.ed_email.getText().toString()+"",
                SignUp3Fragment.ed_mobile.getText().toString()+"",
                SignUp4Fragment.ed_username.getText().toString()+"",
                SignUp4Fragment.ed_password.getText().toString()+""
        );
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
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

                                userLoggedInSession.createLoginSession(id, ginning_name,state, area,email,mobile,"register");

                            }

                        }
                    } else {

                        Toast.makeText(RegistrationActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(RegistrationActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegistrationActivity.this, "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new SignUp1Fragment());

        adapter.addFragment(new SignUp2Fragment());

        adapter.addFragment(new SignUp3Fragment());

        adapter.addFragment(new SignUp4Fragment());

        viewPager.setAdapter(adapter);

    }

    private int getItem(int i) {
        return view_pager.getCurrentItem() + i;
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == 4 - 1) {
                // last page. make button text to GOT IT

                txt_next.setText("Submit");

            } else {
                // still pages are left
                txt_next.setText("Next");
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

/*

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
*/

    @Override
    public void onBackPressed() {

        if(view_pager.getCurrentItem()==0){
            super.onBackPressed();
        }
        else
        {
            view_pager.setCurrentItem(getItem(-1), true);
        }

    }
}
