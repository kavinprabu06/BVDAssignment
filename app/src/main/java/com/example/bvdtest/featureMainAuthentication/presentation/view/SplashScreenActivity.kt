package com.example.bvdtest.featureMainAuthentication.presentation.view

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.bvdtest.R
import com.example.bvdtest.featureDashBoard.presentation.view.activities.DashBoardActivity
import com.example.bvdtest.featureMainAuthentication.presentation.viewModel.SplashViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        requestPermissions()
    }

    private fun navigateToActivity() {
        if (splashViewModel.checkValidityToken()) {
            startActivity(Intent(applicationContext, DashBoardActivity::class.java))
        } else {
            startActivity(Intent(applicationContext, MainLoginActivity::class.java))
        }
        finish()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun requestPermissions() {
        // below line is use to request permission in the current activity.
        // this method is use to handle error in runtime permissions
        Dexter.withActivity(this)
            // below line is use to request the number of permissions which are required in our app.
            .withPermissions(
                Manifest.permission.CAMERA,
                // below is the list of permissions
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            // after adding permissions we are calling an with listener method.
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    // this method is called when all permissions are granted
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        // do you work now

                        lifecycleScope.launch {
                            delay(3000)
                            navigateToActivity()
                        }

                    }
                    // check for permanent denial of any permission
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {
                        // permission is denied permanently, we will show user a dialog message.
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }

            }).withErrorListener {
                // we are displaying a toast message for error message.
                Toast.makeText(applicationContext, "Error occurred! ", Toast.LENGTH_SHORT).show()
            }
            // below line is use to run the permissions on same thread and to check the permissions
            .onSameThread().check()
    }

    // below is the shoe setting dialog method
    // which is use to display a dialogue message.
    private fun showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        val builder = AlertDialog.Builder(this@SplashScreenActivity)

        // below line is the title for our alert dialog.
        builder.setTitle("Need Permissions")

        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog, which ->
            // this method is called on click on positive button and on clicking shit button
            // we are redirecting our user from our app to the settings page of our app.
            dialog.cancel()
            // below is the intent from which we are redirecting our user.
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivityForResult(intent, 101)
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            // this method is called when user click on negative button.
            dialog.cancel()
        }
        // below line is used to display our dialog
        builder.show()
    }
}

