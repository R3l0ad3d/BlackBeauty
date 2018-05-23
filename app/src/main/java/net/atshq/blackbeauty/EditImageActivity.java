package net.atshq.blackbeauty;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class EditImageActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getCanonicalName();

    private ImageView ivImagePreview;

    private RecyclerView recyclerView;
    private RvImagePreviewAdapter adapter;

    private List<ImagePreviewItem> items;

    private Bitmap imageBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        if(MainActivity.bitmap!=null){
            imageBitmap=MainActivity.bitmap;
        }else {
            imageBitmap=null;
        }

        ivImagePreview=findViewById(R.id.ivImagePreview);
        recyclerView = findViewById(R.id.rvFilterPreview);
        filterView();

        ivImagePreview.setImageBitmap(imageBitmap);

    }

    void filterView(){
        items=new ArrayList<>();

        adapter = new RvImagePreviewAdapter(items,this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        if(imageBitmap!=null){
            setData();
        }else {
            Log.d(TAG,"Bitmap Null found .....");
        }
    }

    private void setData() {
        //Bitmap bitmap =BitmapFactory.decodeResource(getResources(),R.drawable.photo);
        Bitmap bitmap=Bitmap.createScaledBitmap(imageBitmap,280,280,true);

        ImageFilter imageFilter = new ImageFilter(bitmap);

        ImagePreviewItem item = new ImagePreviewItem("Gray",imageFilter.Filter(Config.GRAY),Config.GRAY);
        items.add(item);

        item = new ImagePreviewItem("Thresh",imageFilter.Filter(Config.THRESH),Config.THRESH);
        items.add(item);

        item = new ImagePreviewItem("Color Map",imageFilter.Filter(Config.EnhancedContrastImage),Config.EnhancedContrastImage);
        items.add(item);

        item = new ImagePreviewItem("SharpnessImage",imageFilter.Filter(Config.SharpnessImage),Config.SharpnessImage);
        items.add(item);




        adapter.notifyDataSetChanged();

    }
}
