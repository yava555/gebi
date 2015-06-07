package io.gebi.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.HashMap;

import io.gebi.BuildConfig;
import io.gebi.model.Notice;

/**
 * Created by yava on 15/5/6.
 */
public class NoticeDetailActivity extends BaseActivity {
    public static final String EXTRA_NOTICE_ID = "notice_id";
    private WebView mWebView;

    public static void start(Activity activity, Notice notice) {
        Intent intent = new Intent(activity, NoticeDetailActivity.class);
        intent.putExtra(EXTRA_NOTICE_ID, notice.getId());
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String noticeId = getIntent().getStringExtra(EXTRA_NOTICE_ID);

        mWebView = new WebView(this);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        String url = BuildConfig.BASE_URL + "/notice/view/" + noticeId;
        mWebView.loadUrl(url);

        setContentView(mWebView);
    }
}
