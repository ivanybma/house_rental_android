package tenant;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cmpe277.termproj_rentapp.LoginData;
import cmpe277.termproj_rentapp.R;
import common_tools.RestApi;
import common_tools.RestHttpResponse;
import common_tools.RestMD;
import landlord.house_info.Address;
import landlord.house_info.HouseInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tenant.House_search.HouseSchCri;

/**
 * Created by ivanybma on 4/22/16.
 */
public class TenantSearchFragment extends Fragment {

    private Button searchBt, applyBt;
    private EditText cityname = null;
    private Spinner propertytype = null;
    private EditText priceH = null;
    private EditText priceL = null;
    private EditText state = null;
    HouseSearchListFragment fragment = null;
    private double latitude;
    private double longitude;
//    Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl(RestMD.REST_BASE_ADD)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
//    RestApi service = retrofit.create(RestApi.class);
    RestApi service = RestMD.restService();

    public TenantSearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("TenantSearchFragment onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_tenant_search, container, false);
        searchBt = (Button) rootView.findViewById(R.id.tenantsearct);
        cityname = (EditText) rootView.findViewById(R.id.city);
        propertytype = (Spinner) rootView.findViewById(R.id.type_spinner);
        priceH = (EditText) rootView.findViewById(R.id.highprice);
        priceL = (EditText) rootView.findViewById(R.id.lowprice);
        state = (EditText) rootView.findViewById(R.id.state);

//        applyBt = (Button) rootView.findViewById(R.id.settingapply);
        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new HouseSearchListFragment();
                final List<HouseInfo> houseInfos = new ArrayList<HouseInfo>();
                //Call<List<HouseInfo>> call = service.getHouseInfoList();
                HouseSchCri houseSchCri=new HouseSchCri();
                houseSchCri.setCity(cityname.getText().toString());
                houseSchCri.setPropertyType(propertytype.getSelectedItem().toString());
                double priceh = priceH.getText().toString().equals("")?0.0:Double.valueOf(priceH.getText().toString());
                double pricel = priceL.getText().toString().equals("")?0.0:Double.valueOf(priceL.getText().toString());
                houseSchCri.setPriceH(priceh);
                houseSchCri.setPriceL(pricel);
                houseSchCri.setState((state.getText().toString()));
                houseSchCri.setLng(longitude);
                houseSchCri.setLat(latitude);
//                houseSchCri.setAddress("peninsula");
                Call<List<HouseInfo>> call = service.getSearchHouseInfoList(houseSchCri);
                call.enqueue(new Callback<List<HouseInfo>>() {
                    @Override
                    public void onResponse(Call<List<HouseInfo>> call, Response<List<HouseInfo>> response) {
                        //List<HouseInfo> houseInfos = new ArrayList<HouseInfo>();
                        for(int i=0; i<response.body().size(); i++)
                        houseInfos.add(response.body().get(i));

                        String backStateName = fragment.getClass().getName();
                        fragment.setHouseInfos(houseInfos);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction  = fragmentManager.beginTransaction();
                        transaction.replace(R.id.content_frame, fragment, "tenant_house_search");
                        transaction.addToBackStack(backStateName);
                        transaction.commit();
                    }

                    @Override
                    public void onFailure(Call<List<HouseInfo>> call, Throwable t) {
                        System.out.println("Rest request failed.......");
                        fragment.setHouseInfos(houseInfos);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "tenant_house_search").commit();

                    }

                });

            }
        });

//        applyBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                LocationMapFragment fragment =(LocationMapFragment) fragmentManager.findFragmentByTag("location_fragment");
//                System.out.println(getActivity().getLocalClassName());
//                System.out.println(getActivity().getComponentName());
//                System.out.println(getContext().getClass().getName());
//            }
//        });
        return rootView;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        System.out.println("TenantSearchFragment onDestroyView");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("TenantSearchFragment onResume");
        //mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("TenantSearchFragment onDestroy");
        //  mapView.onDestroy();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(this.getContext());
        System.out.println("TenantSearchFragment onAttach");
    }
    @Override
    public void onDetach(){
        super.onDetach();
        System.out.println("TenantSearchFragment onDetach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("TenantSearchFragment onCreate");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("TenantSearchFragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("TenantSearchFragment onStop");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("TenantSearchFragment onStart");
    }


}
