package polyhack.purplesquadmonopoly.cityquest;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import polyhack.purplesquadmonopoly.cityquest.model.User;

/**
 * Created by Ovi on 05-Dec-15.
 */
public class UserManagement {

    public static final String SHARED_PREFERENCES_KEY = "shared_preferences_cityquest";
    public static final String USER_PREF_KEY = "user_preferences_key";

    public static User getUser(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        String userJson = preferences.getString(USER_PREF_KEY, null);
        return userJson == null ? null : new Gson().fromJson(userJson, User.class);
    }

    public static void setUser(Context context, User user) {
        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        String jsonUser = new Gson().toJson(user, User.class);
        editor.putString(USER_PREF_KEY, jsonUser).apply();
    }

    public static void removeUser(Context context) {
        getSharedPreferences(context).edit().remove(USER_PREF_KEY).apply();
    }

    public static boolean isLoggedIn(Context context) {
        return getSharedPreferences(context).getString(USER_PREF_KEY, null) != null;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }
}
