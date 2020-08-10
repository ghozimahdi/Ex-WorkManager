package com.blank.myworkmanager

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Worker
import androidx.work.WorkerParameters

class ChainingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chaining)
    }

    class WorkChaining(context: Context, parameters: WorkerParameters) :
        Worker(context, parameters) {
        override fun doWork(): Result {
            TODO("Not yet implemented")
        }
    }
}