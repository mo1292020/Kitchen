package com.example.myapplication;

//import libraries
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

//This is Setting Activity For GeneralAppSetting
public class SettingsActivity extends AppCompatActivity
{

    //OnCreate method to lunch Setting activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Save Instance State for Setting activity
        super.onCreate(savedInstanceState);
        //Set UI preferences activity for Setting activity
        setContentView(R.layout.activity_preferences);
        //SettingToolbar UI
        Toolbar toolbar= findViewById(R.id.SettingToolbar);
        //Set SettingToolbar to our activity
        setSupportActionBar(toolbar);
        //replace our fragment with id container with our preference
        getSupportFragmentManager().beginTransaction().replace(R.id.FregmentContainerSetting, new SettingsFragment()).commit();
        //handel OnBackPress of our activity
        SettingsActivity.this.getOnBackPressedDispatcher().addCallback(this,
                new OnBackPressedCallback(true)
                {
                    @Override
                    public void handleOnBackPressed()
                    {
                        SettingsActivity.this.finish();
                    }
                });
    }

}