package common_tools;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ivanybma on 4/25/16.
 */
public class RestMD {

//    static public final String REST_BASE_ADD = "http://10.0.2.2:8080/";
    static public final String REST_BASE_ADD = "http://52.36.188.195:8080/house_rental/";
    static private RestApi restsrv = null;

    public static RestApi restService() {
        if (restsrv == null) {
            restsrv = new Retrofit.Builder()
                    .baseUrl(RestMD.REST_BASE_ADD)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RestApi.class);
        }
        return restsrv;
    }
}
