package com.atiwari.stateinfocenter

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.atiwari.stateinfocenter.databinding.ActivityMainBinding
import com.atiwari.stateinfocenter.network.ApiServiceImpl
import com.atiwari.stateinfocenter.repository.StateRepository
import com.atiwari.stateinfocenter.viewModel.SharedViewModel
import com.atiwari.stateinfocenter.viewModel.SharedViewModelFactory
import com.atiwari.stateinfocenter.views.fragments.StateDetailFragment
import com.atiwari.stateinfocenter.views.fragments.StatesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private var backPressedCallback: OnBackPressedCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewModelFactory = SharedViewModelFactory(StateRepository(ApiServiceImpl()))
        val viewModel = ViewModelProvider(this, viewModelFactory)[SharedViewModel::class.java]

        mainBinding.lifecycleOwner = this

        if (savedInstanceState == null) {
            addFragment(StatesFragment())
        }

        viewModel.navigationLD.observe(this){ navigate ->
            if (navigate) addFragment(StateDetailFragment())
        }

        // Calling state list API
        viewModel.getStates()

        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Check if there are fragments in the back stack
                if (supportFragmentManager.backStackEntryCount > 1) {
                    // If there are, pop the top fragment off the back stack
                    supportFragmentManager.popBackStack()
                } else {
                    // If no fragments are in the back stack, finish the activity
                    finish()
                }
            }
        }

        // Add the callback to the OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, backPressedCallback as OnBackPressedCallback)
    }

    private fun addFragment(fragment: Fragment){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment.tag)
                .addToBackStack(null)
                .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove the callback when the activity is destroyed
        backPressedCallback?.remove()
    }
}