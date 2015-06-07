package io.gebi.model;

import java.util.ArrayList;

/**
 * Created by yava on 15/3/24.
 */
public class PhoneGroup {

    private String name;
    private ArrayList<Phone> phoneList;

    public PhoneGroup(String name, ArrayList<Phone> phoneList) {
        this.name = name;
        this.phoneList = phoneList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(ArrayList<Phone> phoneList) {
        this.phoneList = phoneList;
    }
}
