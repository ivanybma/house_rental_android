package tenant.House_search;

/**
 * Created by ivanybma on 5/9/16.
 */
public class HouseSchCri {

    private String address;
    private double lat;
    private double lng;
    private String city;
    private String state;
    private String propertyType;
    private int numOfBathroom;
    private int numOfBedroom;
    private double priceH;
    private double priceL;

    public double getPriceH() {
        return priceH;
    }

    public void setPriceH(double priceH) {
        this.priceH = priceH;
    }

    public double getPriceL() {
        return priceL;
    }

    public void setPriceL(double priceL) {
        this.priceL = priceL;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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




}
