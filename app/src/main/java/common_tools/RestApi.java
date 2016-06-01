package common_tools;

import java.util.List;
import java.util.Objects;

import cmpe277.termproj_rentapp.LoginData;
import landlord.house_info.HouseInfo;
import landlord.landlord_info.Landlord;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tenant.favorite_info.Favorite;
import tenant.House_search.HouseSchCri;
import tenant.tenant_info.Tenant;

/**
 * Created by ivanybma on 4/24/16.
 */
public interface RestApi {
    @GET("house_list")
    Call<List<HouseInfo>> getHouseInfoList();

    @POST("landlordLogin")
    Call<Landlord> landlordLogin(
            @Body Landlord landlord
    );

    @POST("landlordUpdate")
    Call<Landlord> landlordUpdate(
            @Body Landlord landlord
    );

    @POST("incrementViewCount")
    Call<RestHttpResponse> incrementViewCount(
            @Body HouseInfo houseInfo
    );

    @POST("tenantLogin")
    Call<Tenant> tenantLogin(
            @Body Tenant tenant
    );

    @POST("tenantUpdate")
    Call<Tenant> tenantUpdate(
            @Body Tenant tenant
    );

    @POST("search_house_list")
    Call<List<HouseInfo>> getSearchHouseInfoList(@Body HouseSchCri criteria);

    @POST("getFavoriteList")
    Call<List<HouseInfo>> getFavoriteList(@Body List<Favorite> favoriteList);

}
