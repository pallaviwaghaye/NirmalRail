package com.webakruti.nirmalrail.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.webakruti.nirmalrail.model.AdminLoginSuccess;
import com.webakruti.nirmalrail.model.UserResponse;


/**
 * Manages the shared preferences all over the application
 */
public class SharedPreferenceManager {
    private static Context applicationContext;
    private static SharedPreferences tuitionPlusPreferences;
    public static void setApplicationContext(Context context) {
        applicationContext = context;
        setPreferences();
    }

    private static void setPreferences() {
        if (tuitionPlusPreferences == null) {
            tuitionPlusPreferences = applicationContext.getSharedPreferences("niramlrail",
                    Context.MODE_PRIVATE);
        }
    }

    public static void clearPreferences() {
        tuitionPlusPreferences.edit().clear().commit();
    }



    public static void storeUserResponseObjectInSharedPreference(UserResponse user) {
        SharedPreferences.Editor prefsEditor = tuitionPlusPreferences.edit();
        //  prefsEditor.clear();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("UserResponseObject", json);
        prefsEditor.commit();
    }

    public static UserResponse getUserObjectFromSharedPreference() {
        Gson gson1 = new Gson();
        String json1 = tuitionPlusPreferences.getString("UserResponseObject", "");
        UserResponse obj = gson1.fromJson(json1, UserResponse.class);
//		Log.e("RetrivedName:", obj.getFirstName());
        return obj;
    }


    public static void storeAdminResponseObjectInSharedPreference(AdminLoginSuccess adminLoginSuccess) {
        SharedPreferences.Editor prefsEditor = tuitionPlusPreferences.edit();
        //  prefsEditor.clear();
        Gson gson = new Gson();
        String json = gson.toJson(adminLoginSuccess);
        prefsEditor.putString("AdminResponseObject", json);
        prefsEditor.commit();
    }

    public static AdminLoginSuccess getAdminObjectFromSharedPreference() {
        Gson gson1 = new Gson();
        String json1 = tuitionPlusPreferences.getString("AdminResponseObject", "");
        AdminLoginSuccess obj = gson1.fromJson(json1, AdminLoginSuccess.class);
//		Log.e("RetrivedName:", obj.getFirstName());
        return obj;
    }

}
