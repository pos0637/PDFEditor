package com.example.pdfdemo1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }
}