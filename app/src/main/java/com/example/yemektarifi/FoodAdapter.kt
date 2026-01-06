package com.example.yemektarifi

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class FoodAdapter(
    private val fullFoodList: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private val foodList = fullFoodList.toMutableList()

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName: TextView = itemView.findViewById(R.id.foodNameText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val formattedName = formatFoodName(foodList[position])

        holder.foodName.text = formattedName
        holder.itemView.setOnClickListener {
            onItemClick(foodList[position])
        }
    }

    override fun getItemCount(): Int = foodList.size

    fun filter(query: String) {
        foodList.clear()

        if (query.isEmpty()) {
            foodList.addAll(fullFoodList)
        } else {
            val filtered = fullFoodList.filter {
                it.contains(query, ignoreCase = true)
            }
            foodList.addAll(filtered)
        }
        notifyDataSetChanged()
    }

    private fun formatFoodName(name: String): String {
        return name
            .replace("-", " ")
            .split(" ")
            .joinToString(" ") {
                it.replaceFirstChar { ch ->
                    if (ch.isLowerCase()) ch.titlecase() else ch.toString()
                }
            }
    }
}
