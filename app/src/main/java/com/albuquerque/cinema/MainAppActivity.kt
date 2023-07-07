package com.albuquerque.cinema

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.albuquerque.cinema.databinding.ActivityMainBinding
import com.albuquerque.common.deeplink.CinemaDeeplink
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.messaging.FirebaseMessaging
import java.util.UUID
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainAppActivity : AppCompatActivity() {

    companion object {
        private const val APP_UPDATE_REQUEST_CODE = 426
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var appUpdateManager: AppUpdateManager
    private var currentTab: String? = null
    private val viewModel: MainAppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentTab = intent?.data?.getQueryParameter("tab")
        appUpdateManager = AppUpdateManagerFactory.create(this)
    }

    override fun onResume() {
        super.onResume()

        checkAppUpdate(
            onUpdateNotAvailable = {
                setupMainAppGraph()
            }
        )
        checkFirebaseToken()
        generateDeviceUUID()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == APP_UPDATE_REQUEST_CODE) {
            if (resultCode == RESULT_CANCELED) {
                // Call analytics
            }
        }
    }

    private fun checkAppUpdate(onUpdateNotAvailable: () -> Unit) {
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

    // todo: checar se não é bloqueante
    private fun checkFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("Teste firebase FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            viewModel.generateFcmToken(task.result)
        })
    }

    private fun generateDeviceUUID() {
        viewModel.generateDeviceUuid(uuid = UUID.randomUUID().toString())
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