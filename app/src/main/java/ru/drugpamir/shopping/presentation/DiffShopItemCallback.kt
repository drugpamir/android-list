package ru.drugpamir.shopping.presentation

import androidx.recyclerview.widget.DiffUtil
import ru.drugpamir.shopping.domain.ShopItem

class DiffShopItemCallback : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}