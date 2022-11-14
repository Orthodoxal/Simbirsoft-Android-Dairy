package com.example.diary.screens.main.diary_todo_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.R
import com.example.diary.databinding.FragmentBusinessBinding
import com.example.diary.databinding.FragmentDiaryTodoListBinding
import com.example.diary.screens.base.BaseFragment

class DiaryToDoListFragment : BaseFragment(R.layout.fragment_diary_todo_list) {

    override val viewModel by viewModels<DiaryToDoListViewModel>()
    private lateinit var binding: FragmentDiaryTodoListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiaryTodoListBinding.bind(view)

        binding.buttonOpen.setOnClickListener {
            val direction = DiaryToDoListFragmentDirections.actionDiaryToDoListFragmentToBusinessFragment()
            findNavController().navigate(direction)
        }
    }

}