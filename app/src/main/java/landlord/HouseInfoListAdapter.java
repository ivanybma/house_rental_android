package landlord;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cmpe277.termproj_rentapp.R;
import landlord.house_info.HouseInfo;
import landlord.review_info.Review;

/**
 * Created by cheyikung on 4/22/16.
 */
public class HouseInfoListAdapter extends ArrayAdapter<HouseInfo>{
    private final Context context;
    private List<HouseInfo> houseInfos;

    public HouseInfoListAdapter(Context context, List<HouseInfo> houseInfos) {
        super(context, R.layout.listview_houseinfo, houseInfos);
        this.context = context;
        this.houseInfos = houseInfos;
    }

    public void updateHouseInfos(List<HouseInfo> houseInfos) {
        this.houseInfos = houseInfos;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listview_houseinfo, parent, false);

        ImageView houseImg = (ImageView) rowView.findViewById(R.id.listView_houseinfo_img);
        TextView houseDescription = (TextView) rowView.findViewById(R.id.listView_houseinfo_descriptions);
        houseDescription.setTextColor(Color.BLACK);
        TextView street = (TextView) rowView.findViewById(R.id.listView_houseinfo_address);
        street.setTextColor(Color.BLACK);
        TextView city = (TextView) rowView.findViewById(R.id.listView_houseinfo_city);
        city.setTextColor(Color.BLACK);
        TextView price = (TextView) rowView.findViewById(R.id.listView_houseinfo_price);
        price.setTypeface(Typeface.DEFAULT_BOLD);
        price.setTextColor(Color.BLACK);
        TextView numOfBed = (TextView) rowView.findViewById(R.id.listView_houseinfo_numOfBed);
        numOfBed.setTextColor(Color.BLACK);
        TextView numOfBath = (TextView) rowView.findViewById(R.id.listView_houseinfo_numOfBath);
        numOfBath.setTextColor(Color.BLACK);
        TextView status = (TextView) rowView.findViewById(R.id.listView_status);
        status.setTextColor(Color.BLACK);
        status.setTypeface(Typeface.DEFAULT_BOLD);

        if(houseInfos.get(position).getDescription()!=null){
            houseDescription.setText(houseInfos.get(position).getDescription());
        }
        if(houseInfos.get(position).getAddress().getCity()!=null){
            city.setText(houseInfos.get(position).getAddress().getCity());
        }

        street.setText(houseInfos.get(position).getAddress().getAddress());
        price.setText("Price: $ " + Double.toString(houseInfos.get(position).getPrice()));
        numOfBed.setText(Integer.toString(houseInfos.get(position).getNumOfBedroom()));
        numOfBath.setText(Integer.toString(houseInfos.get(position).getNumOfBathroom()));
        status.setText("Status: " + houseInfos.get(position).getStatus());

        return rowView;
    }
}
