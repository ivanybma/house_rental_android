package landlord.house_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import landlord.house_info.Address;

/**
 * Created by yunlongxu on 4/19/16.
 */
public class HouseInfo implements Serializable {

    @SerializedName("houseId")
    @Expose
    private String houseId;
    @SerializedName("landlordFbId")
    @Expose
    private String landlordFbId;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("propertyType")
    @Expose
    private String propertyType;
    @SerializedName("numOfBathroom")
    @Expose
    private int numOfBathroom;
    @SerializedName("numOfBedroom")
    @Expose
    private int numOfBedroom;
    @SerializedName("sqrtft")
    @Expose
    private double sqrtft;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("numOfViews")
    @Expose
    private int numOfViews = 0;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("postingDate")
    @Expose
    private String postingDate;
    @SerializedName("ld_phone")
    @Expose
    private String ld_phone;
    @SerializedName("ld_email")
    @Expose
    private String ld_email;

    public HouseInfo() {}

    public HouseInfo(String landlordFbId, Address address, String propertyType, int numOfBathroom, int numOfBedroom, double sqrtft, double price, String description, String status, String postingDate) {
        this.address = address;
        this.propertyType = propertyType;
        this.numOfBathroom = numOfBathroom;
        this.numOfBedroom = numOfBedroom;
        this.sqrtft = sqrtft;
        this.price = price;
        this.description = description;
        this.status = status;
        this.postingDate = postingDate;
        this.landlordFbId=landlordFbId;
//        this.landlordName=landlordName;
    }

    public HouseInfo(String landlordFbId, Address address, String propertyType, int numOfBathroom, int numOfBedroom, double sqrtft, String status, String postingDate, String ld_phone, String ld_email) {
        this.landlordFbId = landlordFbId;
        this.address = address;
        this.propertyType = propertyType;
        this.numOfBathroom = numOfBathroom;
        this.numOfBedroom = numOfBedroom;
        this.sqrtft = sqrtft;
        this.status = status;
        this.postingDate = postingDate;
        this.ld_phone = ld_phone;
        this.ld_email = ld_email;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

//    public String getlandlordName() {
//        return landlordName;
//    }
//
//    public void setlandlordName(String landlordName) {
//        this.landlordName = landlordName;
//    }


    public String getLd_phone() {
        return ld_phone;
    }

    public void setLd_phone(String ld_phone) {
        this.ld_phone = ld_phone;
    }

    public String getLd_email() {
        return ld_email;
    }

    public void setLd_email(String ld_email) {
        this.ld_email = ld_email;
    }

    public String getLandlordFbId() {
        return landlordFbId;
    }

    public void setLandlordFbId(String landlordFbId) {
        this.landlordFbId = landlordFbId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public int getNumOfBathroom() {
        return numOfBathroom;
    }

    public void setNumOfBathroom(int numOfBathroom) {
        this.numOfBathroom = numOfBathroom;
    }

    public int getNumOfBedroom() {
        return numOfBedroom;
    }

    public void setNumOfBedroom(int numOfBedroom) {
        this.numOfBedroom = numOfBedroom;
    }

    public double getSqrtft() {
        return sqrtft;
    }

    public void setSqrtft(double sqrtft) {
        this.sqrtft = sqrtft;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public int getNumOfViews() {
        return numOfViews;
    }

    public void setNumOfViews(int numOfViews) {
        this.numOfViews = numOfViews;
    }
}

