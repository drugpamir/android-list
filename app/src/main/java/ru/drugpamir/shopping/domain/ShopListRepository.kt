package ru.drugpamir.shopping.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {

    fun getShopList(): LiveData<List<ShopItem>>

    fun getShopItem(shopItemId: Int): ShopItem

    fun editShopItem(shopItem: ShopItem): Boolean

    fun addShopItem(shopItem: ShopItem): Boolean

    fun deleteShopItem(shopItemId: Int): Boolean
}