package com.brayandev.credibancoapp.ui.transactionDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.brayandev.credibancoapp.databinding.FragmentTransactionDetailBinding

class TransactionDetailFragment : Fragment() {

    private var _binding: FragmentTransactionDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}
