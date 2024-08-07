package ru.drugpamir.shopping.domain

class DeleteShopItemUseCase(private val repository: ShopListRepository) {

    fun deleteShopItem(id: Int): Boolean {
        TODO()
    }

    fun deleteShopItem(shopItem: ShopItem): Boolean {
        return repository.deleteShopItem(shopItem)
    }
}