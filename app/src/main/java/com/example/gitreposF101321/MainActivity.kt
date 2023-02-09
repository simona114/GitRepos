package com.example.gitreposF101321

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.gitreposF101321.databinding.ActivityMainBinding
import com.example.gitreposF101321.utils.CheckConnection

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val checkConnection by lazy { CheckConnection(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        //Enable navigation via back arrow on phone
        navController.enableOnBackPressed(true)

        setupActionBarWithNavController(navController)

        checkConnection.observe(this@MainActivity){
            isNetworkAvailable ->
            if (isNetworkAvailable){
                Toast.makeText(this@MainActivity, "isConnected true",  Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this@MainActivity, "isConnected false",  Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}