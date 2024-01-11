package com.brayandev.credibancoapp.ui.searchTransaction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brayandev.credibancoapp.R
import com.brayandev.credibancoapp.domain.model.TransactionModel

class SearchTransactionAdapter(private val onItemSelected: (TransactionModel) -> Unit) :
    RecyclerView.Adapter<SearchTransactionViewHolder>() {

    private var transactionList: List<TransactionModel> = listOf()

    fun updateList(list: List<TransactionModel>) {
        transactionList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTransactionViewHolder {
        return SearchTransactionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_transaction, parent, false),
        )
    }

    override fun getItemCount(): Int = transactionList.size

    override fun onBindViewHolder(holder: SearchTransactionViewHolder, position: Int) {
        holder.binding(transactionList[position], onItemSelected)
    }
}
