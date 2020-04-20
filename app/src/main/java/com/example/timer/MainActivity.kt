package com.example.timer

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    val handler = Handler()
    var timeValue = 0 // 経過時間

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeText : TextView = findViewById(R.id.timeText)
        val startButton : Button = findViewById(R.id.start)
        val stopButton : Button = findViewById(R.id.stop)
        val resetButton : Button = findViewById(R.id.reset)

//        1秒ごとに処理を実行
        val runnable = object : Runnable{
            override fun run(){
                timeValue++

                timeToText(timeValue)?.let{
                    timeText.text = it // it はtimeToTextの戻り値
                }
                handler.postDelayed(this, 1000)
            }
        }

        // スタートボタンを押したときの処理
        startButton.setOnClickListener{
            // スタートしていない場合のみpostする
            if(!handler.hasCallbacks(runnable)) {
                handler.post(runnable)
            }
        }

        // ストップボタンを押したときの処理
        stopButton.setOnClickListener{
            handler.removeCallbacks(runnable)
        }

        // リセットボタンを押したときの処理
        resetButton.setOnClickListener{
            handler.removeCallbacks(runnable)
            timeValue = 0
            // 時間表示のテキストをセットする
            timeToText()?.let{
                timeText.text = it
            }
        }
    }
}

private fun timeToText(time : Int = 0) : String? {
    return if(time < 0){
        null
    }
    else if(time == 0){
        "00:00:00"
    }else {
        val h = time / 3600
        val m = time % 3600 / 60
        val s = time % 60
        "%1$02d:%2$02d:%3$02d".format(h, m, s)
    }
}