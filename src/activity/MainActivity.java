package com.example.isilu.notes.activity;

import android.support.v4.app.Fragment;

import com.example.isilu.notes.fragment.NoteListFragment;

import static com.example.isilu.notes.logger.LogHelper.log;

public class MainActivity extends SingleActivity {
    @Override
    Fragment getFragment() {
        log(this.getClass(), "getFragment");
        return new NoteListFragment();
    }
}
