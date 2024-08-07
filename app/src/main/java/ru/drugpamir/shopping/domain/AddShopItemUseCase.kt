package ru.drugpamir.shopping.domain

class AddShopItemUseCase(private val repository: ShopListRepository) {

    fun addShopItem(shopItem: ShopItem): Boolean {
        return repository.addShopItem(shopItem)
    }
}