package com.blank.myworkmanager

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_one_time.*

class OneTimeActivity : AppCompatActivity() {
    private val ONE_TIME_UNIQUE = "ONE_TIME"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_time)

        val data = workDataOf("title" to "One Time Request")

        val workRequest = OneTimeWorkRequestBuilder<WorkOneTime>()
            //.setInitialDelay(10, TimeUnit.MINUTES)
            .setInputData(data)
            .build()

        btnStartWorker.setOnClickListener {
            WorkManager.getInstance(this)
                .enqueue(workRequest)
        }

        cancelWorker.setOnClickListener {
            WorkManager.getInstance(this)
                .cancelWorkById(workRequest.id)
        }

        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, Observer {
                val currentWork = it
                tvStatus.text = currentWork?.state.toString()
                when (currentWork?.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        val data = currentWork.outputData
                        val result = data.getString("result")
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
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

    class WorkOneTime(context: Context, parameters: WorkerParameters) :
        Worker(context, parameters) {
        override fun doWork(): Result {
            return try {
                for (i in 0..100) {
                    Log.d("OneTimeWork", "$i")

                    if (isStopped) {
                        break
                    }

                    Thread.sleep(500)
                }

                if (!isStopped) {
                    val data = inputData
                    val title = data.getString("title") ?: "Tidak ada title"

                    showAlarmNotification(
                        applicationContext,
                        title,
                        "One Time Sukses",
                        this.id.variant()
                    )
                }

                val outData = workDataOf("result" to "Work Sukses")
                return Result.success(outData)
            } catch (e: Exception) {
                return Result.failure()
            }
        }
    }
}