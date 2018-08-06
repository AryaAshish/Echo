package com.Aryan.echo.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.Aryan.echo.R

class SplashActivity : AppCompatActivity() {

    var permissionsString = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.RECORD_AUDIO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (!hasPermissions(this@SplashActivity, *permissionsString)) {
            //We have to ask for permissions

            ActivityCompat.requestPermissions(this@SplashActivity, permissionsString, 131)
        } else {
            handle()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            131 ->{
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED
                        && grantResults[1]==PackageManager.PERMISSION_GRANTED
                        && grantResults[2]==PackageManager.PERMISSION_GRANTED
                        && grantResults[3]==PackageManager.PERMISSION_GRANTED
                        && grantResults[4]==PackageManager.PERMISSION_GRANTED)
                {
                    handle()                                           // open splash screen if all permissions are granted
                }
                else{
                    Toast.makeText(this@SplashActivity,"Grant Permissions", Toast.LENGTH_SHORT).show()
                    this.finish()
                }
                return
            }
            else -> {
                Toast.makeText(this@SplashActivity,"Something Went Wrong", Toast.LENGTH_SHORT).show()
                this.finish()
                return
            }
        }

    }

    fun hasPermissions(context: Context, vararg permissions: String): Boolean {
        var hasAllPermissions = true
        for (permission in permissions) {
            val res = context.checkCallingOrSelfPermission(permission)
            if (res != PackageManager.PERMISSION_GRANTED) {
                hasAllPermissions = false
            }
        }
        return hasAllPermissions
    }

    fun handle() {
        Handler().postDelayed({
            val startAct = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(startAct)
            this.finish()
        }, 1000)
    }
}
