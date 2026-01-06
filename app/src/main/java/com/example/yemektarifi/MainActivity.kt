package com.example.yemektarifi

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.yemektarifi.databinding.ActivityMainBinding
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var interpreter: Interpreter
    private lateinit var binding: ActivityMainBinding
    private val modelPath = "model.tflite"
    private val imageSize = 150
    private val numClasses = 102
    private lateinit var classLabels: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        interpreter = loadModelFile()!!

        classLabels = listOf("adana-kebap", "anne-koftesi", "armut", "avokado", "ayran", "baklava", "beyaz-lahana-sarmasi", "biber-dolma", "brokoli", "bruksel-lahanasi", "bulgur-pilavi", "cacik", "canak-enginar", "cay", "cig kofte", "cilek", "cipura", "coban-salatasi", "domates", "domates-corbasi", "dondurma", "doner", "ekmek", "elma", "erik", "et-sote", "hamsi-tava", "haslanmis-yumurta", "havuc", "hunkar-begendi", "icli-kofte", "incir", "iskender", "ispanak-yemegi", "kabak-mucver", "kalburabasti", "karnabahar", "karniyarik", "karpuz", "kavun", "kayisi", "kazandibi", "kemal-pasa-tatlisi", "kiraz", "kisir", "kivi", "kiymali-borek", "kiymali-pide", "kokorec", "lahmacun", "levrek", "lokma", "mango", "manti", "menemen", "mercimek-corbasi", "mercimek-koftesi", "midye-dolma", "midye-tava", "mumbar-dolmasi", "muz", "nar", "omlet", "patates-kizartmasi", "patates-puresi", "patates-salatasi", "patlican-kebabi", "peynirli-borek", "pilav", "pirasa", "portakal", "sahlep", "salatalik", "salcali-makarna", "sandvic", "seftali", "sehriye-corbasi", "siyah-zeytin", "su-boregi", "sucuklu-yumurta", "sulu-bamya-yemegi", "sulu-barbunya-yemegi", "sulu-bezelye-yemegi", "sulu-kuru-fasulye-yemegi", "sulu-mercimek-yemegi", "sulu-nohut-yemegi", "sulu-patates-yemegi", "sutlac", "tantuni", "tarhana-corbasi", "tas-kebabi", "tavuk-sote", "tulumba-tatlisi", "turk-kahvesi", "tursu", "uzum", "yaprak-sarma", "yayla-corbasi", "yesil-zeytin", "yogurt", "yogurtlu-makarna", "zeytinyagli-fasulye")

        binding.uploadButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        binding.recipesButton.setOnClickListener {
            val intent = Intent(this, RecipesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            try {
                val uri = data?.data
                uri?.let {
                    val inputStream = contentResolver.openInputStream(it)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()

                    if (bitmap != null) {
                        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, true)
                        val byteBuffer = convertBitmapToByteBuffer(scaledBitmap)
                        val result = classifyImage(byteBuffer)
                        Log.d("MainActivity", "Tahmin Edilen Yemek: $result")
                        navigateToFoodDetails(result)
                    } else {
                        Log.e("MainActivity", "Bitmap null oldu")
                    }
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Fotoğraf seçme hatası: ${e.message}")
            }
        }
    }

    private fun loadModelFile(): Interpreter? {
        return try {
            val fileDescriptor = assets.openFd(modelPath)
            val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
            val fileChannel = inputStream.channel
            val startOffset = fileDescriptor.startOffset
            val declaredLength = fileDescriptor.declaredLength
            val buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
            Interpreter(buffer)
        } catch (e: IOException) {
            Log.e("MainActivity", "Model dosyası yüklenemedi: ${e.message}")
            null
        }
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(imageSize * imageSize)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        for (pixelValue in intValues) {
            val r = (pixelValue shr 16 and 0xFF) / 255.0f
            val g = (pixelValue shr 8 and 0xFF) / 255.0f
            val b = (pixelValue and 0xFF) / 255.0f

            byteBuffer.putFloat(r)
            byteBuffer.putFloat(g)
            byteBuffer.putFloat(b)
        }

        return byteBuffer
    }

    private fun classifyImage(byteBuffer: ByteBuffer): String {
        val outputMap = Array(1) { FloatArray(numClasses) }
        interpreter.run(byteBuffer, outputMap)

        val maxIndex = outputMap[0].indices.maxByOrNull { outputMap[0][it] } ?: -1
        return if (maxIndex != -1) {
            classLabels[maxIndex]
        } else {
            "Bilinmeyen Yemek"
        }
    }

    private fun navigateToFoodDetails(foodName: String) {
        val intent = Intent(this, FoodDetailsActivity::class.java)
        intent.putExtra("foodName", foodName)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        interpreter.close()
        super.onDestroy()
    }
}
