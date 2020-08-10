package com.blank.myworkmanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clicked(v: View) {
        when (v.id) {
            R.id.btnOnetime -> {
                startActivity(Intent(this, OneTimeActivity::class.java))
            }

            R.id.btnPeriodic -> {
                startActivity(Intent(this, PeriodicActivity::class.java))
            }

            R.id.btnChaining -> {
                startActivity(Intent(this, ChainingActivity::class.java))
            }

            R.id.btnConstraints -> {
                startActivity(Intent(this, ConstraintsActivity::class.java))
            }
        }
    }
}