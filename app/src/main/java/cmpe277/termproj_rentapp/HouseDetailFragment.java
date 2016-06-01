package cmpe277.termproj_rentapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import common_tools.RestApi;
import common_tools.RestMD;
import common_tools.UserPref;
import landlord.HouseStatusEvent;
import landlord.LandlordInfoFragment;
import landlord.MyBus;
import landlord.ReviewFragment;
import landlord.house_info.HouseInfo;
import landlord.landlord_info.Landlord;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yunlongxu on 4/23/16.
 */
public class HouseDetailFragment extends Fragment {

    private MapView mapView;
    private TextView commentIcon;
    private ImageView editIcon;
    private ImageView statusIcon;
    private TextView comment;
    private TextView displayAddress;
    private TextView description;
    private TextView telephone;
    private TextView email;
    private boolean isLandlord = false;
    private HouseInfo houseInfo;
    private Landlord landlord;
    private int position;
    private PopupWindow popupWindow;
    private PercentRelativeLayout statusLayout;
    RestApi service = RestMD.restService();

    public HouseDetailFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        houseInfo = (HouseInfo) bundle.getSerializable("houseinfo");
        isLandlord = (Boolean) bundle.getSerializable("islandlord");
        position = (Integer) bundle.getSerializable("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;

//        isTenant = true;
        if (isLandlord) {
            rootView = inflater.inflate(R.layout.fragment_house_detail, container,false);
            commentIcon = (TextView)rootView.findViewById(R.id.comment_icon);
            commentIcon.setTypeface(Typeface.DEFAULT_BOLD);
            commentIcon.setTextColor(Color.BLACK);
            commentIcon.setText(String.valueOf(houseInfo.getNumOfViews()));
            comment = (TextView)rootView.findViewById(R.id.comment_post);
            editIcon = (ImageView)rootView.findViewById(R.id.edit_icon);
            statusIcon = (ImageView)rootView.findViewById(R.id.status_icon);
            statusLayout = (PercentRelativeLayout)rootView.findViewById(R.id.status_layout);
            isLandlord = false;
            displayAddress = (TextView) rootView.findViewById(R.id.google_address);
            displayAddress.setText(houseInfo.getAddress().getAddress()
                                    + ", "
                                    + houseInfo.getAddress().getCity()
                                    + ", "
                                    + houseInfo.getAddress().getState()
                                    + "."
                                    + houseInfo.getAddress().getZipcode());
            description = (TextView) rootView.findViewById(R.id.description);
            description.setText(houseInfo.getDescription());

            landlord = UserPref.getLanlordUser(getActivity());

            telephone = (TextView) rootView.findViewById(R.id.telephone);
            telephone.setText(landlord.getPhoneNum());
            email = (TextView) rootView.findViewById(R.id.email);
            email.setText(landlord.getEmail());


            editIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    LandlordInfoFragment fragment = new LandlordInfoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("isOld", true);
                    bundle.putSerializable("position", position);
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.detail_layout, fragment);
                    transaction.addToBackStack("Edit Landlord Info");
                    transaction.commit();
                }
            });

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

            statusLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View popUpView = layoutInflater.inflate(R.layout.popup, null);
                    popupWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    Button btnPosting = (Button) popUpView.findViewById(R.id.pop_posting);
                    Button btnCancel = (Button) popUpView.findViewById(R.id.pop_cancel);
                    Button btnRented = (Button) popUpView.findViewById(R.id.pop_rented);

                    btnPosting.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            landlord.getHouseInfoList().get(position).setStatus("Posting");
                            UserPref.setLanlordUser(landlord, getContext());
                            pushtoDB();
                            MyBus.getInstance().post(new HouseStatusEvent(0));
                        }
                    });

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            landlord.getHouseInfoList().get(position).setStatus("Cancelled");
                            UserPref.setLanlordUser(landlord, getContext());
                            pushtoDB();
                            MyBus.getInstance().post(new HouseStatusEvent(1));
                        }
                    });

                    btnRented.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            landlord.getHouseInfoList().get(position).setStatus("Rented");
                            UserPref.setLanlordUser(landlord, getContext());
                            pushtoDB();
                            MyBus.getInstance().post(new HouseStatusEvent(2));
                        }
                    });
                    popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
                }
            });

        } else {

            // Tenant Implementation
            //working on this
/*            rootView = inflater.inflate(R.layout.fragment_tenant_house_detail, container, false);
            commentIcon = (ImageView)rootView.findViewById(R.id.comment_icon);
            comment = (TextView)rootView.findViewById(R.id.comment_post);
            commentIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    ReviewFragment fragment = new ReviewFragment();
                    transaction.replace(R.id.detail_layout, fragment);
                    transaction.addToBackStack("House Detail");
                    transaction.commit();
                }
            });*/

        }


        mapView = (MapView) rootView.findViewById(R.id.google_map);
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

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                ReviewFragment fragment = new ReviewFragment();
                transaction.replace(R.id.detail_layout, fragment);
                transaction.addToBackStack("House Detail");
                transaction.commit();
            }
        });

        setUpGoogleMap();
        mapView.onCreate(savedInstanceState);


        return rootView;
    }

    private void pushtoDB() {
        Call<Landlord> call = service.landlordUpdate(landlord);
        call.enqueue(new Callback<Landlord>() {
            @Override
            public void onResponse(Call<Landlord> call, Response<Landlord> response) {
                UserPref.setLanlordUser(landlord, getActivity());
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getActivity())
                                .setSmallIcon(R.drawable.logo_small)
                                .setContentTitle("Change Status Successful")
                                .setContentText("The house Status has changed");
                int mNotificationId = 003;
                NotificationManager mNotifyMgr = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
                popupWindow.dismiss();
            }

            @Override
            public void onFailure(Call<Landlord> call, Throwable t) {

            }
        });
    }

    private void setUpGoogleMap() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng latLng = new LatLng(houseInfo.getAddress().getLat(), houseInfo.getAddress().getLng());
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

    public boolean isLandlord() {
        return isLandlord;
    }

    public void setLandlord(boolean landlord) {
        isLandlord = landlord;
    }
}
