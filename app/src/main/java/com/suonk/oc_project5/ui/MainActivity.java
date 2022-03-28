package com.suonk.oc_project5.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.suonk.oc_project5.R;
import com.suonk.oc_project5.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}