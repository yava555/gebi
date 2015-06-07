package io.gebi.model;

import java.util.Date;

/**
 * Created by yava on 15/4/14.
 */
public class Goods {

    private String name;
    private String[] images;
    private String description;
    private Date updateAt;
    private String price;


    public String getFirstImageUrl() {
        if (images == null || images.length == 0) {
            return null;
        }
        return images[0];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
