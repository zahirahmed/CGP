package com.eleganzit.cgp.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.eleganzit.cgp.HomeActivity;
import com.eleganzit.cgp.R;
import com.eleganzit.cgp.adapters.BalesAdapter;
import com.eleganzit.cgp.adapters.PurchaseAdapter;
import com.eleganzit.cgp.adapters.SeedAdapter;
import com.eleganzit.cgp.api.RetrofitAPI;
import com.eleganzit.cgp.api.RetrofitInterface;
import com.eleganzit.cgp.models.AvgPurchaseData;
import com.eleganzit.cgp.models.AvgPurchaseResponse;
import com.eleganzit.cgp.models.AvgSaleBalesData;
import com.eleganzit.cgp.models.AvgSaleBalesResponse;
import com.eleganzit.cgp.models.BalesData;
import com.eleganzit.cgp.models.GetSaleBaleData;
import com.eleganzit.cgp.models.GetSaleBaleResponse;
import com.eleganzit.cgp.models.PurchaseData;
import com.eleganzit.cgp.models.PurchaseListData;
import com.eleganzit.cgp.models.PurchaseListResponse;
import com.eleganzit.cgp.models.SaleBaleMillData;
import com.eleganzit.cgp.models.SaleBaleMillResponse;
import com.eleganzit.cgp.models.SaleBaleTraderData;
import com.eleganzit.cgp.models.SaleBaleTraderResponse;
import com.eleganzit.cgp.utils.CustomAdapter2;
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
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalesListFragment extends Fragment {

    RecyclerView rc_list;
    Button btn_add;
    BalesAdapter balesAdapter;
    ArrayList<GetSaleBaleData> arrayList=new ArrayList<>();
    TextView txt_avg_brate,txt_total_bweight,txt_total_sbales,txt_total_bamount,avg_label;
    String from,to;

    int list_size=0;
    boolean end=false;

    ProgressDialog progressDialog;
    UserLoggedInSession userLoggedInSession;
    NestedScrollView scrollview;

    String avg_title="Season: 2019-20";

    public BalesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_bales_list, container, false);

        HomeActivity.txt_title.setText("Sale Bales");
        HomeActivity.share.setVisibility(View.VISIBLE);
        HomeActivity.filter.setVisibility(View.VISIBLE);

        rc_list=v.findViewById(R.id.rc_list);
        btn_add=v.findViewById(R.id.btn_add);
        txt_avg_brate=v.findViewById(R.id.txt_avg_brate);
        avg_label=v.findViewById(R.id.avg_label);
        txt_total_bweight=v.findViewById(R.id.txt_total_bweight);
        txt_total_sbales=v.findViewById(R.id.txt_total_sbales);
        txt_total_bamount=v.findViewById(R.id.txt_total_bamount);
        scrollview=v.findViewById(R.id.scrollview);

        userLoggedInSession=new UserLoggedInSession(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        balesAdapter=new BalesAdapter(arrayList,getActivity());

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_list.setLayoutManager(layoutManager);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SalesBalesFormFragment salesBalesFormFragment=new SalesBalesFormFragment();
                Bundle bundle=new Bundle();
                bundle.putString("from","add");
                salesBalesFormFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("HomeActivity")
                        .replace(R.id.container,salesBalesFormFragment)
                        .commit();
            }
        });

        HomeActivity.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.blaes_filter_dialog);

                TextView date,heap,season,mills,trader;

                date=dialog.findViewById(R.id.date);
                heap=dialog.findViewById(R.id.heap);
                season=dialog.findViewById(R.id.season);
                mills=dialog.findViewById(R.id.mills);
                trader=dialog.findViewById(R.id.trader);

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



                        calendar.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
                            @Override
                            public void onFirstDateSelected(Calendar startDate) {

                            }

                            @Override
                            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {

                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog1.dismiss();
                            }
                        });

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //getFPurchaseform(calendar.getStartDate());

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
                                Log.d("result", result);
                                from=result;


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
                                    Log.d("result", result2);
                                    to=result2;
                                }
                                else
                                {
                                    to=from;
                                }

                                avgFkapasrate(from,to);
                                getFPurchaseform(from,to);
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

                mills.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getSellbalemills(dialog);
                    }
                });

                trader.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getSellbaletrader(dialog);
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

                takess();

            }
        });

        getPurchaseform();
        avgkapasrate();

        /*


            */

        return v;
    }


    private void getSellbalemills(final Dialog dialog) {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SaleBaleMillResponse> call = myInterface.getSellbalemills(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"");
        call.enqueue(new Callback<SaleBaleMillResponse>() {
            @Override
            public void onResponse(Call<SaleBaleMillResponse> call, Response<SaleBaleMillResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleMillData> parameterListDataArrayList=new ArrayList<>();
                    ArrayList<String> millsList=new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String millid, userid, millname;

                            Log.d("responseeee",response.toString());

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                millid = response.body().getData().get(i).getMillId();
                                userid = response.body().getData().get(i).getUserId();
                                millname = response.body().getData().get(i).getMillName();

                                SaleBaleMillData parameterListData=new SaleBaleMillData(millid,millname,userid);
                                parameterListDataArrayList.add(parameterListData);
                                millsList.add(millname);
                            }
                            dialog.dismiss();
                            showMillsDialog(millsList,parameterListDataArrayList);

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
            public void onFailure(Call<SaleBaleMillResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getSellbaletrader(final Dialog dialog) {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SaleBaleTraderResponse> call = myInterface.getSellbaletrader(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"");
        call.enqueue(new Callback<SaleBaleTraderResponse>() {
            @Override
            public void onResponse(Call<SaleBaleTraderResponse> call, Response<SaleBaleTraderResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleTraderData> parameterListDataArrayList=new ArrayList<>();
                    ArrayList<String> stradersList=new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String traderid, sell_id, trader_name;

                            Log.d("responseeee",response.toString());

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                traderid = response.body().getData().get(i).getTraderId();
                                sell_id = response.body().getData().get(i).getSellId();
                                trader_name = response.body().getData().get(i).getTraderName();

                                SaleBaleTraderData saleBaleTraderData=new SaleBaleTraderData(traderid,sell_id,trader_name);
                                parameterListDataArrayList.add(saleBaleTraderData);
                                stradersList.add(trader_name);
                            }
                            dialog.dismiss();
                            showTradersDialog(stradersList,parameterListDataArrayList);

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
            public void onFailure(Call<SaleBaleTraderResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }


    void showMillsDialog(final ArrayList<String> smillsList,final ArrayList<SaleBaleMillData> millsList) {

        final ListAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, smillsList);

        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));

        builder.setTitle("Select Spinning Mill");

        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                Log.d("asdasf",millsList.get(i).getMillId()+"");

                avgFmillSalesbalerate(millsList.get(i).getMillId()+"","");
                getFmillsSellbaleform(millsList.get(i).getMillId()+"","");

            }
        });

        builder.show();

    }

    void showTradersDialog(final ArrayList<String> stradersList,final ArrayList<SaleBaleTraderData> tradersList) {

        final ListAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, stradersList);

        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));

        builder.setTitle("Select Trader");

        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                Log.d("asdasf",tradersList.get(i).getTraderId()+"");

                avgFmillSalesbalerate("",tradersList.get(i).getTraderId()+"");
                getFmillsSellbaleform("",tradersList.get(i).getTraderId()+"");

            }
        });

        builder.show();

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
    private void avgkapasrate() {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AvgSaleBalesResponse> call = myInterface.avgSalesbalerate(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+""
        );
        call.enqueue(new Callback<AvgSaleBalesResponse>() {
            @Override
            public void onResponse(Call<AvgSaleBalesResponse> call, Response<AvgSaleBalesResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String id,ginning_name,state, area = "", email,expance;
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                //ed_heap.setHint("Heap (Last heap - "+response.body().getData().get(i).getHeap()+")");
                                AvgSaleBalesData avgSaleBalesData=response.body().getData().get(i);

                                String yourFormattedString1,yourFormattedString2,yourFormattedString3,yourFormattedString4;

                                DecimalFormat formatter = new DecimalFormat("##,##,##,###");
                                yourFormattedString1 = formatter.format(Double.valueOf(avgSaleBalesData.getAvgBaleRate()));
                                yourFormattedString2 = formatter.format(Double.valueOf(avgSaleBalesData.getTotalBaleWeight()));
                                yourFormattedString3 = formatter.format(Double.valueOf(avgSaleBalesData.getTotalBaleSold()));
                                yourFormattedString4 = formatter.format(Double.valueOf(avgSaleBalesData.getTotalBaleAmmount()));

                                txt_avg_brate.setText((Html.fromHtml("<b>" + yourFormattedString1+"</b>"+"<font color='#707070'>"+" "+" Rs/"+avgSaleBalesData.getGetbales_weight()+"kg"+"</font>")));
                                txt_total_bweight.setText((Html.fromHtml("<b>" + yourFormattedString2+"</b>"+"<font color='#707070'>"+" "+" kg"+"</font>")));
                                txt_total_sbales.setText((Html.fromHtml("<b>" + yourFormattedString3+"</b>"+"<font color='#707070'>"+" "+" Bales"+"</font>")));
                                txt_total_bamount.setText((Html.fromHtml("<b>" + yourFormattedString4+"</b>"+"<font color='#707070'>"+" "+" Rs"+"</font>")));

                                avg_label.setText(""+avg_title);

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
            public void onFailure(Call<AvgSaleBalesResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void getPurchaseform() {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetSaleBaleResponse> call = myInterface.getSellbaleform(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                "1"
        );
        call.enqueue(new Callback<GetSaleBaleResponse>() {
            @Override
            public void onResponse(Call<GetSaleBaleResponse> call, Response<GetSaleBaleResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {


                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            if(arrayList.size()>0){
                                arrayList.clear();
                            }

                            for(int i=0;i<response.body().getData().size();i++){


                                GetSaleBaleData balesData=new GetSaleBaleData(response.body().getData().get(i).getSellId(),response.body().getData().get(i).getUserId(),response.body().getData().get(i).getInvoiceNo(),response.body().getData().get(i).getPrNo(),response.body().getData().get(i).getSellDate(),response.body().getData().get(i).getHeap(),response.body().getData().get(i).getBaleRate(),response.body().getData().get(i).getFinalWeight(),response.body().getData().get(i).getNoOfBales(),response.body().getData().get(i).getMillId(),response.body().getData().get(i).getTraderId(),response.body().getData().get(i).getApproxBaleAmmount(),response.body().getData().get(i).getCreatedDate());


                                arrayList.add(balesData);

                            }
                            balesAdapter=new BalesAdapter(arrayList,getActivity());

                            rc_list.setAdapter(balesAdapter);

                            getPurchaseform2((arrayList.size()+1)+"");

                        }
                    } else {
                        end=true;
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetSaleBaleResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getPurchaseform2(String limit) {


        balesAdapter.addNullData();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetSaleBaleResponse> call = myInterface.getSellbaleform(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                ""+limit
        );
        call.enqueue(new Callback<GetSaleBaleResponse>() {
            @Override
            public void onResponse(Call<GetSaleBaleResponse> call, Response<GetSaleBaleResponse> response) {
                balesAdapter.removeNull();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            ArrayList<GetSaleBaleData> arrayList=new ArrayList<>();

                            for(int i=0;i<response.body().getData().size();i++){

                                GetSaleBaleData balesData=new GetSaleBaleData(response.body().getData().get(i).getSellId(),response.body().getData().get(i).getUserId(),response.body().getData().get(i).getInvoiceNo(),response.body().getData().get(i).getPrNo(),response.body().getData().get(i).getSellDate(),response.body().getData().get(i).getHeap(),response.body().getData().get(i).getBaleRate(),response.body().getData().get(i).getFinalWeight(),response.body().getData().get(i).getNoOfBales(),response.body().getData().get(i).getMillId(),response.body().getData().get(i).getTraderId(),response.body().getData().get(i).getApproxBaleAmmount(),response.body().getData().get(i).getCreatedDate());

                                arrayList.add(balesData);


                            }
                            list_size=list_size+response.body().getData().size();

                            balesAdapter.addData(arrayList);

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
            public void onFailure(Call<GetSaleBaleResponse> call, Throwable t) {
                balesAdapter.removeNull();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void avgFkapasrate(String from,String to) {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AvgSaleBalesResponse> call = myInterface.avgFSalesbalerate(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                ""+from,""+to
        );
        call.enqueue(new Callback<AvgSaleBalesResponse>() {
            @Override
            public void onResponse(Call<AvgSaleBalesResponse> call, Response<AvgSaleBalesResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String id,ginning_name,state, area = "", email,expance;
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                //ed_heap.setHint("Heap (Last heap - "+response.body().getData().get(i).getHeap()+")");
                                AvgSaleBalesData avgSaleBalesData=response.body().getData().get(i);

                                String yourFormattedString1,yourFormattedString2,yourFormattedString3,yourFormattedString4;

                                DecimalFormat formatter = new DecimalFormat("##,##,##,###");
                                yourFormattedString1 = formatter.format(Double.valueOf(avgSaleBalesData.getAvgBaleRate()));
                                yourFormattedString2 = formatter.format(Double.valueOf(avgSaleBalesData.getTotalBaleWeight()));
                                yourFormattedString3 = formatter.format(Double.valueOf(avgSaleBalesData.getTotalBaleSold()));
                                yourFormattedString4 = formatter.format(Double.valueOf(avgSaleBalesData.getTotalBaleAmmount()));

                                txt_avg_brate.setText((Html.fromHtml("<b>" + yourFormattedString1+"</b>"+"<font color='#707070'>"+" "+" Rs/"+avgSaleBalesData.getGetbales_weight()+"kg"+"</font>")));
                                txt_total_bweight.setText((Html.fromHtml("<b>" + yourFormattedString2+"</b>"+"<font color='#707070'>"+" "+" kg"+"</font>")));
                                txt_total_sbales.setText((Html.fromHtml("<b>" + yourFormattedString3+"</b>"+"<font color='#707070'>"+" "+" Bales"+"</font>")));
                                txt_total_bamount.setText((Html.fromHtml("<b>" + yourFormattedString4+"</b>"+"<font color='#707070'>"+" "+" Rs"+"</font>")));

                                avg_label.setText(""+avg_title);


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
            public void onFailure(Call<AvgSaleBalesResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getFPurchaseform(final String from, final String to) {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetSaleBaleResponse> call = myInterface.getFSellbaleform(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                ""+from,""+to,1+""
        );
        call.enqueue(new Callback<GetSaleBaleResponse>() {
            @Override
            public void onResponse(Call<GetSaleBaleResponse> call, Response<GetSaleBaleResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {


                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            if(arrayList.size()>0){
                                arrayList.clear();
                            }

                            for(int i=0;i<response.body().getData().size();i++){


                                GetSaleBaleData balesData=new GetSaleBaleData(response.body().getData().get(i).getSellId(),response.body().getData().get(i).getUserId(),response.body().getData().get(i).getInvoiceNo(),response.body().getData().get(i).getPrNo(),response.body().getData().get(i).getSellDate(),response.body().getData().get(i).getHeap(),response.body().getData().get(i).getBaleRate(),response.body().getData().get(i).getFinalWeight(),response.body().getData().get(i).getNoOfBales(),response.body().getData().get(i).getMillId(),response.body().getData().get(i).getTraderId(),response.body().getData().get(i).getApproxBaleAmmount(),response.body().getData().get(i).getCreatedDate());


                                arrayList.add(balesData);

                            }
                            balesAdapter=new BalesAdapter(arrayList,getActivity());

                            rc_list.setAdapter(balesAdapter);

                            getFPurchaseform2((arrayList.size()+1)+"",from,to);

                        }
                    } else {
                        end=true;

                        if(arrayList.size()>0){
                            arrayList.clear();
                        }

                        balesAdapter=new BalesAdapter(arrayList,getActivity());

                        rc_list.setAdapter(balesAdapter);

                        Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetSaleBaleResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getFPurchaseform2(String limit, final String from, final String to) {


        balesAdapter.addNullData();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetSaleBaleResponse> call = myInterface.getFSellbaleform(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                ""+from,""+to,""+limit
        );
        call.enqueue(new Callback<GetSaleBaleResponse>() {
            @Override
            public void onResponse(Call<GetSaleBaleResponse> call, Response<GetSaleBaleResponse> response) {
                balesAdapter.removeNull();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            ArrayList<GetSaleBaleData> arrayList=new ArrayList<>();

                            for(int i=0;i<response.body().getData().size();i++){

                                GetSaleBaleData balesData=new GetSaleBaleData(response.body().getData().get(i).getSellId(),response.body().getData().get(i).getUserId(),response.body().getData().get(i).getInvoiceNo(),response.body().getData().get(i).getPrNo(),response.body().getData().get(i).getSellDate(),response.body().getData().get(i).getHeap(),response.body().getData().get(i).getBaleRate(),response.body().getData().get(i).getFinalWeight(),response.body().getData().get(i).getNoOfBales(),response.body().getData().get(i).getMillId(),response.body().getData().get(i).getTraderId(),response.body().getData().get(i).getApproxBaleAmmount(),response.body().getData().get(i).getCreatedDate());

                                arrayList.add(balesData);


                            }
                            list_size=list_size+response.body().getData().size();

                            balesAdapter.addData(arrayList);

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
            public void onFailure(Call<GetSaleBaleResponse> call, Throwable t) {
                balesAdapter.removeNull();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void avgFmillSalesbalerate(String mill_id,String trader_id) {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AvgSaleBalesResponse> call = myInterface.avgFmillSalesbalerate(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                ""+mill_id,""+trader_id
        );
        call.enqueue(new Callback<AvgSaleBalesResponse>() {
            @Override
            public void onResponse(Call<AvgSaleBalesResponse> call, Response<AvgSaleBalesResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String id,ginning_name,state, area = "", email,expance;
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                //ed_heap.setHint("Heap (Last heap - "+response.body().getData().get(i).getHeap()+")");
                                AvgSaleBalesData avgSaleBalesData=response.body().getData().get(i);

                                String yourFormattedString1,yourFormattedString2,yourFormattedString3,yourFormattedString4;

                                DecimalFormat formatter = new DecimalFormat("##,##,##,###");
                                yourFormattedString1 = formatter.format(Double.valueOf(avgSaleBalesData.getAvgBaleRate()));
                                yourFormattedString2 = formatter.format(Double.valueOf(avgSaleBalesData.getTotalBaleWeight()));
                                yourFormattedString3 = formatter.format(Double.valueOf(avgSaleBalesData.getTotalBaleSold()));
                                yourFormattedString4 = formatter.format(Double.valueOf(avgSaleBalesData.getTotalBaleAmmount()));

                                txt_avg_brate.setText((Html.fromHtml("<b>" + yourFormattedString1+"</b>"+"<font color='#707070'>"+" "+" Rs/"+avgSaleBalesData.getGetbales_weight()+"kg"+"</font>")));
                                txt_total_bweight.setText((Html.fromHtml("<b>" + yourFormattedString2+"</b>"+"<font color='#707070'>"+" "+" kg"+"</font>")));
                                txt_total_sbales.setText((Html.fromHtml("<b>" + yourFormattedString3+"</b>"+"<font color='#707070'>"+" "+" Bales"+"</font>")));
                                txt_total_bamount.setText((Html.fromHtml("<b>" + yourFormattedString4+"</b>"+"<font color='#707070'>"+" "+" Rs"+"</font>")));

                                avg_label.setText(""+avg_title);


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
            public void onFailure(Call<AvgSaleBalesResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getFmillsSellbaleform(final String mill_id, final String trader_id) {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetSaleBaleResponse> call = myInterface.getFmillsSellbaleform(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                ""+mill_id,""+trader_id,1+""
        );
        call.enqueue(new Callback<GetSaleBaleResponse>() {
            @Override
            public void onResponse(Call<GetSaleBaleResponse> call, Response<GetSaleBaleResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {


                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            if(arrayList.size()>0){
                                arrayList.clear();
                            }

                            for(int i=0;i<response.body().getData().size();i++){


                                GetSaleBaleData balesData=new GetSaleBaleData(response.body().getData().get(i).getSellId(),response.body().getData().get(i).getUserId(),response.body().getData().get(i).getInvoiceNo(),response.body().getData().get(i).getPrNo(),response.body().getData().get(i).getSellDate(),response.body().getData().get(i).getHeap(),response.body().getData().get(i).getBaleRate(),response.body().getData().get(i).getFinalWeight(),response.body().getData().get(i).getNoOfBales(),response.body().getData().get(i).getMillId(),response.body().getData().get(i).getTraderId(),response.body().getData().get(i).getApproxBaleAmmount(),response.body().getData().get(i).getCreatedDate());


                                arrayList.add(balesData);

                            }
                            balesAdapter=new BalesAdapter(arrayList,getActivity());

                            rc_list.setAdapter(balesAdapter);

                            getFmillsSellbaleform2((arrayList.size()+1)+"",from,to);

                        }
                    } else {
                        end=true;

                        if(arrayList.size()>0){
                            arrayList.clear();
                        }

                        balesAdapter=new BalesAdapter(arrayList,getActivity());

                        rc_list.setAdapter(balesAdapter);

                        Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetSaleBaleResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getFmillsSellbaleform2(String limit, final String mill_id, final String trader_id) {


        balesAdapter.addNullData();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetSaleBaleResponse> call = myInterface.getFmillsSellbaleform(
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                ""+mill_id,""+trader_id,""+limit
        );
        call.enqueue(new Callback<GetSaleBaleResponse>() {
            @Override
            public void onResponse(Call<GetSaleBaleResponse> call, Response<GetSaleBaleResponse> response) {
                balesAdapter.removeNull();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            ArrayList<GetSaleBaleData> arrayList=new ArrayList<>();

                            for(int i=0;i<response.body().getData().size();i++){

                                GetSaleBaleData balesData=new GetSaleBaleData(response.body().getData().get(i).getSellId(),response.body().getData().get(i).getUserId(),response.body().getData().get(i).getInvoiceNo(),response.body().getData().get(i).getPrNo(),response.body().getData().get(i).getSellDate(),response.body().getData().get(i).getHeap(),response.body().getData().get(i).getBaleRate(),response.body().getData().get(i).getFinalWeight(),response.body().getData().get(i).getNoOfBales(),response.body().getData().get(i).getMillId(),response.body().getData().get(i).getTraderId(),response.body().getData().get(i).getApproxBaleAmmount(),response.body().getData().get(i).getCreatedDate());

                                arrayList.add(balesData);


                            }
                            list_size=list_size+response.body().getData().size();

                            balesAdapter.addData(arrayList);

                            getFmillsSellbaleform2(list_size+"",mill_id,trader_id);
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
            public void onFailure(Call<GetSaleBaleResponse> call, Throwable t) {
                balesAdapter.removeNull();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }


}
