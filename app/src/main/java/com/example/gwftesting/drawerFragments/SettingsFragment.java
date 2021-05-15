package com.example.gwftesting.drawerFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.gwftesting.LoginActivity;
import com.example.gwftesting.R;
import com.example.gwftesting.measurements.TokenSaver;


public class SettingsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.settings_fragments_preferences);

        setLogoutPreference();
    }

    private void setLogoutPreference() {
        Preference preference = findPreference("settings_fragment_logout");
        assert preference != null;
        preference.setOnPreferenceClickListener(preference1 -> {
            TokenSaver.setSharedPrefAccess(requireContext(), null);
            TokenSaver.setSharedPrefRefresh(requireContext(), null);
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            return false;
        });
    }


}
