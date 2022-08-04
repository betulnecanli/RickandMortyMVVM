package com.betulnecanli.rickandmortymvvm.ui.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.betulnecanli.rickandmortymvvm.R
import com.betulnecanli.rickandmortymvvm.adapter.RickandMortyPagingAdapter
import com.betulnecanli.rickandmortymvvm.data.models.Details
import com.betulnecanli.rickandmortymvvm.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), RickandMortyPagingAdapter.OnItemClickListener {


    private lateinit var binding : FragmentListBinding
    private lateinit var mAdapter : RickandMortyPagingAdapter
    private val viewModel : ListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentListBinding.bind(view)

        setupRecyclerView()
        loadingData()
    }


    fun setupRecyclerView(){
        mAdapter = RickandMortyPagingAdapter(this)
        binding.recyclerView.apply {
            adapter = mAdapter
            layoutManager  = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)



        }

    }

    fun loadingData(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted{
            viewModel.listData.collect{pagingData ->

                Log.d("aaa", "load: $pagingData")
                mAdapter.submitData(pagingData)

            }
        }
    }

    override fun onItemClickListener(details: Details) {
        viewModel.openCharacterDetails(details)
    }


}