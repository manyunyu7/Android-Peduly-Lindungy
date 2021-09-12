package com.feylabs.lindungipeduli.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tourismapp.core.data.Resource
import com.feylabs.lindungipeduli.core.domain.model.News
import com.feylabs.lindungipeduli.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val newsAdapter by lazy { NewsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvNews.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = newsAdapter
        }

        newsAdapter.setAdapterInterfacez(obj = object  : NewsAdapter.NewsItemInterface{
            override fun onclick(model: News?) {

            }

        })


        homeViewModel.news.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.toMutableList()?.let { it1 -> newsAdapter.setWithNewData(it1) }
                    newsAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {

                }
            }

        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}