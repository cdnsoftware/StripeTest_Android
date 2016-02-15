package com.test.shop.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.test.shop.app.ProductListActivity;
import com.test.shop.app.ProductOrderActivity;
import com.test.shop.app.R;
import com.test.shop.app.SignInActivity;
import com.test.shop.app.SignUpActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by akshaysoni on 13/1/16.
 */
public class Utils {

    /**
     * This method is used for validating email id
     */
    public static boolean isValidEmail(CharSequence target) {

        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    /**
     * This method is used for change date formate
     */
    public static String getRequiedformatDate(String serverFormate,
                                              String requiredFormate, String ourDate) {
        DateFormat theDateFormat = new SimpleDateFormat(serverFormate);
        Date date = null;
        String dateStr = "";
        try {
            if (ourDate != null && !ourDate.equals("null")) {
                date = theDateFormat.parse(ourDate);
                theDateFormat = new SimpleDateFormat(requiredFormate);
                dateStr = theDateFormat.format(date).toString();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return dateStr;
    }


    /**
     * Validate string is empty or null
     */
    public static String checkString(String str) {

        if (str != null) {
            if (str.isEmpty()) {
                return null;
            } else {
                if (str.equalsIgnoreCase(" ")) {
                    return null;
                } else {
                    return str;
                }
            }
        } else {
            return str;
        }
    }

    /**
     * Exit alert dialog
     */
    public static void showExitAlert(final Context mContext) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
                (Context) mContext);
        dialogBuilder.setMessage(((Context) mContext).getResources().getString(
                R.string.exitMessage));
        dialogBuilder.setTitle(((Context) mContext).getResources().getString(
                R.string.app_name));
        dialogBuilder.setNegativeButton(((Context) mContext).getResources()
                .getString(R.string.cancel), null);

        dialogBuilder.setPositiveButton(((Context) mContext).getResources()
                .getString(R.string.ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ((Activity) mContext).startActivity(intent);
                if (SignInActivity.instance != null) {
                    SignInActivity.instance.finish();
                }
                if (SignUpActivity.instance != null) {
                    SignUpActivity.instance.finish();
                }
                ((Activity) mContext).onBackPressed();
                System.exit(0);

            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    /**
     * logout from success
     */
    public static void logout(Activity acitivity) {
        SharedPrefrence.getInstance(acitivity).writeBooleanPrefs(SharedPrefrence.IS_LOGIN, false);
        acitivity.startActivity(new Intent(acitivity, SignInActivity.class));
        acitivity.finish();
        if (ProductOrderActivity.instance != null) {
            ProductOrderActivity.instance.finish();
        }
        if (ProductListActivity
                .instance != null) {
            ProductListActivity
                    .instance.finish();
        }
    }


    public static boolean isValidCardNumber(String mNumber) {
        if (mNumber.length() != 16 && (mNumber.length() < 12)) {
            return false;
        }
        return true;
    }

    public static boolean isValidCvvNumber(String mCvvNumber) {
        if (mCvvNumber.length() > 4 && (mCvvNumber.length() < 3)) {
            return false;
        }
        return true;
    }
}