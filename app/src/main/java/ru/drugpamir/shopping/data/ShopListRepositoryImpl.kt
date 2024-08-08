package ru.drugpamir.shopping.data

import ru.drugpamir.shopping.domain.ShopItem
import ru.drugpamir.shopping.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var shopItemIdIncrement = 0

    init {
        for (i: Int in 1..100) {
            addShopItem(ShopItem(
                name = "Item $i",
                count = 0,
                enabled = true,
            ))
        }
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("ShopItem with id = $shopItemId not found at repository")
    }

    override fun editShopItem(shopItem: ShopItem): Boolean {
        if (!deleteShopItem(shopItem.id)) {
            return false
        }
        return addShopItem(shopItem)
    }

    override fun addShopItem(shopItem: ShopItem): Boolean {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = shopItemIdIncrement++
        }
        return shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItemId: Int): Boolean {
        return shopList.removeIf {
            it.id == shopItemId
        }
    }
}