package tenant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.List;

import cmpe277.termproj_rentapp.R;
import common_tools.RestApi;
import common_tools.RestHttpResponse;
import common_tools.RestMD;
import common_tools.UserPref;
import landlord.ReviewFragment;
import landlord.house_info.HouseInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tenant.favorite_info.Favorite;
import tenant.tenant_info.Tenant;

/**
 * Created by Alan on 4/23/16.
 */
public class FavoriteDetailFragment extends Fragment {
    //for testing purpose, will integrate in the house detail later

//    private List<Favorite> favorites = new FavoriteData().getFavorites();
    private List<Favorite> favoriteList;
    private ImageView commentIcon;
    private TextView comment;
    private ImageView favoriteIcon;
    private TextView favoriteStatus;
    private MapView mapView;
    private Tenant tenant;
    private HouseInfo houseInfo;
    private boolean isFavorite;
    RestApi service = RestMD.restService();


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String houseId;
    private String landlordFbId;
    private String phone;
    private String email;
    private String description;
    private double lng;
    private double lat;


    public String getLandlordFbId() {
        return landlordFbId;
    }

    public void setLandlordFbId(String landlordFbId) {
        this.landlordFbId = landlordFbId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public FavoriteDetailFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_tenant_house_detail, container, false);

        tenant = UserPref.getTenantUser(rootView.getContext());
        favoriteList = tenant.getFavoriteList();
        isFavorite = false;
        commentIcon = (ImageView)rootView.findViewById(R.id.tenant_comment_icon);
        comment = (TextView)rootView.findViewById(R.id.comment_post);
        TextView phone_txv = (TextView) rootView.findViewById(R.id.telephone);
        TextView email_txv = (TextView) rootView.findViewById(R.id.email);
        TextView description_txv = (TextView) rootView.findViewById(R.id.description);

        phone_txv.setText(phone);
        email_txv.setText(email);
        description_txv.setText(description);

        commentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                ReviewFragment fragment = new ReviewFragment();
                transaction.replace(R.id.detail_layout, fragment);
                transaction.addToBackStack("House Detail");
                transaction.commit();
            }
        });


        favoriteIcon = (ImageView) rootView.findViewById(R.id.favorite_icon);
        favoriteStatus = (TextView) rootView.findViewById(R.id.favorite_status);

        favoriteIcon.setImageResource(R.mipmap.ic_favorite_unmark);

        for(Favorite favorite: favoriteList){
            if(favorite.getHouseId().equals(houseId) && favorite.getLandlordfbId().equals(landlordFbId)){
                isFavorite = true;
                favoriteIcon.setImageResource(R.mipmap.ic_favorite_mark);
                break;
            }
        }

        favoriteIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                favoriteIcon.setImageResource(R.mipmap.ic_favorite_mark);
                Log.v("Tag", "favorite icon click");
                if(isFavorite==false) {
                    Favorite favorite = new Favorite();
                    favorite.setHouseId(houseId);
                    favorite.setLandlordfbId(landlordFbId);
                    favoriteList.add(favorite);
                    tenant.setFavoriteList(favoriteList);
                    updateDB();
                }else{
                    favoriteIcon.setImageResource(R.mipmap.ic_favorite_unmark);
                    isFavorite = false;
                    for(int i = 0; i < favoriteList.size(); i++){
                        Favorite favorite = favoriteList.get(i);
                        if(favorite.getHouseId().equals(houseId) && favorite.getLandlordfbId().equals(landlordFbId)){
                            favoriteList.remove(i);
                            tenant.setFavoriteList(favoriteList);
                            updateDB();
                            break;
                        }
                    }
                }
            }
        });

        favoriteStatus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                favoriteIcon.setImageResource(R.mipmap.ic_favorite_mark);
                Log.v("Tag", "favorite icon click");
                if(isFavorite==false) {
                    Favorite favorite = new Favorite();
                    favorite.setHouseId(houseId);
                    favorite.setLandlordfbId(landlordFbId);
                    favoriteList.add(favorite);
                    tenant.setFavoriteList(favoriteList);
                    updateDB();
                }else{
                    favoriteIcon.setImageResource(R.mipmap.ic_favorite_unmark);
                    isFavorite = false;
                    for(int i = 0; i < favoriteList.size(); i++){
                        Favorite favorite = favoriteList.get(i);
                        if(favorite.getHouseId().equals(houseId) && favorite.getLandlordfbId().equals(landlordFbId)){
                            favoriteList.remove(i);
                            tenant.setFavoriteList(favoriteList);
                            updateDB();
                            break;
                        }
                    }
                }
            }
        });

        mapView = (MapView) rootView.findViewById(R.id.google_map);
        setUpGoogleMap();
        mapView.onCreate(saveInstanceState);
//        incrementDBViewCount();
        return rootView;
    }

    private void updateDB(){
        Call<Tenant> call = service.tenantUpdate(tenant);
        call.enqueue(new Callback<Tenant>() {
            @Override
            public void onResponse(Call<Tenant> call, Response<Tenant> response) {
                UserPref.setTenantUser(tenant, getActivity());
            }

            @Override
            public void onFailure(Call<Tenant> call, Throwable t) {
                System.out.print("Rest request failed");
            }
        });
    }



    private void setUpGoogleMap() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng latLng = new LatLng(lat, lng);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng) // Center Set
                        .zoom(15.0f)                // Zoom
                        .bearing(90)                // Orientation of the camera to east
                        .tilt(0)                   // Tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder

                googleMap.addMarker(new MarkerOptions().position(latLng)
                        .title("Marker")
                        .snippet("Please move the marker if needed.")
                        .draggable(true));

                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.getUiSettings().setCompassEnabled(false);
                googleMap.getUiSettings().setRotateGesturesEnabled(false);
                googleMap.getUiSettings().setTiltGesturesEnabled(false);
            }
        });
    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
