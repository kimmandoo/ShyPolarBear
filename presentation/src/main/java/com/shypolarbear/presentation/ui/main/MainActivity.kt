package com.shypolarbear.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseActivity
import com.shypolarbear.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main
) {
    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

    }

    private lateinit var navController: NavController

    override fun initView() {
        initNavBar()
        binding.apply {
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                when(destination.id){
                    R.id.signupFragment, R.id.loginFragment, R.id.quizDailyOXFragment,
                    R.id.quizDailyMultiChoiceFragment, R.id.feedWriteFragment, R.id.feedDetailFragment,
                    R.id.changeMyInfoFragment, R.id.feedDetailNoImageFragment, R.id.feedCommentChangeFragment,
                    R.id.myPageFragment, R.id.splashFragment -> bottomNavigationBar.visibility = View.INVISIBLE
                    else -> bottomNavigationBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initNavBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
//        binding.bottomNavigationBar.itemIconTintList = null

        binding.bottomNavigationBar.setupWithNavController(navController)
        binding.bottomNavigationBar.setOnItemReselectedListener { }

    }
}