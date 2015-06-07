package io.gebi;

import java.util.ArrayList;
import java.util.List;

import io.gebi.model.Goods;
import io.gebi.model.Notice;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by yava on 15/4/23.
 */
public interface API {

    @GET("/notice")
    void notcieList(Callback<ArrayList<Notice>> callback);

    @GET("/goods")
    void goodsList(Callback<ArrayList<Goods>> callback);

    @Multipart
    @POST("/goods")
    void postGoods(@Part("images") TypedFile image1, @Part("images") TypedFile image2, @Part("images") TypedFile image3, @Part("images") TypedFile image4, @Query("name") String name, @Query("description") String description, @Query("price") String price, Callback<Goods> callback);

}
