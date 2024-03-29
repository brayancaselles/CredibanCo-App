package com.brayandev.credibancoapp.ui.transactionList

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.brayandev.credibancoapp.R
import com.brayandev.credibancoapp.databinding.FragmentTransactionListBinding
import com.brayandev.credibancoapp.ui.transactionList.adapter.TransactionAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionListFragment : Fragment() {

    private var _binding: FragmentTransactionListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionListViewModel by viewModels()
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTransactionListBinding.inflate(inflater, container, false)

        binding.apply {
            transactionAdapter = TransactionAdapter()
            recyclerViewTransaction.apply {
                layoutManager = GridLayoutManager(context, 1)
                adapter = transactionAdapter
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListAdapter()
        viewModel.getTransactionList()
    }

    private fun setListAdapter() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.transactions.collect { transactionsState ->
                    when (transactionsState) {
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
                            binding.textViewNoListTransaction.isVisible =
                                transactionsState.transactionList.isEmpty()
                            transactionAdapter.updateList(transactionsState.transactionList)
                            transactionAdapter.notifyDataSetChanged()
                        }

                        else -> {
                            showLoading(false)
                            showMessageDialog(
                                R.string.transaction_authorization_text_title_error_dialog,
                                R.string.transaction_list_text_error,
                            )
                        }
                    }
                }
            }
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
