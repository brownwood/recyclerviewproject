package com.globalsoftng.recyclerviewproject;

/**
 * Created by Adebowale.Odulaja on 11/8/2017.
 */

public class ListItem {

    private String head, desc, imageURL;

    public ListItem(String head, String desc, String imageURL) {
        this.head = head;
        this.desc = desc;
        this.imageURL = imageURL;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageURL() {
        return imageURL;
    }
}
