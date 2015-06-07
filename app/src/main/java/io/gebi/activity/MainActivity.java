package io.gebi.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mechat.mechatlibrary.MCClient;
import com.mechat.mechatlibrary.MCOptions;

import io.gebi.R;


public class MainActivity extends BaseActivity {

    private GridView mGridView;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        Drawable indicator = getResources().getDrawable(R.drawable.icon_home);
        indicator.setColorFilter(0xffffffff, PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(indicator);

        mGridView = (GridView) findViewById(R.id.gridview);
        mAdapter = new MainAdapter(this);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) mAdapter.getItem(position);
                if (getString(R.string.item_broadcast).equals(item)) {
                    NoticeListActivity.start(MainActivity.this);
                } else if (getString(R.string.item_phone_directory).equals(item)) {
                    PhoneDirectoryActivity.start(MainActivity.this);
                } else if (getString(R.string.item_fix).equals(item)) {
                    //启动对话界面
                    MCOptions options = new MCOptions(MainActivity.this);
                    options.setShowAgentJoinEvent(false); //是否显示客服加入聊天事件
                    options.setShowVoiceMessage(true); //是否启用语音消息
                    MCClient.getInstance().startMCConversationActivity(null);
                } else if (getString(R.string.item_secondhand).equals(item)) {
                    GoodsListActivity.start(MainActivity.this);
                }
            }
        });
    }

    public static class MainAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private Context context;

        public MainAdapter(Context context) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        private int[] names = new int[]{R.string.item_broadcast, R.string.item_phone_directory, R.string.item_fix, R.string.item_takeout,
                R.string.item_secondhand, R.string.item_bbs};
        private int[] icons = new int[]{R.drawable.icon_broadcast, R.drawable.icon_phone_directory, R.drawable.icon_fix, R.drawable.icon_takeout,
                R.drawable.icon_sencondhand, R.drawable.icon_bbs};

        public MainAdapter() {
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return context.getString(names[position]);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.item_main, parent, false);
            TextView textView = (TextView) view.findViewById(R.id.text);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageview);

            String name = context.getString(names[position]);
            int iconRes = icons[position];
            textView.setText(name);
            Drawable drawable = context.getResources().getDrawable(iconRes);
            drawable.setColorFilter(context.getResources().getColor(R.color.color_primary), PorterDuff.Mode.SRC_ATOP);
            imageView.setImageDrawable(drawable);

            return view;
        }
    }

}
