package com.example.phase12

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapt(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return  6
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> Text()
            1 -> Choices()
            2 -> ToDo()
            3 -> Profile()
            4 -> Colors()
            5 -> LowerRight()
            else -> Text()
        }
    }
}