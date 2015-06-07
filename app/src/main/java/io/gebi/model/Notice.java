package io.gebi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.gebi.BuildConfig;

/**
 * Created by yava on 15/4/23.
 */
public class Notice implements Parcelable {
    private String id;
    private String title;
    private String content;
    private String image;
    @SerializedName("updatedAt")
    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        if (image != null && !image.startsWith("http")) {
            return BuildConfig.BASE_URL + "/" + image;
        }
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.image);
        dest.writeLong(updatedAt != null ? updatedAt.getTime() : -1);
    }

    public Notice() {
    }

    private Notice(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.image = in.readString();
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
    }

    public static final Parcelable.Creator<Notice> CREATOR = new Parcelable.Creator<Notice>() {
        public Notice createFromParcel(Parcel source) {
            return new Notice(source);
        }

        public Notice[] newArray(int size) {
            return new Notice[size];
        }
    };
}
