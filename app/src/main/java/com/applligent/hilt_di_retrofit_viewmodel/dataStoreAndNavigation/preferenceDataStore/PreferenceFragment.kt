package com.applligent.hilt_di_retrofit_viewmodel.dataStoreAndNavigation.preferenceDataStore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import com.applligent.hilt_di_retrofit_viewmodel.databinding.FragmentPreferenceBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PreferenceFragment : Fragment() {
    private val binding : FragmentPreferenceBinding by lazy { FragmentPreferenceBinding.inflate(layoutInflater) }
    private lateinit var userManager: PreferenceUserManager
    private var age = 0
    var name = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        userManager = PreferenceUserManager(requireContext())
        buttonSave()
        observeData()
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun buttonSave() {
        binding.btnSave.setOnClickListener{
            name = binding.etName.text.toString()
            age = binding.etAge.text.toString().toInt()
            GlobalScope.launch {
                userManager.storeUser(age,name)
            }
        }
    }


    private fun observeData() {
        this.userManager.userAgeFlow.asLiveData().observe(requireActivity()){
            age = it
            binding.tvName.text = it.toString()
        }

        userManager.userNameFlow.asLiveData().observe(requireActivity()) {
            name = it
            binding.tvAge.text = it.toString()
        }
    }

}