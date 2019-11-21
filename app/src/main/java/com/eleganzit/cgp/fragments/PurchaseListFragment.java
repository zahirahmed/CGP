package com.eleganzit.cgp.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.eleganzit.cgp.HomeActivity;
import com.eleganzit.cgp.R;
import com.eleganzit.cgp.adapters.BalesAdapter;
import com.eleganzit.cgp.adapters.PurchaseAdapter;
import com.eleganzit.cgp.api.RetrofitAPI;
import com.eleganzit.cgp.api.RetrofitInterface;
import com.eleganzit.cgp.models.AvgPurchaseData;
import com.eleganzit.cgp.models.AvgPurchaseResponse;
import com.eleganzit.cgp.models.PurchaseData;
import com.eleganzit.cgp.models.PurchaseFormResponse;
import com.eleganzit.cgp.models.PurchaseListData;
import com.eleganzit.cgp.models.PurchaseListResponse;
import com.eleganzit.cgp.models.Utils;
import com.eleganzit.cgp.utils.UserLoggedInSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseListFragment extends Fragment {

    RecyclerView rc_list;
    Button btn_add;
    PurchaseAdapter purchaseAdapter;
    ArrayList<PurchaseListData> arrayList=new ArrayList<>();
    TextView txt_date,txt_avg_krate,avg_srate,txt_avg_per_cott,txt_avg_per_seed,txt_pur_krate,txt_seed_weight,txt_shortage,txt_approx_brate,txt_approx_bales,avg_label;
    String from,to;

    int list_size=0;
    boolean end=false;
    String avg_title="Season: 2019-20";
    String avg_title2="Season: 2019-20";

    ProgressDialog progressDialog;
    LinearLayout cardviewdashboard;
    UserLoggedInSession userLoggedInSession;
    NestedScrollView scrollview;
    LinearLayout ss;
    //SwipeRefreshLayout swipe_refresh;

    public PurchaseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_purchase_list, container, false);

        HomeActivity.txt_title.setText("Purchase");
        HomeActivity.share.setVisibility(View.VISIBLE);
        HomeActivity.filter.setVisibility(View.VISIBLE);

        userLoggedInSession=new UserLoggedInSession(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        rc_list=v.findViewById(R.id.rc_list);
        btn_add=v.findViewById(R.id.btn_add);
        ss=v.findViewById(R.id.ss);

        //swipe_refresh=v.findViewById(R.id.swipe_refresh);
        cardviewdashboard=v.findViewById(R.id.cardviewdashboard);
        avg_label=v.findViewById(R.id.avg_label);
        txt_date=v.findViewById(R.id.txt_date);
        txt_avg_krate=v.findViewById(R.id.txt_avg_krate);
        avg_srate=v.findViewById(R.id.avg_srate);
        txt_avg_per_cott=v.findViewById(R.id.txt_avg_per_cott);
        txt_avg_per_seed=v.findViewById(R.id.txt_avg_per_seed);
        txt_pur_krate=v.findViewById(R.id.txt_pur_krate);
        txt_seed_weight=v.findViewById(R.id.txt_seed_weight);
        txt_shortage=v.findViewById(R.id.txt_shortage);
        txt_approx_brate=v.findViewById(R.id.txt_approx_brate);
        txt_approx_bales=v.findViewById(R.id.txt_approx_bales);
        scrollview=v.findViewById(R.id.scrollview);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_list.setLayoutManager(layoutManager);

        purchaseAdapter=new PurchaseAdapter(arrayList,getActivity());

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PurchaseFormFragment purchaseFormFragment=new PurchaseFormFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("HomeActivity")
                        .replace(R.id.container,purchaseFormFragment)
                        .commit();
            }
        });

        HomeActivity.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.filter_dialog);

                TextView date,heap,season;

                date=dialog.findViewById(R.id.date);
                heap=dialog.findViewById(R.id.heap);
                season=dialog.findViewById(R.id.season);

                final Calendar newCalendar = Calendar.getInstance();

                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        final Dialog dialog1=new Dialog(getActivity());
                        avg_title="Your Avg. Values";

                        dialog1.setContentView(R.layout.dialog);

                        TextView cancel,ok;

                        cancel=dialog1.findViewById(R.id.cancel);
                        ok=dialog1.findViewById(R.id.ok);

                        final DateRangeCalendarView calendar=dialog1.findViewById(R.id.calendar);
                        calendar.setNavLeftImage(ContextCompat.getDrawable(getActivity(),R.mipmap.ic_left));
                        calendar.setNavRightImage(ContextCompat.getDrawable(getActivity(),R.mipmap.ic_right));


                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                String mydate = calendar.getStartDate().getTime().toString();
                                SimpleDateFormat src = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH);
                                SimpleDateFormat dest = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                                Date date = null;
                                try {
                                    date = src.parse(mydate);
                                } catch (ParseException e) {
                                    Log.d("Exception",e.getMessage());
                                }
                                String result = dest.format(date);
                                from=result;
                                Log.d("resulttttt", "from "+from);


                                if(calendar.getEndDate()!=null){
                                    String mydate2 = calendar.getEndDate().getTime().toString();
                                    SimpleDateFormat src2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH);
                                    SimpleDateFormat dest2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                                    Date date2 = null;
                                    try {
                                        date2 = src2.parse(mydate2);
                                    } catch (ParseException e) {
                                        Log.d("Exception",e.getMessage());
                                    }
                                    String result2 = dest2.format(date2);
                                    to=result2;
                                    Log.d("resulttttt", "to "+to);
                                }
                                else
                                {
                                    to=from;
                                }

                                avgFkapasrate(from,to);
                                getFPurchaseform(from,to);

                                String fromString = from;
                                String toString = to;
                                String[] separatedfrom = fromString.split("-");
                                String[] separatedto = toString.split("-");

                                from=separatedfrom[2]+"-"+separatedfrom[1]+"-"+separatedfrom[0];
                                to=separatedto[2]+"-"+separatedto[1]+"-"+separatedto[0];


                                if(from.equalsIgnoreCase(to)){
                                    avg_title2="Date - "+from;
                                }
                                else
                                {
                                    avg_title2="Date - "+from+" to "+to;
                                }

                                dialog1.dismiss();
                            }
                        });

                        dialog1.show();
                    }
                });

                season.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        final Dialog dialog1=new Dialog(getActivity());
                        avg_title="Season: 2019-20";
                        avg_title2="Season: 2019-20";

                        dialog1.setContentView(R.layout.season_dialog);

                        TextView txt_yr=dialog1.findViewById(R.id.txt_yr);

                        txt_yr.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();

                                getPurchaseform();
                                avgkapasrate();

                            }
                        });

                        dialog1.show();
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

        HomeActivity.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //takess();
                takeScreenshot();

            }
        });

        getPurchaseform();
        avgkapasrate();
/*

        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getPurchaseform();
                avgkapasrate();

            }
        });
*/

        return v;
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

    //create bitmap from the ScrollView


    private void shareImage(File file){
        Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");

        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");

        intent.putExtra(Intent.EXTRA_STREAM, uri);

        try {

            startActivity(Intent.createChooser(intent, "Share Screenshot"));

        } catch (ActivityNotFoundException e) {

            Toast.makeText(getActivity(), "No App Available", Toast.LENGTH_SHORT).show();

        }

    }

    public void takess(){
        Date now = new Date();

        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {

            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            View v1 = getActivity().getWindow().getDecorView().getRootView();

            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());

            v1.setDrawingCacheEnabled(false);

            Bitmap bitmap2 = getBitmapFromView(scrollview, scrollview.getChildAt(0).getHeight(), scrollview.getChildAt(0).getWidth());
            Bitmap icon = bitmap2;
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("image/*");
            i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(icon, getActivity()));
            startActivity(Intent.createChooser(i, "Share Image"));
        } catch (Throwable e) {
            Log.d("erfdfsda","error: "+e.getMessage());
            e.printStackTrace();
        }
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
            //icon = getBitmapFromView(ss, ss.getChildAt(0).getHeight(), ss.getChildAt(0).getWidth());
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

    private void openScreenshot(File imageFile) {

        Intent intent = new Intent();

        intent.setAction(Intent.ACTION_VIEW);

        Uri uri = Uri.fromFile(imageFile);

        intent.setDataAndType(uri, "image/*");

        startActivity(intent);

    }
    private void getPurchaseform() {

        //swipe_refresh.setRefreshing(false);

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<PurchaseListResponse> call = myInterface.getPurchaseform(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                "1"
        );
        call.enqueue(new Callback<PurchaseListResponse>() {
            @Override
            public void onResponse(Call<PurchaseListResponse> call, Response<PurchaseListResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            if(arrayList.size()>0){
                                arrayList.clear();
                            }

                            for(int i=0;i<response.body().getData().size();i++){

                                PurchaseListData purchaseData=new PurchaseListData(response.body().getData().get(i).getPurchaseId(),response.body().getData().get(i).getUserId(),response.body().getData().get(i).getAddDate(),response.body().getData().get(i).getHeap(),response.body().getData().get(i).getVehicleNo(),response.body().getData().get(i).getFinalWeight(),response.body().getData().get(i).getRateOfKapas(),response.body().getData().get(i).getPerOfCotton(),response.body().getData().get(i).getShortage(),response.body().getData().get(i).getSeedRate(),response.body().getData().get(i).getApproxBaleRate(),response.body().getData().get(i).getCreatedDate());


                                arrayList.add(purchaseData);

                            }

                            list_size=list_size+response.body().getData().size();
                            purchaseAdapter=new PurchaseAdapter(arrayList,getActivity());

                            rc_list.setAdapter(purchaseAdapter);
                            Log.d("arfasd","1>>"+(list_size)+"");

                            getPurchaseform2((arrayList.size()+1)+"");

                        }
                    } else {
                        end=true;

                        if(arrayList.size()>0){
                            arrayList.clear();
                        }

                        purchaseAdapter=new PurchaseAdapter(arrayList,getActivity());

                        rc_list.setAdapter(purchaseAdapter);

                        Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PurchaseListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getFPurchaseform2(String limit, final String from, final String to) {


        purchaseAdapter.addNullData();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<PurchaseListResponse> call = myInterface.getFPurchaseform(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                ""+limit,
                from+"",
                to+""
        );
        call.enqueue(new Callback<PurchaseListResponse>() {
            @Override
            public void onResponse(Call<PurchaseListResponse> call, Response<PurchaseListResponse> response) {
                purchaseAdapter.removeNull();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            ArrayList<PurchaseListData> arrayList=new ArrayList<>();

                            for(int i=0;i<response.body().getData().size();i++){

                                PurchaseListData purchaseData=new PurchaseListData(response.body().getData().get(i).getPurchaseId(),response.body().getData().get(i).getUserId(),response.body().getData().get(i).getAddDate(),response.body().getData().get(i).getHeap(),response.body().getData().get(i).getVehicleNo(),response.body().getData().get(i).getFinalWeight(),response.body().getData().get(i).getRateOfKapas(),response.body().getData().get(i).getPerOfCotton(),response.body().getData().get(i).getShortage(),response.body().getData().get(i).getSeedRate(),response.body().getData().get(i).getApproxBaleRate(),response.body().getData().get(i).getCreatedDate());

                                arrayList.add(purchaseData);


                            }
                            list_size=list_size+response.body().getData().size();

                            purchaseAdapter.addData(arrayList);
                            Log.d("arfasd","3>>"+list_size+"");

                            getFPurchaseform2(list_size+"",from,to);
                        }
                        end=false;
                    } else {
                        end=true;
                    }

                }
                else {

                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PurchaseListResponse> call, Throwable t) {
                purchaseAdapter.removeNull();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getFPurchaseform(final String from, final String to) {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<PurchaseListResponse> call = myInterface.getFPurchaseform(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                "1",
                from+"",
                to+""
        );
        call.enqueue(new Callback<PurchaseListResponse>() {
            @Override
            public void onResponse(Call<PurchaseListResponse> call, Response<PurchaseListResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            if(arrayList.size()>0){
                                arrayList.clear();
                            }

                            for(int i=0;i<response.body().getData().size();i++){

                                PurchaseListData purchaseData=new PurchaseListData(response.body().getData().get(i).getPurchaseId(),response.body().getData().get(i).getUserId(),response.body().getData().get(i).getAddDate(),response.body().getData().get(i).getHeap(),response.body().getData().get(i).getVehicleNo(),response.body().getData().get(i).getFinalWeight(),response.body().getData().get(i).getRateOfKapas(),response.body().getData().get(i).getPerOfCotton(),response.body().getData().get(i).getShortage(),response.body().getData().get(i).getSeedRate(),response.body().getData().get(i).getApproxBaleRate(),response.body().getData().get(i).getCreatedDate());


                                arrayList.add(purchaseData);

                            }
                            list_size=list_size+response.body().getData().size();

                            purchaseAdapter=new PurchaseAdapter(arrayList,getActivity());


                            rc_list.setAdapter(purchaseAdapter);


                            getFPurchaseform2((arrayList.size()+1)+"",from,to);

                        }
                    } else {
                        end=true;

                        if(arrayList.size()>0){
                            arrayList.clear();
                        }

                        purchaseAdapter=new PurchaseAdapter(arrayList,getActivity());

                        rc_list.setAdapter(purchaseAdapter);
                        Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
                else {

                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PurchaseListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getPurchaseform2(String limit) {


        purchaseAdapter.addNullData();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<PurchaseListResponse> call = myInterface.getPurchaseform(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                ""+limit
        );
        call.enqueue(new Callback<PurchaseListResponse>() {
            @Override
            public void onResponse(Call<PurchaseListResponse> call, Response<PurchaseListResponse> response) {
                purchaseAdapter.removeNull();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            ArrayList<PurchaseListData> arrayList=new ArrayList<>();

                            for(int i=0;i<response.body().getData().size();i++){

                                PurchaseListData purchaseData=new PurchaseListData(response.body().getData().get(i).getPurchaseId(),response.body().getData().get(i).getUserId(),response.body().getData().get(i).getAddDate(),response.body().getData().get(i).getHeap(),response.body().getData().get(i).getVehicleNo(),response.body().getData().get(i).getFinalWeight(),response.body().getData().get(i).getRateOfKapas(),response.body().getData().get(i).getPerOfCotton(),response.body().getData().get(i).getShortage(),response.body().getData().get(i).getSeedRate(),response.body().getData().get(i).getApproxBaleRate(),response.body().getData().get(i).getCreatedDate());

                                arrayList.add(purchaseData);

                                Log.d("arfasd","2>>"+(list_size)+"");


                            }
                            list_size=(list_size+response.body().getData().size());

                            purchaseAdapter.addData(arrayList);

                            getPurchaseform2(list_size+"");
                        }
                        end=false;
                    } else {
                        end=true;
                    }

                }
                else {

                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PurchaseListResponse> call, Throwable t) {
                purchaseAdapter.removeNull();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void avgkapasrate() {
        //swipe_refresh.setRefreshing(false);
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AvgPurchaseResponse> call = myInterface.avgkapasrate(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+""
        );
        call.enqueue(new Callback<AvgPurchaseResponse>() {
            @Override
            public void onResponse(Call<AvgPurchaseResponse> call, Response<AvgPurchaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String id,ginning_name,state, area = "", email,expance;
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                //ed_heap.setHint("Heap (Last heap - "+response.body().getData().get(i).getHeap()+")");
                                final AvgPurchaseData avgPurchaseData=response.body().getData().get(i);

                                String yourFormattedString1,yourFormattedString2,yourFormattedString3,yourFormattedString4;

                                DecimalFormat formatter = new DecimalFormat("##,##,##,###");
                                yourFormattedString1 = formatter.format(Double.valueOf(avgPurchaseData.getPurchaseCotton()));
                                yourFormattedString2 = formatter.format(Double.valueOf(avgPurchaseData.getTotalSeedWeight()));
                                yourFormattedString3 = formatter.format(Double.valueOf(avgPurchaseData.getApproxBaleRate()));
                                yourFormattedString4 = formatter.format(Double.valueOf(avgPurchaseData.getBales()));

                                txt_avg_krate.setText((Html.fromHtml("<b>" + avgPurchaseData.getAvgKapasRate() + "</b>"+"<font color='#707070'>"+" "+"Rs/"+avgPurchaseData.getGetcotton_rate()+"kg"+"</font>")));//+" Rs/"+avgPurchaseData.getGetcotton_rate()+"kg"
                                avg_srate.setText((Html.fromHtml("<b>"+ avgPurchaseData.getAvgSeedRate()+"</b>"+"<font color='#707070'>"+" "+" Rs/"+avgPurchaseData.getGetseed_rate()+"kg"+"</font>")));
                                txt_avg_per_cott.setText((Html.fromHtml("<b>" +avgPurchaseData.getAvgOfPerCotton()+"</b>"+"<font color='#707070'>"+" "+" %"+"</font>")));
                                txt_avg_per_seed.setText((Html.fromHtml("<b>" +avgPurchaseData.getAvgPerSeedRate()+"</b>"+"<font color='#707070'>"+" "+" %"+"</font>")));
                                txt_pur_krate.setText((Html.fromHtml("<b>"+ yourFormattedString1+"</b>"+"<font color='#707070'>"+" "+" Rs"+"</font>")));
                                txt_seed_weight.setText((Html.fromHtml("<b>" +yourFormattedString2+"</b>"+"<font color='#707070'>"+" "+" kg"+"</font>")));
                                txt_shortage.setText((Html.fromHtml("<b>" +avgPurchaseData.getAvgShortage()+"</b>"+"<font color='#707070'>"+" "+" %"+"</font>")));
                                txt_approx_brate.setText((Html.fromHtml("<b>" +yourFormattedString3+"</b>"+"<font color='#707070'>"+" "+" Rs/"+avgPurchaseData.getGetbales_weight()+"kg"+"</font>")));
                                txt_approx_bales.setText((Html.fromHtml("<b>" +yourFormattedString4+"</b>"+"<font color='#707070'>"+" "+" Bales"+"</font>")));
                                avg_label.setText(""+avg_title);
                                //format.format(new BigDecimal("100000000"));


                                cardviewdashboard.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        ViewAvgDetailsFragment viewAvgDetailsFragment=new ViewAvgDetailsFragment();

                                        Bundle bundle=new Bundle();
                                        //String personJsonString = Utils.getGsonParser().toJson(avgPurchaseData);
                                        bundle.putSerializable("avgPurchaseData",avgPurchaseData);
                                        bundle.putString("avg_title",avg_title2);

                                        viewAvgDetailsFragment.setArguments(bundle);

                                        getActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .addToBackStack("HomeActivity")
                                                .replace(R.id.container,viewAvgDetailsFragment)
                                                .commit();
                                    }
                                });

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
            public void onFailure(Call<AvgPurchaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void avgFkapasrate(String from,String to) {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AvgPurchaseResponse> call = myInterface.avgFkapasrate(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                ""+from,""+to
        );
        call.enqueue(new Callback<AvgPurchaseResponse>() {
            @Override
            public void onResponse(Call<AvgPurchaseResponse> call, Response<AvgPurchaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String id,ginning_name,state, area = "", email,expance;
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                //ed_heap.setHint("Heap (Last heap - "+response.body().getData().get(i).getHeap()+")");
                                final AvgPurchaseData avgPurchaseData=response.body().getData().get(i);

                                String yourFormattedString1,yourFormattedString2,yourFormattedString3,yourFormattedString4;

                                DecimalFormat formatter = new DecimalFormat("##,##,##,###");
                                yourFormattedString1 = formatter.format(Double.valueOf(avgPurchaseData.getPurchaseCotton()));
                                yourFormattedString2 = formatter.format(Double.valueOf(avgPurchaseData.getTotalSeedWeight()));
                                yourFormattedString3 = formatter.format(Double.valueOf(avgPurchaseData.getApproxBaleRate()));
                                yourFormattedString4 = formatter.format(Double.valueOf(avgPurchaseData.getBales()));


                                txt_avg_krate.setText((Html.fromHtml("<b>" + avgPurchaseData.getAvgKapasRate() + "</b>"+"<font color='#707070'>"+" "+"Rs/"+avgPurchaseData.getGetcotton_rate()+"kg"+"</font>")));//+" Rs/"+avgPurchaseData.getGetcotton_rate()+"kg"
                                avg_srate.setText((Html.fromHtml("<b>"+ avgPurchaseData.getAvgSeedRate()+"</b>"+"<font color='#707070'>"+" "+" Rs/"+avgPurchaseData.getGetseed_rate()+"kg"+"</font>")));
                                txt_avg_per_cott.setText((Html.fromHtml("<b>" +avgPurchaseData.getAvgOfPerCotton()+"</b>"+"<font color='#707070'>"+" "+" %"+"</font>")));
                                txt_avg_per_seed.setText((Html.fromHtml("<b>" +avgPurchaseData.getAvgPerSeedRate()+"</b>"+"<font color='#707070'>"+" "+" %"+"</font>")));
                                txt_pur_krate.setText((Html.fromHtml("<b>"+ yourFormattedString1+"</b>"+"<font color='#707070'>"+" "+" Rs"+"</font>")));
                                txt_seed_weight.setText((Html.fromHtml("<b>" +yourFormattedString2+"</b>"+"<font color='#707070'>"+" "+" kg"+"</font>")));
                                txt_shortage.setText((Html.fromHtml("<b>" +avgPurchaseData.getAvgShortage()+"</b>"+"<font color='#707070'>"+" "+" %"+"</font>")));
                                txt_approx_brate.setText((Html.fromHtml("<b>" +yourFormattedString3+"</b>"+"<font color='#707070'>"+" "+" Rs/"+avgPurchaseData.getGetbales_weight()+"kg"+"</font>")));
                                txt_approx_bales.setText((Html.fromHtml("<b>" +yourFormattedString4+"</b>"+"<font color='#707070'>"+" "+" Bales"+"</font>")));
                                avg_label.setText(""+avg_title);


                                cardviewdashboard.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        ViewAvgDetailsFragment viewAvgDetailsFragment=new ViewAvgDetailsFragment();

                                        Bundle bundle=new Bundle();
                                        //String personJsonString = Utils.getGsonParser().toJson(avgPurchaseData);
                                        bundle.putSerializable("avgPurchaseData",avgPurchaseData);
                                        bundle.putString("avg_title",avg_title2);

                                        viewAvgDetailsFragment.setArguments(bundle);

                                        getActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .addToBackStack("HomeActivity")
                                                .replace(R.id.container,viewAvgDetailsFragment)
                                                .commit();
                                    }
                                });

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
            public void onFailure(Call<AvgPurchaseResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

}
