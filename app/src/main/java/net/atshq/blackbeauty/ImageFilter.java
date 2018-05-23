package net.atshq.blackbeauty;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static net.atshq.blackbeauty.Config.bitmapToMat;
import static net.atshq.blackbeauty.Config.bitmapToMatBnW;

public class ImageFilter {

    protected static Bitmap image;
    protected Bitmap copyBitmap;

    public ImageFilter(Bitmap image) {
        this.image = image;
    }

  public Bitmap Filter(int FILTER){
        switch (FILTER){
            case Config.GRAY:
                copyBitmap=Bitmap.createScaledBitmap(image,image.getWidth(),image.getHeight(),true);
                return GrayscaleImage(copyBitmap);
            case Config.THRESH:
                copyBitmap=Bitmap.createScaledBitmap(image,image.getWidth(),image.getHeight(),true);
                return ThreshImage(copyBitmap,Imgproc.THRESH_TRUNC);

            case Config.EnhancedContrastImage:
                copyBitmap=Bitmap.createScaledBitmap(image,image.getWidth(),image.getHeight(),true);
                return ColorMapImage(copyBitmap,Imgproc.COLORMAP_HOT);
            case Config.SharpnessImage:
                copyBitmap=Bitmap.createScaledBitmap(image,image.getWidth(),image.getHeight(),true);
                return SharpnessImage(copyBitmap);

                default:
                    return image;

        }
  }

    //gray-1
    protected static   Bitmap GrayscaleImage(Bitmap bitmap){
        Mat src= bitmapToMatBnW(bitmap);
        Mat dst = new Mat(bitmap.getWidth(),bitmap.getHeight(),CvType.CV_8UC1);
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);
        Utils.matToBitmap(dst,bitmap);
        return bitmap;
    }

    //Thresh image
    protected  Bitmap ThreshImage(Bitmap bitmap,int Imgproc_THRESH_FLAG ){
        Mat src=bitmapToMatBnW(GrayscaleImage(bitmap));
        Mat dst = new Mat(bitmap.getWidth(),bitmap.getHeight(),CvType.CV_8UC1);


        Imgproc.threshold(src,dst,127,255,Imgproc_THRESH_FLAG); //Thresh binary image convert

        Utils.matToBitmap(dst,bitmap); //convert Mat to bitmap

        return bitmap;

    }
    //Adaptive ThreshHold Image
    protected static Bitmap AdaptiveThreshHoldImage(Bitmap bitmap,int ADAPTIVE_THRESH,int Imgproc_THRESH_FLAG){
        Mat src=bitmapToMatBnW(GrayscaleImage(bitmap));
        Mat dst = new Mat(bitmap.getWidth(),bitmap.getHeight(),CvType.CV_8UC1);


        //representing the value that is to be given if pixel value is more than the threshold value.
        double maxValue = 255;

        //representing size of the pixel neighborhood used to calculate the threshold value
        int blockSize=11;

        //representing the constant used in the both methods (subtracted from the mean or weighted mean).
        double c=12;

        Imgproc.adaptiveThreshold(src,dst,maxValue,ADAPTIVE_THRESH,Imgproc_THRESH_FLAG,blockSize,c);

        Utils.matToBitmap(dst,bitmap);

        return bitmap;

    }

    //COLORMAP
    protected  Bitmap ColorMapImage(Bitmap bitmap,int Imgproc_COLORMAP_FLAG){

        //source Matrix . Convert bitmap to Matrix
        //Mat src = bitmapToMat(bitmap);
        Mat src = new Mat(bitmap.getWidth(),bitmap.getHeight(),CvType.CV_8UC3);

        //destination Matrix
        Mat dst = new Mat(src.rows(),src.cols(),src.type());

        Imgproc.applyColorMap(src,dst,Imgproc_COLORMAP_FLAG);

        Utils.matToBitmap(dst,bitmap);
        return bitmap;
    }



    protected Bitmap EnhancedContrastImage(Bitmap bitmap){
        //source Matrix . Convert bitmap to Matrix
        Mat src = new Mat(bitmap.getWidth(),bitmap.getHeight(),CvType.CV_8UC1);

        //destination Matrix
        Mat dst = new Mat(src.rows(),src.cols(),src.type());

        Imgproc.equalizeHist(src,dst);

        Utils.matToBitmap(dst,bitmap);

        return bitmap;
    }

    public static Bitmap SharpnessImage(Bitmap bitmap){
        //source Matrix . Convert bitmap to Matrix
        Mat src =bitmapToMatBnW(bitmap);

        //destination Matrix
        Mat dst = new Mat();

        Imgproc.GaussianBlur(src,dst,new Size(45,45),0);
        Core.addWeighted(src,1.5,dst,-0.5,1,dst);

        Utils.matToBitmap(dst,bitmap);

        return bitmap;

    }

    //Convolution Image
    public static Bitmap ConvolutionImage(Bitmap bitmap){
//source Matrix . Convert bitmap to Matrix
        Mat src =bitmapToMatBnW(bitmap);

        //destination Matrix
        Mat dst = new Mat();

        int kernelSize=3;

        Mat kernel = new Mat(kernelSize,kernelSize, CvType.CV_32F){
            {
                put(0,0,0);
                put(0,1,0);
                put(0,2,0);

                put(1,0,0);
                put(1,1,1);
                put(1,2,0);

                put(2,0,0);
                put(2,1,0);
                put(2,2,0);
            }
        };

        Imgproc.filter2D(src,dst,-1,kernel);

        Utils.matToBitmap(dst,bitmap);

        return bitmap;
    }

    //Blur
    public static Bitmap BlurAverageImage(Bitmap bitmap){
        Mat src = bitmapToMat(bitmap);
        Mat dst = new Mat();

        // Creating the Size and Point objects
        Size size = new Size(45, 45);
        Point point = new Point(20, 30);

        Imgproc.blur(src, dst, size, point, Core.BORDER_DEFAULT);

        Utils.matToBitmap(dst,bitmap);

        return bitmap;
    }

    public static Bitmap GaussianBlurImage(Bitmap bitmap){
        Mat src = bitmapToMat(bitmap);
        Mat dst = new Mat(bitmap.getWidth(),bitmap.getHeight(),CvType.CV_8UC1);

        Size size = new Size(45,45);

        Imgproc.GaussianBlur(src,dst,size,0);

        Utils.matToBitmap(dst,bitmap);

        return bitmap;
    }
    public static Bitmap MedianBlurImage(Bitmap bitmap){
        Mat src = bitmapToMat(bitmap);
        Mat dst = new Mat(bitmap.getWidth(),bitmap.getHeight(),CvType.CV_8UC1);


        Imgproc.medianBlur(src,dst,25);

        Utils.matToBitmap(dst,bitmap);

        return bitmap;
    }

    public static Bitmap BilateralFilterImage(Bitmap bitmap){
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
        //source Matrix . Convert bitmap to Matrix
        Mat src = bitmapToMat(bitmap);

        //destination Matrix
        Mat dst = new Mat(bitmap.getWidth(),bitmap.getHeight(),CvType.CV_8UC1);

        //representing the diameter of the pixel neighborhood.
        int d=15;

        //representing the filter sigma in the color space.
        double sigmaColor=80;

        //representing the filter sigma in the coordinate space.
        double sigmaSpace=80;

        //representing the type of the border used.
        int borderType=Core.BORDER_DEFAULT;

        //convert 4 channels RGBA format to 3 channels RGB
        Imgproc.cvtColor(src,src,Imgproc.COLOR_RGBA2BGR);

        //apply bilateral filter
        Imgproc.bilateralFilter(src,dst,d,sigmaColor,sigmaSpace,borderType);

        //convert 3 channels RGB format to 4 channels RGBA
        Imgproc.cvtColor(dst,dst,Imgproc.COLOR_BGR2RGBA);

        Utils.matToBitmap(dst,bitmap);

        return bitmap;
    }
    public static Bitmap Filter2DImage(Bitmap bitmap){

        //source Matrix . Convert bitmap to Matrix
        //Mat src = bitmapToMat(GrayscaleImage(bitmap));
        Mat src = bitmapToMat(bitmap);

        //destination Matrix
        Mat dst = new Mat();

        //representing the depth of the output image.
        int ddepth=-1;
        int kernelSize=2;
        // Creating kernel matrix
        Mat kernel = Mat.ones(kernelSize,kernelSize, CvType.CV_32F);

        for(int i = 0; i<kernel.rows(); i++) {
            for(int j = 0; j<kernel.cols(); j++) {
                double[] m = kernel.get(i, j);

                for(int k = 1; k<m.length; k++) {
                    m[k] = m[k]/(kernelSize * kernelSize);
                }
                kernel.put(i,j, m);
            }
        }

        Imgproc.filter2D(src,dst,ddepth,kernel);

        Utils.matToBitmap(dst,bitmap);

        return bitmap;
    }
    public static Bitmap DilateImage(Bitmap bitmap){
        //source Matrix . Convert bitmap to Matrix
        Mat src = bitmapToMat(bitmap);

        //destination Matrix
        Mat dst = new Mat();

        int kernelSize=5;

        // Preparing the kernel matrix object
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
                new Size((kernelSize*kernelSize) + 1, (kernelSize*kernelSize)+1));

        //Apply dilate
        Imgproc.dilate(src,dst,kernel);

        Utils.matToBitmap(dst,bitmap);

        return bitmap;

    }
    public static Bitmap ErodeImage(Bitmap bitmap){
        //source Matrix . Convert bitmap to Matrix
        Mat src = bitmapToMat(bitmap);

        //destination Matrix
        Mat dst = new Mat();

        int kernelSize=5;

        // Preparing the kernel matrix object
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
                new  Size((kernelSize*kernelSize) + 1, (kernelSize*kernelSize)+1));

        // Applying erode on the Image
        Imgproc.erode(src, dst, kernel);

        Utils.matToBitmap(dst,bitmap);

        return bitmap;
    }
    public static Bitmap MorphologyEx(Bitmap bitmap){
        //source Matrix . Convert bitmap to Matrix
        Mat src = bitmapToMat(bitmap);

        //destination Matrix
        Mat dst = new Mat();

        int kernelSize=5;

        // Creating kernel matrix
        Mat kernel = Mat.ones(kernelSize,kernelSize, CvType.CV_32F);

        // Applying Blur effect on the Image
        Imgproc.morphologyEx(src, dst, Imgproc.MORPH_TOPHAT, kernel);

        Utils.matToBitmap(dst,bitmap);

        return bitmap;
    }
}
