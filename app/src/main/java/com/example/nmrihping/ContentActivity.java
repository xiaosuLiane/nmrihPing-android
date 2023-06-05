package com.example.nmrihping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        TextView textView = findViewById(R.id.ServerName_);
        textView.setText(getIntent().getStringExtra("s_name"));
        TextView textView1 = findViewById(R.id.ServerPlayer_);
        textView1.setText(getIntent().getStringExtra("s_players"));
        TextView textView2 = findViewById(R.id.ServerMap_);
        textView2.setText(getIntent().getStringExtra("s_map"));
        TextView textView3 = findViewById(R.id.ServerIP);
        textView3.setText(getIntent().getStringExtra("s_ip"));


        Button button = findViewById(R.id.Return);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}