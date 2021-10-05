package com.ybrflight552.fitnessapp.ui.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.ybrflight552.fitnessapp.R
import androidx.constraintlayout.widget.ConstraintLayout


class OnboardingAdapter(val context: Context) : PagerAdapter() {

    private val sliderImages =
        listOf<Int>(
            R.drawable.ic_boarding_image_1,
            R.drawable.ic_boarding_image_2,
            R.drawable.ic_boarding_image_3
        )

    private val sliderTitles = listOf(
        context.resources.getString(R.string.onboarding_title_1),
        context.resources.getString(R.string.onboarding_title_2),
        context.resources.getString(R.string.onboarding_title_3)
    )

    private val sliderDesc = listOf(
        context.resources.getString(R.string.onboarding_desc_1),
        context.resources.getString(R.string.onboarding_desc_2),
        context.resources.getString(R.string.onboarding_desc_3)
    )


    override fun getCount(): Int {
        return sliderImages.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.onboarding_container, container, false)
        val imageView = view.findViewById<ImageView>(R.id.imageLayout)
        val textTitle = view.findViewById<TextView>(R.id.tvOnBoardingTitle)
        val textDesc = view.findViewById<TextView>(R.id.tvOnBoardingDescription)
        imageView.setImageResource(sliderImages[position])
        textTitle.text = sliderTitles[position]
        textDesc.text = sliderDesc[position]
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

}