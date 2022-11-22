package com.example.diary.screens.base

import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.diary.app.Singletons

/**
 * Base class for all fragments
 */
abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    /**
     * View-model that manages this fragment
     */
    abstract val viewModel: BaseViewModel

    /**
     * @param [message]
     * Standard make toast method
     */
    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}