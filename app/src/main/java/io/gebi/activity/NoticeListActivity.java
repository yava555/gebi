package io.gebi.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.DraweeView;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.gebi.API;
import io.gebi.APIBuilder;
import io.gebi.BuildConfig;
import io.gebi.Commons;
import io.gebi.R;
import io.gebi.TimeUtils;
import io.gebi.model.Notice;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by yava on 15/3/24.
 */
public class NoticeListActivity extends BaseProgressActivity {

    private ListView mListView;
    private NoticeAdapter mAdapter;

    public static void start(BaseActivity activity) {
        Intent intent = new Intent(activity, NoticeListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onViewCreated() {
        loadData();
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_listview, null, false);
        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapter != null) {
                    Notice notice = (Notice) mAdapter.getItem(position);
                    NoticeDetailActivity.start(NoticeListActivity.this, notice);
                }
            }
        });
        return view;
    }

    private void loadData() {
        showProgress();
        APIBuilder.create().notcieList(new Callback<ArrayList<Notice>>() {
            @Override
            public void success(ArrayList<Notice> notices, Response response) {
                hiddenProgress();
                if (notices != null && notices.size() > 0) {
                    mAdapter = new NoticeAdapter(NoticeListActivity.this, notices);
                    mListView.setAdapter(mAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                hiddenProgress();
            }
        });

    }


    private class NoticeAdapter extends BaseAdapter {
        private final Context context;
        private final LayoutInflater layoutInflater;
        private final ArrayList<Notice> noticeList;

        public NoticeAdapter(Context context, ArrayList<Notice> noticeList) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.noticeList = noticeList;
        }


        @Override
        public int getCount() {
            if (noticeList == null) {
                return 0;
            } else {
                return noticeList.size();
            }

        }

        @Override
        public Object getItem(int position) {
            return noticeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.item_notice, parent, false);

            DraweeView imageView = (DraweeView) view.findViewById(R.id.imageView);
            TextView titleText = (TextView) view.findViewById(R.id.title_text);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);

            Notice notice = (Notice) getItem(position);
            titleText.setText(notice.getTitle());
            dateText.setText(TimeUtils.humanTime(notice.getUpdatedAt().getTime()));
            Uri imageUri = Commons.getImageUri(notice.getImage());
            if (imageUri != null) {
                imageView.setImageURI(imageUri);
            }

            return view;
        }
    }
}
