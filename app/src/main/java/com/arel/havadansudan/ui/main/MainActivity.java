package com.arel.havadansudan.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.arel.havadansudan.R;


public class MainActivity extends AppCompatActivity {

    private AppCompatEditText firstEdT;
    private EditText secondEdT;
    private TextView tvTitle;
    private Button btnUygula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        firstEdT = findViewById(R.id.ac_main_ac_ed_first);
        secondEdT = findViewById(R.id.ac_main_ed_second);
        tvTitle = findViewById(R.id.ac_main_tv_title);
        btnUygula = findViewById(R.id.ac_main_btn_uygula);

        initButtonWorks();
    }

    private void initButtonWorks() {

        btnUygula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTitle.setText(firstEdT.getText());
            }
        });

    }

}
