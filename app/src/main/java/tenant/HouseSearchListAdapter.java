package tenant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import cmpe277.termproj_rentapp.R;
import landlord.house_info.HouseInfo;

/**
 * Created by ivanybma on 4/22/16.
 */
public class HouseSearchListAdapter extends ArrayAdapter<HouseInfo>{
    private final Context context;
    private final List<HouseInfo> houseInfos;

    public HouseSearchListAdapter(Context context, List<HouseInfo> houseInfos) {
        super(context, R.layout.listview_houseinfo, houseInfos);
        this.context = context;
        this.houseInfos = houseInfos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listview_houseinfo, parent, false);

        ImageView houseImg = (ImageView) rowView.findViewById(R.id.listView_houseinfo_img);
        TextView houseDescription = (TextView) rowView.findViewById(R.id.listView_houseinfo_descriptions);
        TextView city = (TextView) rowView.findViewById(R.id.listView_houseinfo_city);
        TextView address = (TextView) rowView.findViewById(R.id.listView_houseinfo_address);
        TextView price = (TextView) rowView.findViewById(R.id.listView_houseinfo_price);
        TextView numOfBed = (TextView) rowView.findViewById(R.id.listView_houseinfo_numOfBed);
        TextView numOfBath = (TextView) rowView.findViewById(R.id.listView_houseinfo_numOfBath);
        //below fields are hidden for favorite/review/detail screen usage
        TextView houseid = (TextView) rowView.findViewById(R.id.houseid);
        TextView landlordfbid = (TextView) rowView.findViewById(R.id.landlordfbid);
        TextView postingdate = (TextView) rowView.findViewById(R.id.postingdate);
        TextView sqrtft = (TextView) rowView.findViewById(R.id.sqrtft);
        TextView status = (TextView) rowView.findViewById(R.id.listView_status);
        TextView lat = (TextView) rowView.findViewById(R.id.lat);
        TextView lng = (TextView) rowView.findViewById(R.id.lng);
        TextView ld_email = (TextView) rowView.findViewById(R.id.email);
        TextView ld_phone = (TextView) rowView.findViewById(R.id.phone);

        houseid.setText(houseInfos.get(position).getHouseId());
        landlordfbid.setText(houseInfos.get(position).getLandlordFbId());
        postingdate.setText(houseInfos.get(position).getPostingDate());
        sqrtft.setText(String.valueOf(houseInfos.get(position).getSqrtft()));
        status.setText(houseInfos.get(position).getStatus());


        houseImg.setImageResource(R.drawable.room1);

          //  if (houseInfos.get(position).getDescription() != null) {
                houseDescription.setText(houseInfos.get(position).getDescription());
          //  }
            if (houseInfos.get(position).getAddress() != null) {
                city.setText(houseInfos.get(position).getAddress().getCity());
                address.setText(houseInfos.get(position).getAddress().getAddress());
                lat.setText(String.valueOf(houseInfos.get(position).getAddress().getLat()));
                lng.setText(String.valueOf(houseInfos.get(position).getAddress().getLng()));
            }
            ld_email.setText(houseInfos.get(position).getLd_email());
            ld_phone.setText(houseInfos.get(position).getLd_phone());
            price.setText(Double.toString(houseInfos.get(position).getPrice()));
            numOfBed.setText(Integer.toString(houseInfos.get(position).getNumOfBedroom()));
            numOfBath.setText(Integer.toString(houseInfos.get(position).getNumOfBathroom()));
        return rowView;
    }
}
