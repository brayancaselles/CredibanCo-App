package com.brayandev.credibancoapp.ui.searchTransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.brayandev.credibancoapp.databinding.FragmentSearchTransactionBinding

class SearchTransactionFragment : Fragment() {

    private var _binding: FragmentSearchTransactionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchTransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSearchTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }
}
