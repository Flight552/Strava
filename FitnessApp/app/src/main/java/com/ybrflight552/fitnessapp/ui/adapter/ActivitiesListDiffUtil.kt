package com.ybrflight552.fitnessapp.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ybrflight552.fitnessapp.internet.data.remoteActivity.ServerActivity

class ActivitiesListDiffUtil : DiffUtil.ItemCallback<ServerActivity>() {
    override fun areItemsTheSame(oldItem: ServerActivity, newItem: ServerActivity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ServerActivity, newItem: ServerActivity): Boolean {
        return oldItem == newItem
    }
}