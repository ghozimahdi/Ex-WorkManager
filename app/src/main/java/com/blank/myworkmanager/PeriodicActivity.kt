package com.blank.myworkmanager

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_periodic.*
import java.util.concurrent.TimeUnit

class PeriodicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_periodic)

        val data = workDataOf("title" to "Periodic Work Request")

        //https://developer.android.com/topic/libraries/architecture/workmanager/how-to/define-work#flexible_run_intervals
        val periodicRequest =
            PeriodicWorkRequestBuilder<WorkPeriodic>(15, TimeUnit.MINUTES, 14, TimeUnit.MINUTES)
                .setInputData(data)
                .build()

        btnStartWorker.setOnClickListener {
            WorkManager.getInstance(this)
                .enqueue(periodicRequest)
        }

        cancelWorker.setOnClickListener {
            WorkManager.getInstance(this)
                .cancelWorkById(periodicRequest.id)
        }

        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(periodicRequest.id)
            .observe(this, Observer {
                val currentWork = it
                tvStatus.text = currentWork?.state.toString()
                when (currentWork?.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        Toast.makeText(this, "Work Sukses", Toast.LENGTH_SHORT).show()
                    }

                    WorkInfo.State.FAILED -> {
                        Toast.makeText(this, "Work Error", Toast.LENGTH_SHORT).show()
                    }

                    WorkInfo.State.CANCELLED -> {
                        Toast.makeText(this, "Work Cancelled", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    class WorkPeriodic(context: Context, parameters: WorkerParameters) :
        Worker(context, parameters) {
        override fun doWork(): Result {
            return try {
                for (i in 0..100) {
                    Log.d("PeriodicWork", "$i")

                    if (isStopped) {
                        break
                    }

                    Thread.sleep(100)
                }

                if (!isStopped) {
                    val data = inputData
                    val title = data.getString("title") ?: "Tidak ada title"

                    showAlarmNotification(
                        applicationContext,
                        title,
                        "Periodic Work Sukses",
                        this.id.variant()
                    )
                }
                return Result.success()
            } catch (e: Exception) {
                return Result.failure()
            }
        }
    }
}