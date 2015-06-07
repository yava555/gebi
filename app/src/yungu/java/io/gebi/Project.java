package io.gebi;

import java.util.ArrayList;

import io.gebi.model.Phone;
import io.gebi.model.PhoneGroup;

/**
 * Created by yava on 15/3/27.
 */
public class Project {

    public static ArrayList<PhoneGroup> getPhoneListData() {

        ArrayList<PhoneGroup> phoneGroupList = new ArrayList<PhoneGroup>();
        ArrayList<Phone> list = new ArrayList<>();
        list.add(new Phone("技术支持", "www.gebi.io", "18600835163", R.drawable.icon_support));
        list.add(new Phone("泰山云谷", "联系电话", "0538-5050288", R.drawable.icon_property));
        phoneGroupList.add(new PhoneGroup(null, list));

        ArrayList<Phone> list1 = new ArrayList<>();
        list1.add(new Phone("顺丰快递", "史存来", "13406225030", R.drawable.icon_delivery_sf));
        list1.add(new Phone("中通快递", "周光华", "15269838123", R.drawable.icon_delivery_zto));
        list1.add(new Phone("韵达快递", "韵达", "13053869808", R.drawable.icon_delivery_yunda));
        list1.add(new Phone("圆通快递", "圆通", "18353879912", R.drawable.icon_delivery_yt));
        list1.add(new Phone("天天快递", "任绪伟", "15153834042", R.drawable.icon_delivery_tiantian));
        list1.add(new Phone("优速快递", "优速", "05386521881", R.drawable.icon_delivery_yousu));
        phoneGroupList.add(new PhoneGroup("快递", list1));

        ArrayList<Phone> list2 = new ArrayList<>();
        list2.add(new Phone("泰安往返新泰", "", "13562801119", R.drawable.icon_car));
        list2.add(new Phone("泰安往返新泰", "", "6633910", R.drawable.icon_car));
        list2.add(new Phone("泰安往返济南", "", "13562801119", R.drawable.icon_car));
        list2.add(new Phone("泰安全代驾服务", "", "18263892266", R.drawable.icon_car));
        list2.add(new Phone("废品回收", "", "18954863910", R.drawable.icon_recycling));
        phoneGroupList.add(new PhoneGroup("其它", list2));
        return phoneGroupList;
    }
}
