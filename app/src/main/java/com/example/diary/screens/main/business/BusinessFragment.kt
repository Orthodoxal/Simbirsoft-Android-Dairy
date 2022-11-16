package com.example.diary.screens.main.business

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.R
import com.example.diary.databinding.FragmentBusinessBinding
import com.example.diary.model.businesses.entities.BusinessCreate
import com.example.diary.screens.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.*

class BusinessFragment : BaseFragment(R.layout.fragment_business) {

    override val viewModel by viewModels<BusinessViewModel>()
    private lateinit var binding: FragmentBusinessBinding

    private val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        .apply { timeZone = TimeZone.getTimeZone("GMT") }
    private val simpleTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        .apply { timeZone = TimeZone.getTimeZone("GMT") }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBusinessBinding.bind(view)

        with(binding) {
            buttonChange.visibility = View.INVISIBLE
            buttonDelete.visibility = View.INVISIBLE
            buttonSave.visibility = View.VISIBLE
        }

        with(binding) {

            // only create
            simpleDateFormat.timeZone = TimeZone.getDefault()
            simpleTimeFormat.timeZone = TimeZone.getDefault()
            val currentDateTime = System.currentTimeMillis()
            val currentDateTimeNextHour = currentDateTime + HOUR
            dateStartTextView.text = simpleDateFormat.format(currentDateTime)
            timeStartTextView.text = simpleTimeFormat.format(currentDateTime)
            dateFinishTextView.text = simpleDateFormat.format(currentDateTimeNextHour)
            timeFinishTextView.text = simpleTimeFormat.format(currentDateTimeNextHour)
            simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
            simpleTimeFormat.timeZone = TimeZone.getTimeZone("GMT")

            buttonSave.setOnClickListener {
                val start = getTimes(dateStartTextView.text.toString(), timeStartTextView.text.toString())
                val finish = getTimes(dateFinishTextView.text.toString(), timeFinishTextView.text.toString())
                viewModel.createBusiness(
                    BusinessCreate(
                        start,
                        finish,
                        businessNameTextEdit.text.toString(),
                        businessDescriptionTextEdit.text.toString()
                    )
                )
            }
        }

        // viewModel.deleteBusiness(1)

        /*viewModel.updateBusiness(Business(
            1,
            100000,
            1000000,
            "testX",
            "des_test2"
        ))*/

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getTimes(date: String, time: String): Long {
        val dateParse = simpleDateFormat.parse(date)
        val timeParse = simpleTimeFormat.parse(time)
        return if (dateParse != null && timeParse != null) dateParse.time + timeParse.time
        else throw Exception()
    }

    companion object {
        const val HOUR: Long = 3600 * 1000
    }
}