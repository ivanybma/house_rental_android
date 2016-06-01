package common_tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by cheyikung on 4/25/16.
 */
public class UserPrefHelper {
    private static UserPrefHelper userPrefHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static Gson GSON = new Gson();
    Type typeOfObject = new TypeToken<Object>() {
    }.getType();

    private static final String NAME_SPACE = "user_pref";

    private UserPrefHelper(Context context, int mode) {
        sharedPreferences = context.getSharedPreferences(NAME_SPACE, mode);
        editor = sharedPreferences.edit();
    }

    public static UserPrefHelper getUserPrefHelper(Context context, int mode){
        userPrefHelper = new UserPrefHelper(context, mode);
        return userPrefHelper;
    }

    public void putObject(String key, Object object) {
        editor.putString(key, GSON.toJson(object));
    }

    public void commit() {
        editor.commit();
    }

    public void clearObject() {
        editor.clear();
    }

    public <T> T getObject(String key, Class<T> a) {

        String gson = sharedPreferences.getString(key, null);
        if (gson == null) {
            return null;
        } else {
            try{
                return GSON.fromJson(gson, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object storaged with key " + key + " is instanceof other class");
            }
        }
    }

}
