package facebook;

import android.content.Context;

/**
 * Created by yunlongxu on 4/21/16.
 */
public class PrefUtils {

    public static void setCurrentUser(User currentUser, Context context) {
        ComplexPrefereneces complexPrefereneces = ComplexPrefereneces.getComplexPrefereneces(context, "user_prefs", 0);
        complexPrefereneces.putObject("current_user_value", currentUser);
        complexPrefereneces.commit();
    }

    public static User getCurrentUser(Context context) {
        ComplexPrefereneces complexPrefereneces = ComplexPrefereneces.getComplexPrefereneces(context, "user_prefs", 0);
        User currentUser = complexPrefereneces.getObject("current_user-value", User.class);
        return currentUser;
    }

    public static void clearCurrentUser(Context context) {
        ComplexPrefereneces complexPrefereneces = ComplexPrefereneces.getComplexPrefereneces(context, "user_prefs", 0);
        complexPrefereneces.clearObject();;
        complexPrefereneces.commit();
    }
}
