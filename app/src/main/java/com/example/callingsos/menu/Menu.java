package com.example.callingsos.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.example.callingsos.R;
public class Menu extends AppCompatActivity implements View.OnClickListener{
    //NavigationView.OnNavigationItemSelectedListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }

    @Override
    public void onClick(View view) {

    }
}