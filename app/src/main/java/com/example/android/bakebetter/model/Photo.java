package com.example.android.bakebetter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Photo {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("width")
    @Expose
    public Integer width;
    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("color")
    @Expose
    public String color;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("urls")
    @Expose
    public PhotoUrls urls;

}
