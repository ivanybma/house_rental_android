package landlord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cmpe277.termproj_rentapp.R;
import landlord.review_info.Review;

/**
 * Created by cheyikung on 4/22/16.
 */
public class ReviewAdapter extends ArrayAdapter<Review> {
    private final Context context;
    private final List<Review> reviews;

    public ReviewAdapter(Context context, List<Review> reviews) {
        super(context, R.layout.listview_review, reviews);
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.listview_review, parent, false);

        TextView tenantName = (TextView) rowView.findViewById(R.id.listView_review_tenant_name);
        TextView reviewDate = (TextView) rowView.findViewById(R.id.listView_review_date);
        TextView rating = (TextView) rowView.findViewById(R.id.listView_review_rating);
        TextView descriptions = (TextView) rowView.findViewById(R.id.listView_review_descriptions);

        if(reviews.get(position).getTenantfbId()!=null) {
            tenantName.setText(reviews.get(position).getTenantfbId());
        }

        if(reviews.get(position).getDate()!=null){
            reviewDate.setText(reviews.get(position).getDate());
        }

        if(reviews.get(position).getRating()!=null){
            rating.setText(reviews.get(position).getRating());
        }

        if(reviews.get(position).getDescription()!=null){
            descriptions.setText(reviews.get(position).getDescription());
        }

        return rowView;
    }

}
