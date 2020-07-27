package com.example.share_example;

import android.os.Bundle;

import com.example.share.*;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        GeneratedPluginRegistrant.registerWith(this);
        registerCustomPlugin(this);
    }

    private static void registerCustomPlugin(PluginRegistry registrar) {

        SharePlugin.registerWith(registrar.registrarFor(SharePlugin.CHANNEL));

    }



}
