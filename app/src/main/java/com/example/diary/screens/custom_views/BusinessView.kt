package com.example.diary.screens.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.diary.R
import com.example.diary.app.Singletons
import com.example.diary.databinding.BusinessViewBinding
import com.example.diary.model.businesses.entities.Business
import com.example.diary.model.date_time.IDateTimeFormatter

class BusinessView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val dateTimeFormatter: IDateTimeFormatter = Singletons.dateTimeFormatter
    private val binding: BusinessViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.business_view, this, true)
        binding = BusinessViewBinding.bind(this)
    }

    fun setParams(business: Business, onClickAction: (Business) -> Unit) {
        binding.name.text = business.name
        val timeStart = dateTimeFormatter.getTime(business.dateStart)
        val timeFinish = dateTimeFormatter.getTime(business.dateFinish)
        binding.time.text = "$timeStart - $timeFinish"
        this.setOnClickListener {
            onClickAction.invoke(business)
        }
    }

}