package com.saber.users.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.saber.users.data.Result
import com.saber.users.databinding.FragmentUsersListBinding
import com.saber.users.ui.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UsersListFragment : Fragment() {

    private lateinit var binding: FragmentUsersListBinding

    private val viewModel: UsersViewModel by viewModels()

    private lateinit var adapter: UsersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.users.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let {
                        adapter = UsersAdapter(it, this::onUserItemClicked)
                        binding.list.adapter = adapter
                    }
                }
                Result.Status.ERROR -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
                Result.Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onUserItemClicked(userID: Int) {
        val action =
            UsersListFragmentDirections.actionUsersListFragmentToUserDetailsFragment(userID)
        findNavController().navigate(action)
    }
}