package com.upv.magicplace.detail.entities;

import java.io.Serializable;

public class FavoritePhotoModel implements Serializable{

    String url;
    String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
