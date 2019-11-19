package com.eleganzit.cgp.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.eleganzit.cgp.HomeActivity;
import com.eleganzit.cgp.R;
import com.eleganzit.cgp.RegistrationActivity;
import com.eleganzit.cgp.api.RetrofitAPI;
import com.eleganzit.cgp.api.RetrofitInterface;
import com.eleganzit.cgp.models.AvgPurchaseResponse;
import com.eleganzit.cgp.models.GetBaleResponse;
import com.eleganzit.cgp.models.GetPurchaseResponse;
import com.eleganzit.cgp.models.LastHeapResponse;
import com.eleganzit.cgp.models.LoginResponse;
import com.eleganzit.cgp.models.PurchaseFormResponse;
import com.eleganzit.cgp.models.RegisterResponse;
import com.eleganzit.cgp.models.SaleBaleMillData;
import com.eleganzit.cgp.models.SaleBaleTraderData;
import com.eleganzit.cgp.models.UpdateBaleResponse;
import com.eleganzit.cgp.models.UpdatePurchaseResponse;
import com.eleganzit.cgp.utils.UserLoggedInSession;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseFormFragment extends Fragment {

    ProgressDialog progressDialog;
    UserLoggedInSession userLoggedInSession;
    TextInputLayout il_heap;
    EditText ed_date,ed_heap,ed_vehicle,ed_weight,ed_krate,ed_srate,ed_cotton,ed_shortage;
    Button btn_submit;
    String byDate="";
    private String id="";
    private String from="";
    SharedPreferences prefs = null;
    ScrollView ss;

    public PurchaseFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_purchase_form, container, false);

        HomeActivity.txt_title.setText("Purchase Form");
        HomeActivity.share.setVisibility(View.GONE);
        HomeActivity.filter.setVisibility(View.GONE);

        prefs = getActivity().getSharedPreferences("com.eleganzit.cgp", MODE_PRIVATE);

        userLoggedInSession=new UserLoggedInSession(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        Bundle bundle=getArguments();

        //here is your list array
        if(bundle!=null){
            id=bundle.getString("id");
            from=bundle.getString("from");
        }

        ed_date=v.findViewById(R.id.ed_date);
        ss=v.findViewById(R.id.ss);
        il_heap=v.findViewById(R.id.il_heap);
        ed_heap=v.findViewById(R.id.ed_heap);
        ed_vehicle=v.findViewById(R.id.ed_vehicle);
        ed_weight=v.findViewById(R.id.ed_weight);
        ed_krate=v.findViewById(R.id.ed_krate);
        ed_srate=v.findViewById(R.id.ed_srate);
        ed_cotton=v.findViewById(R.id.ed_cotton);
        ed_shortage=v.findViewById(R.id.ed_shortage);
        btn_submit=v.findViewById(R.id.btn_submit);

        lastHeap();

        il_heap.setHint("Challan No.");

        final Calendar newCalendar = Calendar.getInstance();

        ed_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog StartTime = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        byDate=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;

                        ed_date.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);

                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                StartTime.getDatePicker().setMaxDate(System.currentTimeMillis());
                StartTime.show();
            }
        });

        ed_shortage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog2=new Dialog(getActivity());

                dialog2.setContentView(R.layout.mic_dialog);

                final ListView list_mic=dialog2.findViewById(R.id.list_mic);

                ArrayList<String> items = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.heap_array)));

                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);

                list_mic.setAdapter(itemsAdapter);

                list_mic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog2.dismiss();
                        ed_shortage.setText(list_mic.getItemAtPosition(position)+"");
                    }
                });

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog2.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog2.getWindow().setAttributes(lp);
                Window window = dialog2.getWindow();
                window.setBackgroundDrawableResource(android.R.color.transparent);

                dialog2.setCanceledOnTouchOutside(true);
                dialog2.setCancelable(true);

                dialog2.show();
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){

                    if(from.equalsIgnoreCase("edit"))
                    {

                        updateSalebaleform();
                    }
                    else {
                        addPurchaseform();
                    }
                }
            }
        });

        if(from.equalsIgnoreCase("edit"))
        {
            getSalebale_byid();
        }

        if(from.equalsIgnoreCase("view"))
        {
            HomeActivity.txt_title.setText("View Details");
            HomeActivity.share.setVisibility(View.VISIBLE);
            getSalebale_byid();
            btn_submit.setVisibility(View.GONE);

            ed_date.setOnClickListener(null);


            ed_date.setFocusable(false);
            il_heap.setFocusable(false);
            ed_heap.setFocusable(false);
            ed_vehicle.setFocusable(false);
                    ed_weight.setFocusable(false);
            ed_krate.setFocusable(false);
                    ed_srate.setFocusable(false);
                    ed_cotton.setFocusable(false);
            ed_shortage.setFocusable(false);
            ed_shortage.setOnClickListener(null);
        
            ed_date.setCursorVisible(false);
            ed_heap.setCursorVisible(false);
            ed_vehicle.setCursorVisible(false);
                    ed_weight.setCursorVisible(false);
            ed_krate.setCursorVisible(false);
                    ed_srate.setCursorVisible(false);
                    ed_cotton.setCursorVisible(false);
            ed_shortage.setCursorVisible(false);
        }

        HomeActivity.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot();
            }
        });

        return v;
    }


    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getActivity().getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            /*outputStream.flush();
            outputStream.close();*/

            Bitmap icon = bitmap;
            icon = getBitmapFromView(ss, ss.getChildAt(0).getHeight(), ss.getChildAt(0).getWidth());
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("image/*");
            i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(icon, getActivity()));
            startActivity(Intent.createChooser(i, "Share Image"));

            //openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
            Log.d("erfdfsda","error: "+e.getMessage());
        }
    }


    public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = FileProvider.getUriForFile(context, "com.eleganzit.cgp.provider", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;

    }

    private Bitmap getBitmapFromView(View view, int height, int width) {

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        Drawable bgDrawable = view.getBackground();

        if (bgDrawable != null)

            bgDrawable.draw(canvas);

        else

            canvas.drawColor(Color.WHITE);

        view.draw(canvas);

        return bitmap;

    }

    private void updateSalebaleform() {


        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<UpdatePurchaseResponse> call = myInterface.updatePurchaseform(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                id+"",
                byDate+"",
                ed_heap.getText().toString()+"",
                ed_vehicle.getText().toString()+"",
                ed_weight.getText().toString()+"",
                ed_krate.getText().toString()+"",
                ed_cotton.getText().toString()+"",
                ed_shortage.getText().toString()+"",
                ed_srate.getText().toString()+"");
        call.enqueue(new Callback<UpdatePurchaseResponse>() {
            @Override
            public void onResponse(Call<UpdatePurchaseResponse> call, Response<UpdatePurchaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleMillData> parameterListDataArrayList=new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            PurchaseListFragment purchaseListFragment=new PurchaseListFragment();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container,purchaseListFragment)
                                    .commit();

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
            public void onFailure(Call<UpdatePurchaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getSalebale_byid() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetPurchaseResponse> call = myInterface.getPurchase_byid(id+"");
        call.enqueue(new Callback<GetPurchaseResponse>() {
            @Override
            public void onResponse(Call<GetPurchaseResponse> call, Response<GetPurchaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleMillData> parameterListDataArrayList=new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            Log.d("responseeee",response.toString());

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                ed_date.setText(""+response.body().getData().get(i).getAddDate());
                                ed_vehicle.setText(""+response.body().getData().get(i).getVehicleNo());
                                ed_weight.setText(""+response.body().getData().get(i).getFinalWeight());
                                ed_krate.setText(""+response.body().getData().get(i).getRateOfKapas());
                                ed_srate.setText(""+response.body().getData().get(i).getSeedRate());
                                ed_cotton.setText(""+response.body().getData().get(i).getPerOfCotton());
                                ed_shortage.setText(""+response.body().getData().get(i).getShortage());
                                ed_heap.setText(""+response.body().getData().get(i).getHeap());
                                byDate=ed_date.getText().toString();

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
            public void onFailure(Call<GetPurchaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean isValid() {

        if (ed_date.getText().toString().equals("")) {
            ed_date.setError("Please select date");

            ed_date.requestFocus();
            return false;
        }else if (ed_heap.getText().toString().equals("") || ed_heap.getText().toString().equals("0")) {
            ed_heap.setError("Please enter heap");

            ed_heap.requestFocus();
            return false;
        }else if (ed_vehicle.getText().toString().equals("") || ed_vehicle.getText().toString().equals("0")) {
            ed_vehicle.setError("Please enter vehicle number");

            ed_vehicle.requestFocus();
            return false;
        }else if (ed_weight.getText().toString().equals("") || ed_weight.getText().toString().equals("0")) {
            ed_weight.setError("Please enter weight");

            ed_weight.requestFocus();
            return false;
        }else if (ed_krate.getText().toString().equals("") || ed_krate.getText().toString().equals("0")) {
            ed_krate.setError("Please enter kapas rate");

            ed_krate.requestFocus();
            return false;
        }else if (ed_srate.getText().toString().equals("") || ed_srate.getText().toString().equals("0")) {
            ed_srate.setError("Please enter seed rate");

            ed_srate.requestFocus();
            return false;
        }else if (ed_cotton.getText().toString().equals("") || ed_cotton.getText().toString().equals("")) {
            ed_cotton.setError("Please enter % of cotton");

            ed_cotton.requestFocus();
            return false;
        }else if (ed_shortage.getText().toString().equals("")) {
            ed_shortage.setError("Please select shortage");

            ed_shortage.requestFocus();
            return false;
        }
        return true;
    }

    private void lastHeap() {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<LastHeapResponse> call = myInterface.lastHeap(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+""
        );
        call.enqueue(new Callback<LastHeapResponse>() {
            @Override
            public void onResponse(Call<LastHeapResponse> call, Response<LastHeapResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String id,ginning_name,state, area = "", email,expance;
                            for (int i = 0; i < response.body().getData().size(); i++) {
                               //ed_heap.setHint("Heap (Last heap - "+response.body().getData().get(i).getHeap()+")");
                               il_heap.setHint("Challan No. (Last Challan No. - "+response.body().getData().get(i).getHeap()+")");

                            }

                        }
                    } else {

                        //Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LastHeapResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addPurchaseform() {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<PurchaseFormResponse> call = myInterface.addPurchaseform(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                byDate+"",
                ed_heap.getText().toString()+"",
                ed_vehicle.getText().toString()+"",
                ed_weight.getText().toString()+"",
                ed_krate.getText().toString()+"",
                ed_cotton.getText().toString()+"",
                ed_shortage.getText().toString()+"",
                ed_srate.getText().toString()+""
        );
        call.enqueue(new Callback<PurchaseFormResponse>() {
            @Override
            public void onResponse(Call<PurchaseFormResponse> call, Response<PurchaseFormResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            if (prefs.getBoolean("firstrun", true)) {
                                // Do first run stuff here then set 'firstrun' as false
                                // using the following line to edit/commit prefs
                                prefs.edit().putBoolean("firstrun", false).commit();
                                PurchaseListFragment purchaseFormFragment=new PurchaseListFragment();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,purchaseFormFragment).commit();

                            }
                            else
                            {
                                getFragmentManager().popBackStack();
                            }

                            Toast.makeText(getActivity(), "Successful" , Toast.LENGTH_SHORT).show();

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
            public void onFailure(Call<PurchaseFormResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }


}
