package io.gebi;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by yava on 15/4/23.
 */
public class Commons {

    public static String getAssetFileContent(Context context, String assetFileName) {
        AssetManager assetManager = context.getAssets();
        InputStream is = null;
        BufferedReader reader = null;
        String lineSeparator = System.getProperty("line.separator");
        try {
            is = assetManager.open(assetFileName);
            reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while (((line = reader.readLine()) != null)) {
                sb.append(line).append(lineSeparator);
            }
            return sb.toString();
        } catch (IOException e) {
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static Uri getImageUri(String image) {
        if (image == null) {
            return null;
        }
        if (!image.startsWith("http")) {
            return Uri.parse(BuildConfig.BASE_URL + "/" + image);
        }
        return Uri.parse(image);
    }

}
