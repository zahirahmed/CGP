package com.eleganzit.cgp.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.cgp.HomeActivity;
import com.eleganzit.cgp.R;
import com.eleganzit.cgp.api.RetrofitAPI;
import com.eleganzit.cgp.api.RetrofitInterface;
import com.eleganzit.cgp.models.AddBaleMillResponse;
import com.eleganzit.cgp.models.AddBaleTraderResponse;
import com.eleganzit.cgp.models.GetBaleResponse;
import com.eleganzit.cgp.models.GetPurchaseResponse;
import com.eleganzit.cgp.models.HeapListResponse;
import com.eleganzit.cgp.models.PurchaseFormResponse;
import com.eleganzit.cgp.models.SaleBaleFormResponse;
import com.eleganzit.cgp.models.SaleBaleMillData;
import com.eleganzit.cgp.models.SaleBaleMillResponse;
import com.eleganzit.cgp.models.SaleBaleTraderData;
import com.eleganzit.cgp.models.SaleBaleTraderResponse;
import com.eleganzit.cgp.models.UpdateBaleResponse;
import com.eleganzit.cgp.utils.CustomAdapter;
import com.eleganzit.cgp.utils.CustomAdapter2;
import com.eleganzit.cgp.utils.UserLoggedInSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesBalesFormFragment extends Fragment {

    ProgressDialog progressDialog;
    UserLoggedInSession userLoggedInSession;
    ArrayList<SaleBaleMillData> arrayList=new ArrayList<>();
    ArrayList<String> sarrayList=new ArrayList<>();
    ArrayList<SaleBaleTraderData> arrayList2=new ArrayList<>();
    ArrayList<String> sarrayList2=new ArrayList<>();
    Spinner spinner,spinner2;
    EditText ed_invoice,ed_date,ed_heap,ed_pr,ed_bales_no,ed_brate,ed_weight;
    ImageButton add,add2;
    ImageView down,down2;
    ArrayList<String>  mStringList= new ArrayList<String>();
    ArrayList<String> itemsSelected = new ArrayList();
    String selected_heaps="";
    String mill_id="";
    String trader_id="";
    Button btn_submit;
    String byDate="";
    String id="";
    String from="";
    LinearLayout layout1,layout2;
    ScrollView ss;


    public SalesBalesFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_sales_bales_form, container, false);

        HomeActivity.txt_title.setText("Sale Bales Form");
        HomeActivity.share.setVisibility(View.GONE);
        HomeActivity.filter.setVisibility(View.GONE);

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


        ed_invoice=v.findViewById(R.id.ed_invoice);
        ed_date=v.findViewById(R.id.ed_date);
        ed_heap=v.findViewById(R.id.ed_heap);
        ed_pr=v.findViewById(R.id.ed_pr);
        ed_bales_no=v.findViewById(R.id.ed_bales_no);
        ed_brate=v.findViewById(R.id.ed_brate);
        ed_weight=v.findViewById(R.id.ed_weight);
        layout1=v.findViewById(R.id.layout1);
        layout2=v.findViewById(R.id.layout2);
        ss=v.findViewById(R.id.ss);

        spinner=v.findViewById(R.id.spinner);
        spinner2=v.findViewById(R.id.spinner2);
        add=v.findViewById(R.id.add);
        add2=v.findViewById(R.id.add2);
        down=v.findViewById(R.id.down);
        down2=v.findViewById(R.id.down2);
        btn_submit=v.findViewById(R.id.btn_submit);

        ed_heap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog;

                String[] mStringArray = new String[mStringList.size()];
                mStringArray = mStringList.toArray(mStringArray);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final String[] finalMStringArray = mStringArray;

                builder.setMultiChoiceItems(mStringArray, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedItemId,
                                                boolean isSelected) {
                                if (isSelected) {
                                    itemsSelected.add(finalMStringArray[selectedItemId]);
                                } else if (itemsSelected.contains(selectedItemId)) {
                                    itemsSelected.remove(finalMStringArray[selectedItemId]);
                                }
                            }
                        })
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //Your logic when OK button is clicked

                                StringBuilder sb2 = new StringBuilder();

                                for(int i=0;i<itemsSelected.size();i++)
                                {
                                    Log.d("productsssssssss",itemsSelected.get(i)+"");
                                    if (i==itemsSelected.size()-1)
                                    {
                                        sb2.append(itemsSelected.get(i)).append("");
                                    }
                                    else {
                                        sb2.append(itemsSelected.get(i)).append(",");

                                    }
                                }

                                Log.d("fgergsdf2222",sb2.toString()+"");

                                selected_heaps=sb2.toString();
                                ed_heap.setText(selected_heaps+"");

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                dialog = builder.create();
                dialog.show();

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.salebalemill_dailog);

                final EditText ed_sale=dialog.findViewById(R.id.ed_sale);
                ed_sale.setHint("Spinning Mill Name");
                TextView txt_submit=dialog.findViewById(R.id.txt_submit);

                txt_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ed_sale.getText().toString().equalsIgnoreCase("")){

                        }
                        else
                        {
                            dialog.dismiss();
                            addSellbalemills(ed_sale.getText().toString());
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

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.salebalemill_dailog);

                final EditText ed_sale=dialog.findViewById(R.id.ed_sale);
                ed_sale.setHint("Trader Name");
                TextView txt_submit=dialog.findViewById(R.id.txt_submit);

                txt_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ed_sale.getText().toString().equalsIgnoreCase("")){

                        }
                        else
                        {
                            dialog.dismiss();
                            addSellbaletrader(ed_sale.getText().toString());
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

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){

                    SaleBaleMillData saleBaleMillData=arrayList.get(spinner.getSelectedItemPosition());

                    mill_id=saleBaleMillData.getMillId();

                    SaleBaleTraderData saleBaleTraderData=arrayList2.get(spinner2.getSelectedItemPosition());

                    trader_id=saleBaleTraderData.getTraderId();

                    if(from.equalsIgnoreCase("edit"))
                    {

                        SaleBaleMillData saleBaleMillData2=arrayList.get(spinner.getSelectedItemPosition());

                        mill_id=saleBaleMillData2.getMillId();

                        SaleBaleTraderData saleBaleTraderData2=arrayList2.get(spinner2.getSelectedItemPosition());

                        trader_id=saleBaleTraderData2.getTraderId();


                        updateSalebaleform();
                    }
                    else
                    {
                        addSellform();
                    }

                }
            }
        });

        if(from.equalsIgnoreCase("edit"))
        {
            getSalebale_byid();

            getSellbalemills();
            getSellbaletrader();
            //
            // heapList();

        }

        if(from.equalsIgnoreCase("add"))
        {

            getSellbalemills();
            getSellbaletrader();
            //
            // heapList();

        }

        if(from.equalsIgnoreCase("view"))
        {
            HomeActivity.txt_title.setText("View Details");
            HomeActivity.share.setVisibility(View.VISIBLE);
            getSalebale_byid();

                layout1.setClickable(true);
                layout2.setClickable(true);

            getSellbalemills();
            getSellbaletrader();
            btn_submit.setVisibility(View.GONE);
            down2.setVisibility(View.GONE);
            down.setVisibility(View.GONE);
            add.setVisibility(View.GONE);
            add2.setVisibility(View.GONE);
            /*spinner.setEnabled(false);
            spinner2.setEnabled(false);*/
            ed_invoice.setFocusable(false);
            ed_pr.setFocusable(false);
            ed_heap.setFocusable(false);
            ed_date.setFocusable(false);
            ed_date.setOnClickListener(null);
            ed_bales_no.setFocusable(false);
            ed_brate.setFocusable(false);
            ed_weight.setFocusable(false);
        
            ed_invoice.setCursorVisible(false);
            ed_pr.setCursorVisible(false);
            ed_heap.setCursorVisible(false);
            ed_date.setCursorVisible(false);
            ed_bales_no.setCursorVisible(false);
            ed_brate.setCursorVisible(false);
            ed_weight.setCursorVisible(false);
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

    private void addSellform() {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SaleBaleFormResponse> call = myInterface.addSellform(
                ed_invoice.getText().toString()+"",
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                ed_pr.getText().toString()+"",
                byDate+"",
                ed_heap.getText().toString()+"",
                ed_brate.getText().toString()+"",
                ed_weight.getText().toString()+"",
                ed_bales_no.getText().toString()+"",
                mill_id+"",
                trader_id+""
        );
        call.enqueue(new Callback<SaleBaleFormResponse>() {
            @Override
            public void onResponse(Call<SaleBaleFormResponse> call, Response<SaleBaleFormResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            getFragmentManager().popBackStack();

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
            public void onFailure(Call<SaleBaleFormResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }


    public boolean isValid() {

        if (ed_invoice.getText().toString().equals("") ||ed_invoice.getText().toString().equals("0")) {
            ed_invoice.setError("Please enter invoice");

            ed_invoice.requestFocus();
            return false;
        }else if (ed_date.getText().toString().equals("")) {
            ed_date.setError("Please select date");

            ed_date.requestFocus();
            return false;
        }/*else if (ed_heap.getText().toString().equals("")) {
            ed_heap.setError("Please select heap");

            ed_heap.requestFocus();
            return false;
        }*/else if (ed_weight.getText().toString().equals("") || ed_weight.getText().toString().equals("0")) {
            ed_weight.setError("Please enter weight");

            ed_weight.requestFocus();
            return false;
        }else if (ed_pr.getText().toString().equals("") || ed_pr.getText().toString().equals("0")) {
            ed_pr.setError("Please enter Pr");

            ed_pr.requestFocus();
            return false;
        }else if (ed_bales_no.getText().toString().equals("") ||ed_bales_no.getText().toString().equals("0")) {
            ed_bales_no.setError("Please enter no. of bales");

            ed_bales_no.requestFocus();
            return false;
        }else if (ed_brate.getText().toString().equals("") || ed_brate.getText().toString().equals("0")) {
            ed_brate.setError("Please enter bale rate");

            ed_brate.requestFocus();
            return false;
        }else if ((spinner2.getSelectedItem()==null)){

            Toast.makeText(getActivity(), "Add Trader name", Toast.LENGTH_SHORT).show();


            return false;
        }else if ((spinner.getSelectedItem()==null)) {

            Toast.makeText(getActivity(), "Add Spinning mill name", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }


    private void heapList() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<HeapListResponse> call = myInterface.heapList(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"");
        call.enqueue(new Callback<HeapListResponse>() {
            @Override
            public void onResponse(Call<HeapListResponse> call, Response<HeapListResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleMillData> parameterListDataArrayList=new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            Log.d("responseeee",response.toString());

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                mStringList.add(""+response.body().getData().get(i).getHeap());
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
            public void onFailure(Call<HeapListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getSalebale_byid() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetBaleResponse> call = myInterface.getSalebale_byid(id+"");
        call.enqueue(new Callback<GetBaleResponse>() {
            @Override
            public void onResponse(Call<GetBaleResponse> call, Response<GetBaleResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleMillData> parameterListDataArrayList=new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            Log.d("responseeee",response.body().getData().get(0).getTrader_name());
                            Log.d("responseeee",response.body().getData().get(0).getMillName());

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                ed_invoice.setText(""+response.body().getData().get(i).getInvoiceNo());
                                ed_date.setText(""+response.body().getData().get(i).getSellDate());
                                ed_pr.setText(""+response.body().getData().get(i).getPrNo());
                                ed_bales_no.setText(""+response.body().getData().get(i).getNoOfBales());
                                ed_brate.setText(""+response.body().getData().get(i).getBaleRate());
                                ed_weight.setText(""+response.body().getData().get(i).getFinalWeight());
                                byDate=ed_date.getText().toString();

                                String[] mStringArray = new String[sarrayList.size()];
                                mStringArray = sarrayList.toArray(mStringArray);

                                String compareValue = response.body().getData().get(i).getMillName();
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                        (getActivity(), android.R.layout.simple_spinner_item,
                                                mStringArray);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(adapter);
                                if (compareValue != null) {
                                    int spinnerPosition = adapter.getPosition(compareValue);
                                    spinner.setSelection(spinnerPosition);
                                }

                                Log.d("responseeeess",compareValue);

                                String[] mStringArray2 = new String[sarrayList2.size()];
                                mStringArray2 = sarrayList2.toArray(mStringArray2);

                                String compareValue2 = response.body().getData().get(i).getTrader_name();
                                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                                        (getActivity(), android.R.layout.simple_spinner_item,
                                                mStringArray2);
                                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner2.setAdapter(adapter2);
                                if (compareValue2 != null) {
                                    int spinnerPosition = adapter2.getPosition(compareValue2);
                                    spinner2.setSelection(spinnerPosition);
                                }
                                Log.d("responss",""+compareValue2);

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
            public void onFailure(Call<GetBaleResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getSellbalemills() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SaleBaleMillResponse> call = myInterface.getSellbalemills(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"");
        call.enqueue(new Callback<SaleBaleMillResponse>() {
            @Override
            public void onResponse(Call<SaleBaleMillResponse> call, Response<SaleBaleMillResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleMillData> parameterListDataArrayList=new ArrayList<>();

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
                                sarrayList.add(millname);
                            }

                            arrayList=parameterListDataArrayList;

                            CustomAdapter2 dataAdapter = new CustomAdapter2(getActivity(), android.R.layout.simple_spinner_item, parameterListDataArrayList,-1)
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
                            spinner.setSelection(parameterListDataArrayList.size()-1);

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

    private void getSellbaletrader() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SaleBaleTraderResponse> call = myInterface.getSellbaletrader(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"");
        call.enqueue(new Callback<SaleBaleTraderResponse>() {
            @Override
            public void onResponse(Call<SaleBaleTraderResponse> call, Response<SaleBaleTraderResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleTraderData> parameterListDataArrayList=new ArrayList<>();

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
                                sarrayList2.add(trader_name);
                            }

                            arrayList2=parameterListDataArrayList;

                            CustomAdapter2 dataAdapter = new CustomAdapter2(getActivity(), android.R.layout.simple_spinner_item, parameterListDataArrayList,-1)
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
                            spinner2.setAdapter(dataAdapter);
                            spinner2.setSelection(parameterListDataArrayList.size()-1);

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

    private void addSellbalemills(String mill) {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AddBaleMillResponse> call = myInterface.addSellbalemills(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",mill+"");
        call.enqueue(new Callback<AddBaleMillResponse>() {
            @Override
            public void onResponse(Call<AddBaleMillResponse> call, Response<AddBaleMillResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleMillData> parameterListDataArrayList=new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            getSellbalemills();

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
            public void onFailure(Call<AddBaleMillResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateSalebaleform() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<UpdateBaleResponse> call = myInterface.updateSalebaleform(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",
                id+"",
                ed_invoice.getText().toString()+"",
                ed_pr.getText().toString()+"",
                byDate+"",
                ed_heap.getText().toString()+"",
                ed_brate.getText().toString()+"",
                ed_weight.getText().toString()+"",
                ed_bales_no.getText().toString()+"",
                mill_id+"",
                trader_id+"");
        call.enqueue(new Callback<UpdateBaleResponse>() {
            @Override
            public void onResponse(Call<UpdateBaleResponse> call, Response<UpdateBaleResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleMillData> parameterListDataArrayList=new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {


                            BalesListFragment balesListFragment=new BalesListFragment();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container,balesListFragment)
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
            public void onFailure(Call<UpdateBaleResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addSellbaletrader(String trader) {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AddBaleTraderResponse> call = myInterface.addSellbaletrader(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+"",trader+"");
        call.enqueue(new Callback<AddBaleTraderResponse>() {
            @Override
            public void onResponse(Call<AddBaleTraderResponse> call, Response<AddBaleTraderResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleMillData> parameterListDataArrayList=new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            getSellbaletrader();

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
            public void onFailure(Call<AddBaleTraderResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }



}
