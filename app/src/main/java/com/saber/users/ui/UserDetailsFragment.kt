package com.saber.users.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.saber.users.data.Result
import com.saber.users.data.User
import com.saber.users.databinding.FragmentUserDetailsBinding
import com.saber.users.service.CountdownTimerService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding

    private val viewModel: UserDetailsViewModel by viewModels()

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as CountdownTimerService.LocalBinder
            startBackgroundTimer(binder.getService())
        }

        override fun onServiceDisconnected(name: ComponentName) {
        }
    }

    private fun startBackgroundTimer(service: CountdownTimerService) {
        lifecycleScope.launchWhenStarted {
            service.startTimer().collect { timer ->
                binding.tvTimer.text = timer.toString()
                if (timer == 0) findNavController().popBackStack()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        Intent(requireContext(), CountdownTimerService::class.java).also { intent ->
            activity?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        activity?.unbindService(connection)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userDetails.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let {
                        populateUserData(it)
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

    private fun populateUserData(user: User) {
        binding.ivUserAvatar.load(user.avatar)
        binding.tvUserDetails.text = user.getUserDetails()
        binding.tvUserEmail.text = user.email
    }
}