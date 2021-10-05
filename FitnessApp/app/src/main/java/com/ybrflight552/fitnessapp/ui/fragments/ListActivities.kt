package com.ybrflight552.fitnessapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ybrflight552.fitnessapp.R
import com.ybrflight552.fitnessapp.ui.adapter.ListActivitiesAdapter
import com.ybrflight552.fitnessapp.ui.adapter.PaginationListener
import com.ybrflight552.fitnessapp.databinding.FragmentListActivityBinding
import com.ybrflight552.fitnessapp.internet.data.remoteActivity.ServerActivity
import com.ybrflight552.fitnessapp.utils.lib.ViewBindingFragment
import com.ybrflight552.fitnessapp.viewModel.AthleteRequestsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListActivities : ViewBindingFragment<FragmentListActivityBinding>(FragmentListActivityBinding::inflate) {

    private val viewModel: AthleteRequestsViewModel by viewModels()
    private var listAdapter: ListActivitiesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipe.setOnRefreshListener {
            getActivities()
            binding.swipe.isRefreshing = false
        }

        binding.ivHome.setOnClickListener {
            getAthleteDetailedInfo()
        }

        getActivities()
        binding.fabAddActivity.setOnClickListener {
            createNewActivity()
        }
    }

    // переход на фрагмент детальной инфо атлета
    private fun getAthleteDetailedInfo() {
        findNavController().navigate(ListActivitiesDirections.actionListActivitiesToProfileFragment())
    }


    private fun createNewActivity() {
        findNavController().navigate(ListActivitiesDirections.actionListActivitiesToCreateActivityFragment())
    }

    private fun getActivities() {
        lifecycleScope.launch {
            viewModel.onSuccess.observe(viewLifecycleOwner) {
                binding.barProgress.isVisible = it
            }
            viewModel.getAthleteActivity()
            viewModel.listActivities.observe(viewLifecycleOwner) {
                if(it.isNullOrEmpty()) {
                    emptyListListWaring(true)
                } else {
                    emptyListListWaring(false)
                    setAdapter(it)
                }
            }
        }
    }

    // если отсутствуют записи на сервере или в БД
    private fun emptyListListWaring(isList: Boolean) {
        if(isList) {
            binding.tvEmptyWaring.isVisible = isList
            binding.tvEmptyWaring.text = getString(R.string.no_activities)
        } else {
            binding.tvEmptyWaring.isVisible = isList
        }
    }


    private fun setAdapter(list: List<ServerActivity>) {
        listAdapter = ListActivitiesAdapter()
        if (listAdapter != null) {
            listAdapter?.updateList(list)
        }
        with(binding.rvActivities) {
            val scrollView: RecyclerView.OnScrollListener?
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
            scrollView = getScrollListener(LinearLayoutManager(requireContext()))
            addOnScrollListener(scrollView)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        listAdapter = null
    }

    private fun getScrollListener(manager: RecyclerView.LayoutManager): PaginationListener {
        return when (manager) {
            is LinearLayoutManager -> object : PaginationListener(manager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {

                }
            }
            else -> error("No such Layout")
        }
    }

}