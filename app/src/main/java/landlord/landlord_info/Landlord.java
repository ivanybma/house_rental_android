package landlord.landlord_info;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import cmpe277.termproj_rentapp.R;
import landlord.house_info.HouseInfo;

/**
 * Created by yunlongxu on 4/20/16.
 */
public class Landlord implements Serializable {

    private String landlordId;

    //unique
    private String facebookId;

    private String name;

    private List<HouseInfo> houseInfoList = new ArrayList<HouseInfo>();

    private String phoneNum;

    private String email;

    public Landlord() {
    }

    public Landlord(String facebookId, String name, String phoneNum, String email) {
        super();
        this.facebookId = facebookId;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public Landlord(String phoneNum, String email) {
        super();
        this.phoneNum = phoneNum;
        this.email = email;
    }


    public String getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(String landlordId) {
        this.landlordId = landlordId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HouseInfo> getHouseInfoList() {
        return houseInfoList;
    }

    public HouseInfo getHouseById(String houseId) {
        for (HouseInfo house : houseInfoList) {
            if (house.getHouseId().endsWith(houseId)) {
                return house;
            }
        }
        return null;
    }

    public void setHouseById(String houseId, HouseInfo house){
        houseInfoList.set(Integer.parseInt(houseId), house);
    }

    public void setHouseInfoList(List<HouseInfo> houseInfoList) {
        this.houseInfoList = houseInfoList;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumOfHouses() {
        return houseInfoList.size();
    }

    public void addHouse(HouseInfo houseInfo) {
        if (houseInfoList == null) {
            houseInfoList = new ArrayList<>();
            houseInfoList.add(houseInfo);
        } else {
            houseInfoList.add(houseInfo);
        }
    }

    @Override
    public String toString() {
        return "Landlord [landlordfbId= " + facebookId + ", name= " + name + ", phone number= " + phoneNum + ", email= " + email + ", houseInfoList= " + houseInfoList + "]";
    }
}

