package landlord;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cmpe277.termproj_rentapp.HouseDetailFragment;
import cmpe277.termproj_rentapp.OnLayoutSelectListener;
import cmpe277.termproj_rentapp.R;
import common_tools.UserPref;
import landlord.house_info.Address;
import landlord.house_info.HouseInfo;
import landlord.landlord_info.Landlord;

/**
 * Created by cheyikung on 4/22/16.
 */
public class HouseInfoListFragment extends Fragment{

    private ListView listView;

    private List<HouseInfo> houseInfos;
    private OnLayoutSelectListener mCallBack;

    private Landlord landlord;


    public HouseInfoListFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_house_info_list, container, false);
        landlord = UserPref.getLanlordUser(getContext());
        listView = (ListView) rootView.findViewById(R.id.fragment_house_info_list);
        listView.setItemsCanFocus(false);
        MyBus.getInstance().register(this);
        return rootView;
    }

    private HouseInfoListAdapter adapter;
    @Override
    public void onResume() {
        super.onResume();
        if (landlord.getHouseInfoList() == null || landlord.getHouseInfoList().size() == 0) {
            Context context = getActivity().getApplicationContext();
            CharSequence text = "No Post Available, Make a new Post!";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            adapter = new HouseInfoListAdapter(getContext(), landlord.getHouseInfoList());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mCallBack.onLayoutSelected();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    HouseDetailFragment fragment = new HouseDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("islandlord", true);
                    bundle.putSerializable("houseinfo", landlord.getHouseInfoList().get(position));
                    bundle.putSerializable("position", position);
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.detail_layout, fragment);
                    transaction.addToBackStack("House Detail");
                    transaction.commit();
                }
            });
        }
    }

    @Subscribe
    public void onHouseStatusChange(HouseStatusEvent event) {
        System.out.println("status: " + event.getStatus());
        if (getContext() == null) {
            return;
        }
        landlord = UserPref.getLanlordUser(getContext());
        adapter.updateHouseInfos(landlord.getHouseInfoList());

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallBack = (OnLayoutSelectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnLayoutSelectListener");
        }
    }
}
