package com.jonathanduran.floatinglockbutton.ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.jonathanduran.floatinglockbutton.R;

/**
 * Created by jonathanduran on 7/26/15.
 */
public final class FloatingButtonFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
