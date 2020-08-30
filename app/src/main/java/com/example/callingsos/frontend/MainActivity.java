package com.example.callingsos.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.callingsos.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editText;
    FloatingActionButton _entrenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _entrenumber = (FloatingActionButton)findViewById(R.id.btn_calling);
        _entrenumber.setOnClickListener(this);
        editText = (EditText)findViewById(R.id.ed_enter);
    }

    @Override
    public void onClick(View view) {
        int index = view.getId();
        switch (index)
        {
            case R.id.btn_calling :
                Intent i = new Intent(new Intent(this, AudioCall.class));
                startActivity(i);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}