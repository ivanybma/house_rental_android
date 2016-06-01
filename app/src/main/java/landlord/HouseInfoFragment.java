package landlord;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cmpe277.termproj_rentapp.R;
import common_tools.UserPref;
import landlord.house_info.Address;
import landlord.house_info.HouseInfo;
import landlord.landlord_info.Landlord;

/**
 * Created by yunlongxu on 4/19/16.
 */
public class HouseInfoFragment extends Fragment {

    private EditText address;
    private EditText city;
    private EditText state;
    private EditText zipcode;
    private Spinner spinner;
    private EditText numBedroom;
    private EditText numBathroom;
    private EditText sqrft;
    private Button nextButton;

    private TextInputLayout tlAddress;
    private TextInputLayout tlCity;
    private TextInputLayout tlState;
    private TextInputLayout tlZipcode;
    private TextInputLayout tlNumBedroom;
    private TextInputLayout tlNumBathroom;
    private TextInputLayout tlSqrft;

    private Landlord landlordInfo;
    private HouseInfo houseInfo;
    private String houseType;
    private int position;

    private Boolean isOld = false;
    private Bundle bundle;

    public HouseInfoFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        isOld = (Boolean) bundle.getSerializable("isOld");
        if (isOld) {
            position = (Integer) bundle.getSerializable("position");
        } else {
            landlordInfo = (Landlord)bundle.getSerializable("landlord");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_house_info, container, false);
        address = (EditText) rootView.findViewById(R.id.address);
        city = (EditText) rootView.findViewById(R.id.city);
        state = (EditText) rootView.findViewById(R.id.state);
        zipcode = (EditText) rootView.findViewById(R.id.zipcode);
        spinner = (Spinner) rootView.findViewById(R.id.type_spinner);
        numBedroom = (EditText) rootView.findViewById(R.id.num_bedroom);
        numBedroom.setInputType(InputType.TYPE_CLASS_NUMBER);
        numBathroom = (EditText) rootView.findViewById(R.id.num_bathroom);
        numBathroom.setInputType(InputType.TYPE_CLASS_NUMBER);
        sqrft = (EditText) rootView.findViewById(R.id.sqrft);
        sqrft.setInputType(InputType.TYPE_CLASS_NUMBER);
        nextButton = (Button) rootView.findViewById(R.id.next1);

        tlAddress = (TextInputLayout) rootView.findViewById(R.id.tl_house_address);
        tlCity = (TextInputLayout) rootView.findViewById(R.id.tl_house_city);
        tlState = (TextInputLayout) rootView.findViewById(R.id.tl_house_state);
        tlZipcode = (TextInputLayout) rootView.findViewById(R.id.tl_house_zipcode);
        tlNumBathroom = (TextInputLayout) rootView.findViewById(R.id.tl_num_bathroom);
        tlNumBedroom = (TextInputLayout) rootView.findViewById(R.id.tl_num_bedroom);
        tlSqrft = (TextInputLayout) rootView.findViewById(R.id.tl_sqrft);

        if (isOld) {
            landlordInfo = UserPref.getLanlordUser(getContext());
            houseInfo = landlordInfo.getHouseInfoList().get(position);
            address.setText(houseInfo.getAddress().getAddress());
            city.setText(houseInfo.getAddress().getCity());
            state.setText(houseInfo.getAddress().getState());
            zipcode.setText(houseInfo.getAddress().getZipcode());
            spinner.setPrompt(houseInfo.getPropertyType());
            numBathroom.setText(String.valueOf(houseInfo.getNumOfBathroom()));
            numBedroom.setText(String.valueOf(houseInfo.getNumOfBedroom()));
            sqrft.setText(String.valueOf(houseInfo.getSqrtft()));
        }

        final List<String> categories = new ArrayList<String>();
        categories.add("Single House");
        categories.add("Town House");
        categories.add("Apartment");
        categories.add("Condo");
        categories.add("Mobile Home");
        categories.add("Studio");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = spinner.getSelectedItemPosition();
                        houseType = categories.get(position);
//                        Toast.makeText(getActivity(),"You have selected " + categories.get(position), Toast.LENGTH_LONG).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                }
        );

        if (!isOld) {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValid()) {
                        Address houseAddress = new Address(address.getText().toString(),
                                                            city.getText().toString(),
                                                            state.getText().toString(),
                                                            zipcode.getText().toString());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        String currentDate = simpleDateFormat.format(new Date());
                        HouseInfo houseInfo = new HouseInfo(landlordInfo.getFacebookId(),
                                                            houseAddress,
                                                            houseType,
                                                            Integer.parseInt(numBathroom.getText().toString()),
                                                            Integer.parseInt(numBedroom.getText().toString()),
                                                            Double.parseDouble(sqrft.getText().toString()),
                                                            "Posting",
                                                            currentDate,
                                                            landlordInfo.getPhoneNum(),
                                                            landlordInfo.getEmail());
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        SubmitFragment fragment = new SubmitFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("landlord", landlordInfo);
                        bundle.putSerializable("house", houseInfo);
                        bundle.putSerializable("isOld", false);
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.search_detail, fragment);
                        transaction.addToBackStack("House Info");
                        transaction.commit();
                    }
                }
            });
        } else {
            isOld = false;
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValid()) {
                        houseInfo.getAddress().setAddress(address.getText().toString());
                        houseInfo.getAddress().setCity(city.getText().toString());
                        houseInfo.getAddress().setState(state.getText().toString());
                        houseInfo.getAddress().setZipcode(zipcode.getText().toString());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        String currentDate = simpleDateFormat.format(new Date());
                        houseInfo.setPropertyType(houseType);
                        houseInfo.setNumOfBathroom(Integer.parseInt(numBathroom.getText().toString()));
                        houseInfo.setNumOfBedroom(Integer.parseInt(numBedroom.getText().toString()));
                        houseInfo.setSqrtft(Double.parseDouble(sqrft.getText().toString()));
                        houseInfo.setPostingDate(currentDate);
                        houseInfo.setLd_phone(landlordInfo.getPhoneNum());
                        houseInfo.setLd_email(landlordInfo.getEmail());
                        UserPref.setLanlordUser(landlordInfo, getContext());
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        SubmitFragment fragment = new SubmitFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("isOld", true);
                        bundle.putSerializable("position", position);
//                        fragment.setOld(true);
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.detail_layout, fragment);
                        transaction.addToBackStack("House Info");
                        transaction.commit();
                    }
                }
            });
        }



        return rootView;
    }

    public void setOld(Boolean old) {
        this.isOld = old;
    }

    public boolean isValid() {
        boolean value = true;
        if (address.getText() == null || address.getText().toString().isEmpty()) {
            tlAddress.setError("Empty Address");
            address.setFocusable(true);
            value = false;
        }

        if (city.getText() == null || city.getText().toString().isEmpty()) {
            tlCity.setError("Empty City");
            city.setFocusable(true);
            value = false;
        }

        if (state.getText() == null || state.getText().toString().isEmpty()) {
            tlState.setError("Empty State");
            state.setFocusable(true);
            value = false;
        }

        if (zipcode.getText() == null || zipcode.getText().toString().isEmpty()) {
            tlZipcode.setError("Empty Zipcode");
            zipcode.setFocusable(true);
            value = false;
        }

        if (numBedroom.getText() == null || numBedroom.getText().toString().isEmpty()) {
            tlNumBedroom.setError("Empty Bedroom Number");
            numBedroom.setFocusable(true);
            value = false;
        }

        if (numBathroom.getText() == null || numBathroom.getText().toString().isEmpty()) {
            tlNumBathroom.setError("Empty Bathroom Number");
            numBathroom.setFocusable(true);
            value = false;
        }

        if (sqrft.getText() == null || sqrft.getText().toString().isEmpty()) {
            tlSqrft.setError("Empty Value of Square Foot");
            sqrft.setFocusable(true);
            value = false;
        }
        return value;
    }
}
