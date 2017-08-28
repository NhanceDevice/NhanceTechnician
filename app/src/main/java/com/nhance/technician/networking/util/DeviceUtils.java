package com.nhance.technician.networking.util;

import android.content.Context;
import android.os.Build;

import com.nhance.technician.logger.LOG;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DeviceUtils {
	public static final String TAG = DeviceUtils.class.getName();
	private static DeviceUtils instance;
	private boolean isKindleFire = false;

	public boolean isKindleFire() {
		return isKindleFire;
	}

	public static DeviceUtils getInstance() {
		if (instance == null) {
			instance = new DeviceUtils();
		}
		return instance;
	}

	private DeviceUtils() {
		isKindleFire = Build.MODEL.equalsIgnoreCase("kindle fire");
	}

	public String getDeviceName(Context context) {
		String manufacturer = Build.MANUFACTURER;
		String undecodedModel = Build.MODEL;
		String model = null;

		try {
			Properties prop = new Properties();
			InputStream fileStream;
			// Read the device name from a precomplied list:
			// see
			// http://making.meetup.com/post/29648976176/human-readble-android-device-names
			fileStream = context.getAssets().open("android_models.properties");
			prop.load(fileStream);
			fileStream.close();
			String decodedModel = prop.getProperty(undecodedModel.replaceAll(" ", "_"));
			if (decodedModel != null && !decodedModel.trim().equals("")) {
				model = decodedModel;
			}
		} catch (IOException e) {
			LOG.e(TAG, e.getMessage());
		}

		if (model == null) { // Device model not found in the list
			if (undecodedModel.startsWith(manufacturer)) {
				model = capitalize(undecodedModel);
			} else {
				model = capitalize(manufacturer) + " " + undecodedModel;
			}
		}
		return model;
	}

	private String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}
}