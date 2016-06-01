package cmpe277.termproj_rentapp;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import common_tools.GPSTracker;
import common_tools.RestApi;
import common_tools.RestMD;
import common_tools.UserPref;
import facebook.PrefUtils;
import landlord.house_info.HouseInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tenant.HouseSearchListFragment;
import tenant.House_search.HouseSchCri;
import tenant.LocationMapFragment;
import tenant.TenantSearchFragment;
import tenant.FavoriteFragment;
import tenant.tenant_info.Tenant;

/**
 * Created by Alan on 4/20/16.
 */
public class TenantManual extends AppCompatActivity implements  AdapterView.OnItemClickListener {
    private ArrayAdapter<String> myAdapter;
    private ListView myDrawerList;
    private ActionBarDrawerToggle myDrawerToggle;
    private DrawerLayout myDrawerLayout;
    private String myActivityTitle;
    private GoogleMap mMap;
    boolean mapShow;
    private GPSTracker gps;
    private double latitude;
    private double longitude;
    String[] tenantListArray = {"Search", "Favorite", "Log out"};
    private EditText searchview;
    RestApi service = RestMD.restService();
    HouseSearchListFragment fragment = null;
    FragmentManager fragmentManager = getSupportFragmentManager();

    private Tenant tenant;
    private List<HouseInfo> favoriteHouseInfoList;

    public void onCreate(Bundle saveInstanceState) {
        System.out.println("TenantManual onCreate");
        super.onCreate(saveInstanceState);
        setContentView(R.layout.tenant);
        // create map
/*        setupMapIfRequired();*/

//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_prefs",0);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        String fbId = sharedPreferences.getString("fbId", null);


        myDrawerList = (ListView) findViewById(R.id.navList_tenant);

        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_tenant);
        myActivityTitle = getTitle().toString();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo_small);
        toolbar.setTitle(null);


        addDrawerItems();
        setupDrawer();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        searchview = (EditText) findViewById(R.id.searchview);
        searchview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getX() <= (searchview.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                        System.out.println("onclick on the left icon");
                        searchHouse();
                        return true;
                    }
                    System.out.println(event.getX()+" "+Integer.valueOf(v.getRight()-searchview.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()-70));
                    if(event.getX() >= Integer.valueOf(v.getRight()-searchview.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()-70)) {
                        System.out.println("onclick on the right icon");
                        prepareSearchFrag();
                        return true;
                    }
                }
                return false;
            }
        });


        myDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(TenantManual.this, "Item Click", Toast.LENGTH_LONG).show();
                displayView(position);
            }
        });

        //create map by calling helper function below
//        mapShow = setupMapIfRequired();
//        setupMap();

        setupMapIfRequired();

    }

    private void getFavoriteHouseInfoList(){
        tenant = UserPref.getTenantUser(TenantManual.this);
        Call<List<HouseInfo>> call = service.getFavoriteList(tenant.getFavoriteList());
        call.enqueue(new Callback<List<HouseInfo>>() {
            @Override
            public void onResponse(Call<List<HouseInfo>> call, Response<List<HouseInfo>> response) {
                favoriteHouseInfoList = response.body();
                FavoriteFragment fragment = FavoriteFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putSerializable("favoriteList", (Serializable) favoriteHouseInfoList);
                fragment.setArguments(bundle);
                myDrawerLayout.closeDrawers();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            }

            @Override
            public void onFailure(Call<List<HouseInfo>> call, Throwable t) {
                System.out.print("Rest request failed");

            }
        });
    }
        //create map
/*        mapShow = setupMapIfRequired();
        setupMap();*/
    // set location data
    public void setLocationData() {
        gps = new GPSTracker(this.getApplicationContext());
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        } else {
            gps.showSettingAlert();
        }
    }

    public void searchHouse(){
        fragment = new HouseSearchListFragment();
        final List<HouseInfo> houseInfos = new ArrayList<HouseInfo>();
//        Call<List<HouseInfo>> call = service.getHouseInfoList();
        HouseSchCri houseSchCri=new HouseSchCri();
        houseSchCri.setLng(longitude);
        houseSchCri.setLat(latitude);
        houseSchCri.setAddress(searchview.getText().toString());
        Call<List<HouseInfo>> call = service.getSearchHouseInfoList(houseSchCri);
        call.enqueue(new Callback<List<HouseInfo>>() {
            @Override
            public void onResponse(Call<List<HouseInfo>> call, Response<List<HouseInfo>> response) {
                //List<HouseInfo> houseInfos = new ArrayList<HouseInfo>();

                for(int i=0; response.body()!=null&&i<response.body().size(); i++)
                    houseInfos.add(response.body().get(i));

                fragment.setHouseInfos(houseInfos);
                String backStateName = fragment.getClass().getName();
                //FragmentManager fragmentManager = getSupportFragmentManager();
                boolean fragmentPopped = getSupportFragmentManager().popBackStackImmediate(backStateName, 0);
                System.out.println("****"+getSupportFragmentManager().getBackStackEntryCount());

                if (!fragmentPopped) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content_frame, fragment, backStateName);
                    transaction.addToBackStack(backStateName);
                    transaction.commit();
                    System.out.println(getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()-1));

                }
            }

            @Override
            public void onFailure(Call<List<HouseInfo>> call, Throwable t) {
                System.out.println("Rest request failed.......");
               // fragment.setHouseInfos(houseInfos);
                //FragmentManager fragmentManager = getSupportFragmentManager();
               // fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "tenant_house_search").commit();

            }

        });
    }


    @Override
    protected void onPostCreate(Bundle saveInstanceState) {
        super.onPostCreate(saveInstanceState);
        myDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        myDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void addDrawerItems() {
        String[] tenantListArray = {"Search", "Favorite List", "Log out"};
        myAdapter = new ArrayAdapter<>(this, R.layout.drawer_list_item, tenantListArray);
        myDrawerList.setAdapter(myAdapter);
    }

    private void setupDrawer() {
        myDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu();


            }

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(myActivityTitle);
                invalidateOptionsMenu();
            }
        };


        myDrawerToggle.setDrawerIndicatorEnabled(true);
        myDrawerLayout.setDrawerListener(myDrawerToggle);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (myDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void prepareSearchFrag(){
        TenantSearchFragment fragment=new TenantSearchFragment();
        if (fragment != null) {
            fragment.setLatitude(latitude);
            fragment.setLongitude(longitude);
           // FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, fragment);
            transaction.addToBackStack(fragment.getClass().getName());
            transaction.commit();
        } else {
            Log.e("TenantManual", "Error in creating fragment");
        }
    }

     //launch different fragment from drawer
    private void displayView(int position){

        Fragment fragment=null;
        Class fragmentClass = null;
        switch(position){


            //for search
            case 0:
                fragment=new TenantSearchFragment();
                if (fragment != null) {
                    //FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    transaction.replace(R.id.content_frame, fragment,"tenant_search");
                    transaction.addToBackStack(null);
                    transaction.commit();

                    myDrawerList.setItemChecked(position, true);
                    myDrawerList.setSelection(position);
                    getSupportActionBar().setTitle(tenantListArray[position]);
                    myDrawerLayout.closeDrawer(myDrawerList);

                } else {
                    Log.e("TenantManual", "Error in creating fragment");
                }
                break;

            // for favorite
            case 1:
                Toast.makeText(TenantManual.this, "Click Favorite", Toast.LENGTH_SHORT).show();
                getFavoriteHouseInfoList();

                break;

            //for log out
            case 2:
                logoutFacebook();
                finish();
                break;
        }

    }


    // for checking whether map is setup
    private void setupMapIfRequired(){
//        if(mMap == null){
//           MapFragment myMapFrag  =  (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
//            mMap = myMapFrag.getMap();
//
//        }
//        return  mMap!=null;
        setLocationData();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
//        latitude=37.3352;
//        longitude=-121.8811;
        bundle.putDouble("latitude",latitude);
        bundle.putDouble("longitude",longitude);
        LocationMapFragment mapFragment = new LocationMapFragment();
        mapFragment.setArguments(bundle);
        transaction.replace(R.id.content_frame, mapFragment,"location_fragment");
        transaction.addToBackStack(null);
        transaction.commit();


    }


    private void setupMap() {
        //SJSU: location 37°20'08.6"N 121°52'52.3"W

//        LatLng location = new LatLng(37.20, -121.52);
//        if(mapShow){
//            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(location,5);
//            mMap.moveCamera(update);
//        }
//        mMap.addMarker(new MarkerOptions().position(location)
//                .title("marker").anchor(.5f,.5f).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        LatLng location = new LatLng(37.20, -121.52);
        if (mapShow) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(location, 5);
            mMap.moveCamera(update);
        }
        mMap.addMarker(new MarkerOptions().position(location)

                .title("marker").anchor(.5f, .5f).icon(BitmapDescriptorFactory.fromResource(R.drawable.com_facebook_button_like_icon_selected)));
    }

    public void logoutFacebook(){
        PrefUtils.clearCurrentUser(TenantManual.this);
        LoginManager.getInstance().logOut();
    }

//    //google map search
//    public void tenantSearch(View view) throws IOException {
//        EditText searchTxt = (EditText) findViewById(R.id.tenantSearch);
//        String location = searchTxt.getText().toString();
//        List<Address> addressList = null;
//
//        if(location !=null || !location.equals("")){
//            Geocoder geocoder = new Geocoder(this);
//
//            try{
//                addressList = geocoder.getFromLocationName(location , 1);
//
//            }
//            catch(IOException e){
//                e.printStackTrace();
//            }
//
//                Address address = addressList.get(0);
//                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//            mMap.addMarker(new MarkerOptions().position(latLng)
//                    .title("marker").anchor(.5f,.5f).icon(BitmapDescriptorFactory.fromResource(R.drawable.com_facebook_button_like_icon_selected)));
//
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//        }
//
//
//    }



/*    //google map search
    public void tenantSearch(View view) throws IOException {
        EditText searchTxt = (EditText) findViewById(R.id.tenantSearch);
        String location = searchTxt.getText().toString();
        List<Address> addressList = null;

        if(location !=null || !location.equals("")){
            Geocoder geocoder = new Geocoder(this);

            try{
                addressList = geocoder.getFromLocationName(location , 1);

            }
            catch(IOException e){
                e.printStackTrace();
            }

                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng)
                    .title("marker").anchor(.5f,.5f).icon(BitmapDescriptorFactory.fromResource(R.drawable.com_facebook_button_like_icon_selected)));

            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        }


    }*/

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        System.out.println("TenantManual OnStart");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        System.out.println("TenantManual OnResume");

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        System.out.println("TenantManual OnPause");
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        System.out.println("TenantManual OnStop");
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        System.out.println("TenantManual OnDestroy");
    }
}
