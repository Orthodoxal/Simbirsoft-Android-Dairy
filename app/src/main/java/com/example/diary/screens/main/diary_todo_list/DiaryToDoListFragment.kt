package com.example.diary.screens.main.diary_todo_list

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.R
import com.example.diary.databinding.FragmentDiaryTodoListBinding
import com.example.diary.model.businesses.entities.Business
import com.example.diary.screens.base.BaseFragment
import com.example.diary.screens.main.business.BusinessFragment
import com.example.diary.utils.OnSwipeTouchListener
import java.util.*

class DiaryToDoListFragment : BaseFragment(R.layout.fragment_diary_todo_list) {

    override val viewModel by viewModels<DiaryToDoListViewModel>()
    private lateinit var binding: FragmentDiaryTodoListBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiaryTodoListBinding.bind(view)

        with(binding) {

            val millis = findNavController().currentBackStackEntry?.savedStateHandle?.get<Long>(
                BusinessFragment.ACTUAL_DATE
            )

            var actualMillis =
                if (millis == null)
                    viewModel.getTimes(
                        viewModel.getDate(
                            System.currentTimeMillis(),
                            default = true
                        ), "00:00"
                    )
                else {
                    viewModel.getTimes(
                        viewModel.getDate(
                            millis
                        ), "00:00"
                    )
                }

            setActualInfo(actualMillis)

            val cal = Calendar.getInstance()

            dateTextView.setOnClickListener {
                context?.let { context ->
                    DatePickerDialog(
                        context,
                        { _, year, monthOfYear, dayOfMonth ->
                            // action
                            cal.set(Calendar.YEAR, year)
                            cal.set(Calendar.MONTH, monthOfYear)
                            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            val chosenMillis = cal.timeInMillis
                            dateTextView.text =
                                viewModel.getDate(chosenMillis, default = true)
                            actualMillis = chosenMillis
                            setActualInfo(actualMillis)
                        },
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }

            addButton.setOnClickListener {
                val direction =
                    DiaryToDoListFragmentDirections.actionDiaryToDoListFragmentToBusinessFragment(
                        actualMillis + viewModel.getHour(
                            viewModel.getTime(
                                System.currentTimeMillis(),
                                default = true
                            )
                        ),
                        null,
                        null
                    )
                findNavController().navigate(direction)
            }

            root.setOnTouchListener(object :
                OnSwipeTouchListener(this@DiaryToDoListFragment.context) {
                override fun onSwipeLeft() {
                    super.onSwipeLeft()
                    actualMillis += DiaryToDoListViewModel.DAY
                    setActualInfo(actualMillis)
                }

                override fun onSwipeRight() {
                    super.onSwipeRight()
                    if (actualMillis - DiaryToDoListViewModel.DAY > 0)
                        actualMillis -= DiaryToDoListViewModel.DAY
                    setActualInfo(actualMillis)
                }
            })
        }
    }

    private fun setActualInfo(actualMillis: Long) {
        with(binding) {
            val currentMillis = viewModel.getTimes(
                viewModel.getDate(
                    System.currentTimeMillis(),
                    default = true
                ), "00:00"
            )
            dateTextView.text = when (actualMillis) {
                currentMillis -> resources.getString(R.string.today)
                currentMillis - DiaryToDoListViewModel.DAY -> resources.getString(R.string.yesterday)
                currentMillis + DiaryToDoListViewModel.DAY -> resources.getString(R.string.tomorrow)
                else -> viewModel.getDate(actualMillis)
            }

            val businessesList = viewModel.getFilteredBusinessesByDay(actualMillis)

            val adapter: BusinessesAdapter? = if (businessesList.isEmpty()) {
                emptyTextView.visibility = View.VISIBLE
                null
            } else {
                emptyTextView.visibility = View.INVISIBLE
                val onClickAction = { business: Business ->
                    val direction =
                        DiaryToDoListFragmentDirections.actionDiaryToDoListFragmentToBusinessFragment(
                            id = business.id,
                            dateStart = business.dateStart,
                            dateFinish = business.dateFinish,
                            name = business.name,
                            description = business.description,
                        )
                    findNavController().navigate(direction)
                }
                context?.let { BusinessesAdapter(it, businessesList, onClickAction) }
            }
            todoListView.adapter = adapter
        }
    }

}