package com.tibz.lpsimulation.common.helper

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import java.lang.reflect.Type
import java.security.Permission

class LPPermissionManager {
    companion object {
        fun permissionFragment(
            fragment: Fragment,
            callback: ((Boolean) -> Unit)?
        ): ActivityResultLauncher<String> {
            return fragment
                .registerForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { callback?.invoke(it) }
        }
        fun permissionActivity(
            activity: AppCompatActivity,
            callback: ((Boolean) -> Unit)?
        ): ActivityResultLauncher<String> {
            return activity
                .registerForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { callback?.invoke(it) }
        }
        fun registerForPermissionCamera(
            fragment: Fragment,
            callback: ((Boolean) -> Unit)?
        ): ActivityResultLauncher<String>? {
            if (VERSION.SDK_INT > 23) {
                val cameraPermission = fragment.context?.checkSelfPermission(Manifest.permission.CAMERA)
            }
            return null
        }
        fun permissionCamera(context: AppCompatActivity) {
            if (VERSION.SDK_INT > 23) {
                val cameraPermission = context.checkSelfPermission(Manifest.permission.CAMERA)
                if (cameraPermission != PackageManager.PERMISSION_GRANTED)
                    context.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
//                        if (it.0 == true) {
//                            Toast.makeText(context, "Already Allowed", Toast.LENGTH_SHORT).show()
//                        } else {
//                            Toast.makeText(context, "Denied", Toast.LENGTH_SHORT).show()
//                        }
                    }.launch(arrayOf(Manifest.permission.CAMERA))
                else
                    Toast.makeText(context, "Already Allowed", Toast.LENGTH_SHORT).show()
            }

        }
        fun permissionForStorage(context: AppCompatActivity): Boolean {
            return if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val storagePermission = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                storagePermission == PackageManager.PERMISSION_GRANTED
            } else {
                permissionOldVersion(context)
                true
            }
        }
        private fun permissionOldVersion(context: AppCompatActivity) {
            context.enforceCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE, "This " +
                    "app " +
                    "need your permission to save the image to your phone device please grant " +
                    "permission for write a file to this application.")
        }
    }
}