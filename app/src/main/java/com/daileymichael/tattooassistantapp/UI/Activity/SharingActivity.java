package com.daileymichael.tattooassistantapp.UI.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daileymichael.tattooassistantapp.Database.Database;
import com.daileymichael.tattooassistantapp.R;

/**
 *
 */
public class SharingActivity extends AppCompatActivity {
    public static final String SHARED_PREFS_FILENAME = "com.daileymichael.wgutracker.sharingactivity.sharedpreffilename";
    public static final String SHARED_PREF_SHARE = "com.daileymichael.wgutracker.sharingactivity.sharedprefshare";
    SharedPreferences sharedPref;
    private String shareFromSharedPref;
    public Database db;

    /**
     * This method sets the contentView and toolbar & textView & editText action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPref = this.getSharedPreferences(SHARED_PREFS_FILENAME, 0);
        shareFromSharedPref = sharedPref.getString(SHARED_PREF_SHARE, "EMAIL");

        TextView shareLabel = findViewById(R.id.share_label);
        EditText shareTarget = findViewById(R.id.share_target);
        if (shareFromSharedPref.equals("EMAIL")) {
            shareLabel.setText("Share via Email");
            shareTarget.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else {
            shareLabel.setText("Share via SMS");
            shareTarget.setInputType(InputType.TYPE_CLASS_PHONE);
        }

    }

    /**
     * This method saves the entered and selected information when called
     * @param view
     */
    public void onSave(View view) {
        EditText shareTarget = findViewById(R.id.share_target);
        String shareTargetText = shareTarget.getText().toString();

        if (shareFromSharedPref.equals("EMAIL")) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/html");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, shareTargetText);
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        } else {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("sms:" + shareTargetText));
            startActivity(smsIntent);
        }
    }

    /**
     * This method handles action to take place based on the item selected
     * @param view
     */
    public void onSettings(View view) {
        Intent intent = new Intent(this, ReportsActivity.class);
        startActivity(intent);
    }
}
