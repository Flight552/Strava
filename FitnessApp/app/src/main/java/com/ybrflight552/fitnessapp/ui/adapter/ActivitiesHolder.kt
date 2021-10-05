package com.ybrflight552.fitnessapp.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ybrflight552.fitnessapp.R
import com.ybrflight552.fitnessapp.internet.data.remoteActivity.ServerActivity

class ActivitiesHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvName = view.findViewById<TextView>(R.id.tvUserName)
    private val tvType = view.findViewById<TextView>(R.id.tvType)
    private val tvDate = view.findViewById<TextView>(R.id.tvDate)
    private val ivType = view.findViewById<ImageView>(R.id.ivImageType)


    fun bind(serverActivity: ServerActivity) {
        tvName.text = serverActivity.name
        tvType.text = serverActivity.type
        tvDate.text = serverActivity.start_date_local
        when (serverActivity.type) {
            com.ybrflight552.fitnessapp.internet.data.ActivityType.AlpineSki.name -> glideImage(R.drawable.icons8skiing)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.BackcountrySki.name -> glideImage(R.drawable.icons8skiing)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Canoeing.name -> glideImage(R.drawable.icons8canoe)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Crossfit.name -> glideImage(R.drawable.icons8pullups)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.EBikeRide.name -> glideImage(R.drawable.iconscycling)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Elliptical.name -> glideImage(R.drawable.icons8rowingmachine)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Golf.name -> glideImage(R.drawable.icons8golf)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Handcycle.name -> glideImage(R.drawable.icons8rowingmachine)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Hike.name -> glideImage(R.drawable.icons8trekking)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.IceSkate.name -> glideImage(R.drawable.icons8skating)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.InlineSkate.name -> glideImage(R.drawable.icons8skating)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Kayaking.name -> glideImage(R.drawable.icons8canoe)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Kitesurf.name -> glideImage(R.drawable.icons8kitesurfing)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.NordicSki.name ->glideImage( R.drawable.icons8skiing)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Ride.name -> glideImage(R.drawable.iconscycling)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.RockClimbing.name -> glideImage(R.drawable.icons8pullups)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.RollerSki.name -> glideImage(R.drawable.icons8skating)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Rowing.name -> glideImage(R.drawable.icons8rowingmachine)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Run.name -> glideImage(R.drawable.icons8exercise)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Sail.name ->glideImage( R.drawable.icons8sailing)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Skateboard.name -> glideImage(R.drawable.icons8skateboarding)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Snowboard.name -> glideImage(R.drawable.icons8skiing)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Snowshoe.name -> glideImage(R.drawable.icons8skiing)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Soccer.name -> glideImage(R.drawable.icons8soccer)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.StairStepper.name -> glideImage(R.drawable.icons8staircase)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.StandUpPaddling.name -> glideImage(R.drawable.icons8stepper)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Surfing.name -> glideImage(R.drawable.icons8surfing)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Swim.name -> glideImage(R.drawable.icons8swimmer)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Velomobile.name -> glideImage(R.drawable.icons8velo)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.VirtualRide.name -> glideImage(R.drawable.iconscycling)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.VirtualRun.name ->glideImage( R.drawable.icons8exercise)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Walk.name -> glideImage(R.drawable.icons8walking)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.WeightTraining.name -> glideImage(R.drawable.icons8benchpress)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Wheelchair.name -> glideImage(R.drawable.icons8handicapped)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Windsurf.name -> glideImage(R.drawable.icons8windsurf)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Workout.name -> glideImage(R.drawable.icons8gymnastics)
            com.ybrflight552.fitnessapp.internet.data.ActivityType.Yoga.name -> glideImage(R.drawable.icons8gymnastics)
        }
    }

    private fun glideImage(type: Int) {
        Glide.with(itemView)
            .load(type)
            .into(ivType)
    }
}