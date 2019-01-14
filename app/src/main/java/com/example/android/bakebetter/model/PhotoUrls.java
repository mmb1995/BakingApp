package com.example.android.bakebetter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoUrls {

    @SerializedName("raw")
    @Expose
    public String raw;
    @SerializedName("full")
    @Expose
    public String full;
    @SerializedName("regular")
    @Expose
    public String regular;
    @SerializedName("small")
    @Expose
    public String small;
    @SerializedName("thumb")
    @Expose
    public String thumb;

}
