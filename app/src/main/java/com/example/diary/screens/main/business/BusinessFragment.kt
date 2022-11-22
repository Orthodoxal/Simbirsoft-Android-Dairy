package com.example.diary.screens.main.business

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.diary.R
import com.example.diary.app.Const.HOUR
import com.example.diary.app.RequiredFieldIsEmptyException
import com.example.diary.databinding.FragmentBusinessBinding
import com.example.diary.model.businesses.entities.Business
import com.example.diary.model.businesses.entities.BusinessCreate
import com.example.diary.screens.base.BaseFragment
import java.util.*


class BusinessFragment : BaseFragment(R.layout.fragment_business) {

    override val viewModel by viewModels<BusinessViewModel>()
    private lateinit var binding: FragmentBusinessBinding
    private val args: BusinessFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBusinessBinding.bind(view)

        with(binding) {
            if (args.id == -1L) {
                // create new
                changeButton.visibility = View.INVISIBLE
                deleteButton.visibility = View.INVISIBLE

                val times = viewModel.getDefaultTimes(args.dateStart + HOUR)
                dateStartTextView.text = times.first.first
                timeStartTextView.text = times.first.second
                dateFinishTextView.text = times.second.first
                timeFinishTextView.text = times.second.second

                saveButton.setOnClickListener {
                    try {
                        if (businessNameTextEdit.text.toString() == "")
                            throw RequiredFieldIsEmptyException(resources.getString(R.string.business_name))
                        val start = viewModel.getTimes(
                            dateStartTextView.text.toString(),
                            timeStartTextView.text.toString()
                        )
                        val finish = viewModel.getTimes(
                            dateFinishTextView.text.toString(),
                            timeFinishTextView.text.toString()
                        )
                        viewModel.createBusiness(
                            BusinessCreate(
                                start,
                                finish,
                                businessNameTextEdit.text.toString(),
                                businessDescriptionTextEdit.text.toString()
                            )
                        )
                        toast(resources.getString(R.string.save_toast))
                        sendResultBack(start)
                        findNavController().popBackStack()
                    } catch (e: Exception) {
                        e.message?.let { message -> toast(message) }
                    }
                }

                requireActivity().onBackPressedDispatcher.addCallback(
                    viewLifecycleOwner,
                    getOnBackPressedCallback()
                )
            } else {
                // update or delete
                saveButton.visibility = View.INVISIBLE
                businessNameTextEdit.setText(args.name)
                businessDescriptionTextEdit.setText(args.description)
                dateStartTextView.text = viewModel.getDate(args.dateStart)
                timeStartTextView.text = viewModel.getTime(args.dateStart)
                dateFinishTextView.text = viewModel.getDate(args.dateFinish)
                timeFinishTextView.text = viewModel.getTime(args.dateFinish)

                changeButton.setOnClickListener {
                    try {
                        if (businessNameTextEdit.text.toString() == "")
                            throw RequiredFieldIsEmptyException(resources.getString(R.string.business_name))
                        val start = viewModel.getTimes(
                            dateStartTextView.text.toString(),
                            timeStartTextView.text.toString()
                        )
                        val finish = viewModel.getTimes(
                            dateFinishTextView.text.toString(),
                            timeFinishTextView.text.toString()
                        )
                        viewModel.updateBusiness(
                            Business(
                                args.id,
                                start,
                                finish,
                                businessNameTextEdit.text.toString(),
                                businessDescriptionTextEdit.text.toString()
                            )
                        )
                        toast(resources.getString(R.string.save_toast))
                        sendResultBack(start)
                        findNavController().popBackStack()
                    } catch (e: Exception) {
                        e.message?.let { message -> toast(message) }
                    }
                }

                deleteButton.setOnClickListener {
                    AlertDialog.Builder(this@BusinessFragment.context)
                        .setTitle(R.string.delete_business_alert_dialog_title)
                        .setMessage(R.string.delete_business_alert_dialog_message)
                        .setPositiveButton(android.R.string.yes) { _, _ ->
                            viewModel.deleteBusiness(args.id)
                            val start = viewModel.getTimes(
                                dateStartTextView.text.toString(),
                                timeStartTextView.text.toString()
                            )
                            toast(resources.getString(R.string.delete_toast, args.name))
                            sendResultBack(start)
                            findNavController().popBackStack()
                        }
                        .setNegativeButton(android.R.string.no) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                }

                requireActivity().onBackPressedDispatcher.addCallback(
                    viewLifecycleOwner,
                    getOnBackPressedCallback(createdNow = false)
                )
            }

            initDateTimeListeners()
        }
    }

    private fun getOnBackPressedCallback(createdNow: Boolean = true): OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val start = viewModel.getTimes(
                    binding.dateStartTextView.text.toString(),
                    binding.timeStartTextView.text.toString()
                )
                sendResultBack(if (createdNow) start - HOUR else start)
                findNavController().popBackStack()
            }
        }

    private fun sendResultBack(millis: Long) =
        findNavController().previousBackStackEntry?.savedStateHandle?.set(ACTUAL_DATE, millis)

    private fun initDateTimeListeners() {
        with(binding) {
            val cal = Calendar.getInstance()

            dateStartTextView.setOnClickListener {
                context?.let { context ->
                    DatePickerDialog(
                        context,
                        { _, year, monthOfYear, dayOfMonth ->
                            // action
                            cal.set(Calendar.YEAR, year)
                            cal.set(Calendar.MONTH, monthOfYear)
                            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            dateStartTextView.text =
                                viewModel.getDate(cal.timeInMillis, default = true)
                            if (cal.timeInMillis >= viewModel.getTimes(
                                    dateFinishTextView.text.toString(),
                                    "00:00"
                                )
                            ) {
                                dateFinishTextView.text = dateStartTextView.text
                            }
                        },
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }

            dateFinishTextView.setOnClickListener {
                context?.let { context ->
                    DatePickerDialog(
                        context,
                        { _, year, monthOfYear, dayOfMonth ->
                            // action
                            cal.set(Calendar.YEAR, year)
                            cal.set(Calendar.MONTH, monthOfYear)
                            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            dateFinishTextView.text =
                                viewModel.getDate(cal.timeInMillis, default = true)
                        },
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }

            timeStartTextView.setOnClickListener {
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)
                        timeStartTextView.text = viewModel.getTime(cal.timeInMillis, default = true)
                    }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
                ).show()
            }

            timeFinishTextView.setOnClickListener {
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)
                        timeFinishTextView.text =
                            viewModel.getTime(cal.timeInMillis, default = true)
                    }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
                ).show()
            }
        }
    }

    companion object {
        const val ACTUAL_DATE = "ACTUAL_DATE"
    }
}