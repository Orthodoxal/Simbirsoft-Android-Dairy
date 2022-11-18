package com.example.diary.screens.base

import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.diary.app.Singletons

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {
    abstract val viewModel: BaseViewModel

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}