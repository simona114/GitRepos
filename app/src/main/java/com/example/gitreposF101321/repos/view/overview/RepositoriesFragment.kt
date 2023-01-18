package com.example.gitreposF101321.repos.view.overview

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitreposF101321.databinding.FragmentRepositoriesBinding
import com.example.gitreposF101321.os.MyBroadcastReceiver
import com.example.gitreposF101321.os.MyService
import com.example.gitreposF101321.repos.data.model.RepositoryModel
import com.example.gitreposF101321.repos.viewmodel.RepositoriesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RepositoriesFragment : Fragment(), IRepositoryClickListener {

    private lateinit var binding: FragmentRepositoriesBinding
    private val reposViewModel by sharedViewModel<RepositoriesViewModel>()
    private var repoAdapter: RepositoryAdapter? = null

    lateinit var intentFilter: IntentFilter
    lateinit var myBroadcastReceiver: MyBroadcastReceiver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSendBroadcast?.setOnClickListener {
            Log.d("broadcast", "onViewCreated: send broadcast")

            val intent = Intent(context, MyBroadcastReceiver::class.java)
            intent.action = "com.example.gitreposF101321.myBroadcastMessage"

            //Instruction the OS that even if the receiving application is stopped or killed, send the intent
            intent.flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
            activity?.sendBroadcast(intent)
        }

        intentFilter = IntentFilter("com.example.gitreposF101321.myBroadcastMessage")
        myBroadcastReceiver = MyBroadcastReceiver()


        // Get the value of AIRPLANE MODE
        // from the system settings.
        // This is an Integer. Airplane Mode
        // is ON is its 1 and 0 otherwise.
        // Toast the message (off/on)
        if(Settings.System.getInt(context?.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) == 0){
            Log.d("my_service", "onViewCreated: Airplane mode is Off ")
        } else {
            Log.d("my_service", "onViewCreated: Airplane mode is On ")
            requireActivity().startService(Intent(context, MyService::class.java))
        }

        //todo:monitor the internet connection
        val isConnected = false
        if (isConnected) {
            reposViewModel.requestReposWhenOnline()
        } else {
            reposViewModel.requestReposWhenOffline()
        }

        reposViewModel.liveDataRepos.observe(viewLifecycleOwner) { reposList ->
            repoAdapter = RepositoryAdapter(this)
            repoAdapter?.injectList(reposList as List<RepositoryModel>)

            binding.rvRepositories.apply {
                adapter = repoAdapter
                layoutManager = LinearLayoutManager(activity)
            }
        }
    }


    override fun onResume() {
        super.onResume()

//        activity?.registerReceiver(
//            myBroadcastReceiver,
//            intentFilter,
//        )

        context?.let { LocalBroadcastManager.getInstance(it).registerReceiver(myBroadcastReceiver,intentFilter) }
    }

    override fun onPause() {
        super.onPause()
//       activity?.unregisterReceiver(myBroadcastReceiver)
        context?.let { LocalBroadcastManager.getInstance(it).unregisterReceiver(myBroadcastReceiver) }
    }

    override fun onRepositoryClick(selectedRepository: RepositoryModel) {
        reposViewModel.liveDataSelectedRepo.postValue(selectedRepository)
        val action =
            RepositoriesFragmentDirections
                .actionRepositoriesFragmentToRepositoryDetailsFragment()
        findNavController().navigate(action)
    }
}
