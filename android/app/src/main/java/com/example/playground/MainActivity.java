package com.example.playground;

import java.io.IOException;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.app.Activity;
import android.os.Bundle;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.view.View;

import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;



public class MainActivity extends FlutterActivity {
	private static final String CHANNEL = "samples.flutter.io/battery";

	Bitmap bitmap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

	new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(
		new MethodCallHandler() {
			@Override
			public void onMethodCall(MethodCall call, Result result) {
				if (call.method.equals("getBatteryLevel")) {
					int batteryLevel = getBatteryLevel();

					if (batteryLevel != -1) {
						result.success(batteryLevel);
					} else {
						result.error("UNAVAILABLE", "Battery level not available.", null);
					}
				} else if(call.method.equals("setWallpaper")) {
					boolean changed = changeWallpaper();
					if(changed){
						result.success("Nice");						
					} else {
						result.error("UNAVAILABLE", "Cant change the wallpaper", null);
					}
				} else {
					result.notImplemented();
				}
			}
		});
  }

    private int getBatteryLevel() {
		int batteryLevel = -1;
		if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
			BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
			batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
		} else {
			Intent intent = new ContextWrapper(getApplicationContext()).
				registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
			batteryLevel = (intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100) /
				intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		}

		return batteryLevel;
	}

	private boolean changeWallpaper() {
		WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
		try {
			myWallpaperManager.setResource(R.drawable.graphic);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
