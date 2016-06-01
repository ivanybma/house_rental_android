package tenant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cmpe277.termproj_rentapp.MainActivity;
import cmpe277.termproj_rentapp.R;
import common_tools.RestApi;
import common_tools.RestHttpResponse;
import common_tools.RestMD;
import landlord.HouseInfoListAdapter;
import landlord.house_info.HouseInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ivanybma on 4/22/16.
 */
public class HouseSearchListFragment extends Fragment{
    private List<HouseInfo> houseInfos;
    private HouseInfo houseInfo;
    private FavoriteDetailFragment fragment;
    RestApi service = RestMD.restService();

    public HouseSearchListFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_house_search_list, container, false);
        final ListView listView = (ListView) rootView.findViewById(R.id.fragment_house_search_list);
        listView.setItemsCanFocus(false);
        listView.setItemsCanFocus(false);
        listView.setAdapter(new HouseSearchListAdapter(getContext(), houseInfos));
        TextView tx = (TextView) rootView.findViewById(R.id.result_count);
        tx.setText(houseInfos.size()+" searching results were found");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //   Toast.makeText(getActivity(),((TextView) view.findViewById(R.id.houseid)).getText().toString(), Toast.LENGTH_SHORT).show();
                houseInfo = new HouseInfo();
                houseInfo.setHouseId(((TextView) view.findViewById(R.id.houseid)).getText().toString());
                houseInfo.setLandlordFbId(((TextView) view.findViewById(R.id.landlordfbid)).getText().toString());
                fragment = new FavoriteDetailFragment();
                fragment.setHouseId(((TextView) view.findViewById(R.id.houseid)).getText().toString());
                fragment.setLandlordFbId(((TextView) view.findViewById(R.id.landlordfbid)).getText().toString());
                fragment.setDescription(((TextView) view.findViewById(R.id.listView_houseinfo_descriptions)).getText().toString());
                fragment.setEmail(((TextView) view.findViewById(R.id.email)).getText().toString());
                fragment.setPhone(((TextView) view.findViewById(R.id.phone)).getText().toString());
                fragment.setLat(Double.valueOf(((TextView) view.findViewById(R.id.lat)).getText().toString()));
                fragment.setLng(Double.valueOf(((TextView) view.findViewById(R.id.lng)).getText().toString()));
                String backStateName = fragment.getClass().getName();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction  = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack(backStateName);
                transaction.commit();
                incrementDBViewCount();
            }
        });
        System.out.println("###***"+getFragmentManager().getBackStackEntryCount());
        return rootView;
    }

    private void incrementDBViewCount(){

        Call<RestHttpResponse> call = service.incrementViewCount(houseInfo);
        call.enqueue(new Callback<RestHttpResponse>() {
            @Override
            public void onResponse(Call<RestHttpResponse> call, Response<RestHttpResponse> response) {

            }

            @Override
            public void onFailure(Call<RestHttpResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("HouseSearchListFragment onResume");
        //mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("HouseSearchListFragment onDestroy");
        //  mapView.onDestroy();
    }


    public void setHouseInfos(List<HouseInfo> houseInfos) {
        this.houseInfos = houseInfos;
    }
}
