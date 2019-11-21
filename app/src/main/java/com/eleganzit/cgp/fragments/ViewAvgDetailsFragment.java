package com.eleganzit.cgp.fragments;


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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eleganzit.cgp.HomeActivity;
import com.eleganzit.cgp.R;
import com.eleganzit.cgp.models.AvgPurchaseData;
import com.eleganzit.cgp.utils.UserLoggedInSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAvgDetailsFragment extends Fragment {

    TextView txt_ginning_name,txt_date,txt_avg_krate,avg_srate,txt_avg_per_cott,txt_avg_per_seed,txt_pur_krate,txt_seed_weight,txt_shortage,txt_approx_brate,txt_approx_bales,txt_k_weight,avg_label;
    String avg_title;
    AvgPurchaseData avgPurchaseData;
    LinearLayout ss;
    UserLoggedInSession userLoggedInSession;

    public ViewAvgDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_view_avg_details, container, false);

        HomeActivity.txt_title.setText("View Details");
        HomeActivity.share.setVisibility(View.VISIBLE);
        HomeActivity.filter.setVisibility(View.GONE);

        userLoggedInSession=new UserLoggedInSession(getActivity());

        avg_label=v.findViewById(R.id.avg_label);
        txt_ginning_name=v.findViewById(R.id.txt_ginning_name);
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
        txt_k_weight=v.findViewById(R.id.txt_k_weight);
        ss=v.findViewById(R.id.ss);

        txt_ginning_name.setText("Ginning Name : "+userLoggedInSession.getUserDetails().get(UserLoggedInSession.GINNING_NAME)+"");

        if(getArguments()!=null){
            avgPurchaseData=(AvgPurchaseData)getArguments().getSerializable("avgPurchaseData");
            avg_title=getArguments().getString("avg_title");


            String yourFormattedString1,yourFormattedString2,yourFormattedString3,yourFormattedString4,yourFormattedString5;

            DecimalFormat formatter = new DecimalFormat("##,##,##,###");
            yourFormattedString1 = formatter.format(Double.valueOf(avgPurchaseData.getPurchaseCotton()));
            yourFormattedString2 = formatter.format(Double.valueOf(avgPurchaseData.getTotalSeedWeight()));
            yourFormattedString3 = formatter.format(Double.valueOf(avgPurchaseData.getApproxBaleRate()));
            yourFormattedString4 = formatter.format(Double.valueOf(avgPurchaseData.getBales()));
            yourFormattedString5 = formatter.format(Double.valueOf(avgPurchaseData.getAvgPureCottonWeight()));


            txt_avg_krate.setText((Html.fromHtml("<b>" + avgPurchaseData.getAvgKapasRate() + "</b>"+"<font color='#707070'>"+" "+"Rs/"+avgPurchaseData.getGetcotton_rate()+"kg"+"</font>")));//+" Rs/"+avgPurchaseData.getGetcotton_rate()+"kg"
            avg_srate.setText((Html.fromHtml("<b>"+ avgPurchaseData.getAvgSeedRate()+"</b>"+"<font color='#707070'>"+" "+" Rs/"+avgPurchaseData.getGetseed_rate()+"kg"+"</font>")));
            txt_avg_per_cott.setText((Html.fromHtml("<b>" +avgPurchaseData.getAvgOfPerCotton()+"</b>"+"<font color='#707070'>"+" "+" %"+"</font>")));
            txt_avg_per_seed.setText((Html.fromHtml("<b>" +avgPurchaseData.getAvgPerSeedRate()+"</b>"+"<font color='#707070'>"+" "+" %"+"</font>")));
            txt_pur_krate.setText((Html.fromHtml("<b>"+ yourFormattedString1+"</b>"+"<font color='#707070'>"+" "+" Rs"+"</font>")));
            txt_seed_weight.setText((Html.fromHtml("<b>" +yourFormattedString2+"</b>"+"<font color='#707070'>"+" "+" kg"+"</font>")));
            txt_shortage.setText((Html.fromHtml("<b>" +avgPurchaseData.getAvgShortage()+"</b>"+"<font color='#707070'>"+" "+" %"+"</font>")));
            txt_approx_brate.setText((Html.fromHtml("<b>" +yourFormattedString3+"</b>"+"<font color='#707070'>"+" "+" Rs/"+avgPurchaseData.getGetbales_weight()+"kg"+"</font>")));
            txt_approx_bales.setText((Html.fromHtml("<b>" +yourFormattedString4+"</b>"+"<font color='#707070'>"+" "+" Bales"+"</font>")));
            txt_k_weight.setText((Html.fromHtml("<b>" +yourFormattedString5+"</b>"+"<font color='#707070'>"+" "+" kg"+"</font>")));
            avg_label.setText(""+avg_title);


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


}
