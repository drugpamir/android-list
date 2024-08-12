package ru.drugpamir.shopping.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import ru.drugpamir.shopping.R
import ru.drugpamir.shopping.domain.ShopItem

class ShopItemFragment : Fragment() {

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
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        initViewModel()
        addTextListeners()

        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun parseArguments() {
        arguments?.let {
            if (!it.containsKey(EXTRA_MODE)) {
                throw RuntimeException("EXTRA_MODE intent parameter not found")
            }
            screenMode = it.getString(EXTRA_MODE) ?: MODE_UNKNOWN
            if (screenMode != MODE_ADD && screenMode != MODE_EDIT) {
                throw RuntimeException("UNKNOWN MODE")
            }
            if (screenMode == MODE_EDIT) {
                if (!it.containsKey(EXTRA_ITEM)) {
                    throw RuntimeException("EXTRA_ITEM intent parameter not found")
                }
                shopItemId = it.getInt(EXTRA_ITEM, ShopItem.UNDEFINED_ID)
                if (shopItemId == ShopItem.UNDEFINED_ID) {
                    throw RuntimeException("ShopItemId = $shopItemId not allowed")
                }
            }
        } ?: throw RuntimeException("Fragment arguments not found")
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        btnSave = view.findViewById(R.id.save_button)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            tilName.error = if (it) {
                "Not a valid name"
            } else {
                null
            }
        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            tilCount.error = if (it) {
                "Not a valid count"
            } else {
                null
            }
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
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
        viewModel.shopItem.observe(viewLifecycleOwner) {
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

        fun newFragmentAddItem() = ShopItemFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_MODE, MODE_ADD)
            }
        }

        fun newFragmentEditItem(shopItemId: Int) = ShopItemFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_MODE, MODE_EDIT)
                putInt(EXTRA_ITEM, shopItemId)
            }
        }
    }
}