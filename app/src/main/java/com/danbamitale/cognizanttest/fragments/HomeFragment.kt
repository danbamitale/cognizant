package com.danbamitale.cognizanttest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.danbamitale.cognizanttest.R
import com.danbamitale.cognizanttest.adapters.AlbumAdapter
import com.danbamitale.cognizanttest.databinding.FragmentHomeBinding
import com.danbamitale.cognizanttest.utils.BindingFragment
import com.danbamitale.cognizanttest.viewmodels.HomeFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var adapter: AlbumAdapter

    private val viewModel by viewModels<HomeFragmentViewModel>()

    override fun bindView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        attachToParent: Boolean
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater, parent, attachToParent)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }


        viewModel.liveAlbum.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.onError.observe(viewLifecycleOwner) {
            it?.let {
                Snackbar.make(binding.recyclerView, getString(R.string.failed_to_get_albums), Snackbar.LENGTH_LONG)
                    .setAction(R.string.refresh) {
                        viewModel.refresh()
                    }.show()
            }
        }

    }

}