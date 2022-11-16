package com.example.diary.screens.main.business

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.R
import com.example.diary.databinding.FragmentBusinessBinding
import com.example.diary.model.businesses.entities.Business
import com.example.diary.model.businesses.entities.BusinessCreate
import com.example.diary.screens.base.BaseFragment

class BusinessFragment : BaseFragment(R.layout.fragment_business) {

    override val viewModel by viewModels<BusinessViewModel>()
    private lateinit var binding: FragmentBusinessBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBusinessBinding.bind(view)

        /*viewModel.createBusiness(BusinessCreate(
            1000,
            1000000,
            "test2",
            "des_test2"
        ))*/

        // viewModel.deleteBusiness(1)

        /*viewModel.updateBusiness(Business(
            1,
            100000,
            1000000,
            "testX",
            "des_test2"
        ))*/

        val test = viewModel.getAllBusinesses()

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}