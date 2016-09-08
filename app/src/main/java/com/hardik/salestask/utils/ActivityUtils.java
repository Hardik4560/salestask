package com.hardik.salestask.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.hardik.salestask.network.model.WeResponse;

import java.net.HttpURLConnection;

public class ActivityUtils {

    public static float convertPixelsToDp(Context context, float px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static void showMessageDialog(Context context, String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(message);
        dialog.setTitle(title);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }

    public static void showErrorDialog(Context context, WeResponse response) {
        int errorCode = -1;
        String message = "Something is not in place";

        if (response.getMessage() != null) {
            errorCode = response.getError_code();
            message = response.getMessage();
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        switch (errorCode) {
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
            case HttpURLConnection.HTTP_BAD_REQUEST:
            case HttpURLConnection.HTTP_RESET:
            case HttpURLConnection.HTTP_UNAVAILABLE:
            case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
            default:
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                break;

        }

        dialog.setMessage(message);
        dialog.setTitle("Info");
        dialog.setCancelable(false);
        dialog.create();
        dialog.show();
    }
}
