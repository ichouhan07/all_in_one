package com.applligent.hilt_di_retrofit_viewmodel.dataStoreAndNavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.applligent.hilt_di_retrofit_viewmodel.R
import com.applligent.hilt_di_retrofit_viewmodel.databinding.FragmentDataStoreBinding

class DataStoreFragment : Fragment() {
    private val binding : FragmentDataStoreBinding by lazy { FragmentDataStoreBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding.toExternalFragmentBtn.setOnClickListener {
            findNavController().navigate(R.id.action_dataStoreFragment_to_externalStorageFragment,null)
        }
        binding.toInternalFragmentBtn.setOnClickListener {
            findNavController().navigate(R.id.action_dataStoreFragment_to_internalStorageFragment,null)
        }
        binding.toPreferenceFragmentBtn.setOnClickListener {
            findNavController().navigate(R.id.action_dataStoreFragment_to_preferenceFragment,null)
        }
        return binding.root
    }

}