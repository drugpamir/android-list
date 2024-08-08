package ru.drugpamir.shopping.domain

class EditShopItemUseCase(private val repository: ShopListRepository) {

    fun editShopItem(shopItem: ShopItem): Boolean {
        return repository.editShopItem((shopItem))
    }

    fun changeEnableState(shopItem: ShopItem): Boolean {
        val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
        return editShopItem(newShopItem)
    }
}