package tenant.favorite_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cheyikung on 5/11/16.
 */
public class Favorite {

    @SerializedName("landlordfbId")
    @Expose
    private String landlordfbId;

    @SerializedName("houseId")
    @Expose
    private String houseId;

    public Favorite() {
    }

    public Favorite(String landlordfbId, String houseId) {
        this.landlordfbId = landlordfbId;
        this.houseId = houseId;
    }

    public String getLandlordfbId() {
        return landlordfbId;
    }

    public void setLandlordfbId(String landlordfbId) {
        this.landlordfbId = landlordfbId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
