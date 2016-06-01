package tenant.tenant_info;

import java.util.List;
import tenant.favorite_info.Favorite;

/**
 * Created by cheyikung on 4/25/16.
 */
public class Tenant {

    private String tenantId;

    //unique
    private String facebookId;

    private String name;

    private String phoneNum;

    private String email;

    private List<String> reviewIdList;

    private List<Favorite> favoriteList;

    public  Tenant() {

    }

    public Tenant(String facebookId, String name, String phoneNum, String email) {
        super();
        this.facebookId = facebookId;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    public List<String> getReviewIdList() {
        return reviewIdList;
    }

    public void setReviewIdList(List<String> reviewIdList) {
        this.reviewIdList = reviewIdList;
    }

    public List<Favorite> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public void addFavorite(Favorite favorite){
        favoriteList.add(favorite);
    }

    public void addReviewId(String reviewId){
        reviewIdList.add(reviewId);
    }

    @Override
    public String toString() {
        return "Tenant [TenantfbId= " + facebookId + ", name= " + name + ", reviewIdList= " + reviewIdList + ", favoriteList= " + favoriteList + "]";
    }
}


