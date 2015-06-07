package io.gebi.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import io.gebi.R;


/**
 * Created by yava on 15/2/9.
 */
public abstract class BaseProgressActivity extends BaseActivity {

    private View mContentView;
    private View mProgressView;
    private boolean mProgressVisible = false;
    private int mShowNum = 0;
    private FrameLayout mRootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);

        mRootView = new FrameLayout(this);
        mRootView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        setContentView(mRootView);

        mContentView = onCreateContentView(inflater, savedInstanceState);
        mRootView.addView(mContentView);


        mProgressView = inflater.inflate(R.layout.loading, mRootView, false);
        mRootView.addView(mProgressView);

        if (mProgressVisible) {
            mProgressView.setVisibility(View.VISIBLE);
            mContentView.setVisibility(View.GONE);
        } else {
            mProgressView.setVisibility(View.GONE);
            mContentView.setVisibility(View.VISIBLE);
        }
        onViewCreated();
    }

    protected abstract void onViewCreated();


    public abstract View onCreateContentView(LayoutInflater inflater,
                                             Bundle savedInstanceState);

    protected void showProgress() {
        mShowNum++;
        if (mShowNum > 0) {
            mProgressVisible = true;
            if (mProgressView != null) {
                mProgressView.setVisibility(View.VISIBLE);
                mContentView.setVisibility(View.GONE);
            }
        }
    }

    protected void hiddenProgress() {
        mShowNum--;
        if (mShowNum <= 0) {
            mProgressVisible = false;
            if (mProgressView != null) {
                mProgressView.setVisibility(View.GONE);
                mContentView.setVisibility(View.VISIBLE);
            }
        }
    }

//    public Response.ErrorListener getErrorListenerForProgress() {
//        return new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                hiddenProgress();
//                UIUtils.showMsg(BaseProgressActivity.this, volleyError.getLocalizedMessage());
//            }
//        };
//    }

}
