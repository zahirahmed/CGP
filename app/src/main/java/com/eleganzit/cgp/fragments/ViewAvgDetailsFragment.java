package com.eleganzit.cgp.fragments;


import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleganzit.cgp.HomeActivity;
import com.eleganzit.cgp.R;
import com.eleganzit.cgp.models.AvgPurchaseData;

import java.text.DecimalFormat;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAvgDetailsFragment extends Fragment {

    TextView txt_date,txt_avg_krate,avg_srate,txt_avg_per_cott,txt_avg_per_seed,txt_pur_krate,txt_seed_weight,txt_shortage,txt_approx_brate,txt_approx_bales,avg_label;
    String avg_title;
    AvgPurchaseData avgPurchaseData;

    public ViewAvgDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_view_avg_details, container, false);

        HomeActivity.txt_title.setText("View Details");
        HomeActivity.share.setVisibility(View.GONE);
        HomeActivity.filter.setVisibility(View.GONE);

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

        if(getArguments()!=null){
            avgPurchaseData=(AvgPurchaseData)getArguments().getSerializable("avgPurchaseData");
            avg_title=getArguments().getString("avg_title");


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


        }

        return v;
    }

}
