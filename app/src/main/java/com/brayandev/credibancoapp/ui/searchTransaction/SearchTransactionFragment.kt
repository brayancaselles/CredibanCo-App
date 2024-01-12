package com.brayandev.credibancoapp.ui.searchTransaction

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.brayandev.credibancoapp.R
import com.brayandev.credibancoapp.databinding.FragmentSearchTransactionBinding
import com.brayandev.credibancoapp.domain.model.TransactionModel
import com.brayandev.credibancoapp.ui.searchTransaction.adapter.SearchTransactionAdapter
import com.brayandev.credibancoapp.ui.transactionDetail.TransactionDetailFragment
import com.brayandev.credibancoapp.ui.transactionList.TransactionListUIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchTransactionFragment : Fragment() {

    private var _binding: FragmentSearchTransactionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchTransactionViewModel by viewModels()
    private lateinit var searchTransactionAdapter: SearchTransactionAdapter
    private var transactionList: List<TransactionModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTransactionList()
        setListAdapter()
        initRecycler()
        initTextFilter()
    }

    private fun setListAdapter() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.transactions.collect { transactionState ->
                    when (transactionState) {
                        is TransactionListUIState.Error -> {
                            showLoading(false)
                            showMessageDialog(
                                R.string.transaction_authorization_text_title_error_dialog,
                                R.string.transaction_list_text_error,
                            )
                        }

                        TransactionListUIState.Loading -> {
                            showLoading(true)
                        }

                        is TransactionListUIState.Success -> {
                            showLoading(false)
                            transactionList = transactionState.transactionList
                            binding.textViewNoListTransaction.isVisible =
                                transactionState.transactionList.isEmpty()
                            searchTransactionAdapter.updateList(transactionList)
                            searchTransactionAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    private fun initRecycler() {
        binding.apply {
            searchTransactionAdapter = SearchTransactionAdapter {
                val dialog = TransactionDetailFragment(
                    it.id,
                    it.receiptNumber,
                    it.transactionIdentifier,
                    it.statusCode,
                    it.statusDescription,
                ) { isDeleted ->
                    if (isDeleted) {
                        showLoading(true)
                        viewModel.getTransactionList()
                    }
                }
                dialog.show(childFragmentManager, "DialogAnnulationTransaction")
            }
            recyclerViewSearchTransaction.apply {
                layoutManager = GridLayoutManager(context, 1)
                adapter = searchTransactionAdapter
            }
            showLoading(false)
        }
    }

    private fun initTextFilter() {
        binding.editTextFilter.addTextChangedListener { receiptNumberFilter ->
            val transactionListFilter = transactionList.filter { transaction ->
                transaction.receiptNumber.lowercase()
                    .contains(receiptNumberFilter.toString().lowercase())
            }
            if (receiptNumberFilter.toString().length > 0 && transactionListFilter.isEmpty()) {
                binding.textViewNoListTransaction.isVisible = true
                binding.textViewNoListTransaction.text =
                    getString(R.string.transaction_list_text_no_transaction)
            } else if (transactionListFilter.isEmpty()) {
                binding.textViewNoListTransaction.isVisible = true
                binding.textViewNoListTransaction.text =
                    getString(R.string.transaction_list_text_no_transaction_list)
            } else if (transactionListFilter.isNotEmpty()) {
                binding.textViewNoListTransaction.isVisible = false
            }

            searchTransactionAdapter.updateList(transactionListFilter)
        }
    }

    private fun showLoading(show: Boolean) {
        binding.viewStubCustomLoading.isVisible = show
    }

    private fun showMessageDialog(
        title: Int,
        message: Int,
    ) {
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
