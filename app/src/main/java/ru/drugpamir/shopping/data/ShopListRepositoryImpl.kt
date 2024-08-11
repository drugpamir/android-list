package ru.drugpamir.shopping.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.drugpamir.shopping.domain.ShopItem
import ru.drugpamir.shopping.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id - o2.id })
    private val shopListLiveData = MutableLiveData<List<ShopItem>>()

    private var shopItemIdIncrement = 0

    init {
        for (i: Int in 1..100) {
            addShopItem(ShopItem(
                name = "Item $i",
                count = 0,
                enabled = Random.nextBoolean(),
            ))
        }
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveData
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
        return shopList.add(shopItem).also {
            updateList()
        }
    }

    override fun deleteShopItem(shopItemId: Int): Boolean {
        return shopList.removeIf {
            it.id == shopItemId
        }.also {
            updateList()
        }
    }

    private fun updateList() {
        shopListLiveData.value = shopList.toList()
    }
}