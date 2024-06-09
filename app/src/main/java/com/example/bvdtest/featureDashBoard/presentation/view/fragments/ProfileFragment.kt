package com.example.bvdtest.featureDashBoard.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.bvdtest.R
import com.example.bvdtest.databinding.FragmentProfileBinding
import com.example.bvdtest.featureDashBoard.presentation.viewModel.FuelSiteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfileFragment : Fragment() {

    private val userProfileViewModel: FuelSiteViewModel by activityViewModels()
    private lateinit var binding: FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
                userProfileViewModel.getUserProfileDetails()

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userProfileViewModel.userProfileDetails.observe(viewLifecycleOwner, Observer { userProfile ->
            binding.apply {
                tvProfileFirstNameDetail.text = userProfile.first_name
                tvProfileLastNameDetail.text = userProfile.last_name
                tvProfileEmailDetail.text = userProfile.email
                tvProfilePhoneDetail.text = userProfile.phone
            }
        })

    }

}