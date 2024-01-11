package com.brayandev.credibancoapp.ui.transactionDetail

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.brayandev.credibancoapp.R
import com.brayandev.credibancoapp.databinding.FragmentTransactionDetailBinding
import com.brayandev.credibancoapp.ui.transactionAuthorization.EnumResponseService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionDetailFragment(
    private val idTransaction: Int,
    private val receiptNumber: String,
    private val transactionIdentifier: String,
    private val statusCode: String,
    private val statusDescription: String,
    private val isDeleteTransaction: (Boolean) -> Unit,
) : DialogFragment() {

    private var _binding: FragmentTransactionDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)
        isCancelable = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
            )
            setGravity(Gravity.CENTER)
            attributes.windowAnimations = R.style.FullDialogAnimation
        }

        initDataText()
        annulationTransaction()
    }

    private fun initDataText() {
        binding.apply {
            textViewReceiptNumber.text =
                getString(R.string.transaction_list_text_receipt_number, receiptNumber)
            textViewTransactionIdentifier.text = getString(
                R.string.transaction_list_text_identifier,
                transactionIdentifier,
            )
            textViewStatusCode.text = getString(
                R.string.transaction_list_text_status_code,
                statusCode,
            )
            textViewStatusDescription.text = statusDescription

            imageButtonCancel.setOnClickListener { dismiss() }
            buttonVoidTransaction.setOnClickListener {
                showMessageDialogWarning(
                    R.string.transaction_authorization_text_title_dialog,
                    getString(R.string.transaction_annulation_text_success_annulation_warning),
                )
            }
        }
    }

    private fun annulationTransaction() {
        viewModel.showDialog.observe(viewLifecycleOwner) {
            when (it) {
                EnumResponseService.IS_SUCCESS -> {
                    showLoading(false)
                    isDeleteTransaction(true)
                    showMessageDialog(
                        R.string.transaction_authorization_text_title_dialog,
                        getString(R.string.transaction_annulation_text_success_annulation),
                    )
                }

                EnumResponseService.IS_FAILURE -> {
                    showLoading(false)
                    isDeleteTransaction(false)
                    showMessageDialog(
                        R.string.transaction_authorization_text_title_error_dialog,
                        getString(
                            R.string.transaction_list_text_error,
                        ),
                    )
                }

                EnumResponseService.IS_DEFAULT -> {
                    isDeleteTransaction(false)
                    Log.d("ISDEFAULT", "is default value enum")
                }
            }
        }
    }

    private fun showMessageDialog(
        title: Int,
        message: String,
    ) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(title))
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { view, _ ->
                view.dismiss()
                dismiss()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }

    private fun showMessageDialogWarning(
        title: Int,
        message: String,
    ) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(title))
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { view, _ ->
                showLoading(true)
                viewModel.onDataSelected(
                    idTransaction = idTransaction,
                    receiptNumber = receiptNumber,
                    transactionIdentifier = transactionIdentifier,
                )
                view.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { view, _ ->
                view.dismiss()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.isVisible = show
    }
}
