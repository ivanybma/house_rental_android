package tenant.FavoriteHistory;

import java.util.ArrayList;
import java.util.List;

import cmpe277.termproj_rentapp.R;
import tenant.FavoriteHistory.Favorite;

/**
 * Created by Alan on 4/22/16.
 */
public class FavoriteData {

    private List<Favorite> favorites = new ArrayList<>();
    public List<Favorite> getFavorites(){  return favorites;}

    public FavoriteData(){
        favorites.add( new Favorite("Rent Apartment", "San Jose", R.drawable.manhattan5));
        favorites.add( new Favorite("Rent Hhouse", "San Francisco", R.drawable.room1));
        favorites.add( new Favorite("Rent Hhouse", "San Francisco", R.drawable.room1));
        favorites.add( new Favorite("Rent Hhouse", "San Francisco", R.drawable.room1));
        favorites.add( new Favorite("Rent Hhouse", "San Francisco", R.drawable.room1));
        favorites.add( new Favorite("Rent Hhouse", "San Francisco", R.drawable.room1));
        favorites.add( new Favorite("Rent Hhouse", "San Francisco", R.drawable.room1));
        favorites.add( new Favorite("Rent Hhouse", "San Francisco", R.drawable.room1));
        favorites.add( new Favorite("Rent Hhouse", "San Francisco", R.drawable.room1));
        favorites.add( new Favorite("Rent Hhouse", "San Francisco", R.drawable.room1));
        favorites.add( new Favorite("Rent Hhouse", "San Francisco", R.drawable.room1));
        favorites.add( new Favorite("Rent Hhouse", "San Francisco", R.drawable.room1));


    }
}
