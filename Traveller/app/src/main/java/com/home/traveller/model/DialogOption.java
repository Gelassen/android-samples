package com.home.traveller.model;

import android.content.Intent;
import android.provider.MediaStore;

/**
 * Created by dmitry.kazakov on 10/3/2015.
 */
public class DialogOption {

    public static final int TYPE_CAMERA = 0;
    public static final int TYPE_GALLERY = 1;

    private String name;
    private int type;

    public DialogOption(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Intent getIntent() {
        switch (type) {
            case TYPE_CAMERA:
                Intent takeCameraImageIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                return takeCameraImageIntent;
            case TYPE_GALLERY:
                Intent takeGalleryImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                return takeGalleryImage;
            default:
                throw new IllegalArgumentException("Unsupported type " + type);
        }
    }
}
