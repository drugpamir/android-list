package ru.drugpamir.shopping.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.drugpamir.shopping.R
import ru.drugpamir.shopping.domain.ShopItem

class ShopListAdapter: ListAdapter<ShopItem, ShopItemViewHolder>(DiffShopItemCallback()) {
    private var createdViewHoldersCount = 0

    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val res = when (viewType) {
            VIEW_TYPE_ITEM_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ITEM_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        Log.d("ShopListAdapter", "onCreateViewHolder: ${++createdViewHoldersCount}")
        val shopItem = getItem(position)
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
        val shopItem = getItem(position)
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
}