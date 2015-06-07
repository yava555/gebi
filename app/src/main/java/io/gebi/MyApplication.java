package io.gebi;

import android.app.Application;
import android.widget.Toast;

import com.mechat.mechatlibrary.MCClient;
import com.mechat.mechatlibrary.MCUserConfig;
import com.mechat.mechatlibrary.callback.OnInitCallback;
import com.mechat.mechatlibrary.callback.UpdateUserInfoCallback;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by yava on 15/4/14.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        // 初始化美洽SDK
        MCClient.init(this, "552d25204eae35571a000002", new OnInitCallback() {

            @Override
            public void onSuccess(String response) {
                MCUserConfig mcUserConfig = new MCUserConfig();
                Map<String, String> userInfo = new HashMap<String, String>();
                userInfo.put(MCUserConfig.PersonalInfo.REAL_NAME, "test");
                userInfo.put(MCUserConfig.Contact.TEL, "130000000");
                Map<String, String> userInfoExtra = new HashMap<String, String>();
                userInfoExtra.put("门牌号", "601");
                mcUserConfig.setUserInfo(MyApplication.this, userInfo, userInfoExtra, null);
            }

            @Override
            public void onFailed(String response) {
                //Failed
                Toast.makeText(getApplicationContext(), "init MCSDK failed " + response, Toast.LENGTH_SHORT).show();
            }
        });

        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);


    }
}
