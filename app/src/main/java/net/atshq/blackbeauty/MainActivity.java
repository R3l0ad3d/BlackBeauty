package net.atshq.blackbeauty;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG=MainActivity.class.getName();
    private static final int PICK_IMAGE = 111;
    static {
        if(OpenCVLoader.initDebug()){
            Log.d(TAG,"OpneCV Load ....");
        }else {
            Log.d(TAG,"OpenCV Load Failed .....");
        }
    }

    private String imagePath;
    public static Bitmap bitmap=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utility.checkPermission(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {

            android.net.Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            android.database.Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            if (filePath != null) {
                Log.d("File Path : ", filePath);
                imagePath = filePath;

                bitmap = BitmapFactory.decodeFile(filePath);

                if(bitmap!=null){
                    startActivity(new Intent(MainActivity.this,EditImageActivity.class));
                }else {
                    Toast.makeText(this, "Image Select Failed ....", Toast.LENGTH_SHORT).show();
                }

            } else {
                Log.d("File Path : ", "NUll found ..." + selectedImage.toString() + " " + columnIndex);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(this,MainActivity.class));
                }else {
                    Utility.checkCameraPermission(MainActivity.this);
                }
        }
    }

    public void galleryClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }
}
