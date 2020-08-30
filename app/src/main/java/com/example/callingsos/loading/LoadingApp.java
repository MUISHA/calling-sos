package com.example.callingsos.loading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.callingsos.R;
import com.example.callingsos.menu.Menu;

public class LoadingApp extends AppCompatActivity implements View.OnClickListener {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_app);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button = (Button) findViewById(R.id.button_acceder);
            button.setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View view) {
        int index = view.getId();
        switch (index)
        {
            case R.id.button_acceder:
                Intent menu = new Intent(this, Menu.class);
                startActivity(menu);
                break;
            default:
                return;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onResume() {
        if (button != null)
        {
            super.onResume();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}