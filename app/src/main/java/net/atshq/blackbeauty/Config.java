package net.atshq.blackbeauty;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Config {
    public static final int GRAY=1;
    public static final int THRESH=2;
    public static final int EnhancedContrastImage=3;
    public static final int SharpnessImage=4;
    //Bitmap to matrix convert
    protected static Mat bitmapToMatBnW(Bitmap bitmap){
        Mat mat = new Mat();
        Bitmap bmp32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bmp32, mat);
        Utils.bitmapToMat(bitmap,mat);

        return mat;
    }


    public static Mat bitmapToMat(Bitmap bitmap){
        //bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
        Mat mat=new Mat(bitmap.getWidth(),bitmap.getHeight(), CvType.CV_8UC1);
        Utils.bitmapToMat(bitmap,mat);

        return mat;
    }
}
