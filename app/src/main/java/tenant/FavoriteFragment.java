package tenant;


import android.app.ListFragment;
import android.os.Bundle;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cmpe277.termproj_rentapp.HouseDetailFragment;
import cmpe277.termproj_rentapp.R;
import common_tools.RestApi;
import common_tools.RestMD;
import common_tools.UserPref;
import landlord.house_info.HouseInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tenant.favorite_info.Favorite;
import tenant.tenant_info.Tenant;

/**
 * Created by Alan on 4/20/16.
 */
public class FavoriteFragment extends Fragment{

    private List<HouseInfo> houseInfoList;

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            houseInfoList = (List<HouseInfo>) bundle.getSerializable("favoriteList");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.fragment_favorite_list);
        listView.setItemsCanFocus(false);
        listView.setItemsCanFocus(false);

        listView.setAdapter(new HouseSearchListAdapter(getContext(), houseInfoList));

        listView.setItemsCanFocus(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FavoriteDetailFragment fragment = new FavoriteDetailFragment();
                fragment.setHouseId(houseInfoList.get(position).getHouseId());
                fragment.setLandlordFbId(houseInfoList.get(position).getLandlordFbId());
                fragment.setDescription(houseInfoList.get(position).getDescription());
                fragment.setEmail(houseInfoList.get(position).getLd_email());
                fragment.setPhone(houseInfoList.get(position).getLd_phone());
                fragment.setLat(houseInfoList.get(position).getAddress().getLat());
                fragment.setLng(houseInfoList.get(position).getAddress().getLng());
                String backStateName = fragment.getClass().getName();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack(backStateName);
                transaction.commit();

            }
        });

        return rootView;
    }
}

