package ru.drugpamir.shopping.domain

class DeleteShopItemUseCase(private val repository: ShopListRepository) {

    fun deleteShopItem(shopItemId: Int): Boolean {
        return repository.deleteShopItem(shopItemId)
    }

    fun deleteShopItem(shopItem: ShopItem): Boolean {
        return deleteShopItem(shopItem.id)
    }
}