package com.openxc.openxcdiagnostic.diagnostic;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.LinearLayout;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.openxc.messages.DiagnosticRequest;
import com.openxc.openxcdiagnostic.R;

public class DiagnosticFavoritesManager {

    private DiagnosticActivity mContext;
    private SharedPreferences mPreferences;
    private ArrayList<DiagnosticRequest> favorites = loadFavorites();
    
    public DiagnosticFavoritesManager(DiagnosticActivity context) {
        mContext = context;
    }
        
    public void showAlert() {
        
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LinearLayout settingsLayout = (LinearLayout) mContext.getLayoutInflater().inflate(R.layout.diagfavoritesalert, null);
        
        fill(settingsLayout);
        builder.setView(settingsLayout);

        builder.setTitle(mContext.getResources().getString(R.string.favorites_alert_label));
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.create().show();
        
    }
    
    private void fill(View parent) {
        
    }
    
    private void set() {
        Editor prefsEditor = mPreferences.edit();
        ArrayList<DiagnosticRequest> favs = new ArrayList<>();
        String json = (new Gson()).toJson(favs);
        prefsEditor.putString(getFavoritesKey(), json);
        prefsEditor.commit();
    }
    
    private ArrayList<DiagnosticRequest> loadFavorites() {
        Type type = new TypeToken<List<DiagnosticRequest>>(){}.getType();
        String json = mPreferences.getString(getFavoritesKey(), "");
        List<DiagnosticRequest> favoriteList = (new Gson()).fromJson(json, type);
        return new ArrayList<>(favoriteList);
    }
    
    private String getFavoritesKey() {
        return mContext.getResources().getString(R.string.favorites_key);
    }
    
}
