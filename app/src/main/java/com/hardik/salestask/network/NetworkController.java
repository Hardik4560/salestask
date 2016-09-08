
package com.hardik.salestask.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Class that handles the networking mechanism of the application. You should {@link NetworkController#initialize(Context)} in your
 * application class before using any of the methods of this class.
 *
 * @author Hardik
 */
final public class NetworkController {
    private static final String TAG = NetworkController.class.getSimpleName();
    private static final int DEFAULT_TIME_OUT = 60 * 1000;

    private RequestQueue mRequestQueue;
    private static NetworkController mNetworkController;
    private Context mContext;

    private DefaultRetryPolicy defaultRetryPolicy;

    private NetworkController(Context context) {
        this.mContext = context;
        defaultRetryPolicy = new DefaultRetryPolicy(DEFAULT_TIME_OUT, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    public static NetworkController initialize(Context context) {
        if (mNetworkController == null) {
            mNetworkController = new NetworkController(context);
        }
        return mNetworkController;
    }

    public static NetworkController getInstance() {
        if (mNetworkController == null) {
            throw new RuntimeException(NetworkController.class + " not initialized");
        }
        return mNetworkController;
    }

    public synchronized RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(com.android.volley.Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(com.android.volley.Request<T> req, RetryPolicy retryPolicy) {
        req.setRetryPolicy(retryPolicy);

        // set the default tag if tag is empty
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(com.android.volley.Request<T> req) {
        // set the default tag if tag is empty
        addToRequestQueue(req, defaultRetryPolicy);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * Gets the state of Airplane Mode.
     *
     * @param context
     * @return true if enabled.
     */
    public static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;

    }

    // Check network connection
    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getQueryParams(LinkedHashMap<String, String> params) {
        StringBuilder queryParam = new StringBuilder();
        queryParam.append("?");
        Set<String> keys = params.keySet();
        String equals = "=";
        String and = "&";

        //http://java.boot.by/customers?zip=220119

        int i = 0;
        Iterator<String> keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String type = (String) keyIterator.next();
            String name = params.get(type);
            queryParam.append(type);
            queryParam.append(equals);
            queryParam.append(URLEncoder.encode(name));
            if (i < keys.size() - 1) {
                queryParam.append(and);
            }
            i++;
        }
        return queryParam.toString();
    }

    /**
     * This method create path params based on the key value pair from passed Linked map.
     * The key over here is the tag in the url and the value is the value of the tag of the url.
     * NOTE: it append '/' before the string.
     *
     * @param params - The hashMap with the key value.
     * @return string PathParams
     * @author Hardik Shah
     */
    public static String getPathParams(LinkedHashMap<String, String> params) {
        StringBuilder pathParam = new StringBuilder();

        Set<String> keySet = params.keySet();
        String slash = "/";
        // '/getBidsForProbableTaskMaster/3/task/2'

        int i = 0;
        Iterator<String> keyIterator = keySet.iterator();
        while (keyIterator.hasNext()) {
            String key = (String) keyIterator.next();
            String valueForKey = params.get(key);
            if (!key.equals("")) {
                pathParam.append(slash);
            }
            pathParam.append(key);
            pathParam.append(slash);
            pathParam.append(valueForKey);
            i++;
        }
        return pathParam.toString();
    }

}
