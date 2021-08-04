package com.example.mememaker.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mememaker.R
import com.example.mememaker.databinding.CatMakerFragmentBinding
import com.example.mememaker.ui.viewmodel.CatMakerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatMakerFragment : Fragment(R.layout.cat_maker_fragment) {

    private val viewModel: CatMakerViewModel by activityViewModels()

    private var _binding: CatMakerFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CatMakerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filterItems = resources.getStringArray(R.array.filter_types)
        val filterAdapter = ArrayAdapter(requireContext(), R.layout.list_item, filterItems)

        val colorItems = resources.getStringArray(R.array.colors)
        val colorAdapter = ArrayAdapter(requireContext(), R.layout.list_item, colorItems)
        val binding = CatMakerFragmentBinding.bind(view)
        with(binding) {
            memeText.setText(viewModel.text)
            size.setText(viewModel.textSize.toString())

            size.addTextChangedListener {
                viewModel.textSize = it.toString()
            }

            memeText.addTextChangedListener {
                viewModel.text = it.toString()
            }
            colorMenuInput.setOnItemClickListener { parent, view, position, id ->
                if (parent?.getItemAtPosition(position).toString() == "Default") viewModel.filter =
                    null
                else viewModel.textColor =
                    parent?.getItemAtPosition(position).toString().lowercase()
            }
            filterMenuInput.setAdapter(filterAdapter)
            filterMenuInput.setText("None", false)

            colorMenuInput.setAdapter(colorAdapter)
            colorMenuInput.setText("Default", false)


            filterMenuInput.setOnItemClickListener { parent, view, position, id ->
                if (parent?.getItemAtPosition(position).toString() == "None") viewModel.filter =
                    null
                else viewModel.filter = parent?.getItemAtPosition(position).toString().lowercase()
            }


            resultStyle.setOnCheckedChangeListener { group, checkedId ->
                val radioButton: RadioButton = group.findViewById(checkedId)
                viewModel.mediaType =
                    CatMakerViewModel.MediaType.valueOf(radioButton.text.toString().uppercase())

            }

            createButton.setOnClickListener {
                viewModel.getImage()
                findNavController().navigate(CatMakerFragmentDirections.actionCatMakerFragmentToCatViewFragment())
            }


        }


        viewModel.showTextAttributes.observe(viewLifecycleOwner) {
            binding.apply {
                colorMenu.isVisible = it
                colorMenu.isVisible = it
                size.isVisible = it
                sizeLabel.isVisible = it
            }
        }
    }

}
