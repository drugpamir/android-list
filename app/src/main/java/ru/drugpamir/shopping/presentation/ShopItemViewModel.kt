package ru.drugpamir.shopping.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.drugpamir.shopping.data.ShopListRepositoryImpl
import ru.drugpamir.shopping.domain.AddShopItemUseCase
import ru.drugpamir.shopping.domain.EditShopItemUseCase
import ru.drugpamir.shopping.domain.GetShopItemUseCase
import ru.drugpamir.shopping.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(shopItemId: Int) {
        _shopItem.value = getShopItemUseCase.getShopItem(shopItemId)
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name: String = parseInputName(inputName)
        val count: Int = parseInputCount(inputCount)
        if (isValidInput(name, count)) {
            _shopItem.value?.let {
                val item = it.copy(
                    name = name,
                    count = count,
                )
                editShopItemUseCase.editShopItem(item)
                finishWork()
            }
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name: String = parseInputName(inputName)
        val count: Int = parseInputCount(inputCount)
        if (isValidInput(name, count)) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    fun resetInputNameError() {
        _errorInputName.value = false
    }

    fun resetInputCountError() {
        _errorInputCount.value = false
    }

    private fun parseInputName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseInputCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun isValidInput(name: String, count: Int): Boolean {
        if (name.isBlank()) {
            _errorInputName.value = true
            return false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            return false
        }
        return true
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}