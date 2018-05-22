package net.atshq.blackbeauty;

import android.graphics.Bitmap;

public class ImagePreviewItem {
    private String name;
    private Bitmap bitmap;
    private int filterCode;
    private boolean isSelect;

    public ImagePreviewItem(String name, Bitmap bitmap,int filterCode) {
        this.name = name;
        this.bitmap = bitmap;
        this.filterCode=filterCode;
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

    public int getFilterCode() {
        return filterCode;
    }

    public void setFilterCode(int filterCode) {
        this.filterCode = filterCode;
    }
}
