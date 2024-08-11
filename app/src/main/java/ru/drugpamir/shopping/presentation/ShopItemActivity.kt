package ru.drugpamir.shopping.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import ru.drugpamir.shopping.R
import ru.drugpamir.shopping.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var btnSave: Button

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()
        initViews()
        initViewModel()
        addTextListeners()

        when(screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("EXTRA_MODE intent parameter not found")
        }
        screenMode = intent.getStringExtra(EXTRA_MODE) ?: MODE_UNKNOWN
        if (screenMode != MODE_ADD && screenMode != MODE_EDIT) {
            throw RuntimeException("UNKNOWN MODE")
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

    private fun initViews() {
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        etName = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        btnSave = findViewById(R.id.save_button)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        viewModel.errorInputName.observe(this) {
            tilName.error = if (it) {
                "Not a valid name"
            } else {
                null
            }
        }

        viewModel.errorInputCount.observe(this) {
            tilCount.error = if (it) {
                "Not a valid count"
            } else {
                null
            }
        }

        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    private fun addTextListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetInputNameError()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetInputCountError()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        btnSave.setOnClickListener {
            viewModel.editShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun launchAddMode() {
        btnSave.setOnClickListener {
            viewModel.addShopItem(etName.text?.toString(), etCount.text?.toString())
        }
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