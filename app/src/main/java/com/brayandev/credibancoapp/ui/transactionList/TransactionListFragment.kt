package com.brayandev.credibancoapp.ui.transactionList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.brayandev.credibancoapp.databinding.FragmentTransactionListBinding

class TransactionListFragment : Fragment() {

    private var _binding: FragmentTransactionListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTransactionListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
