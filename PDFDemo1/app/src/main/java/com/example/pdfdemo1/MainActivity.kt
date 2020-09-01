package com.example.pdfdemo1

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.captain_miao.grantap.CheckPermission
import com.example.captain_miao.grantap.listeners.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*


/**
 * 主界面
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener {
            val fragment = this.supportFragmentManager.fragments[0] as PdfViewerFragment
            fragment.prev()
        }

        button2.setOnClickListener {
            val fragment = this.supportFragmentManager.fragments[0] as PdfViewerFragment
            fragment.next()
        }

        CheckPermission
            .from(this)
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .setRationaleConfirmText("Request permissions")
            .setDeniedMsg("Permissions denied")
            .setPermissionListener(object : PermissionListener {
                override fun permissionGranted() {
                    Toast.makeText(this@MainActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
                    val fragment = this@MainActivity.supportFragmentManager.fragments[0] as PdfViewerFragment
                    fragment.openDocument()
                }

                override fun permissionDenied() {
                    Toast.makeText(this@MainActivity, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            })
            .check()
    }
}