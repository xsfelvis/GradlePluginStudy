package com.example.xsf.gradleplugintest

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView

class MainActivity : Activity(), OnClickListener {

    lateinit var tvClick: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvClick = findViewById(R.id.tvClick)
        tvClick.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tvClick -> {

            }
        }
    }
}
