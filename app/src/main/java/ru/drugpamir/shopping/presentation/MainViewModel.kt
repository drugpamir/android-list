package ru.drugpamir.shopping.presentation

import androidx.lifecycle.ViewModel
import ru.drugpamir.shopping.data.ShopListRepositoryImpl
import ru.drugpamir.shopping.domain.DeleteShopItemUseCase
import ru.drugpamir.shopping.domain.EditShopItemUseCase
import ru.drugpamir.shopping.domain.GetShopItemUseCase
import ru.drugpamir.shopping.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)

    val shopList = getShopItemUseCase.getShopList()

    fun editShopItem(shopItem: ShopItem) {
        editShopItemUseCase.editShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        editShopItemUseCase.changeEnableState(shopItem)
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }
}