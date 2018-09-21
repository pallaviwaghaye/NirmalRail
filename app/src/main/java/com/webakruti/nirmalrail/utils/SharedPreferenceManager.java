package com.webakruti.nirmalrail.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;


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
            tuitionPlusPreferences = applicationContext.getSharedPreferences("tuitionplusRef",
                    Context.MODE_PRIVATE);
        }
    }

    public static void clearPreferences() {
        tuitionPlusPreferences.edit().clear().commit();
    }


    public static void saveWorkspace(String workspace) {
        SharedPreferences.Editor prefsEditor = tuitionPlusPreferences.edit();
        prefsEditor.putString("Workspace", workspace);
        Log.e("SavedWorkspace:", workspace);
        prefsEditor.commit();
    }

    public static String getWorkspace() {
        String workspace = tuitionPlusPreferences.getString("Workspace", null);
        Log.e("RetrievedWorkspace:", workspace);
        return workspace;
    }

    public static void saveToken(String token) {
        SharedPreferences.Editor prefsEditor = tuitionPlusPreferences.edit();
        prefsEditor.putString("Token", token);
        Log.e("SavedWorkspace:", token);
        prefsEditor.commit();
    }

    public static String getToken() {
        String token = tuitionPlusPreferences.getString("Token", null);
       // Log.e("RetrievedToken:", success);
        return token;
    }

   /* public static void storeUserObjectInSharedPreference(Student user) {
        SharedPreferences.Editor prefsEditor = tuitionPlusPreferences.edit();
        //  prefsEditor.clear();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("UserObject", json);
        prefsEditor.commit();
    }

    public static Student getUserObjectFromSharedPreference() {
        Gson gson1 = new Gson();
        String json1 = tuitionPlusPreferences.getString("UserObject", "");
        Student obj = gson1.fromJson(json1, Student.class);
//		Log.e("RetrivedName:", obj.getFirstName());
        return obj;
    }*/


}
