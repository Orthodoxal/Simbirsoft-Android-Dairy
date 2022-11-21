package com.example.diary.screens.main.diary_todo_list

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.R
import com.example.diary.databinding.FragmentDiaryTodoListBinding
import com.example.diary.model.businesses.entities.Business
import com.example.diary.screens.base.BaseFragment
import com.example.diary.screens.main.business.BusinessFragment
import com.example.diary.screens.main.business.BusinessViewModel
import com.example.diary.utils.OnSwipeTouchListener
import java.util.*
import kotlin.properties.Delegates

class DiaryToDoListFragment : BaseFragment(R.layout.fragment_diary_todo_list) {

    override val viewModel by viewModels<DiaryToDoListViewModel>()
    private lateinit var binding: FragmentDiaryTodoListBinding
    private var actualMillis by Delegates.notNull<Long>()
    private var viewMode = ViewMode.LIST

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiaryTodoListBinding.bind(view)

        with(binding) {

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
                    ViewMode.LIST -> {
                        todoListView.visibility = View.GONE
                        scrollView.visibility = View.VISIBLE
                        ViewMode.TABLE
                    }
                    ViewMode.TABLE -> {
                        todoListView.visibility = View.VISIBLE
                        scrollView.visibility = View.GONE
                        ViewMode.LIST
                    }
                }
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
                currentMillis - DiaryToDoListViewModel.DAY -> resources.getString(R.string.yesterday)
                currentMillis + DiaryToDoListViewModel.DAY -> resources.getString(R.string.tomorrow)
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

                todoTable.removeAllViews()
                val ms = actualMillis
                for (i in 0 until 24) {

                    val businessesByHour =
                        businessesList.filter {
                            it.dateStart in actualMillis + (i * BusinessViewModel.HOUR) until actualMillis + ((i + 1) * BusinessViewModel.HOUR)
                        }

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

                    for (business in businessesByHour) {
                        val tableRowView = LayoutInflater.from(context)
                            .inflate(R.layout.table_row, todoTable, false) as TableRow

                        val view = tableRowView.findViewById<BusinessView>(R.id.businessViewRow)

                        view.setParams(business, onClickAction)
                        /*view.setName(business.name)
                        val timeStart = viewModel.getTime(business.dateStart)
                        val timeFinish = viewModel.getTime(business.dateFinish)
                        view.setTime("$timeStart - $timeFinish")
                        view.setOnClickListener { onClickAction(business) }*/

                        /*val view = LayoutInflater.from(context)
                            .inflate(R.layout.business_view, tableRowView, false)

                        val name = view.findViewById<TextView>(R.id.name)
                        val time = view.findViewById<TextView>(R.id.time)

                        val timeStart = viewModel.getTime(business.dateStart)
                        val timeFinish = viewModel.getTime(business.dateFinish)
                        name.text = business.name
                        time.text = "$timeStart - $timeFinish"

                        view.setOnClickListener { onClickAction(business) }*/
                        //tableRowView.addView(view)

                        todoTable.addView(tableRowView)
                    }

                    /*val listView = tableRowView.findViewById<ListView>(R.id.todoRowListView)

                    val temp = mutableListOf<Business>()

                    for (business in businessesList) {
                        val start = ms + (i * BusinessViewModel.HOUR)
                        val end = ms + ((i + 1) * BusinessViewModel.HOUR)
                        if (business.dateStart in start until end)
                            temp.add(business)
                    }

                    val businessesByHour =
                        businessesList.filter {
                            it.dateStart in actualMillis + (i * BusinessViewModel.HOUR) until actualMillis + ((i + 1) * BusinessViewModel.HOUR)
                        }
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
                    val adapterByHour =
                        context?.let { BusinessesAdapter(it, businessesByHour, onClickAction) }
                    listView.adapter = adapterByHour*/
                    //todoTable.addView(tableRowView)

                    if (i != 23) {
                        val tableRowLineView = LayoutInflater.from(context)
                            .inflate(R.layout.table_line_row, todoTable, false) as TableRow
                        todoTable.addView(tableRowLineView)
                    }
                }

            }
        }
    }

}