package com.brayandev.credibancoapp.ui.transactionList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brayandev.credibancoapp.R
import com.brayandev.credibancoapp.domain.model.TransactionModel
import com.brayandev.credibancoapp.ui.transactionList.adapter.TransactionViewHolder

class TransactionAdapter() : RecyclerView.Adapter<TransactionViewHolder>() {

    private var transactionList: List<TransactionModel> = listOf()

    fun updateList(list: List<TransactionModel>) {
        transactionList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false),
        )
    }

    override fun getItemCount(): Int = transactionList.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.binding(transactionList[position])
    }
}
