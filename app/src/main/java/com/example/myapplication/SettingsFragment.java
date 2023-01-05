package com.example.myapplication;

//import libraries
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

//class to manage our preference
public class SettingsFragment extends PreferenceFragmentCompat
{
    //OnCreate method to link our PreferencesXml
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String s)
    {
        //link our preferences to our class to replace it with our fragment in SettingActivity
        addPreferencesFromResource(R.xml.preferences);
    }


}