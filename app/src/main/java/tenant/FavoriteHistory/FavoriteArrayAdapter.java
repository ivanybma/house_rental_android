package tenant.FavoriteHistory;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cmpe277.termproj_rentapp.R;
import tenant.FavoriteHistory.Favorite;

/**
 * Created by Alan on 4/22/16.
 */
public class FavoriteArrayAdapter extends ArrayAdapter<Favorite> {

    private final Context context;
    private final List<Favorite> favorites;

    public FavoriteArrayAdapter(Context context, List<Favorite> favorites){
        super(context, R.layout.fragment_favorite, favorites);
        this.context=context;
        this.favorites= favorites;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootview = inflater.inflate(R.layout.listview_favorite, parent, false);
        ImageView favoritePic = (ImageView) rootview.findViewById(R.id.favorite_picture);
        TextView favoriteTitile = (TextView) rootview.findViewById(R.id.favorite_info1);
        TextView favoriteAddress = (TextView) rootview.findViewById(R.id.favorite_info2);


        favoritePic.setImageResource(favorites.get(position).getFavoriteImgResource());

        if(favorites.get(position).getFavoriteName()!= null){
            favoriteTitile.setText(favorites.get(position).getFavoriteName());
        }
        if(favorites.get(position).getFavoriteAddress() != null){
            favoriteAddress.setText(favorites.get(position).getFavoriteAddress());
        }


        return rootview;
    }

}
