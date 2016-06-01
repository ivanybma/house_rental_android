package cmpe277.termproj_rentapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.facebook.login.LoginManager;

import common_tools.UserPref;
import facebook.PrefUtils;
import facebook.User;
import landlord.HouseInfoListFragment;
import landlord.LandlordInfoFragment;
import landlord.OptionFragment;
import landlord.landlord_info.Landlord;
import tenant.tenant_info.Tenant;

/**
 * Created by yunlongxu on 4/19/16.
 */
public class LandlordManual extends AppCompatActivity implements OnLayoutSelectListener {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] landlordOption;
    private String option;
    private FrameLayout detail_layout;
    private boolean isCustomLayoutOn = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord);


        // Enable Toolbar as Actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo_small);
        toolbar.setTitle(null);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        landlordOption = getResources().getStringArray(R.array.landlord_option);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );

        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, landlordOption));

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                option = (String) (mDrawerList.getItemAtPosition(position));
                mDrawerLayout.closeDrawer(mDrawerList);
                if (option.equals("Log out")) {
                    logoutFacebook();
                    finish();
//                } else if (option.equals("Change Role")) {
//                    Intent tentantIntent = new Intent(LandlordManual.this, TenantManual.class);
//                    startActivity(tentantIntent);
                } else if (option.equals("New Post")) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    LandlordInfoFragment fragment = new LandlordInfoFragment();
                    transaction.replace(R.id.search_detail, fragment);
                    transaction.addToBackStack("landlord info");
                    transaction.commit();
                } else if (option.equals("List of Posting")) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    HouseInfoListFragment fragment = new HouseInfoListFragment();
                    transaction.replace(R.id.search_detail, fragment);
                    transaction.addToBackStack("List Posting");
                    transaction.commit();
                }

            }
        });

        final View.OnClickListener originalToolbarListener = drawerToggle.getToolbarNavigationClickListener();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 2) {
                    drawerToggle.setDrawerIndicatorEnabled(false);
                    drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getSupportFragmentManager().popBackStack();
                            if (isCustomLayoutOn) {
                                detail_layout.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                } else {
                    drawerToggle.setDrawerIndicatorEnabled(true);
                    drawerToggle.setToolbarNavigationClickListener(originalToolbarListener);
                }
            }
        });
        mDrawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            OptionFragment fragment = new OptionFragment();
            transaction.replace(R.id.search_detail, fragment);
            transaction.addToBackStack("landlord search option");
            transaction.commit();
        }
    }

    public void logoutFacebook() {
        PrefUtils.clearCurrentUser(LandlordManual.this);
        LoginManager.getInstance().logOut();

    }

    @Override
    public void onLayoutSelected() {
        detail_layout = (FrameLayout)findViewById(R.id.detail_layout);
        detail_layout.setVisibility(View.VISIBLE);
        isCustomLayoutOn = true;
    }

    @Override
    public void onLayoutReplace() {
        detail_layout = (FrameLayout) findViewById(R.id.detail_layout);
        detail_layout.setVisibility(View.INVISIBLE);
        isCustomLayoutOn = false;
    }

    //override back key
    //press back key button to popbackstack instead of destroy view
    @Override
    public void onBackPressed(){
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
            if (isCustomLayoutOn) {
                detail_layout.setVisibility(View.INVISIBLE);
            }
        }
    }
}
