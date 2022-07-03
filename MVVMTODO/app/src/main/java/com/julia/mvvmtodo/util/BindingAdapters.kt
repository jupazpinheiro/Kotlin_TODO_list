package com.julia.mvvmtodo.util

import android.graphics.Color
import android.renderscript.RenderScript
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.sql.Timestamp
import java.text.DateFormat

@BindingAdapter("setPrioridade")
fun setPrioridade(view: TextView, priority: Int){
    when (priority){
        0->{
            view.text = "Pra ontem"
            view.setTextColor(Color.RED)
        }
        1->{
            view.text = "Pra agora"
            view.setTextColor(Color.YELLOW)
        }
        else->{
            view.text = "Pra depois"
            view.setTextColor(Color.BLUE)
        }

    }
}

@BindingAdapter("setTimestamp")
fun setTimestamp(view:TextView, timestamp: Long){
    view.text = DateFormat.getInstance().format(timestamp)
}