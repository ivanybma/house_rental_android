package landlord;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.NumberFormat;

import cmpe277.termproj_rentapp.OnLayoutSelectListener;
import cmpe277.termproj_rentapp.R;
import common_tools.RestApi;
import common_tools.RestMD;
import common_tools.UserPref;
import landlord.house_info.HouseInfo;
import landlord.landlord_info.Landlord;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yunlongxu on 4/23/16.
 */
public class SubmitFragment extends Fragment {

    private EditText rentFee;
    private EditText description;
    private Button submit;

    private TextInputLayout tlRentFee;
    private TextInputLayout tlDescription;

    private OnLayoutSelectListener mCallBack;

    private boolean isOld = false;
    private Landlord landlordInfo;
    private HouseInfo houseInfo;
    private int position;
    RestApi service = RestMD.restService();

    public SubmitFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        isOld = (Boolean) bundle.getSerializable("isOld");
        if (!isOld) {
            landlordInfo = (Landlord) bundle.getSerializable("landlord");
            houseInfo = (HouseInfo) bundle.getSerializable("house");
        } else {
            position = (Integer) bundle.getSerializable("position");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_submit, container, false);

        rentFee = (EditText)rootView.findViewById(R.id.et_rent);
        rentFee.setInputType(InputType.TYPE_CLASS_NUMBER);
        rentFee.addTextChangedListener(new TextWatcher() {
            private String current = "";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    rentFee.removeTextChangedListener(this);

                    String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
                    String cleanString = s.toString().replaceAll(replaceable, "");

                    double parsed;
                    try {
                        parsed = Double.parseDouble(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0.00;
                    }
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed/100));

                    current = formatted;
                    rentFee.setText(formatted);
                    rentFee.setSelection(formatted.length());
                    rentFee.addTextChangedListener(this);
                }
            }
        });
        description = (EditText)rootView.findViewById(R.id.et_house_discription);
        tlRentFee = (TextInputLayout)rootView.findViewById(R.id.tl_rent_fee);
        tlDescription = (TextInputLayout)rootView.findViewById(R.id.tl_house_description);
        submit = (Button)rootView.findViewById(R.id.submit);

        if (!isOld) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValid()) {
                        houseInfo.setPrice(Double.parseDouble(rentFee.getText().toString().substring(1).replaceAll(",","")));
                        houseInfo.setDescription(description.getText().toString());
                        landlordInfo.addHouse(houseInfo);
                        pushToDB();
                    }
                }
            });
        } else {
            landlordInfo = UserPref.getLanlordUser(getContext());
            houseInfo = landlordInfo.getHouseInfoList().get(position);
            rentFee.setText(String.valueOf(houseInfo.getPrice() * 10));
            description.setText(houseInfo.getDescription());
            submit.setText("Update");
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValid()) {
                        houseInfo.setPrice(Double.parseDouble(rentFee.getText().toString().substring(1).replaceAll(",","")));
                        houseInfo.setDescription(description.getText().toString());
                        pushToDB();
                    }
                }
            });
        }

        return rootView;
    }

    private void pushToDB() {
        Call<Landlord> call = service.landlordUpdate(landlordInfo);
        call.enqueue(new Callback<Landlord>() {
            @Override
            public void onResponse(Call<Landlord> call, Response<Landlord> response) {
//                landlordInfo = response.body();
                UserPref.setLanlordUser(landlordInfo, getActivity());
                if (!isOld) {
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getActivity())
                                    .setSmallIcon(R.drawable.logo_small)
                                    .setContentTitle("Posting Successful")
                                    .setContentText("New house has been post");
                    int mNotificationId = 001;
                    NotificationManager mNotifyMgr = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());
                    FragmentManager fm = getActivity().getSupportFragmentManager();
//                fm.popBackStack("landlord search option", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    while (fm.getBackStackEntryCount() != 1) {
                        fm.popBackStackImmediate();
                    }
                } else {
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getActivity())
                                    .setSmallIcon(R.drawable.logo_small)
                                    .setContentTitle("Updating Successful")
                                    .setContentText("Update house has been post");
                    int mNotificationId = 002;
                    NotificationManager mNotifyMgr = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());
                    mCallBack.onLayoutReplace();
//                    FragmentManager fm = getActivity().getSupportFragmentManager();
//                fm.popBackStack("landlord search option", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    while (fm.getBackStackEntryCount() != 1) {
//                        fm.popBackStackImmediate();
//                    }
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    HouseInfoListFragment fragment = new HouseInfoListFragment();
                    transaction.replace(R.id.search_detail, fragment);
                    transaction.addToBackStack("List Posting");
                    transaction.commit();
                }

            }

            @Override
            public void onFailure(Call<Landlord> call, Throwable t) {
                System.out.print("Rest request failed");

            }
        });
    }

    public void setOld(boolean old) {
        this.isOld = old;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallBack = (OnLayoutSelectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnLayoutSelectListener");
        }
    }

    public boolean isValid() {
        boolean value = true;
        if (rentFee.getText() == null || rentFee.getText().toString().isEmpty()) {
            tlRentFee.setError("Empty Value");
            rentFee.setFocusable(true);
            value = false;
        }

        if (description.getText() == null || description.getText().toString().isEmpty()) {
            tlDescription.setError("Empty Value");
            description.setFocusable(true);
            value =  false;
        }
        return value;
    }
}
