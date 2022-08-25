package com.betulnecanli.rickandmortymvvm.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.betulnecanli.rickandmortymvvm.R
import com.betulnecanli.rickandmortymvvm.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding : FragmentDetailBinding
    private val viewModel : DetailViewModel by viewModels()
    val args : DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         binding = FragmentDetailBinding.bind(view)

        binding.apply {

            charName.text = args.details.name
            val imgLink = args.details.image
            charImg.load(imgLink){
                crossfade(true)
                crossfade(1000)
            }
            if (args.details.status == ("Alive")) charStatus.setBackgroundResource(R.drawable.ic_alive)
            else charStatus.setBackgroundResource(R.drawable.ic_dead)
            // charStatus.


        }






    }




}