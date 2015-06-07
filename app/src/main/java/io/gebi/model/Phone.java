package io.gebi.model;

/**
 * Created by yava on 15/3/24.
 */
public class Phone {

    private String name;
    private String description;
    private String phone;
    private int imageRes;
    private String imageUrl;

    public Phone(String name, String description, String phone, int imageRes) {
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.imageRes = imageRes;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
