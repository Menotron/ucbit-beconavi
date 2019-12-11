package com.ucbits.ble_uart;

import android.app.Application;

import io.mapwize.mapwizeformapbox.AccountManager;

public class UcBitLoader extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AccountManager.start(this, "4011f2d84db736fa6807139d79324eb1");
    }

}
