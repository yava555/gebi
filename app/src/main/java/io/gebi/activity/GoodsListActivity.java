package io.gebi.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.DraweeView;

import java.util.ArrayList;

import io.gebi.APIBuilder;
import io.gebi.Commons;
import io.gebi.R;
import io.gebi.model.Goods;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by yava on 15/3/24.
 */
public class GoodsListActivity extends BaseProgressActivity {

    private ListView mListView;
    private GoodsAdapter mAdapter;
    private static final int REQUEST_POST = 1;

    public static void start(BaseActivity activity) {
        Intent intent = new Intent(activity, GoodsListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onViewCreated() {
        loadData();
    }

    private void loadData() {
        showProgress();
        APIBuilder.create().goodsList(new Callback<ArrayList<Goods>>() {
            @Override
            public void success(ArrayList<Goods> goodsList, Response response) {
                hiddenProgress();
                mAdapter = new GoodsAdapter(getBaseContext(), goodsList);
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                hiddenProgress();
            }

        });
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_listview, null, false);
        mListView = (ListView) view.findViewById(R.id.listview);
        return view;
    }


    private class GoodsAdapter extends BaseAdapter {
        private final Context context;
        private final LayoutInflater layoutInflater;
        private final ArrayList<Goods> goodsList;

        public GoodsAdapter(Context context, ArrayList<Goods> goodsList) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.goodsList = goodsList;
        }


        @Override
        public int getCount() {
            if (goodsList == null) {
                return 0;
            } else {
                return goodsList.size();
            }

        }

        @Override
        public Object getItem(int position) {
            return goodsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.item_goods, parent, false);

            DraweeView imageView = (DraweeView) view.findViewById(R.id.imageView);
            TextView descriptionText = (TextView) view.findViewById(R.id.description_text);

            Goods goods = (Goods) getItem(position);
            descriptionText.setText(goods.getDescription());

            Uri imageUri = Commons.getImageUri(goods.getFirstImageUrl());
            if (imageUri != null) {
                imageView.setImageURI(imageUri);
            }

            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_goods, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                AddGoodsActivity.startForResult(GoodsListActivity.this, REQUEST_POST);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_POST) {
            loadData();
        }
    }
}
