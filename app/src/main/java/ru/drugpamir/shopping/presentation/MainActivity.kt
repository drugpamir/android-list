package ru.drugpamir.shopping.presentation

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.drugpamir.shopping.R
import ru.drugpamir.shopping.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            showList(it)
        }
    }

    private fun showList(list: List<ShopItem>) {
        val viewGroup = findViewById<LinearLayout>(R.id.ll_shop_list)
        for (shopItem in list) {
            val res = if (shopItem.enabled) {
                R.layout.item_view_enabled
            } else {
                R.layout.item_view_enabled
            }
            val itemView = layoutInflater.inflate(res, viewGroup, false)
            itemView.findViewById<TextView>(R.id.tv_name).text = shopItem.name
            itemView.findViewById<TextView>(R.id.tv_count).text = shopItem.count.toString()
            viewGroup.addView(itemView)
        }
    }
}