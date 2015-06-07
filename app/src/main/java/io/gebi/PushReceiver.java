package io.gebi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import io.gebi.activity.NoticeDetailActivity;

/**
 * Created by yava on 15/5/6.
 */
public class PushReceiver extends BroadcastReceiver {
    private String TAG = "push";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
            try {
                JSONObject jsonObj = new JSONObject(json);
                Intent i = new Intent(context, NoticeDetailActivity.class);
                i.putExtra(NoticeDetailActivity.EXTRA_NOTICE_ID, jsonObj.getString("id"));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            } catch (JSONException e) {
            }


        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }
}
