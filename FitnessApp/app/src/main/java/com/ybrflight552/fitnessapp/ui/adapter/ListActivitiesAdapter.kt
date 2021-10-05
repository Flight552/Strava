package com.ybrflight552.fitnessapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.ybrflight552.fitnessapp.R
import com.ybrflight552.fitnessapp.internet.data.remoteActivity.ServerActivity

class ListActivitiesAdapter : RecyclerView.Adapter<ActivitiesHolder>() {

    private val diffUtil = AsyncListDiffer<ServerActivity>(this, ActivitiesListDiffUtil())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_activity, parent, false)
        return ActivitiesHolder(view)
    }

    override fun onBindViewHolder(holder: ActivitiesHolder, position: Int) {
        val item = diffUtil.currentList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    fun updateList(list: List<ServerActivity>) {
        diffUtil.submitList(list)
    }

}