package com.example.permissionsapplication

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val CAMERA_REQUEST_CODE = 101

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = this.getSharedPreferences("pref0", 0)

        val edit: SharedPreferences.Editor =  sharedPref.edit()

        edit.putString("my_app_color", "RED")

        sharedPref.getString("my_app_color", "")

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
//            Toast.makeText(this, "Permission not granted should request", Toast.LENGTH_SHORT).show()

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_SMS
                ),
                CAMERA_REQUEST_CODE
            )
        } else {
            Toast.makeText(this, "Great! Permission already granted!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_REQUEST_CODE) {
            permissions.forEach { permissionString ->
                if (grantResults[permissions.indexOf(permissionString)] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "$permissionString PERMISSION has been granted, what now?",
                        Toast.LENGTH_SHORT
                    ).show()
                } else
                    Toast.makeText(
                        this,
                        "$permissionString PERMISSION has NOT been granted, TRY AGAIN?",
                        Toast.LENGTH_SHORT
                    ).show()
            }

        }

    }
}
