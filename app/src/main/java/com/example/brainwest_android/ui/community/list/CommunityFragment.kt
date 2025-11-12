package com.example.brainwest_android.ui.community.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.brainwest_android.R
import com.example.brainwest_android.data.repository.CommunityRepository
import com.example.brainwest_android.data.state.State
import com.example.brainwest_android.databinding.FragmentCommunityBinding
import com.example.brainwest_android.ui.adapter.CommunityAdapter
import com.example.brainwest_android.ui.parent.MainActivity
import com.example.brainwest_android.utils.Helper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.brainwest_android.ui.parent.CommunityActivity
import com.example.brainwest_android.ui.parent.ConsultationActivity

class CommunityFragment : Fragment() {
    lateinit var binding: FragmentCommunityBinding

    private val viewModel: CommunityViewModel by viewModels {
       CommunityViewModelFactory(CommunityRepository())
    }

    lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    lateinit var adapter: CommunityAdapter
    var group_id = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCommunityBinding.inflate(inflater, container, false)

        binding.imgHistory.setOnClickListener {
            val intent = Intent(requireContext(), CommunityActivity::class.java)
            intent.putExtra("isDetail", false)
            startActivity(intent)
            requireActivity().overridePendingTransition(
                R.anim.zoom_fade_in,
                R.anim.zoom_fade_out
            )
        }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.isHideable = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        adapter = CommunityAdapter { community ->
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            binding.tvName.text = community.name
            binding.tvMembers.text = "241 Members"
            binding.tvDesc.text = community.description
            group_id = community.id

            Glide.with(requireContext())
                .load(community.image)
                .into(binding.imgImage)

            Glide.with(requireContext())
                .load(community.image_logo)
                .into(binding.imgImageLogo)
        }

        binding.btnJoin.setOnClickListener {
            joinCommunity()
        }

        showData()

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.overlay.visibility = View.VISIBLE
                        (requireActivity() as MainActivity).binding.frameNavbar.visibility =
                            View.GONE
                        (requireActivity() as MainActivity).window.navigationBarColor =
                            ContextCompat.getColor(requireContext(), R.color.white)
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.overlay.visibility = View.GONE
                        (requireActivity() as MainActivity).binding.frameNavbar.visibility =
                            View.VISIBLE
                        (requireActivity() as MainActivity).window.navigationBarColor =
                            ContextCompat.getColor(requireContext(), R.color.bg)
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        binding.overlay.visibility = View.VISIBLE
                        (requireActivity() as MainActivity).binding.frameNavbar.visibility =
                            View.GONE
                        (requireActivity() as MainActivity).window.navigationBarColor =
                            ContextCompat.getColor(requireContext(), R.color.white)
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.overlay.visibility = View.GONE
                        (requireActivity() as MainActivity).binding.frameNavbar.visibility =
                            View.VISIBLE
                        (requireActivity() as MainActivity).window.navigationBarColor =
                            ContextCompat.getColor(requireContext(), R.color.bg)
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

            viewModel.result.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is State.Loading -> {
                        binding.pbLoading.visibility = View.VISIBLE
                        binding.rvCommunity.visibility = View.GONE
                    }

                    is State.Success -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.rvCommunity.visibility = View.VISIBLE
                        adapter.setData(state.data)
                        binding.rvCommunity.adapter = adapter
                    }

                    is State.Error -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.rvCommunity.visibility = View.GONE
                        Helper.showErrorToast(requireContext(), state.message)
                    }
                }
            }

            viewModel.postResult.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is State.Loading -> {
                        binding.btnJoin.visibility = View.GONE
                        binding.pbLoading.visibility = View.VISIBLE
                    }

                    is State.Success -> {
                        Helper.showSuccessToast(requireContext(), state.message)
                        binding.btnJoin.visibility = View.VISIBLE
                        binding.pbLoading.visibility = View.GONE

                        val intent = Intent(requireContext(), CommunityActivity::class.java)
                        intent.putExtra("isDetail", true)
                        intent.putExtra("id", group_id)
                        startActivity(intent)
                        requireActivity().overridePendingTransition(
                            R.anim.zoom_fade_in,
                            R.anim.zoom_fade_out
                        )
                    }

                    is State.Error -> {
                        Helper.showErrorToast(requireContext(), state.message)
                        binding.btnJoin.visibility = View.VISIBLE
                        binding.pbLoading.visibility = View.GONE
                    }
                }
            }


        viewModel.result.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.rvCommunity.visibility = View.GONE
                }

                is State.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.rvCommunity.visibility = View.VISIBLE

                    adapter.setData(state.data)
                    binding.rvCommunity.adapter = adapter
                }

                is State.Error -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.rvCommunity.visibility = View.GONE
                    Helper.showErrorToast(requireContext(), state.message)

            }}
        }


        return binding.root
    }

    fun joinCommunity() {
        viewModel.postCommunityMember(requireContext(), group_id)
    }

    private fun showData() {
        viewModel.getCommunity()
    }
}