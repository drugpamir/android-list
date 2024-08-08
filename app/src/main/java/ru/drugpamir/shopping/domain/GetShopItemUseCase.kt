package ru.drugpamir.shopping.domain

import androidx.lifecycle.LiveData

class GetShopItemUseCase(private val repository: ShopListRepository){

    fun getShopList(): LiveData<List<ShopItem>> {
        return repository.getShopList()
    }

    fun getShopItem(shopItemId: Int): ShopItem {
        return repository.getShopItem(shopItemId)
    }
}