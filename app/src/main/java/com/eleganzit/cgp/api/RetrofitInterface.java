package com.eleganzit.cgp.api;

import com.eleganzit.cgp.models.AddBaleMillResponse;
import com.eleganzit.cgp.models.AddBaleTraderResponse;
import com.eleganzit.cgp.models.AddSeedMillsResponse;
import com.eleganzit.cgp.models.AvgPurchaseResponse;
import com.eleganzit.cgp.models.AvgSaleBalesResponse;
import com.eleganzit.cgp.models.AvgSaleSeedResponse;
import com.eleganzit.cgp.models.GetBaleResponse;
import com.eleganzit.cgp.models.GetPurchaseResponse;
import com.eleganzit.cgp.models.GetSaleBaleResponse;
import com.eleganzit.cgp.models.GetSaleSeedResponse;
import com.eleganzit.cgp.models.GetSeedMillsResponse;
import com.eleganzit.cgp.models.GetSeedResponse;
import com.eleganzit.cgp.models.GetSeedTraderResponse;
import com.eleganzit.cgp.models.HeapListResponse;
import com.eleganzit.cgp.models.LastHeapResponse;
import com.eleganzit.cgp.models.LoginResponse;
import com.eleganzit.cgp.models.PurchaseFormResponse;
import com.eleganzit.cgp.models.PurchaseListResponse;
import com.eleganzit.cgp.models.RegisterResponse;
import com.eleganzit.cgp.models.SaleBaleFormResponse;
import com.eleganzit.cgp.models.SaleBaleMillResponse;
import com.eleganzit.cgp.models.SaleBaleTraderResponse;
import com.eleganzit.cgp.models.StateListResponse;
import com.eleganzit.cgp.models.StockResponse;
import com.eleganzit.cgp.models.UpdateBaleResponse;
import com.eleganzit.cgp.models.UpdatePurchaseResponse;
import com.eleganzit.cgp.models.UpdateSeedResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by eleganz on 30/4/19.
 */

public interface RetrofitInterface {

    @FormUrlEncoded()
    @POST("/CBLT-API/cgpaddUser")
    Call<RegisterResponse> registerUser(
            @Field("ginning_name") String ginning_name,
            @Field("state") String state,
            @Field("area") String area,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/addPurchaseform")
    Call<PurchaseFormResponse> addPurchaseform(
            @Field("user_id") String user_id,
            @Field("add_date") String add_date,
            @Field("heap") String heap,
            @Field("vehicle_no") String vehicle_no,
            @Field("final_weight") String final_weight,
            @Field("rate_of_kapas") String rate_of_kapas,
            @Field("per_of_cotton") String per_of_cotton,
            @Field("shortage") String shortage,
            @Field("seed_rate") String seed_rate
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/addSellform")
    Call<SaleBaleFormResponse> addSellform(
            @Field("invoice_no") String invoice_no,
            @Field("user_id") String user_id,
            @Field("pr_no") String pr_no,
            @Field("sell_date") String sell_date,
            @Field("heap") String heap,
            @Field("bale_rate") String bale_rate,
            @Field("final_weight") String final_weight,
            @Field("no_of_bales") String no_of_bales,
            @Field("mill_id") String mill_id,
            @Field("trader_id") String trader_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/addSeedform")
    Call<SaleBaleFormResponse> addSeedform(
            @Field("invoice_no") String invoice_no,
            @Field("user_id") String user_id,
            @Field("seed_date") String seed_date,
            @Field("vehicle_no") String vehicle_no,
            @Field("weight") String weight,
            @Field("seed_rate") String seed_rate,
            @Field("seed_mill_id") String seed_mill_id,
            @Field("seed_trader_id") String seed_trader_id,
            @Field("heap") String heap
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/getPurchaseform")
    Call<PurchaseListResponse> getPurchaseform(
            @Field("user_id") String user_id,
            @Field("from_limit") String from_limit
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/getPurchaseform")
    Call<PurchaseListResponse> getFPurchaseform(
            @Field("user_id") String user_id,
            @Field("from_limit") String from_limit,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/getSellbaleform")
    Call<GetSaleBaleResponse> getSellbaleform(
            @Field("user_id") String user_id,
            @Field("from_limit") String from_limit
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/getSellbaleform")
    Call<GetSaleBaleResponse> getFSellbaleform(
            @Field("user_id") String user_id,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("from_limit") String from_limit
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/getSellbaleform")
    Call<GetSaleBaleResponse> getFmillsSellbaleform(
            @Field("user_id") String user_id,
            @Field("mill_id") String mill_id,
            @Field("trader_id") String trader_id,
            @Field("from_limit") String from_limit
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/getSeedform")
    Call<GetSaleSeedResponse> getSeedform(
            @Field("user_id") String user_id,
            @Field("from_limit") String from_limit
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/getSeedform")
    Call<GetSaleSeedResponse> getFSeedform(
            @Field("user_id") String user_id,
            @Field("from_limit") String from_limit,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/userLogin")
    Call<LoginResponse> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("fpass.php")
    Call<LoginResponse> fpass(
            @Query("email") String email);

    @FormUrlEncoded()
    @POST("/CBLT-API/userUpdate")
    Call<LoginResponse> userUpdate(
            @Field("user_id") String user_id,
            @Field("ginning_name") String ginning_name,
            @Field("state") String state,
            @Field("area") String area,
            @Field("expance") String expance,
            @Field("email") String email
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/userUpdate")
    Call<LoginResponse> userUpdate(
            @Field("user_id") String user_id,

            @Field("password") String password

    );

    @FormUrlEncoded()
    @POST("/CBLT-API/userUpdate")
    Call<LoginResponse> userUpdateexpance(
            @Field("user_id") String user_id,

            @Field("expance") String expance

    );

    @FormUrlEncoded()
    @POST("/CBLT-API/cgpgetUser")
    Call<LoginResponse> cgpgetUser(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/lastHeap")
    Call<LastHeapResponse> lastHeap(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/deleteSalebale")
    Call<LoginResponse> deleteSalebale(
            @Field("sell_id") String sell_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/avgkapasrate")
    Call<AvgPurchaseResponse> avgkapasrate(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/avgkapasrate")
    Call<AvgPurchaseResponse> avgFkapasrate(
            @Field("user_id") String user_id,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/avgSalesbalerate")
    Call<AvgSaleBalesResponse> avgSalesbalerate(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/avgSalesbalerate")
    Call<AvgSaleBalesResponse> avgFSalesbalerate(
            @Field("user_id") String user_id,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/avgSalesbalerate")
    Call<AvgSaleBalesResponse> avgFmillSalesbalerate(
            @Field("user_id") String user_id,
            @Field("mill_id") String mill_id,
            @Field("trader_id") String trader_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/avgSalesseedrate")
    Call<AvgSaleSeedResponse> avgSalesseedrate(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/avgSalesseedrate")
    Call<AvgSaleSeedResponse> avgFSalesseedrate(
            @Field("user_id") String user_id,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/deleteSaleseed")
    Call<LoginResponse> deleteSaleseed(
            @Field("seed_id") String seed_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/deletePurchase")
    Call<LoginResponse> deletePurchase(
            @Field("purchase_id") String purchase_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/addSellbalemills")
    Call<AddBaleMillResponse> addSellbalemills(
            @Field("user_id") String user_id,
            @Field("mill_name") String mill_name
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/addSellbaletrader")
    Call<AddBaleTraderResponse> addSellbaletrader(
            @Field("user_id") String user_id,
            @Field("trader_name") String trader_name
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/getSellbalemills")
    Call<SaleBaleMillResponse> getSellbalemills(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/getSellbaletrader")
    Call<SaleBaleTraderResponse> getSellbaletrader(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/heapList")
    Call<HeapListResponse> heapList(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/addSellseedmills")
    Call<AddSeedMillsResponse> addSellseedmills(
            @Field("user_id") String user_id,
            @Field("mill_name") String mill_name
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/addSellseedtrader")
    Call<AddBaleTraderResponse> addSellseedtrader(
            @Field("user_id") String user_id,
            @Field("trader_name") String trader_name
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/getSellseedmills")
    Call<GetSeedMillsResponse> getSellseedmills(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/getSellseedtrader")
    Call<GetSeedTraderResponse> getSellseedtrader(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded()
    @POST("/CBLT-API/stockValues")
    Call<StockResponse> stockValues(
            @Field("user_id") String user_id
    );



    @FormUrlEncoded()
    @POST("/CBLT-API/cgpstateList")
    Call<StateListResponse> stateList(
            @Field("dummy") String dummy

    );


    @FormUrlEncoded()
    @POST("/CBLT-API/getPurchase_byid")
    Call<GetPurchaseResponse> getPurchase_byid(
            @Field("purchase_id") String purchase_id

    );

    @FormUrlEncoded()
    @POST("/CBLT-API/updatePurchaseform")
    Call<UpdatePurchaseResponse> updatePurchaseform(
            @Field("user_id") String user_id,
            @Field("purchase_id") String purchase_id,
            @Field("add_date") String add_date,
            @Field("heap") String heap,
            @Field("vehicle_no") String vehicle_no,
            @Field("final_weight") String final_weight,
            @Field("rate_of_kapas") String rate_of_kapas,
            @Field("per_of_cotton") String per_of_cotton,
            @Field("shortage") String shortage,
            @Field("seed_rate") String seed_rate

    );

    @FormUrlEncoded()
    @POST("/CBLT-API/getSalebale_byid")
    Call<GetBaleResponse> getSalebale_byid(
            @Field("sell_id") String sell_id

    );

    @FormUrlEncoded()
    @POST("/CBLT-API/updateSalebaleform")
    Call<UpdateBaleResponse> updateSalebaleform(
            @Field("user_id") String user_id,
            @Field("sell_id") String sell_id,
            @Field("invoice_no") String invoice_no,
            @Field("pr_no") String pr_no,
            @Field("sell_date") String sell_date,
            @Field("heap") String heap,
            @Field("bale_rate") String bale_rate,
            @Field("final_weight") String final_weight,
            @Field("no_of_bales") String no_of_bales,
            @Field("mill_id") String mill_id,
            @Field("trader_id") String trader_id

    );

    @FormUrlEncoded()
    @POST("/CBLT-API/getSaleseed_byid")
    Call<GetSeedResponse> getSaleseed_byid(
            @Field("seed_id") String seed_id

    );

    @FormUrlEncoded()
    @POST("/CBLT-API/updateSaleseedform")
    Call<UpdateSeedResponse> updateSaleseedform(
            @Field("user_id") String user_id,
            @Field("seed_id") String seed_id,
            @Field("invoice_no") String invoice_no,
            @Field("seed_date") String seed_date,
            @Field("vehicle_no") String vehicle_no,
            @Field("weight") String weight,
            @Field("seed_rate") String seed_rate,
            @Field("seed_mill_id") String seed_mill_id,
            @Field("seed_trader_id") String seed_trader_id,
            @Field("heap") String heap

    );


}
