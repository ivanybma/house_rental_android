package cmpe277.termproj_rentapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common_tools.RestApi;
import common_tools.RestHttpResponse;
import common_tools.RestMD;
import common_tools.UserPref;
import facebook.PrefUtils;
import landlord.landlord_info.Landlord;
import tenant.tenant_info.Tenant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private LoginButton facebookLogin;
    private Spinner roleSelect;
    private ToggleButton roleBtn;
    private CallbackManager callbackManager;
    private boolean isLogin = false;
    private String role = null;
    private Tenant tenant;
    private Landlord landlord;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        if(isFacebookLoggedIn()){
            LoginManager.getInstance().logOut();
            UserPref.clearUser(this);
        }

        TextView textView = (TextView) findViewById(R.id.role_description);

        textView.setText("Please Select a Role");

        roleSelect = (Spinner) findViewById(R.id.roleSelect);

        // Spinner click listener
        roleSelect.setOnItemSelectedListener(this);

        // Spinner Drop Down elements
        List<String> categories = new ArrayList<>();
        categories.add("LOGIN AS TENANT");
        categories.add("LOGIN AS LANDLORD");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attaching data adapter to spinner
        roleSelect.setAdapter(dataAdapter);

        callbackManager = CallbackManager.Factory.create();
        facebookLogin = (LoginButton) findViewById(R.id.login_button);

        facebookLogin.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends"));

        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role.equals("PLEASE SELECT A ROLE")== false) {
                    userLogin();
                }else{
                    Toast.makeText(MainActivity.this, "please select role to login", Toast.LENGTH_LONG).show();
                }
            }
        });

        facebookLogin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TenantManual.class);
                startActivity(intent);
                return true;
            }
        });
    }

    public void userLogin() {
//        progressDialog = new ProgressDialog(MainActivity.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
        facebookLogin.registerCallback(callbackManager, mCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(LoginResult loginResult) {

            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {

                            Log.e("response: ", response + "");
                            String fbId = "";
                            String name = "";
                            String email = "";
                            try {
                                fbId = object.getString("id").toString();
                                name = object.getString("name").toString();
                                email = object.getString("email").toString();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(MainActivity.this, "welcome " + name, Toast.LENGTH_LONG).show();

                            RestApi service = RestMD.restService();
                            if (role.equals("LOGIN AS LANDLORD")) {
                                landlord = new Landlord();
                                landlord.setFacebookId(fbId);
                                landlord.setName(name);
                                landlord.setEmail(email);
                                Call<Landlord> call = service.landlordLogin(landlord);
                                call.enqueue(new Callback<Landlord>() {
                                    @Override
                                    public void onResponse(Call<Landlord> call, Response<Landlord> response) {
                                        landlord = response.body();
                                        UserPref.setLanlordUser(landlord, MainActivity.this);
                                        final Intent intent = new Intent(getApplicationContext(), LandlordManual.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<Landlord> call, Throwable t) {
                                        System.out.println("Landlord rest request failed.......");
                                    }
                                });
                            } else if (role.equals("LOGIN AS TENANT")) {
                                Call<Tenant> call=null;
                                tenant = new Tenant();
                                tenant.setFacebookId(fbId);
                                tenant.setName(name);
                                tenant.setEmail(email);
                                call = service.tenantLogin(tenant);
                                call.enqueue(new Callback<Tenant>() {
                                    @Override
                                    public void onResponse(Call<Tenant> call, Response<Tenant> response) {
                                        tenant = response.body();
                                        UserPref.setTenantUser(tenant, MainActivity.this);
                                        final Intent intent = new Intent(getApplicationContext(), TenantManual.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<Tenant> call, Throwable t) {
                                        System.out.println("Tenant rest request failed.......");
                                    }
                                });
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, name, email, gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        role = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean isFacebookLoggedIn(){
        return AccessToken.getCurrentAccessToken() != null;
    }


}
