package ru.drugpamir.shopping.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.drugpamir.shopping.R
import ru.drugpamir.shopping.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
//    private var createdViewHoldersCount = 0

    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val res = when (viewType) {
            VIEW_TYPE_ITEM_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ITEM_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
//        Log.d("ShopListAdapter", "onCreateViewHolder: ${++createdViewHoldersCount}")
        return ShopItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()

        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }

        holder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = shopList[position]
        return if (shopItem.enabled) {
            VIEW_TYPE_ITEM_ENABLED
        } else {
            VIEW_TYPE_ITEM_DISABLED
        }
    }

    companion object {
        const val VIEW_TYPE_ITEM_DISABLED = 0
        const val VIEW_TYPE_ITEM_ENABLED = 1

        const val HOLDERS_DISABLED_MAX_COUNT = 10
        const val HOLDERS_ENABLED_MAX_COUNT = 10
    }

    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }
}