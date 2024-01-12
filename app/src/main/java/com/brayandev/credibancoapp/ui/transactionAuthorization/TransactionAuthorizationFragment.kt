package com.brayandev.credibancoapp.ui.transactionAuthorization

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.brayandev.credibancoapp.R
import com.brayandev.credibancoapp.databinding.FragmentTransactionAuthorizationBinding
import com.brayandev.credibancoapp.utils.dismissKeyboard
import com.brayandev.credibancoapp.utils.validateCharacters
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat

@AndroidEntryPoint
class TransactionAuthorizationFragment : Fragment() {

    private var _binding: FragmentTransactionAuthorizationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionAuthorizationViewModel by viewModels()
    private var currentString: String = ""

    private var isCompleteCommerceCode: Boolean? = null
    private var isCompleteTerminalCode: Boolean? = null
    private var isCompleteAmount: Boolean? = null
    private var isCompleteCard: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTransactionAuthorizationBinding.inflate(inflater, container, false)
        binding.apply {
            buttonAuthorize.setOnClickListener {
                it.dismissKeyboard()
                if (validateTextCharacters()) {
                    viewModel.onDataSelected(
                        editTextCommerceCode.text.toString(),
                        editTextTerminalCode.text.toString(),
                        editTextAmount.text.toString(),
                        editTextCard.text.toString(),
                        showDialog = {
                            showMessageDialog(
                                R.string.transaction_authorization_text_title_dialog,
                                R.string.transaction_authorization_text_success_transaction,
                            )
                            clearText()
                        },
                        isErrorRequest = {
                            if (it) {
                                showMessageDialog(
                                    R.string.transaction_authorization_text_title_error_dialog,
                                    R.string.transaction_authorization_text_failure_transaction,
                                )
                            } else {
                                showMessageDialog(
                                    R.string.transaction_authorization_text_title_error_dialog,
                                    R.string.transaction_list_text_error,
                                )
                            }
                        },
                    )
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.transaction_annulation_valdiate_text),
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
        }
        initUIState()
        return binding.root
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect {
                    binding.progressBar.isVisible = it
                }
            }
        }
        initTextChange()
        validateTextCharacters()
    }

    private fun validateTextCharacters(): Boolean {
        with(binding) {
            editTextCommerceCode.validateCharacters(6) { isCompleteCommerceCode = it }
            editTextTerminalCode.validateCharacters(6) { isCompleteTerminalCode = it }
            editTextAmount.validateCharacters(4) { isCompleteAmount = it }
            editTextCard.validateCharacters(16) { isCompleteCard = it }
        }
        return isCompleteCommerceCode == true && isCompleteTerminalCode == true && isCompleteAmount == true && isCompleteCard == true
    }

    private fun initTextChange() {
        binding.apply {
            editTextAmount.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().isNotEmpty() && s.toString() != currentString) {
                        editTextAmount.removeTextChangedListener(this)

                        val cleanString: String = s!!.replace("""[$,.]""".toRegex(), "")

                        val parsed = cleanString.toDouble()
                        val formatted = NumberFormat.getCurrencyInstance().format((parsed / 100))

                        currentString = formatted
                        editTextAmount.setText(formatted)
                        editTextAmount.setSelection(formatted.length)

                        editTextAmount.addTextChangedListener(this)
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    private fun showMessageDialog(
        title: Int,
        message: Int,
    ) {
        if (validateIsNotEmptyText()) {
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle(getString(title))
                .setMessage(getString(message))
                .setPositiveButton(android.R.string.ok) { view, _ ->
                    view.dismiss()
                }
                .setCancelable(false)
                .create()
            dialog.show()
        }
    }

    private fun validateIsNotEmptyText(): Boolean {
        return binding.editTextCommerceCode.text!!.isNotEmpty() &&
            binding.editTextTerminalCode.text!!.isNotEmpty() &&
            binding.editTextAmount.text!!.isNotEmpty() &&
            binding.editTextCard.text!!.isNotEmpty()
    }

    private fun clearText() {
        binding.apply {
            editTextCommerceCode.text?.clear()
            editTextCommerceCode.error = null
            editTextTerminalCode.text?.clear()
            editTextTerminalCode.error = null
            editTextAmount.text?.clear()
            editTextAmount.error = null
            editTextCard.text?.clear()
            editTextCard.error = null
        }
    }
}
