package com.example.ecom

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.ecom.presentation.SignUpScreen
import com.example.ecom.ui.theme.EcomTheme
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class MainActivity : ComponentActivity() {
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcomTheme {
//                CloudinaryUploadScreen()
                SignUpScreen()
            }
        }
    }

    @Composable
    fun CloudinaryUploadScreen() {
        var imageUrl by remember { mutableStateOf<String?>(null) }

        // Image picker launcher
        val imagePicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                uploadImage(it) { url ->
                    imageUrl = url
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = { imagePicker.launch("image/*") }) {
                Text(text = "Pick & Upload Image")
            }

            imageUrl?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = "Uploaded Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

    private fun uploadImage(uri: Uri, onResult: (String?) -> Unit) {
        val inputStream = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        inputStream?.close()

        if (bytes == null) {
            onResult(null)
            return
        }

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file",
                "upload.jpg",
                bytes.toRequestBody("image/*".toMediaType())
            )
            .addFormDataPart("upload_preset", "ECOM_test") // 🔑 your unsigned preset
            .build()

        val request = Request.Builder()
            .url("https://api.cloudinary.com/v1_1/dawdgbh88/image/upload") // 🔑 your cloud name
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Cloudinary", "Upload failed: ${e.message}")
                runOnUiThread { onResult(null) }
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val secureUrl = JSONObject(json!!).getString("secure_url")
                runOnUiThread { onResult(secureUrl) }
            }
        })
    }
}
