package com.brayandev.credibancoapp.ui.searchTransaction.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.brayandev.credibancoapp.R
import com.brayandev.credibancoapp.databinding.ItemSearchTransactionBinding
import com.brayandev.credibancoapp.domain.model.TransactionModel

class SearchTransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSearchTransactionBinding.bind(view)

    fun binding(transactionInfo: TransactionModel, onItemSelected: (TransactionModel) -> Unit) {
        val context = binding.textViewReceiptNumber.context

        binding.apply {
            textViewReceiptNumber.text = context.getString(
                R.string.transaction_list_text_receipt_number,
                transactionInfo.receiptNumber,
            )
            textViewReceiptNumber.setOnClickListener { onItemSelected(transactionInfo) }
        }
    }
}
