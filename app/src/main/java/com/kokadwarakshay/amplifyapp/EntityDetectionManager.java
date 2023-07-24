package com.kokadwarakshay.amplifyapp;

import android.graphics.Bitmap;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.predictions.models.CelebrityDetails;
import com.amplifyframework.predictions.models.EntityDetails;
import com.amplifyframework.predictions.models.EntityMatch;
import com.amplifyframework.predictions.models.IdentifyActionType;
import com.amplifyframework.predictions.result.IdentifyCelebritiesResult;
import com.amplifyframework.predictions.result.IdentifyEntitiesResult;
import com.amplifyframework.predictions.result.IdentifyEntityMatchesResult;

import java.util.List;

public class EntityDetectionManager {

    public interface EntityDetectionCallback {
        void onEntitiesDetected(List<EntityDetails> entities);
        void onEntityMatchesDetected(List<EntityMatch> matches);
        void onCelebritiesDetected(List<CelebrityDetails> celebrities);
        void onEntityDetectionError(Throwable error);
    }

    public void detectEntities(Bitmap image, EntityDetectionCallback callback) {
        Amplify.Predictions.identify(
                IdentifyActionType.DETECT_ENTITIES,
                image,
                result -> {
                    IdentifyEntitiesResult identifyResult = (IdentifyEntitiesResult) result;
                    List<EntityDetails> entities = identifyResult.getEntities();
                    callback.onEntitiesDetected(entities);
                },
                error -> callback.onEntityDetectionError(error)
        );
    }

    public void detectEntitiesFromCollection(Bitmap image, EntityDetectionCallback callback) {
        Amplify.Predictions.identify(
                IdentifyActionType.DETECT_ENTITIES,
                image,
                result -> {
                    IdentifyEntityMatchesResult identifyResult = (IdentifyEntityMatchesResult) result;
                    List<EntityMatch> matches = identifyResult.getEntityMatches();
                    callback.onEntityMatchesDetected(matches);
                },
                error -> callback.onEntityDetectionError(error)
        );
    }

    public void detectCelebrities(Bitmap image, EntityDetectionCallback callback) {
        Amplify.Predictions.identify(
                IdentifyActionType.DETECT_CELEBRITIES,
                image,
                result -> {
                    IdentifyCelebritiesResult identifyResult = (IdentifyCelebritiesResult) result;
                    List<CelebrityDetails> celebrities = identifyResult.getCelebrities();
                    callback.onCelebritiesDetected(celebrities);
                },
                error -> callback.onEntityDetectionError(error)
        );
    }
}
