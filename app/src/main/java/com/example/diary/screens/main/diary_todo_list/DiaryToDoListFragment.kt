package com.example.diary.screens.main.diary_todo_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.R
import com.example.diary.databinding.FragmentDiaryTodoListBinding
import com.example.diary.model.businesses.entities.Business
import com.example.diary.screens.base.BaseFragment

class DiaryToDoListFragment : BaseFragment(R.layout.fragment_diary_todo_list) {

    override val viewModel by viewModels<DiaryToDoListViewModel>()
    private lateinit var binding: FragmentDiaryTodoListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiaryTodoListBinding.bind(view)

        //viewModel.deleteAllBusinesses()

        with(binding) {
            val businessesList = viewModel.getAllBusinesses()
            if (businessesList.isEmpty()) {
                emptyTextView.visibility = View.VISIBLE
            } else {
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
                val adapter = context?.let { BusinessesAdapter(it, businessesList, onClickAction) }
                todoListView.adapter = adapter
            }

            buttonOpen.setOnClickListener {
                val direction =
                    DiaryToDoListFragmentDirections.actionDiaryToDoListFragmentToBusinessFragment(
                        null,
                        null
                    )
                findNavController().navigate(direction)
            }
        }
    }

}