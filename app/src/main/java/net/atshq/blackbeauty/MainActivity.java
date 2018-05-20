package net.atshq.blackbeauty;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RvImagePreviewAdapter adapter;

    private List<ImagePreviewItem> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvFilterPreview);

        items=new ArrayList<>();

        adapter = new RvImagePreviewAdapter(items,this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        setData();
    }

    private void setData() {
        Bitmap bitmap =BitmapFactory.decodeResource(getResources(),R.drawable.photo);
        bitmap=Bitmap.createScaledBitmap(bitmap,80,80,true);
        for(int i=0;i<10;i++){

            ImagePreviewItem item = new ImagePreviewItem("filter "+i+1,bitmap);
            if(i==0){
                item.setSelect(true);
            }
            items.add(item);
        }
        adapter.notifyDataSetChanged();
    }
}
