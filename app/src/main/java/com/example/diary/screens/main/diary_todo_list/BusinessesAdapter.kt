package com.example.diary.screens.main.diary_todo_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.diary.R
import com.example.diary.model.businesses.entities.Business
import java.text.SimpleDateFormat
import java.util.*


class BusinessesAdapter(
    context: Context,
    private val businessList: List<Business>,
    private val onClickAction: (Business) -> Unit
) :
    ArrayAdapter<Business>(context, R.layout.business_view, businessList) {
    private val simpleTimeFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        .apply { timeZone = TimeZone.getTimeZone("GMT") }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.business_view, parent, false)

        val name = view.findViewById<TextView>(R.id.name)
        val description = view.findViewById<TextView>(R.id.time)

        val timeStart = simpleTimeFormat.format(businessList[position].dateStart)
        val timeFinish = simpleTimeFormat.format(businessList[position].dateFinish)

        name.text = businessList[position].name
        description.text = "$timeStart - $timeFinish"

        view.setOnClickListener { onClickAction(businessList[position]) }

        return view
    }
}