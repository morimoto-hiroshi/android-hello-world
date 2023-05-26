package com.example.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        // Get the Intent that started this activity and extract the string
        val message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE)

        // Capture the layout's TextView and set the string as its text
        val textView: TextView = findViewById(R.id.textView)
        textView.text = message
    }
}