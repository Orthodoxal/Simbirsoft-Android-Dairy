package com.example.diary.screens.main.diary_todo_list

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.R
import com.example.diary.app.Const.DAY
import com.example.diary.app.Const.HOUR
import com.example.diary.databinding.FragmentDiaryTodoListBinding
import com.example.diary.model.businesses.entities.Business
import com.example.diary.screens.base.BaseFragment
import com.example.diary.screens.custom_views.BusinessView
import com.example.diary.screens.main.business.BusinessFragment
import com.example.diary.utils.OnSwipeTouchListener
import java.util.*
import kotlin.properties.Delegates

class DiaryToDoListFragment : BaseFragment(R.layout.fragment_diary_todo_list) {

    override val viewModel by viewModels<DiaryToDoListViewModel>()
    private lateinit var binding: FragmentDiaryTodoListBinding
    private var actualMillis by Delegates.notNull<Long>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiaryTodoListBinding.bind(view)

        with(binding) {

            var viewMode = viewModel.getViewMode()
            setViewMode(viewMode)

            val millis = findNavController().currentBackStackEntry?.savedStateHandle?.get<Long>(
                BusinessFragment.ACTUAL_DATE
            )

            actualMillis =
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

            modeButton.setOnClickListener {
                viewMode = when (viewMode) {
                    ViewMode.LIST -> setViewMode(ViewMode.TABLE)
                    ViewMode.TABLE -> setViewMode(ViewMode.LIST)
                }
            }

            root.setOnTouchListener(object :
                OnSwipeTouchListener(this@DiaryToDoListFragment.context) {
                override fun onSwipeLeft() {
                    super.onSwipeLeft()
                    actualMillis += DAY
                    setActualInfo(actualMillis)
                }

                override fun onSwipeRight() {
                    super.onSwipeRight()
                    if (actualMillis - DAY > 0)
                        actualMillis -= DAY
                    setActualInfo(actualMillis)
                }
            })

            observeBusinessesByDay()
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
                currentMillis - DAY -> resources.getString(R.string.yesterday)
                currentMillis + DAY -> resources.getString(R.string.tomorrow)
                else -> viewModel.getDate(actualMillis)
            }

            viewModel.getFilteredBusinessesByDay(actualMillis)
        }
    }

    private fun observeBusinessesByDay() {
        viewModel.businessesByDate.observe(viewLifecycleOwner) { businessesList ->
            with(binding) {
                val adapter: BusinessesAdapter? = if (businessesList.isEmpty()) {
                    emptyTextView.visibility = View.VISIBLE
                    todoTable.visibility = View.INVISIBLE
                    null
                } else {
                    emptyTextView.visibility = View.INVISIBLE
                    todoTable.visibility = View.VISIBLE
                    context?.let { BusinessesAdapter(it, businessesList, onClickAction) }
                }
                todoListView.adapter = adapter

                todoTable.removeAllViews()
                for (i in 0 until 24) {
                    val businessesByHour =
                        businessesList.filter {
                            it.dateStart in actualMillis + (i * HOUR) until actualMillis + ((i + 1) * HOUR)
                        }

                    for (business in businessesByHour) {
                        val tableRowView = LayoutInflater.from(context)
                            .inflate(R.layout.table_row, todoTable, false) as TableRow
                        val view = tableRowView.findViewById<BusinessView>(R.id.businessViewRow)
                        view.setParams(business, onClickAction)
                        todoTable.addView(tableRowView)
                    }

                    if (i != 23) {
                        val tableRowLineView = LayoutInflater.from(context)
                            .inflate(R.layout.table_line_row, todoTable, false) as TableRow

                        val time = tableRowLineView.findViewById<TextView>(R.id.timeRow)
                        val hours = i + 1
                        time.text = if (hours < 10) "0$hours:00" else "$hours:00"

                        todoTable.addView(tableRowLineView)
                    }
                }
            }
        }
    }

    private fun setViewMode(viewMode: ViewMode): ViewMode {
        with(binding) {
            return when (viewMode) {
                ViewMode.LIST -> {
                    todoListView.visibility = View.VISIBLE
                    scrollView.visibility = View.GONE
                    viewModel.setViewMode(ViewMode.LIST)
                    modeButton.setImageResource(R.drawable.ic_table)
                    ViewMode.LIST
                }
                ViewMode.TABLE -> {
                    todoListView.visibility = View.GONE
                    scrollView.visibility = View.VISIBLE
                    viewModel.setViewMode(ViewMode.TABLE)
                    modeButton.setImageResource(R.drawable.ic_list)
                    ViewMode.TABLE
                }
            }
        }
    }

    private val onClickAction = { business: Business ->
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

}