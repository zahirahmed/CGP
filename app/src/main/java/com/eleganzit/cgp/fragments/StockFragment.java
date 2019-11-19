package com.eleganzit.cgp.fragments;


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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.cgp.HomeActivity;
import com.eleganzit.cgp.R;
import com.eleganzit.cgp.api.RetrofitAPI;
import com.eleganzit.cgp.api.RetrofitInterface;
import com.eleganzit.cgp.models.AvgSaleSeedData;
import com.eleganzit.cgp.models.AvgSaleSeedResponse;
import com.eleganzit.cgp.models.StockData;
import com.eleganzit.cgp.models.StockResponse;
import com.eleganzit.cgp.utils.UserLoggedInSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockFragment extends Fragment {


    ProgressDialog progressDialog;
    UserLoggedInSession userLoggedInSession;
    TextView txt_frst,txt_scnd,txt_thrd,txt_frth;
    TextView txt_gn_name,txt_date;
    ScrollView scrollview;

    public StockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_stock, container, false);

        HomeActivity.txt_title.setText("Stock");
        HomeActivity.share.setVisibility(View.VISIBLE);
        HomeActivity.filter.setVisibility(View.GONE);

        userLoggedInSession=new UserLoggedInSession(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        scrollview=v.findViewById(R.id.scrollview);
        txt_frst=v.findViewById(R.id.txt_frst);
        txt_scnd=v.findViewById(R.id.txt_scnd);
        txt_thrd=v.findViewById(R.id.txt_thrd);
        txt_frth=v.findViewById(R.id.txt_frth);
        txt_gn_name=v.findViewById(R.id.txt_gn_name);
        txt_date=v.findViewById(R.id.txt_date);

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date);

        txt_gn_name.setText(userLoggedInSession.getUserDetails().get(UserLoggedInSession.GINNING_NAME)+"");
        txt_date.setText(dateString+"");

        stockValues();

        HomeActivity.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takess();

            }
        });

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
    private void stockValues() {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<StockResponse> call = myInterface.stockValues(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+""
        );
        call.enqueue(new Callback<StockResponse>() {
            @Override
            public void onResponse(Call<StockResponse> call, Response<StockResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String id,ginning_name,state, area = "", email,expance;
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                //ed_heap.setHint("Heap (Last heap - "+response.body().getData().get(i).getHeap()+")");
                                StockData stockData=response.body().getData().get(i);

                                txt_frst.setText((Html.fromHtml("<b>" + stockData.getApproxBaleStockKg()+"</b>"+"<font color='#707070'>"+" kg"+"</font>")));
                                txt_scnd.setText((Html.fromHtml("<b>" + stockData.getApproxBaleStockBales()+"</b>"+"<font color='#707070'>"+" Bales"+"</font>")));
                                txt_thrd.setText((Html.fromHtml("<b>" + stockData.getApproxSeedStockKg()+"</b>"+"<font color='#707070'>"+" kg"+"</font>")));
                                txt_frth.setText((Html.fromHtml("<b>" + stockData.getApproxSeedStockTruck()+"</b>"+"<font color='#707070'>"+" Truck"+"</font>")));

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
            public void onFailure(Call<StockResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
                Log.d("stygerfsbef",t.getMessage()+"");
            }
        });

    }



}
