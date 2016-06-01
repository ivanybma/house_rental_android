package tenant.FavoriteHistory;

import android.os.Bundle;

/**
 * Created by Alan on 4/22/16.
 */
public class Favorite {
    public static final String FAVORITE_TITLE = "favoriteTitle";
    public static final String FAVORITE_ADDRESS = "favoriteAddress";
    public static final String FAVORITE_IMG_RESOURCE = "favoriteImgResource";

    private String favoriteTitle;
    private String favoriteAddress;
    private int favoriteImgResource;



    public Favorite(String favoriteTitle, String favoriteAddress, int favoriteImgResource){
        this.favoriteTitle = favoriteTitle;
        this.favoriteAddress = favoriteAddress;
        this.favoriteImgResource = favoriteImgResource;
    }

    public Favorite(Bundle bundle){
        if(bundle != null){
            this.favoriteTitle = bundle.getString(FAVORITE_TITLE);
            this.favoriteAddress = bundle.getString(FAVORITE_ADDRESS);
            this.favoriteImgResource = bundle.getInt(FAVORITE_IMG_RESOURCE);
        }
    }

    public Bundle toBundle(){
        Bundle bundle = new Bundle();
        bundle.putString(FAVORITE_TITLE,this.favoriteTitle);
        bundle.putString(FAVORITE_ADDRESS, this.favoriteAddress);
        bundle.putInt(FAVORITE_IMG_RESOURCE, this.favoriteImgResource);
        return bundle;
    }

    public String getFavoriteName(){
        return favoriteTitle;
    }

    public void setFavoriteName(String favoriteTitle){
        this.favoriteTitle = favoriteTitle;
    }

    public String getFavoriteAddress(){
        return favoriteAddress;
    }

    public void setFavoriteAddress(String favoriteAddress){
        this.favoriteAddress = favoriteAddress;
    }


    public int getFavoriteImgResource(){
        return favoriteImgResource;
    }
    public void setFavoriteImgResource(int favoriteImgResource){
        this.favoriteImgResource = favoriteImgResource;
    }
}
