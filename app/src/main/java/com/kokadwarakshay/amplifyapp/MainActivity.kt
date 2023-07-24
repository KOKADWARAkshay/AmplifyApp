package com.kokadwarakshay.amplifyapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amplifyframework.predictions.models.CelebrityDetails
import com.amplifyframework.predictions.models.EntityDetails
import com.amplifyframework.predictions.models.EntityMatch
import com.kokadwarakshay.amplifyapp.ui.theme.AmplifyAppTheme

class MainActivity : ComponentActivity(), EntityDetectionManager.EntityDetectionCallback {
    private lateinit var entityDetectionManager: EntityDetectionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        entityDetectionManager = EntityDetectionManager()

        setContent {
            AmplifyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

        val image: Bitmap = getYourBitmap()
        entityDetectionManager.detectEntities(image, this)
    }

    private fun getYourBitmap(): Bitmap {

        return BitmapFactory.decodeResource(resources, R.drawable.image)
    }

    override fun onEntitiesDetected(entities: List<EntityDetails>) {
        // Handle the detected entities here, for example:
        for (entity in entities) {
            val entityType = entity.getType()
            val boundingBox = entity.getBox()?.toShortString() ?: "Unknown"


            Log.i("MyAmplifyApp", "Detected entity: $entityType, Bounding Box: $boundingBox")
        }
    }

    override fun onEntityMatchesDetected(matches: List<EntityMatch>) {

        for (match in matches) {
            Log.i("MyAmplifyApp", "Detected entity match, External Image Id: ${match.getExternalImageId()}")
        }
    }

    override fun onCelebritiesDetected(celebrities: List<CelebrityDetails>) {

        for (celebrity in celebrities) {
            Log.i("MyAmplifyApp", "Detected celebrity: ${celebrity.getCelebrity()?.getName()}")
        }
    }

    override fun onEntityDetectionError(error: Throwable) {

        Log.e("MyAmplifyApp", "Entity detection failed", error)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AmplifyAppTheme {
        Greeting("Android")
    }
}
