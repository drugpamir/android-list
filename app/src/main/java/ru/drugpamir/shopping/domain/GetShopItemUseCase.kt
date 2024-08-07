package ru.drugpamir.shopping.domain

class GetShopItemUseCase(private val repository: ShopListRepository){

    fun getShopList(): List<ShopItem> {
        return repository.getShopList()
    }

    fun getShopItem(shopItemId: Int): ShopItem {
        return repository.getShopItem(shopItemId)
    }
}