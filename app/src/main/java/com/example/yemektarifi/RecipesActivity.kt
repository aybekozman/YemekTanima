package com.example.yemektarifi

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yemektarifi.databinding.ActivityRecipesBinding

class RecipesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipesBinding
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var foodList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }


        foodList = listOf(
            "adana-kebap","anne-koftesi","armut","avokado","ayran","baklava",
            "beyaz-lahana-sarmasi","biber-dolma","brokoli","bruksel-lahanasi",
            "bulgur-pilavi","cacik","canak-enginar","cay","cig-kofte","cilek",
            "cipura","coban-salatasi","domates","domates-corbasi","dondurma",
            "doner","ekmek","elma","erik","et-sote","hamsi-tava",
            "haslanmis-yumurta","havuc","hunkar-begendi","icli-kofte",
            "incir","iskender","ispanak-yemegi","kabak-mucver","kalburabasti",
            "karnabahar","karniyarik","karpuz","kavun","kayisi","kazandibi",
            "kemal-pasa-tatlisi","kiraz","kisir","kivi","kiymali-borek",
            "kiymali-pide","kokorec","lahmacun","levrek","lokma","mango",
            "manti","menemen","mercimek-corbasi","mercimek-koftesi",
            "midye-dolma","midye-tava","mumbar-dolmasi","muz","nar","omlet",
            "patates-kizartmasi","patates-puresi","patates-salatasi",
            "patlican-kebabi","peynirli-borek","pilav","pirasa","portakal",
            "sahlep","salatalik","salcali-makarna","sandvic","seftali",
            "sehriye-corbasi","siyah-zeytin","su-boregi","sucuklu-yumurta",
            "sulu-bamya-yemegi","sulu-barbunya-yemegi","sulu-bezelye-yemegi",
            "sulu-kuru-fasulye-yemegi","sulu-mercimek-yemegi",
            "sulu-nohut-yemegi","sulu-patates-yemegi","sutlac","tantuni",
            "tarhana-corbasi","tas-kebabi","tavuk-sote","tulumba-tatlisi",
            "turk-kahvesi","tursu","uzum","yaprak-sarma","yayla-corbasi",
            "yesil-zeytin","yogurt","yogurtlu-makarna","zeytinyagli-fasulye"
        )

        foodAdapter = FoodAdapter(foodList) { selectedFood ->
            val intent = Intent(this, FoodDetailsActivity::class.java)
            intent.putExtra("foodName", selectedFood)
            startActivity(intent)
        }

        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipesRecyclerView.adapter = foodAdapter

        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                foodAdapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
