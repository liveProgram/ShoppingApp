package com.developer.live.shoppingapp.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.developer.live.shoppingapp.constants.UnchangeableData;

public class LocalStorage {
    private SharedPreferences sp;
    private SharedPreferences.Editor spEdit;

    public LocalStorage(Context con){
        sp = PreferenceManager.getDefaultSharedPreferences(con);
        spEdit = sp.edit();
    }

    public void storeLogin(String email){
        spEdit.putBoolean(UnchangeableData.LOGGED, true);
        spEdit.putString(UnchangeableData.EMAIL, email);
        spEdit.commit();
    }

    public String getDetails(String key){
        return sp.getString(key, "");
    }

    public boolean IsLogged(){
        return sp.getBoolean(UnchangeableData.LOGGED, false);
    }

    public void clearEverything(){
        spEdit.clear();
        //spEdit.remove(Key);  // for removing or deleting any particular data
        spEdit.commit();
    }
}
