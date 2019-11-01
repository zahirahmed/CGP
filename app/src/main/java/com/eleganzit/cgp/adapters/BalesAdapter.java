package com.eleganzit.cgp.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.cgp.R;
import com.eleganzit.cgp.api.RetrofitAPI;
import com.eleganzit.cgp.api.RetrofitInterface;
import com.eleganzit.cgp.fragments.BalesListFragment;
import com.eleganzit.cgp.fragments.SalesBalesFormFragment;
import com.eleganzit.cgp.models.BalesData;
import com.eleganzit.cgp.models.GetSaleBaleData;
import com.eleganzit.cgp.models.LoginResponse;
import com.eleganzit.cgp.models.PurchaseData;
import com.eleganzit.cgp.models.PurchaseListData;
import com.eleganzit.cgp.utils.UserLoggedInSession;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalesAdapter extends RecyclerView.Adapter<BalesAdapter.MyViewHolder> {

    ArrayList<GetSaleBaleData> arrayList;
    Context context;
    int VIEW_TYPE_ITEM=0,VIEW_TYPE_LOADING=1;
    ProgressDialog progressDialog;

    public BalesAdapter(ArrayList<GetSaleBaleData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public int getItemViewType(int position) {
        if (arrayList.get(position) != null)
            return VIEW_TYPE_ITEM;
        else
            return VIEW_TYPE_LOADING;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v;
        if (i == VIEW_TYPE_ITEM) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bales_row_layout,viewGroup,false);
            return new DViewHolder(v);
        } else {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progressbar_layout,viewGroup,false);
            return new PViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


        if (myViewHolder instanceof DViewHolder) {

            final GetSaleBaleData balesData=arrayList.get(i);

            String yourFormattedString1,yourFormattedString2;

            DecimalFormat formatter = new DecimalFormat("##,##,##,###");
            yourFormattedString1 = formatter.format(Double.valueOf(balesData.getBaleRate()));
            yourFormattedString2 = formatter.format(Double.valueOf(balesData.getApproxBaleAmmount()));

            ((DViewHolder)myViewHolder).txt_date.setText("DATE :"+balesData.getSellDate());
            ((DViewHolder)myViewHolder).txt_sr.setText("Invoice No. #"+balesData.getInvoiceNo());
            ((DViewHolder)myViewHolder).txt_pr_no.setText(""+balesData.getPrNo());
            ((DViewHolder)myViewHolder).txt_heap.setText("Heap-"+balesData.getHeap());
            ((DViewHolder)myViewHolder).txt_crate.setText(""+yourFormattedString1+" Rs");
            ((DViewHolder)myViewHolder).txt_approx.setText(""+yourFormattedString2+" Rs");

            ((DViewHolder)myViewHolder).txt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(context)
                            .setMessage("Are you sure you want to delete?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                    deleteSalebale(balesData.getSellId(),i);
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .show();

                }
            });

            ((DViewHolder)myViewHolder).txt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle=new Bundle();
                    bundle.putString("id",balesData.getSellId()+"");
                    bundle.putString("from","edit");

                    SalesBalesFormFragment salesBalesFormFragment=new SalesBalesFormFragment();
                    salesBalesFormFragment.setArguments(bundle);
                    ((FragmentActivity)context).getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack("HomeActivity")
                            .replace(R.id.container,salesBalesFormFragment)
                            .commit();
                }
            });

            ((DViewHolder)myViewHolder).main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle=new Bundle();
                    bundle.putString("id",balesData.getSellId()+"");
                    bundle.putString("from","view");

                    SalesBalesFormFragment salesBalesFormFragment=new SalesBalesFormFragment();
                    salesBalesFormFragment.setArguments(bundle);
                    ((FragmentActivity)context).getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack("HomeActivity")
                            .replace(R.id.container,salesBalesFormFragment)
                            .commit();
                }
            });

        }
    }

    public String convertedDate(String date){

        SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate= null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf= new SimpleDateFormat("dd-MM-yyyy");
        date = spf.format(newDate);
        System.out.println(date);

        return date;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

        }
    }

    public class DViewHolder extends MyViewHolder {


        TextView txt_date,txt_sr,txt_pr_no,txt_heap,txt_crate,txt_approx,txt_delete,txt_edit;
        CardView main;


        public DViewHolder(@NonNull View itemView) {
            super(itemView);
            main=itemView.findViewById(R.id.main);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_sr=itemView.findViewById(R.id.txt_sr);
            txt_pr_no=itemView.findViewById(R.id.txt_pr_no);
            txt_heap=itemView.findViewById(R.id.txt_heap);
            txt_crate=itemView.findViewById(R.id.txt_crate);
            txt_approx=itemView.findViewById(R.id.txt_approx);
            txt_delete=itemView.findViewById(R.id.txt_delete);
            txt_edit=itemView.findViewById(R.id.txt_edit);

        }
    }

    public class PViewHolder extends MyViewHolder {

        public PViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }


    public void addNullData() {
        arrayList.add(null);
        notifyItemInserted(arrayList.size() - 1);
    }

    public void removeNull() {
        arrayList.remove(arrayList.size() - 1);
        notifyItemRemoved(arrayList.size());
    }

    public void addData(ArrayList<GetSaleBaleData> integersList) {
        arrayList.addAll(integersList);
        notifyDataSetChanged();
    }



    private void deleteSalebale(String id, final int pos) {

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<LoginResponse> call = myInterface.deleteSalebale(
                id+""
        );
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {


                            removeItem(pos);
                        }
                    } else {

                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    Toast.makeText(context, "Server or Internet Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void removeItem(int position) {
        arrayList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);

        BalesListFragment balesListFragment=new BalesListFragment();
        ((FragmentActivity)context).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,balesListFragment)
                .commit();
    }



}
