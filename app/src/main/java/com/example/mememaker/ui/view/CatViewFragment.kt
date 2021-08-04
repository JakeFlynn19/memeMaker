package com.example.mememaker.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mememaker.R
import com.example.mememaker.databinding.CatViewFragmentBinding
import com.example.mememaker.ui.viewmodel.CatMakerViewModel
import com.example.mememaker.utils.BASE_URL
import com.example.mememaker.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import loadImage

@AndroidEntryPoint
class CatViewFragment : Fragment(R.layout.cat_view_fragment) {

    private val viewModel: CatMakerViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = CatViewFragmentBinding.bind(view)
        println(viewModel.showTextAttributes.value)
        with(binding) {
            viewModel.catImage.observe(viewLifecycleOwner, {
                println(it)
                when (it) {
                    is Resource.Success -> {
                        progressCircle.visibility = View.GONE
                        catResult.loadImage(BASE_URL + it.data.url.substring(1))
                    }

                    is Resource.Error -> {
                        progressCircle.visibility = View.GONE
                        Toast.makeText(context, it.errorMsg, Toast.LENGTH_LONG).show()
                    }
                    Resource.Loading -> progressCircle.visibility = View.VISIBLE
                }
            }
            )

            createNewButton.setOnClickListener {
                viewModel.clearData()
                findNavController().navigate(CatViewFragmentDirections.actionCatViewFragmentToCatMakerFragment())
            }
        }


    }

}
