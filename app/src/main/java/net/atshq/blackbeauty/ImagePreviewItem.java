package net.atshq.blackbeauty;

import android.graphics.Bitmap;

public class ImagePreviewItem {
    private String name;
    private Bitmap bitmap;
    private boolean isSelect;

    public ImagePreviewItem(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
        isSelect=false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
