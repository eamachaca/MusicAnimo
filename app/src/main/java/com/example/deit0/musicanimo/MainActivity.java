package com.example.deit0.musicanimo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;


public class MainActivity extends AppCompatActivity {
    Bitmap bitmap;
    Intent i;
    private static int cons=0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, cons);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle ext = data.getExtras();
            bitmap = (Bitmap) ext.get("data");
            FaceDetector detector = new FaceDetector.Builder(getApplicationContext())
                    .setTrackingEnabled(false)
                    .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                    .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                    .build();
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<Face> faces = detector.detect(frame);
            if (faces.size() > 0) {
                //TextView faceCountView = (TextView) findViewById(R.id.face_count);
                double xxss =  faces.valueAt(0).getIsSmilingProbability();
                int xxs=(int)(xxss*100);
                Intent intent = new Intent(this, ViewFullImage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                String xxq;
                if (xxs>75){
                    xxq="feliz.php";
                }else{
                    if (xxs>50||xxs==-100){
                        xxq="neutro.php";
                    }else{
                        xxq="nofeliz.php";
                    }
                }

                intent.putExtra("estado", xxq);
      /*  startActivity(new Intent(this, ImageListView.class));*/
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Tuvimos un problema al capturar su rostro", Toast.LENGTH_SHORT).show();
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(i, cons);
            }
            detector.release();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.deit0.musicanimo/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.deit0.musicanimo/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
