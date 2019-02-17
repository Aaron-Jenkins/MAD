package com.example.mapping;

import android.os.Bundle;

public class MyPreferenceActivity extends android.preference.PreferenceActivity {
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
