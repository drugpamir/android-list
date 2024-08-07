package ru.drugpamir.shopping.domain

class EditShopItemUseCase(private val repository: ShopListRepository) {

    fun editShopItem(shopItem: ShopItem): Boolean {
        return repository.editShopItem((shopItem))
    }
}