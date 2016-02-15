package com.test.shop.app;

import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;

/**
 * Created by nilesh patel on 12/02/16
 */
public class ShopTestApp extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);

    }

}
