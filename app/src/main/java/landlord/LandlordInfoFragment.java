package landlord;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import cmpe277.termproj_rentapp.R;
import common_tools.UserPref;
import landlord.house_info.HouseInfo;
import landlord.landlord_info.Landlord;

/**
 * Created by yunlongxu on 4/19/16.
 */
public class LandlordInfoFragment extends Fragment {

    private TextView landlordName;
    private EditText phone;
    private EditText email;
    private Button button;
    private Landlord landlord;
    private PopupWindow pw;

    private TextInputLayout tlPhone;
    private TextInputLayout tlEmail;

    private HouseInfo houseInfo;
    private int position;


    private boolean isOld = false;

    public LandlordInfoFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isOld = (Boolean) bundle.getSerializable("isOld");
            position = (Integer) bundle.getSerializable("position");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_landlord_info, container, false);
        landlord = UserPref.getLanlordUser(rootView.getContext());
        landlordName = (TextView)rootView.findViewById(R.id.landlordName);
        landlordName.setText("Landlord: " + landlord.getName());
        phone = (EditText)rootView.findViewById(R.id.phone);
        email = (EditText)rootView.findViewById(R.id.email);
        email.setText(landlord.getEmail());
        button = (Button)rootView.findViewById(R.id.next);
        tlPhone = (TextInputLayout) rootView.findViewById(R.id.tl_phone);
        tlEmail = (TextInputLayout) rootView.findViewById(R.id.tl_email);

        phone.setRawInputType(Configuration.KEYBOARD_12KEY);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        if (isOld) {
            houseInfo = landlord.getHouseInfoList().get(position);
            phone.setText(houseInfo.getLd_phone());
            email.setText(houseInfo.getLd_email());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    if (!isOld) {
                        landlord.setPhoneNum(phone.getText().toString());
                        landlord.setEmail(email.getText().toString());
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        HouseInfoFragment fragment = new HouseInfoFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("landlord", landlord);
                        bundle.putSerializable("isOld", false);
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.search_detail, fragment);
                        transaction.addToBackStack("landlord info");
                        transaction.commit();
                    } else {
                        isOld = false;
                        landlord.setPhoneNum(phone.getText().toString());
                        landlord.setEmail(email.getText().toString());
                        UserPref.setLanlordUser(landlord, getContext());
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        HouseInfoFragment fragment = new HouseInfoFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("position", position);
                        bundle.putSerializable("isOld", true);
                        fragment.setArguments(bundle);
                        fragment.setOld(true);
                        transaction.replace(R.id.detail_layout, fragment);
                        transaction.addToBackStack("landlord info");
                        transaction.commit();
                    }

                }
            }
        });
        return rootView;
    }

    public boolean isValid() {
        boolean value = true;
        if (phone.getText() == null || phone.getText().toString().isEmpty()) {
            tlPhone.setError("Empty Phone Number");
            phone.setFocusable(true);
            value = false;
        } else if (PhoneNumberUtils.isGlobalPhoneNumber(phone.getText().toString())) {
            tlPhone.setError("Invalid Phone Number");
            phone.setFocusable(true);
            value = false;
        }

        if (email.getText() == null || email.getText().toString().isEmpty()) {
            tlEmail.setError("Empty Email address");
            email.setFocusable(true);
            value = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            tlEmail.setError("Invalid Email address");
            email.setFocusable(true);
        }

        return value;
    }
}
