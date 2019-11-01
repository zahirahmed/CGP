package com.eleganzit.cgp.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.cgp.HomeActivity;
import com.eleganzit.cgp.R;
import com.eleganzit.cgp.api.RetrofitAPI;
import com.eleganzit.cgp.api.RetrofitInterface;
import com.eleganzit.cgp.models.AddBaleMillResponse;
import com.eleganzit.cgp.models.AddBaleTraderResponse;
import com.eleganzit.cgp.models.AddSeedMillsResponse;
import com.eleganzit.cgp.models.GetBaleResponse;
import com.eleganzit.cgp.models.GetSeedMillsData;
import com.eleganzit.cgp.models.GetSeedMillsResponse;
import com.eleganzit.cgp.models.GetSeedResponse;
import com.eleganzit.cgp.models.GetSeedTraderData;
import com.eleganzit.cgp.models.GetSeedTraderResponse;
import com.eleganzit.cgp.models.HeapListResponse;
import com.eleganzit.cgp.models.SaleBaleFormResponse;
import com.eleganzit.cgp.models.SaleBaleMillData;
import com.eleganzit.cgp.models.SaleBaleMillResponse;
import com.eleganzit.cgp.models.SaleBaleTraderData;
import com.eleganzit.cgp.models.SaleBaleTraderResponse;
import com.eleganzit.cgp.models.UpdateBaleResponse;
import com.eleganzit.cgp.models.UpdateSeedResponse;
import com.eleganzit.cgp.utils.CustomAdapter2;
import com.eleganzit.cgp.utils.UserLoggedInSession;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaleSeedFormFragment extends Fragment {

    ProgressDialog progressDialog;
    UserLoggedInSession userLoggedInSession;
    ArrayList<GetSeedMillsData> arrayList = new ArrayList<>();
    ArrayList<String> sarrayList = new ArrayList<>();
    ArrayList<GetSeedTraderData> arrayList2 = new ArrayList<>();
    ArrayList<String> sarrayList2 = new ArrayList<>();
    Spinner spinner, spinner2;
    EditText ed_invoice, ed_date, ed_heap, ed_brate, ed_weight, ed_vehicle, ed_rate;
    ImageButton add, add2;
    ArrayList<String> mStringList = new ArrayList<String>();
    ArrayList<String> itemsSelected = new ArrayList();
    String selected_heaps = "";
    String mill_id = "";
    String trader_id = "";
    Button btn_submit;
    String byDate = "";
    private String id = "";
    private String from = "";
    ImageView down, down2;
    LinearLayout layout1,layout2;

    public SaleSeedFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sale_seed_form, container, false);

        HomeActivity.txt_title.setText("Sale Seed Form");
        HomeActivity.share.setVisibility(View.GONE);
        HomeActivity.filter.setVisibility(View.GONE);


        userLoggedInSession = new UserLoggedInSession(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        Bundle bundle = getArguments();

        //here is your list array
        if (bundle != null) {
            id = bundle.getString("id");
            from = bundle.getString("from");
        }


        ed_invoice = v.findViewById(R.id.ed_invoice);
        ed_date = v.findViewById(R.id.ed_date);
        ed_heap = v.findViewById(R.id.ed_heap);
        ed_brate = v.findViewById(R.id.ed_brate);
        ed_weight = v.findViewById(R.id.ed_weight);
        ed_vehicle = v.findViewById(R.id.ed_vehicle);
        ed_rate = v.findViewById(R.id.ed_rate);
        down = v.findViewById(R.id.down);
        down2 = v.findViewById(R.id.down2);
        layout1=v.findViewById(R.id.layout1);
        layout2=v.findViewById(R.id.layout2);

        spinner = v.findViewById(R.id.spinner);
        spinner2 = v.findViewById(R.id.spinner2);
        add = v.findViewById(R.id.add);
        add2 = v.findViewById(R.id.add2);
        btn_submit = v.findViewById(R.id.btn_submit);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.salebalemill_dailog);

                final EditText ed_sale = dialog.findViewById(R.id.ed_sale);
                TextView txt_submit = dialog.findViewById(R.id.txt_submit);

                ed_sale.setHint("Oil Mill Name");
                txt_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ed_sale.getText().toString().equalsIgnoreCase("")) {

                        } else {
                            dialog.dismiss();
                            addSellseedmills(ed_sale.getText().toString());
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
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.salebalemill_dailog);

                final EditText ed_sale = dialog.findViewById(R.id.ed_sale);
                ed_sale.setHint("Broker Name");
                TextView txt_submit = dialog.findViewById(R.id.txt_submit);

                txt_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ed_sale.getText().toString().equalsIgnoreCase("")) {

                        } else {
                            dialog.dismiss();
                            addSellseedtrader(ed_sale.getText().toString());
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

                        byDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                        ed_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                StartTime.getDatePicker().setMaxDate(System.currentTimeMillis());

                StartTime.show();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {

                    GetSeedMillsData saleBaleMillData = arrayList.get(spinner.getSelectedItemPosition());

                    mill_id = saleBaleMillData.getSeedMillId();

                    GetSeedTraderData saleBaleTraderData = arrayList2.get(spinner2.getSelectedItemPosition());

                    trader_id = saleBaleTraderData.getSeedTraderId();

                    if (from.equalsIgnoreCase("edit")) {

                        GetSeedMillsData saleBaleMillData2 = arrayList.get(spinner.getSelectedItemPosition());

                        mill_id = saleBaleMillData2.getSeedMillId();

                        GetSeedTraderData saleBaleTraderData2 = arrayList2.get(spinner2.getSelectedItemPosition());

                        trader_id = saleBaleTraderData2.getSeedTraderId();


                        updateSalebaleform();
                    } else {
                        addSellform();
                    }
                }
            }
        });

        if (from.equalsIgnoreCase("edit")) {
            getSalebale_byid();

            getSellseedmills();
            getSellseedtrader();
            //heapList();

        }

        if (from.equalsIgnoreCase("add")) {

            getSellseedmills();
            getSellseedtrader();
            //heapList();

        }

        if (from.equalsIgnoreCase("view")) {
            HomeActivity.txt_title.setText("View Details");
            getSalebale_byid();
            getSellseedmills();
            getSellseedtrader();
            btn_submit.setVisibility(View.GONE);
            down2.setVisibility(View.GONE);
            down.setVisibility(View.GONE);
            add.setVisibility(View.GONE);
            add2.setVisibility(View.GONE);
            /*spinner.setEnabled(false);
            spinner2.setEnabled(false);*/
            ed_date.setOnClickListener(null);

            layout1.setClickable(true);
            layout2.setClickable(true);

            ed_invoice.setFocusable(false);
            ed_date.setFocusable(false);
            ed_heap.setFocusable(false);
            ed_weight.setFocusable(false);
            ed_vehicle.setFocusable(false);
            ed_rate.setFocusable(false);
        
            ed_invoice.setCursorVisible(false);
            ed_date.setCursorVisible(false);
            ed_heap.setCursorVisible(false);
            ed_weight.setCursorVisible(false);
            ed_vehicle.setCursorVisible(false);
            ed_rate.setCursorVisible(false);
        }

        return v;
    }

    private void updateSalebaleform() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<UpdateSeedResponse> call = myInterface.updateSaleseedform(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID) + "",
                id + "",
                ed_invoice.getText().toString() + "",
                byDate + "",
                ed_vehicle.getText().toString() + "",
                ed_weight.getText().toString() + "",
                ed_rate.getText().toString() + "",
                mill_id + "",
                trader_id + "",
                selected_heaps + "");
        call.enqueue(new Callback<UpdateSeedResponse>() {
            @Override
            public void onResponse(Call<UpdateSeedResponse> call, Response<UpdateSeedResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleMillData> parameterListDataArrayList = new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {


                            SeedListFragment seedListFragment = new SeedListFragment();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, seedListFragment)
                                    .commit();

                        }

                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateSeedResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void getSalebale_byid() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetSeedResponse> call = myInterface.getSaleseed_byid(id + "");
        call.enqueue(new Callback<GetSeedResponse>() {
            @Override
            public void onResponse(Call<GetSeedResponse> call, Response<GetSeedResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleMillData> parameterListDataArrayList = new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            Log.d("responseeee", response.toString());

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                ed_invoice.setText("" + response.body().getData().get(i).getInvoiceNo());
                                ed_date.setText("" + response.body().getData().get(i).getSeedDate());
                                ed_vehicle.setText("" + response.body().getData().get(i).getVehicleNo());
                                ed_weight.setText("" + response.body().getData().get(i).getWeight());
                                ed_rate.setText("" + response.body().getData().get(i).getSeedRate());
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
                                Log.d("responseeeeds", compareValue);


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
                                Log.d("responseeeeds", compareValue2 + " " + mStringArray2.length);

                            }


                        }

                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetSeedResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addSellform() {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SaleBaleFormResponse> call = myInterface.addSeedform(
                ed_invoice.getText().toString() + "",
                userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID) + "",
                byDate + "",
                ed_vehicle.getText().toString() + "",
                ed_weight.getText().toString() + "",
                ed_rate.getText().toString() + "",
                mill_id + "",
                trader_id + "",
                selected_heaps + ""
        );
        call.enqueue(new Callback<SaleBaleFormResponse>() {
            @Override
            public void onResponse(Call<SaleBaleFormResponse> call, Response<SaleBaleFormResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            getFragmentManager().popBackStack();

                            Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();

                        }
                    } else {

                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SaleBaleFormResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public boolean isValid() {

        if (ed_invoice.getText().toString().equals("") || ed_invoice.getText().toString().equals("0")) {
            ed_invoice.setError("Please enter invoice");

            ed_invoice.requestFocus();
            return false;
        } else if (ed_date.getText().toString().equals("")) {
            ed_date.setError("Please select date");

            ed_date.requestFocus();
            return false;
        } else if (ed_vehicle.getText().toString().equals("") || ed_vehicle.getText().toString().equals("0")) {
            ed_vehicle.setError("Please enter vehicle number");

            ed_vehicle.requestFocus();
            return false;
        } else if (ed_weight.getText().toString().equals("") || ed_weight.getText().toString().equals("0")) {
            ed_weight.setError("Please enter weight");

            ed_weight.requestFocus();
            return false;
        }/*else if (ed_pr.getText().toString().equals("")) {
            ed_pr.setError("Please enter Pr");

            ed_pr.requestFocus();
            return false;
        }*/ else if (ed_rate.getText().toString().equals("") || ed_rate.getText().toString().equals("0")) {
            ed_rate.setError("Please enter seed rate");

            ed_rate.requestFocus();
            return false;
        } else if ((spinner.getSelectedItem() == null)) {

            Toast.makeText(getActivity(), "Add Oil mill name", Toast.LENGTH_SHORT).show();

            return false;
        } else if ((spinner2.getSelectedItem() == null)) {

            Toast.makeText(getActivity(), "Add Broker name", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }


    private void heapList() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<HeapListResponse> call = myInterface.heapList(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID) + "");
        call.enqueue(new Callback<HeapListResponse>() {
            @Override
            public void onResponse(Call<HeapListResponse> call, Response<HeapListResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleMillData> parameterListDataArrayList = new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            Log.d("responseeee", response.toString());

                            for (int i = 0; i < response.body().getData().size(); i++) {

                                mStringList.add("" + response.body().getData().get(i).getHeap());
                            }


                        }

                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HeapListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getSellseedmills() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetSeedMillsResponse> call = myInterface.getSellseedmills(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID) + "");
        call.enqueue(new Callback<GetSeedMillsResponse>() {
            @Override
            public void onResponse(Call<GetSeedMillsResponse> call, Response<GetSeedMillsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<GetSeedMillsData> parameterListDataArrayList = new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String millid, userid, millname;

                            Log.d("responseeee", response.toString());

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                millid = response.body().getData().get(i).getSeedMillId();
                                millname = response.body().getData().get(i).getMillName();

                                GetSeedMillsData parameterListData = new GetSeedMillsData(millid, millname);
                                parameterListDataArrayList.add(parameterListData);
                                sarrayList.add(millname);

                            }

                            arrayList = parameterListDataArrayList;

                            CustomAdapter2 dataAdapter = new CustomAdapter2(getActivity(), android.R.layout.simple_spinner_item, parameterListDataArrayList, -1) {
                                @Override
                                public boolean isEnabled(int position) {
                                    if (position == 0) {
                                        // Disable the first item from Spinner
                                        // First item will be use for hint
                                        return true;
                                    } else {
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
                            spinner.setSelection(parameterListDataArrayList.size() - 1);

                        }

                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetSeedMillsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getSellseedtrader() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<GetSeedTraderResponse> call = myInterface.getSellseedtrader(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID) + "");
        call.enqueue(new Callback<GetSeedTraderResponse>() {
            @Override
            public void onResponse(Call<GetSeedTraderResponse> call, Response<GetSeedTraderResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<GetSeedTraderData> parameterListDataArrayList = new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String traderid, sell_id, trader_name;

                            Log.d("responseeee", response.toString());

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                traderid = response.body().getData().get(i).getSeedTraderId();
                                trader_name = response.body().getData().get(i).getTraderName();

                                GetSeedTraderData saleBaleTraderData = new GetSeedTraderData(traderid, trader_name);
                                parameterListDataArrayList.add(saleBaleTraderData);
                                sarrayList2.add(trader_name);
                            }

                            arrayList2 = parameterListDataArrayList;

                            CustomAdapter2 dataAdapter = new CustomAdapter2(getActivity(), android.R.layout.simple_spinner_item, parameterListDataArrayList, -1) {
                                @Override
                                public boolean isEnabled(int position) {
                                    if (position == 0) {
                                        // Disable the first item from Spinner
                                        // First item will be use for hint
                                        return true;
                                    } else {
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

                } else {
                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetSeedTraderResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addSellseedmills(String mill) {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AddSeedMillsResponse> call = myInterface.addSellseedmills(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID) + "", mill + "");
        call.enqueue(new Callback<AddSeedMillsResponse>() {
            @Override
            public void onResponse(Call<AddSeedMillsResponse> call, Response<AddSeedMillsResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleMillData> parameterListDataArrayList = new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            getSellseedmills();

                        }

                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddSeedMillsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addSellseedtrader(String trader) {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AddBaleTraderResponse> call = myInterface.addSellseedtrader(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID) + "", trader + "");
        call.enqueue(new Callback<AddBaleTraderResponse>() {
            @Override
            public void onResponse(Call<AddBaleTraderResponse> call, Response<AddBaleTraderResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    ArrayList<SaleBaleMillData> parameterListDataArrayList = new ArrayList<>();

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {

                            getSellseedtrader();

                        }

                    } else {
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddBaleTraderResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
