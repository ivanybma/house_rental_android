package landlord;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cmpe277.termproj_rentapp.R;

import landlord.review_info.Review;

/**
 * Created by cheyikung on 4/22/16.
 */
public class ReviewFragment extends Fragment {

    private List<Review> reviews;

    public ReviewFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Testing
        // **************************
        Review review = new Review("","","", "Apr. 21, 2016", "5", "Perfection Condition");
        review.setLandlordfbId("Jack");
        review.setHouseId("jHouse");
        reviews = new ArrayList<>();
        reviews.add(review);

        // **************************
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.fragment_review_review_list);
        listView.setItemsCanFocus(false);
        listView.setItemsCanFocus(false);
        listView.setAdapter(new ReviewAdapter(getContext(), reviews));
        return rootView;
    }
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
