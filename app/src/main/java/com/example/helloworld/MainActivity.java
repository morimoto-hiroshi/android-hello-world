package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editTextTextPersonName);
        editText.setText("ここにメッセージを入力して'SEND MESSAGE'");
    }

    public static final String EXTRA_MESSAGE = "com.example.helloWorld.MESSAGE";

    public void sendMessage(View view) {
        Intent intent = new Intent(this, MessageActivity.class);
        EditText editText = findViewById(R.id.editTextTextPersonName);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void showCanvas(View view) {
        Intent intent = new Intent(this, CanvasActivity.class);
        startActivity(intent);
    }

    public void showList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}