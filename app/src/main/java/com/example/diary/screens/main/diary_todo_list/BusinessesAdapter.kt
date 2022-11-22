package com.example.diary.screens.main.diary_todo_list

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.diary.R
import com.example.diary.model.businesses.entities.Business
import com.example.diary.screens.custom_views.BusinessView

/**
 *  [BusinessesAdapter] based on [ArrayAdapter] adapts Businesses to BusinessViews
 */
class BusinessesAdapter(
    context: Context,
    private val businessList: List<Business>,
    private val onClickAction: (Business) -> Unit
) : ArrayAdapter<Business>(context, R.layout.business_view, businessList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = if (convertView == null) BusinessView(context) else convertView as BusinessView
        view.setParams(businessList[position], onClickAction)
        return view
    }
}