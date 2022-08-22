package com.example.talana.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.talana.data.local.models.Post

//BaseViewHolder, used in case, if multiple adapters are required.
abstract class BaseViewHolder<T>(itemView: View):RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: Post)
}