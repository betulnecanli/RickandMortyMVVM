package com.betulnecanli.rickandmortymvvm.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.betulnecanli.rickandmortymvvm.R
import com.betulnecanli.rickandmortymvvm.adapter.RickandMortyPagingAdapter
import com.betulnecanli.rickandmortymvvm.data.models.Details
import com.betulnecanli.rickandmortymvvm.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint
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

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.taskEvent.collect { event ->
                when(event){
                    is ListViewModel.TaskEvent.NavigateToDetailScreen ->
                    {
                    val action = ListFragmentDirections.actionListFragmentToDetailFragment(event.details)
                        findNavController().navigate(action)
                    }
                    else -> {}
                }


            }
        }
        mAdapter = RickandMortyPagingAdapter(this)
        binding.recyclerView.apply {
            adapter = mAdapter
            layoutManager  = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)



        }

       viewModel.characters.observe(viewLifecycleOwner){
           mAdapter.submitData(viewLifecycleOwner.lifecycle, it)
       }

        binding.charSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchCharacter(query)
                    binding.charSearchView.clearFocus()
                }
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchCharacter(newText)

                }
                return true
            }

        })


    }








    override fun onItemClickListener(details: Details) {
        viewModel.openCharacterDetails(details)
    }


}

