package com.lessons.gar.recyclerview;

/**
 * Created by Garik on 25-Oct-18, 10:27
 */
public class ListItemModel {
    private int imgResId;
    private String title;
    private String description;

    ListItemModel(int imgResId, String title, String description) {
        this.imgResId = imgResId;
        this.title = title;
        this.description = description;
    }
    public int getImgResId() {
        return imgResId;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
