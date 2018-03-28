package edu.gatech.mhiggins36.homeless_shelter_app;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.test.IsolatedContext;
import android.test.mock.MockContext;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by mhigg on 2/21/2018.
 */

import java.util.List;
import java.util.Map;

import edu.gatech.mhiggins36.homeless_shelter_app.activities.DashboardActivity;
import edu.gatech.mhiggins36.homeless_shelter_app.activities.SearchActivity;
import edu.gatech.mhiggins36.homeless_shelter_app.models.Shelter;
import edu.gatech.mhiggins36.homeless_shelter_app.models.User;


public class Controller {

    public static HashMap<String, User> userMap = new HashMap<>();
    public static HashMap<String, Shelter> shelterMap = new HashMap<>();
    public static List<Shelter> shelterList;
//    public static User currentUser;

    public static void createMapFromcsv(List<String[]> list) {
        Log.d("csv", "here");
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).length; j++) {
                Log.d("csv", list.get(i)[j]);
            }
            Log.d("csv", "---------------------------------------------------");
            int uniqueKey = Integer.parseInt(list.get(i)[0]);
            String name = list.get(i)[1].trim();
            String capacity = list.get(i)[2];
            String restrictions = list.get(i)[3];
            float longitude = Float.parseFloat(list.get(i)[4]);
            float lattitude = Float.parseFloat(list.get(i)[5]);
            String address = list.get(i)[6];
            String notes = list.get(i)[7];
            String phone = list.get(i)[8];

            shelterMap.put(name, new Shelter(uniqueKey, name, capacity,
                    restrictions, longitude, lattitude, address, notes, phone));


        }
    }




    public static void addUser(User newUser) {
        userMap.put(newUser.getEmail(), newUser);
    }
}
