package landlord;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cmpe277.termproj_rentapp.HouseDetailFragment;
import cmpe277.termproj_rentapp.OnLayoutSelectListener;
import cmpe277.termproj_rentapp.R;
import landlord.search_info.SearchInfo;

/**
 * Created by yunlongxu on 4/19/16.
 */
public class OptionFragment extends Fragment {

    private SearchInfo selectedOption;
//    private OnLayoutSelectListener mCallBack;

    public OptionFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_options, container, false);
        List<SearchInfo> searchInfo = getListSearchInfo();
        final ListView lv = (ListView) rootView.findViewById(R.id.lv_search);
        lv.setAdapter(new SearchOptionAdapter(getActivity(), searchInfo));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOption = (SearchInfo) lv.getItemAtPosition(position);
                if (selectedOption.getInformation().equals("New Posting")) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    LandlordInfoFragment fragment = new LandlordInfoFragment();
                    transaction.replace(R.id.search_detail, fragment);
                    transaction.addToBackStack("landlord info");
                    transaction.commit();
                } else if (selectedOption.getInformation().equals("List of Posting")) {
//                    mCallBack.onLayoutSelected();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    HouseInfoListFragment fragment = new HouseInfoListFragment();
                    transaction.replace(R.id.search_detail, fragment);
                    transaction.addToBackStack("List Posting");
                    transaction.commit();
                }
            }
        });
        return rootView;
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        try {
//            mCallBack = (OnLayoutSelectListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString() + "must implement OnlayoutSelectListener");
//        }
//    }

    private List<SearchInfo> getListSearchInfo() {
        List<SearchInfo> searchInfos = new ArrayList<>();
        SearchInfo newPost = new SearchInfo("New Posting", R.drawable.ic_create_black_24dp);
        SearchInfo historyPost = new SearchInfo("List of Posting", R.drawable.ic_save_black_24dp);
        searchInfos.add(newPost);
        searchInfos.add(historyPost);
        return searchInfos;
    }
}
