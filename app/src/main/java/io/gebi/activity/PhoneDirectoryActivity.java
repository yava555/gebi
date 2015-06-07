package io.gebi.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import io.gebi.Project;
import io.gebi.R;
import io.gebi.model.Phone;
import io.gebi.model.PhoneGroup;

/**
 * Created by yava on 15/3/24.
 */
public class PhoneDirectoryActivity extends BaseActivity {

    private ExpandableListView mListView;
    private PhoneAdapter mAdapter;

    public static void start(BaseActivity activity) {
        Intent intent = new Intent(activity, PhoneDirectoryActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phone_directory);

        mListView = (ExpandableListView) findViewById(R.id.listview);
        mAdapter = new PhoneAdapter(getBaseContext(), Project.getPhoneListData());
        mListView.setAdapter(mAdapter);

        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
            mListView.expandGroup(i);
        }

        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Phone phone = (Phone) mAdapter.getChild(groupPosition, childPosition);
                if (phone != null) {
                    callPhone(phone.getPhone());
                }
                return true;
            }
        });
    }

    private void callPhone(String phone) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("tel:" + phone));
        startActivity(i);
    }



    private class PhoneAdapter extends BaseExpandableListAdapter {
        private final Context context;
        private final LayoutInflater layoutInflater;
        private final ArrayList<PhoneGroup> phoneData;

        public PhoneAdapter(Context context, ArrayList<PhoneGroup> phoneData) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.phoneData = phoneData;
        }


        @Override
        public int getGroupCount() {
            if (phoneData == null) {
                return 0;
            }
            return phoneData.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (phoneData == null) {
                return 0;
            }
            PhoneGroup phoneGroup = phoneData.get(groupPosition);
            return phoneGroup.getPhoneList() == null ? 0 : phoneGroup.getPhoneList().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return phoneData.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            PhoneGroup phoneGroup = phoneData.get(groupPosition);
            return phoneGroup.getPhoneList().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.group_phone_directory, parent, false);
            TextView textView = (TextView) view.findViewById(R.id.group_name_text);

            PhoneGroup phoneGroup = (PhoneGroup) getGroup(groupPosition);
            if (TextUtils.isEmpty(phoneGroup.getName())) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setVisibility(View.VISIBLE);
                textView.setText(phoneGroup.getName());
            }

            return view;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View view = layoutInflater.inflate(R.layout.item_phone_directory, parent, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
            TextView nameTextView = (TextView) view.findViewById(R.id.name_text);
            TextView descriptionTextView = (TextView) view.findViewById(R.id.description_text);

            Phone phone = (Phone) getChild(groupPosition, childPosition);
            imageView.setImageResource(phone.getImageRes());
            nameTextView.setText(phone.getName());
            if (TextUtils.isEmpty(phone.getDescription())) {
                descriptionTextView.setText(phone.getPhone());
            } else {
                descriptionTextView.setText(phone.getDescription() + " " + phone.getPhone());
            }
            return view;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
