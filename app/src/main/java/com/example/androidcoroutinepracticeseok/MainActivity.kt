package com.example.androidcoroutinepracticeseok

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    var customProgressDialog: Dialog? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExecute: Button = findViewById<Button>(R.id.btn_execute)
        btnExecute.setOnClickListener {
            showProgressDialog()
            lifecycleScope.launch {
                execute("성공적으로 불러왔습니다.")

            }
        }

    }

    private suspend fun execute(result: String) {
        withContext(Dispatchers.IO) {
            for (i in 1..100000) {
                Log.e("delay : ", "" + i)
            }
            // UI 변화를 주고 싶은거는 항사아 runOnUiThread 사용
            runOnUiThread {
                cancelProgressDialog()
                Toast.makeText(this@MainActivity, result, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showProgressDialog() {
        customProgressDialog = Dialog(this@MainActivity)
        customProgressDialog?.setContentView(R.layout.dialog_custom_progress)
        customProgressDialog?.show()
    }

    private fun cancelProgressDialog() {
        if (customProgressDialog != null) {
            customProgressDialog?.dismiss()
            customProgressDialog = null
        }
    }
}