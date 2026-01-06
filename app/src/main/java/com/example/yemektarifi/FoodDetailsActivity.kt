package com.example.yemektarifi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.yemektarifi.databinding.ActivityFoodDetailsBinding
import com.google.firebase.firestore.FirebaseFirestore

class FoodDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailsBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val foodName = intent.getStringExtra("foodName") ?: return
        getFoodDetails(foodName)

        binding.homeButton.setOnClickListener {
            // Ana menüye dön
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun getFoodDetails(foodName: String) {

        db.collection("yemekler")
            .document(foodName)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val adi = document.getString("adi") ?: "Yemek Adı Bulunamadı"
                    val malzemeler = document.getString("malzemeler") ?: "Malzeme Bilgisi Yok"
                    val tarif = document.getString("tarif") ?: "Tarif Bilgisi Yok"
                    val besindegerleri = document.getString("besindegerleri") ?: "Besin Değeri Yok"

                    binding.foodNameTextView.text = adi
                    binding.foodMaterialsTextView.text = malzemeler
                    binding.foodRecipeTextView.text = tarif
                    binding.foodNutritionTextView.text = besindegerleri
                } else {
                    Log.e("FoodDetailsActivity", "Yemek bulunamadı.")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FoodDetailsActivity", "Firebase hatası: ${exception.message}")
            }
    }
}
