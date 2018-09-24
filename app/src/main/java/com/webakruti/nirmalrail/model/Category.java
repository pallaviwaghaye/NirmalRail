package com.webakruti.nirmalrail.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Akruti on 28-01-2018.
 */

public class Category {
    String categoryName;
    Drawable categoryImage;

    public Category(String categoryName, Drawable categoryImage) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Drawable getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(Drawable categoryImage) {
        this.categoryImage = categoryImage;
    }
}
