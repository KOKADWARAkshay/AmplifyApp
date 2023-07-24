package com.kokadwarakshay.amplifyapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.predictions.models.CelebrityDetails;
import com.amplifyframework.predictions.models.EntityDetails;
import com.amplifyframework.predictions.models.EntityMatch;

import java.util.List;

public class Main2Activity extends AppCompatActivity implements EntityDetectionManager.EntityDetectionCallback {
    private EntityDetectionManager entityDetectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entityDetectionManager = new EntityDetectionManager();

        Bitmap bitmap = getYourBitmap();
        entityDetectionManager.detectEntities(bitmap, this);
    }

    private Bitmap getYourBitmap() {

        return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
    }

    @Override
    public void onEntitiesDetected(List<EntityDetails> entities) {

        for (EntityDetails entity : entities) {
            String boundingBox = entity.getBox() != null ? entity.getBox().toShortString() : "Unknown";

            // The list of detected entities is accessible using getEntities()
            List<String> detectedEntities = entity.getEntities();

            Log.i("MyAmplifyApp", "Detected entities: " + detectedEntities.toString() + ", Bounding Box: " + boundingBox);
        }
    }

    @Override
    public void onEntityMatchesDetected(List<EntityMatch> matches) {

        for (EntityMatch match : matches) {

            Log.i("MyAmplifyApp", "Detected entity match, External Image Id: " + match.getExternalImageId());
        }
    }

    @Override
    public void onCelebritiesDetected(List<CelebrityDetails> celebrities) {

        for (CelebrityDetails celebrity : celebrities) {

            Log.i("MyAmplifyApp", "Detected celebrity: " + celebrity.getCelebrity().getName());
        }
    }

    @Override
    public void onEntityDetectionError(Throwable error) {

        Log.e("MyAmplifyApp", "Entity detection failed", error);
    }
}
