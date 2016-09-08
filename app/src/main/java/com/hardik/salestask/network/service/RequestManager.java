package com.hardik.salestask.network.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.hardik.salestask.models.User;
import com.hardik.salestask.network.interfaces.Callback;
import com.hardik.salestask.network.interfaces.OnCompleteListener;
import com.hardik.salestask.network.model.WeData;
import com.hardik.salestask.network.model.WeRequest;
import com.hardik.salestask.network.model.WeResponse;
import com.hardik.salestask.utils.ActivityUtils;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Hardik Shah on 29-07-2015.
 */
public class RequestManager {
    private static final String TAG = RequestManager.class.getSimpleName();

    public static final String BASE_URL = "http://depasserinfotech.in/reporting-manager/api/";

    private static final String ERROR_TITLE = "Error";

    public static final String API_KEY = "47f393cfa2eee339cb33c4361319e2f9";

    private static final String API_LOGIN = "login.php";

    public static Gson getGson() {
        return gson;
    }

    public static final TypeAdapter<Boolean> booleanAsIntAdapter = new TypeAdapter<Boolean>() {
        @Override
        public void write(JsonWriter out, Boolean value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }

        @Override
        public Boolean read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    return in.nextBoolean();
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    return in.nextInt() != 0;
                case STRING:
                    return Boolean.parseBoolean(in.nextString());
                default:
                    throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
            }
        }
    };


    final static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Boolean.class, booleanAsIntAdapter)
            .registerTypeAdapter(boolean.class, booleanAsIntAdapter)
            .create();


    /****************************************************
     * SYNC CALLS.
     ***************************************************/
    public static void loginUser(final Activity context, User user, final OnCompleteListener<WeData> data) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        WeData requestData = new WeData();
        requestData.setUser(user);

        WeRequest request = new WeRequest();
        request.setApi(RequestManager.API_LOGIN);
        request.setData(requestData);


        //Make the call to the server
        NetworkRequest.getInstance().postJsonRequest(RequestManager.BASE_URL, request, new Callback<JSONObject>() {
            @Override
            public void onSuccessResponse(JSONObject response) {

                final WeResponse weResponse = (WeResponse) gson.fromJson(((JSONObject) response).toString(), WeResponse.class);
                String responseString = weResponse.getStatus();

                if ((TextUtils.isEmpty((CharSequence) responseString)) || responseString.equals("error")) {

                    progressDialog.dismiss();
                    ActivityUtils.showErrorDialog(context, weResponse);
                    return;
                } else {
                    //Parse the actual data.
                    progressDialog.dismiss();
                    Log.d(TAG, "Call successful, id = " + weResponse.getData().getUser().getId());
                    if (data != null) {
                        data.onComplete(weResponse.getData());
                    }
                }

            }

            @Override
            public void onErrorResponse(WeResponse response) {
                progressDialog.dismiss();

                if (data != null) {
                    data.onError(response);
                }

                ActivityUtils.showErrorDialog(context, response);
            }
        });
    }

    /*******************************************************
     * PRIVATE METHODS
     *******************************************************/
   /* private static void saveGetData(String uri, String tag) {
        SyncableData syncableData = new SyncableData();
        syncableData.setSyncStatus(DATA_SYNC_STATUS.PENDING.toString());
        syncableData.setMethodType(Request.Method.GET);
        syncableData.setTag(tag);
        syncableData.setUrl(uri);

        AbcdApplication.getSession().insert(syncableData);
        forceSync();
    }

    private static void saveGetData(String uri) {
        saveGetData(uri, null);
    }

    private static void savePostData(String api, WeData requestData, String tag) {
        WeRequest request = new WeRequest();
        request.setApi(api);
        request.setApi_key(API_KEY);
        request.setData(requestData);

        Gson gson = new Gson();
        String requestString = gson.toJson(request);

        SyncableData syncableData = new SyncableData();
        syncableData.setSyncStatus(DATA_SYNC_STATUS.PENDING.name());
        syncableData.setMethodType(Request.Method.POST);
        syncableData.setTag(tag);
        syncableData.setUrl(BASE_WEB_SERVICE_URL);
        syncableData.setCallData(requestString);

        AbcdApplication.getSession().insert(syncableData);
        forceSync();
    }

    private static void savePostData(String api, WeData requestData) {
        savePostData(api, requestData, null);
    }

    public static void forceSync() {
        // Pass the settings flags by inserting them in a bundle
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                *//*
                 * Request the sync for the default account, authority, and
                 * manual sync settings
                 *//*
        ContentResolver.requestSync(AbcdApplication.getInstance().getAccount(), DashboardActivity.AUTHORITY, settingsBundle);
    }*/
}
