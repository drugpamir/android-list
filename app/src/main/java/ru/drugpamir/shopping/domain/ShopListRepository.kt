package ru.drugpamir.shopping.domain

interface ShopListRepository {

    fun getShopList(): List<ShopItem>

    fun getShopItem(shopItemId: Int): ShopItem

    fun editShopItem(shopItem: ShopItem): Boolean

    fun addShopItem(shopItem: ShopItem): Boolean

    fun deleteShopItem(shopItemId: Int): Boolean
}