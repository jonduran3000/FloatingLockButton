package com.jonathanduran.floatinglockbutton.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.jonathanduran.floatinglockbutton.FloatingButtonService;
import com.jonathanduran.floatinglockbutton.R;

/**
 * Created by jonathanduran on 7/26/15.
 */
public final class FloatingButtonFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    private static final String KEY1 = "Enable Administrator";
    private static final String KEY2 = "Display Lock Button";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        findPreference(KEY1).setOnPreferenceChangeListener(this);
        findPreference(KEY2).setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        boolean returnVal = (Boolean) newValue;

        if (preference.getKey().equals(KEY1)) {
            if (returnVal) {
                ((FloatingButtonActivity) getActivity()).startEnableAdminIntent();
            }
        } else if (preference.getKey().equals(KEY2)) {
            if (returnVal) {
                getActivity().startService(new Intent(getActivity(), FloatingButtonService.class));
            } else {
                getActivity().stopService(new Intent(getActivity(), FloatingButtonService.class));
            }
        } else {
            // No operation
        }
        return returnVal;
    }
}
