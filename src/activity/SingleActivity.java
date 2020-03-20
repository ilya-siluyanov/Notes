package com.example.isilu.notes.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.isilu.notes.R;

import static com.example.isilu.notes.logger.LogHelper.log;

public abstract class SingleActivity extends AppCompatActivity {

    abstract Fragment getFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log(this.getClass(), "onCreate");
        setContentView(R.layout.fragment_container);
        super.onCreate(savedInstanceState);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_place);
        if (fragment == null) {
            fragment = getFragment();
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.fragment_place, fragment).
                    commit();

        }

    }
}
