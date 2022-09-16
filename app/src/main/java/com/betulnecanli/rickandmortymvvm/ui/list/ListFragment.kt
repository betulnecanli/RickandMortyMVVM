package com.betulnecanli.rickandmortymvvm.ui.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.betulnecanli.rickandmortymvvm.R
import com.betulnecanli.rickandmortymvvm.adapter.RickandMortyPagingAdapter
import com.betulnecanli.rickandmortymvvm.data.models.Details
import com.betulnecanli.rickandmortymvvm.databinding.FragmentListBinding
import com.betulnecanli.rickandmortymvvm.utils.getTextChipChecked
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list), RickandMortyPagingAdapter.OnItemClickListener {


    private lateinit var binding : FragmentListBinding
    private lateinit var mAdapter : RickandMortyPagingAdapter
    private val viewModel : ListViewModel by viewModels()
    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_open_anim) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_close_anim) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.from_bottom_anim) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.to_bottom_anim) }
    private var filterButtonClicked : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        filterButtonClicked = false
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentListBinding.bind(view)


        binding.floatingActionButton.setOnClickListener {
            filterButtonClicked()
        }



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

        //Search part
        binding.charSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchCharacter(query)
                    binding.charSearchView.clearFocus()
                    filterButtonClicked = false
                }
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchCharacter(newText)
                    filterButtonClicked = false

                }
                return true
            }

        })
        //Search part




        //Filter Part
        binding.statusChipsG.setOnCheckedStateChangeListener { group, checkedIds ->
            //if we choose the chip that's not the "reset" one,
            //the string we got from the extension code is
            //going to be sent to viewModel for filtering
            if(group.getTextChipChecked() != "Reset"){
                viewModel.statusChoose(group.getTextChipChecked())
                //this line of code makes the list scrolls
                // to top after click to chip
                binding.recyclerView.scrollToPosition(0)

            }
             //If we choose "reset", we reser the status filter
            // and see all the data
             else if(group.getTextChipChecked() == "Reset"){
                 viewModel.statusChoose("")
                 binding.recyclerView.scrollToPosition(0)

            }
            else{
                binding.recyclerView.scrollToPosition(0)
            }


            //with that line float button going to close after we click to the any chip
            filterButtonClicked()

        }





    }



    fun filterButtonClicked(){
        setVisibility(filterButtonClicked)
        setAnimation(filterButtonClicked)
        filterButtonClicked = !filterButtonClicked
    }


    fun setVisibility(clicked : Boolean){
        if(!clicked){
            binding.chipAlive.visibility = View.VISIBLE
            binding.chipDead.visibility = View.VISIBLE
            binding.chipUnknown.visibility = View.VISIBLE
            binding.chipClear.visibility = View.VISIBLE

        }
        else{
            binding.chipAlive.visibility = View.GONE
            binding.chipDead.visibility = View.GONE
            binding.chipUnknown.visibility = View.GONE
            binding.chipClear.visibility = View.GONE
        }

    }

    fun setAnimation(clicked : Boolean){
        if(!clicked){
            binding.chipAlive.startAnimation(fromBottom)
            binding.chipDead.startAnimation(fromBottom)
            binding.chipUnknown.startAnimation(fromBottom)
            binding.chipClear.startAnimation(fromBottom)
            binding.floatingActionButton.startAnimation(rotateOpen)
        }
        else{
            binding.chipAlive.startAnimation(toBottom)
            binding.chipDead.startAnimation(toBottom)
            binding.chipUnknown.startAnimation(toBottom)
            binding.chipClear.startAnimation(toBottom)
            binding.floatingActionButton.startAnimation(rotateClose)
        }
    }


    override fun onItemClickListener(details: Details) {
        viewModel.openCharacterDetails(details)
    }


}

