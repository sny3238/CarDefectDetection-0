package com.example.carcarcarcar;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SaveImageActivity extends Activity {
    ImageView imageView;
    private String imagepath = "";
    Button backButton;
    Button nextButton;
    private File mImageFolder;
    private int index = 0;
    Bitmap bitmap;

    String newPath = "";

    static ArrayList<String> savedImgList = new ArrayList<String>();

    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_saveimage);
        createImageFolder();
        imageView = (ImageView) findViewById(R.id.imageView);
        backButton = (Button) findViewById(R.id.backButton);
        nextButton = (Button) findViewById(R.id.nextButton);

        if (intent.getAction().equals("load temp image")) {

            imagepath = intent.getStringExtra("temppath");
            index = intent.getIntExtra("index", 0);
            bitmap = BitmapFactory.decodeFile(imagepath);
            imageView.setImageBitmap(bitmap);

        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraintent;
                FileOutputStream fileOutputStream = null;

                File f = new File(imagepath);
                String position;

                switch(index){

                    case 0:
                        position = "front_top";
                    case 1:
                        position = "front_front";
                    case 2:
                        position = "right_front";
                    case 3:
                        position = "right_back";
                    case 4:
                        position = "back_top";
                    case 5:
                        position = "back_front";
                    case 6:
                        position = "left_back";
                    default:
                        position = "left_front";
                }

                String filename = f.getName();
                String newname = filename.substring(0, filename.lastIndexOf("."))
                        + position + ".jpg";
                newPath = String.valueOf(Paths.get(mImageFolder.getAbsolutePath(),newname));
                File newFile = new File(newPath);


                try {//사진 저장
                    fileOutputStream = new FileOutputStream(newFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                    Toast.makeText(getApplicationContext(), "사진이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    index++; // count the number of pics. All pics should be 8 pictures.
                    savedImgList.add(newPath);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                    Intent mediaStoreUpdateIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaStoreUpdateIntent.setData(Uri.fromFile(newFile));
                    sendBroadcast(mediaStoreUpdateIntent);
                    if (fileOutputStream != null) {

                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (index == 8) {
                    cameraintent = new Intent(getApplicationContext(), BeforePastHistory.class);
                    cameraintent.putExtra("savedImgList", savedImgList);
                    //받을때 ArrayList<String> data = intent.getSerializableExtra("savedImgList");
                    startActivity(cameraintent);


                } else {

                    cameraintent = new Intent(getApplicationContext(), CameraActivity.class);
                    cameraintent.putExtra("file path", f.getAbsolutePath());
                    cameraintent.putExtra("index", index);

                    startActivity(cameraintent);
                }

            }
        });

    }

    private void createImageFolder() {
        File imageFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        mImageFolder = new File(imageFile, "Carcarcar");
        if (!mImageFolder.exists()) {
            mImageFolder.mkdirs();
        }


    }

    @Override
    public void onBackPressed() {
        Intent back_intent = new Intent(getApplicationContext(), CameraActivity.class);
        back_intent.putExtra("back", true);
        startActivity(back_intent);
    }

}
