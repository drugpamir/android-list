package ru.drugpamir.shopping.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.drugpamir.shopping.R
import ru.drugpamir.shopping.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()
        launchInRightMode()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("EXTRA_MODE intent parameter not found")
        }
        screenMode = intent.getStringExtra(EXTRA_MODE) ?: MODE_UNKNOWN
        if (screenMode != MODE_ADD && screenMode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode: $screenMode")
        }
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_ITEM)) {
                throw RuntimeException("EXTRA_ITEM intent parameter not found")
            }
            shopItemId = intent.getIntExtra(EXTRA_ITEM, ShopItem.UNDEFINED_ID)
            if (shopItemId == ShopItem.UNDEFINED_ID) {
                throw RuntimeException("ShopItemId = $shopItemId not allowed")
            }
        }
    }

    private fun launchInRightMode() {
        val fragment = when(screenMode) {
            MODE_ADD -> ShopItemFragment.newFragmentAddItem()
            MODE_EDIT -> ShopItemFragment.newFragmentEditItem(shopItemId)
            else -> throw RuntimeException("Unknown ScreenMode")
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .commit()
    }

    companion object {

        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_ITEM = "extra_item"

        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = "mode_unknown"

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_ITEM, shopItemId)
            return intent
        }
    }
}