package io.gebi.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;

import io.gebi.R;

/**
 * Created by yava on 15/3/24.
 */
public class BaseActivity extends ActionBarActivity {


    private Dialog mProgressDialog;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Dialog showProgressDialog() {
        return showProgressDialog(null);
    }

    public Dialog showProgressDialog(DialogInterface.OnCancelListener onCancelListener) {
        mProgressDialog = new Dialog(this, R.style.ProgressDialogStyle);
        mProgressDialog.setContentView(R.layout.dialog_progress);
        if (onCancelListener != null) {
            mProgressDialog.setOnCancelListener(onCancelListener);
        }
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        return mProgressDialog;
    }

    public void hiddenProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

}
