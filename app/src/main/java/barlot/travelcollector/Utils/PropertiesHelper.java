package barlot.travelcollector.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import barlot.travelcollector.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Properties;

public class PropertiesHelper {
    private static final String TAG = "PropertiesHelper";

    public static String getConfigValue(Context context, String name) {
        return getConfigValue(context, name, null);
    }

    public static String getConfigValue(Context context, String name, String defaults) {
        Resources resources = context.getResources();

        try {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            return properties.getProperty(name);
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Unable to find the config file: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Failed to open config file.");
        }

        return defaults;
    }

    public static String getMessagesValue(Context context, String name) {
        return getMessagesValue(context, name, null);
    }

    public static String getMessagesValue(Context context, String name, String defaults) {
        Resources resources = context.getResources();

        try {

            InputStream rawResource = resources.openRawResource(R.raw.messages);
            Properties properties = new Properties();
            properties.load(new InputStreamReader(rawResource, Charset.forName("UTF-8")));
            return properties.getProperty(name);
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Unable to find the config file: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Failed to open config file.");
        }

        return defaults;
    }


}