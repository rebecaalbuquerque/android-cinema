package com.albuquerque.cinema

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.albuquerque.cinema.databinding.ActivityMainBinding
import com.albuquerque.common.deeplink.CinemaDeeplink
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

class MainAppActivity : AppCompatActivity() {

    companion object {
        private const val APP_UPDATE_REQUEST_CODE = 426
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var appUpdateManager: AppUpdateManager
    private var currentTab: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentTab = intent?.data?.getQueryParameter("tab")
        appUpdateManager = AppUpdateManagerFactory.create(this)
    }

    override fun onResume() {
        super.onResume()

        checkUpdate(
            onUpdateNotAvailable = {
                setupMainAppGraph()
            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == APP_UPDATE_REQUEST_CODE) {
            if (resultCode == RESULT_CANCELED) {
                // Call analytics
            }
        }
    }

    private fun checkUpdate(onUpdateNotAvailable: () -> Unit) {
        if (!BuildConfig.DEBUG) {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { updateInfo ->
                if (updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE ||
                    updateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS ||
                    updateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    appUpdateManager.startUpdateFlowForResult(
                        updateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        APP_UPDATE_REQUEST_CODE
                    )
                } else {
                    onUpdateNotAvailable()
                }
            }
        } else {
            onUpdateNotAvailable()
        }
    }

    private fun setupMainAppGraph() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setupWithNavController(
            navHostFragment.navController
        )

        when (currentTab) {
            CinemaDeeplink.TAB_FAVORITES -> navHostFragment.navController.navigate(R.id.favorites_navigation)
            CinemaDeeplink.TAB_REMINDERS -> navHostFragment.navController.navigate(R.id.reminders_navigation)
            else -> Unit
        }
    }
}