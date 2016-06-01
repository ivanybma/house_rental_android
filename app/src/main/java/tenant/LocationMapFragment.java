package tenant;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import cmpe277.termproj_rentapp.R;

/**
 * Created by ivanybma on 4/22/16.
 */
public class LocationMapFragment extends Fragment{
//        implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,
//        GoogleMap.OnInfoWindowClickListener,GoogleMap.OnMapLongClickListener,
//        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private double latitude;
    private double longitude;
    private MapView mapView;
    private GoogleMap map;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;

    public  LocationMapFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("LocationMapFragment onCreateView");
        latitude=getArguments().getDouble("latitude");
        longitude=getArguments().getDouble("longitude");
        View rootView = inflater.inflate(R.layout.fragment_map_location, container, false);
        mapView = (MapView) rootView.findViewById(R.id.google_map);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                final GoogleMap curmap=googleMap;
                if (Build.VERSION.SDK_INT >= 23) {
                    System.out.println("***"+ ContextCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION) + " "+ PackageManager.PERMISSION_GRANTED);
                    if (ContextCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                LatLng myLocation = new LatLng(latitude, longitude);
                googleMap.setMyLocationEnabled(true);
                googleMap.addMarker(new MarkerOptions().title("current").position(myLocation));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){
                    @Override
                    public void onMapLongClick(LatLng point) {
                        curmap.addMarker(new MarkerOptions().title("selected").position(point));
                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                    }
                });

            }
        });
        return rootView;
    }

    @Override
    public void onResume() {

        super.onResume();
        System.out.println("LocationMapFragment onResume");
        mapView.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("LocationMapFragment onDestroy");
        mapView.onDestroy();
        getActivity().finish();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        System.out.println("LocationMapFragment onDestroyView");
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(this.getContext());
        System.out.println("LocationMapFragment onAttach");
    }
    @Override
    public void onDetach(){
        super.onDetach();
        System.out.println("LocationMapFragment onDetach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("LocationMapFragment onCreate");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("LocationMapFragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("LocationMapFragment onStop");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("LocationMapFragment onStart");
    }

}
